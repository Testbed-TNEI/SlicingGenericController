package org.etsi.osl.example.gc.api.mapper;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition;
import org.etsi.osl.tmf.common.model.EValueType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class ClassTypeResolver {

    private static final Logger logger = LoggerFactory.getLogger("org.etsi.osl.example.gc");
    private static final Map<Class<?>, Map<String, Type>> CLASS_TYPES_MAP_CACHE = new ConcurrentHashMap<>();
    private static final Map<Class<?>, EValueType> CLASS_TO_EVALUE_TYPE = Map.ofEntries(
        Map.entry(byte[].class, EValueType.BINARY),
        Map.entry(Integer.class, EValueType.INTEGER), Map.entry(int.class, EValueType.INTEGER),
        Map.entry(Short.class, EValueType.INTEGER), Map.entry(short.class, EValueType.INTEGER),
        Map.entry(Byte.class, EValueType.SMALLINT), Map.entry(byte.class, EValueType.SMALLINT),
        Map.entry(Long.class, EValueType.lONGINT), Map.entry(long.class, EValueType.lONGINT),
        Map.entry(Float.class, EValueType.FLOAT), Map.entry(float.class, EValueType.FLOAT),
        Map.entry(Double.class, EValueType.FLOAT), Map.entry(double.class, EValueType.FLOAT),
        Map.entry(BigDecimal.class, EValueType.FLOAT), Map.entry(String.class, EValueType.TEXT),
        Map.entry(Boolean.class, EValueType.BOOLEAN), Map.entry(boolean.class, EValueType.BOOLEAN),
        Map.entry(Map.class, EValueType.MAP), Map.entry(Set.class, EValueType.SET),
        Map.entry(List.class, EValueType.ARRAY), Map.entry(Date.class, EValueType.TIMESTAMP),
        Map.entry(LocalDateTime.class, EValueType.TIMESTAMP), Map.entry(Instant.class, EValueType.TIMESTAMP),
        Map.entry(ZonedDateTime.class, EValueType.TIMESTAMP), Map.entry(OffsetDateTime.class, EValueType.TIMESTAMP)
    );
    private static final ObjectMapper mapper = new ObjectMapper()
        .setSerializationInclusion(JsonInclude.Include.NON_ABSENT)
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    /**
     * Gets class field types map (using dot notation for nested parameters)
     * <p>
     * Examples:
     * N6protection ({ "name": <string>, "type: <string> }) -> n6protection.name: String, n6protection.type: String
     */
    public static Map<String, Type> getClassFieldTypesMap(Class<?> clazz) {
        return CLASS_TYPES_MAP_CACHE.computeIfAbsent(clazz, c -> {
            Map<String, Type> result = new LinkedHashMap<>();
            collectFields(c, "", result, new HashSet<>());
            return Collections.unmodifiableMap(result);
        });
    }

    /**
     * Resolves the EValueType for a given field's generic Type.
     * Handles Optional<X>, List<X>, and plain types.
     * <p>
     * Examples:
     * Optional<Integer>        -> INTEGER
     * Optional<List<String>>   -> ARRAY
     * List<String>              -> ARRAY
     * String                    -> TEXT
     */
    public static EValueType resolveEValueType(Type type) {
        Class<?> raw = getRawClass(type);

        EValueType mapped = CLASS_TO_EVALUE_TYPE.get(raw);
        if (mapped != null) return mapped;

        if (Collection.class.isAssignableFrom(raw)) {
            return Set.class.isAssignableFrom(raw) ? EValueType.SET : EValueType.ARRAY;
        }
        if (Map.class.isAssignableFrom(raw)) return EValueType.MAP;

        if (raw == Optional.class) {
            Type inner = getFirstTypeArg(type);
            if (inner != null) return resolveEValueType(inner);
        }

        if (raw.isEnum()) return EValueType.ENUM;
        return EValueType.OBJECT;
    }

    private static void collectFields(
        Class<?> clazz, String prefix,
        Map<String, Type> result, Set<Class<?>> visited
    ) {
        if (clazz == null || clazz == Object.class) return;
        if (!visited.add(clazz)) return;

        // Use Jackson's BeanDescription so that @JsonProperty on getters, setters, or fields
        // is all honoured uniformly — raw reflection only sees field-level annotations.
        BeanDescription beanDesc = mapper.getSerializationConfig().introspect(
            mapper.getTypeFactory().constructType(clazz)
        );

        for (BeanPropertyDefinition propDef : beanDesc.findProperties()) {
            String fullName = prefix.isEmpty() ? propDef.getName() : prefix + "." + propDef.getName();

            Type genericType = resolveGenericType(propDef);
            if (genericType == null) continue;

            result.put(fullName, genericType);

            Class<?> coreClass = unwrapToClass(genericType);
            if (shouldRecurse(coreClass)) {
                collectFields(coreClass, fullName, result, new HashSet<>(visited));
            }
        }
    }

    private static Type resolveGenericType(BeanPropertyDefinition propDef) {
        if (propDef.getField() != null)
            return propDef.getField().getAnnotated().getGenericType();
        if (propDef.getGetter() != null)
            return propDef.getGetter().getAnnotated().getGenericReturnType();
        return null;
    }

    /**
     * Unwraps nested generics to find the core class:
     * Optional<List<SliceN6ProtectionRule>>  ->  SliceN6ProtectionRule
     * List<String>                           ->  String
     * Optional<Integer>                      ->  Integer
     * String                                 ->  String
     */
    private static Class<?> unwrapToClass(Type type) {
        if (type instanceof Class<?>) {
            return (Class<?>) type;
        }

        if (type instanceof ParameterizedType pt) {
            // Keep unwrapping the first type argument (handles nested generics)
            Class<?> raw = (Class<?>) pt.getRawType();
            Type[] args = pt.getActualTypeArguments();

            if (Map.class.isAssignableFrom(raw) && args.length == 2) {
                // Note: for Map<K,V> you would want to unpack values maybe
                // adjust as needed
                return unwrapToClass(args[1]);
            }
            if (args.length > 0) {
                return unwrapToClass(args[0]);
            }
            return unwrapToClass(pt.getRawType());
        }

        if (type instanceof GenericArrayType gat) {
            return unwrapToClass(gat.getGenericComponentType());
        }
        return Object.class;
    }

    /**
     * Only recurse into your own domain classes.
     * Skip primitives, enums, JDK types, etc.
     */
    private static boolean shouldRecurse(Class<?> clazz) {
        if (clazz == null) return false;
        if (clazz.isPrimitive()) return false;
        if (clazz.isEnum()) return false;
        if (clazz.isArray()) return false;
        String pkg = clazz.getPackageName();
        return pkg.startsWith("com.itav.slicemanager.models");
    }

    /**
     * Gets the raw Class<?> from a Type (strips generic params).
     * List<String>                    -> List.class
     * Optional<Integer>               -> Optional.class
     * String                          -> String.class
     */
    private static Class<?> getRawClass(Type type) {
        if (type instanceof Class<?> c) return c;
        if (type instanceof ParameterizedType pt) return getRawClass(pt.getRawType());
        if (type instanceof GenericArrayType) return Object[].class;
        return Object.class;
    }

    /**
     * Returns the first type argument of a generic type, or null if none.
     * Optional<List<String>>  -> List<String>  (still a ParameterizedType)
     * List<String>             -> String
     */
    private static Type getFirstTypeArg(Type type) {
        if (type instanceof ParameterizedType pt) {
            Type[] args = pt.getActualTypeArguments();
            return args.length > 0 ? args[0] : null;
        }
        return null;
    }
}
