package org.etsi.osl.example.gc.api.mapper;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.etsi.osl.tmf.common.model.Any;
import org.etsi.osl.tmf.common.model.EValueType;
import org.etsi.osl.tmf.rcm634.model.ResourceSpecificationCharacteristic;
import org.etsi.osl.tmf.rcm634.model.ResourceSpecificationCharacteristicValue;
import org.etsi.osl.tmf.rcm634.model.ResourceSpecificationCreate;
import org.etsi.osl.tmf.ri639.model.Characteristic;
import org.etsi.osl.tmf.ri639.model.ResourceUpdate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.*;

public class ObjectToOslCharacteristicsMapper {

    private static final Logger logger = LoggerFactory.getLogger("org.etsi.osl.example.gc");
    private static final ObjectMapper mapper = new ObjectMapper()
        .setSerializationInclusion(JsonInclude.Include.NON_ABSENT)
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    public static <T> void addToResourceSpecCharacteristics(
        ResourceSpecificationCreate rsc, T object
    ) throws IllegalArgumentException {
        Map<String, Type> fieldTypeMap = ClassTypeResolver.getClassFieldTypesMap(object.getClass());

        JsonNode jsonNode = mapper.valueToTree(object);
        jsonNode.fields().forEachRemaining(entry -> {
            String name = entry.getKey();
            JsonNode valueNode = entry.getValue();
            if (valueNode.isNull() || valueNode.isMissingNode()) return;

            Type fieldType = fieldTypeMap.getOrDefault(name, Object.class);
            EValueType eValueType = ClassTypeResolver.resolveEValueType(fieldType);

//            logger.info("unwrap field {} type {} evalueType {}", name, fieldType, eValueType);

            ResourceSpecificationCharacteristic ci = addResourceSpecCharacteristicItemShort(
                name,
                null,
                null,
                eValueType,
                "Parameter: " + name,
                "",
                true
            );
            Object actualValue = mapper.convertValue(valueNode, mapper.constructType(fieldType));
            addResourceSpecValues(ci, actualValue, eValueType);
            rsc.addResourceSpecificationCharacteristicItem(ci);
        });
    }

    public static <T> void addToResourceCharacteristics(T object, ResourceUpdate rsu) {
        Map<String, Type> fieldTypeMap = ClassTypeResolver.getClassFieldTypesMap(object.getClass());
        mapper.valueToTree(object).fields().forEachRemaining(entry -> {
            String name = entry.getKey();
            JsonNode node = entry.getValue();
            if (node.isNull() || node.isMissingNode()) return;

            Type fieldType = fieldTypeMap.getOrDefault(name, Object.class);
            EValueType eType = ClassTypeResolver.resolveEValueType(fieldType);
            // Scalars → asText(); arrays/objects → toString() preserves the JSON structure
            String raw = node.isValueNode() ? node.asText() : node.toString();

            Characteristic c = new Characteristic();
            c.setName(name);
            c.setValueType(eType.getValue());
            // ENUM: alias carries the string name so fromResourceCharacteristicsListToClassInstance
            //       can reconstruct the enum without knowing its ordinal.
            c.setValue(eType == EValueType.ENUM ? new Any(raw, raw) : new Any(raw, null));
            rsu.addResourceCharacteristicItem(c);
        });
    }

    public static <T> T fromResourceCharacteristicsListToClassInstance(
        List<Characteristic> characteristics, Class<T> targetClass
    ) throws IllegalArgumentException {
        Map<String, Object> map = new LinkedHashMap<>();
        for (Characteristic c : characteristics) {
            Any v = c.getValue();
            String raw = v.getValue();
            Object parsed;
            if (Objects.equals(c.getValueType(), EValueType.ENUM.getValue())) {
                parsed = v.getAlias(); // alias holds the enum string name
            } else if (raw != null && raw.startsWith("[") && raw.endsWith("]")) {
                try {
                    parsed = mapper.readValue(raw, List.class);
                } catch (Exception e) {
                    logger.warn("Could not parse array characteristic {}, using raw string", c.getName());
                    parsed = raw;
                }
            } else {
                parsed = raw;
            }
            map.put(c.getName(), parsed);
        }
        return mapper.convertValue(map, targetClass);
    }

    public static ResourceSpecificationCharacteristic addResourceSpecCharacteristicItemShort(
        String name,
        Object defaultValue,
        String defaultAlias,
        EValueType valueType,
        String description,
        String regex,
        Boolean isConfigurable
    ) {
        ResourceSpecificationCharacteristic ci = new ResourceSpecificationCharacteristic();
        ci
            .name(name)
            .description(description)
            .valueType(valueType.getValue());
        if (defaultValue != null) {
            ResourceSpecificationCharacteristicValue val = new ResourceSpecificationCharacteristicValue();
            val.setValue(getAnyValue(defaultValue, valueType, defaultAlias));
            val.isDefault(true);
            ci.getResourceSpecCharacteristicValue().add(val);
        }
        ci.configurable(isConfigurable);
        ci.regex(regex);
        return ci;
    }

    private static Any getAnyValue(Object value, EValueType valueType, String alias) {
        if (value == null) {
            return new Any(null, alias);
        }
        String raw = value.toString();
        return switch (valueType) {
            case EValueType.lONGINT -> new Any(Long.parseLong(raw), alias);
            case EValueType.INTEGER -> new Any(Integer.parseInt(raw), alias);
            default -> new Any(raw, alias);
        };
    }

    /**
     * Dispatches to the appropriate handler based on the OSL EValueType of the field.
     */
    private static void addResourceSpecValues(ResourceSpecificationCharacteristic ci, Object value, EValueType eValueType) {
        switch (eValueType) {
            case SET -> addSetSpecValues(ci, value);
            case ARRAY -> addArraySpecValues(ci, value);
            case MAP -> addMapSpecValues(ci, value);
            case ENUM -> addEnumSpecValues(ci, value);
            case OBJECT -> addObjectSpecValues(ci, value);
            default -> addScalarSpecValue(ci, value, eValueType);
        }
    }

    /**
     * SET: one value per element, no index in alias (unordered).
     */
    private static void addSetSpecValues(ResourceSpecificationCharacteristic ci, Object value) {
        if (!(value instanceof Collection<?> col)) {
            addScalarSpecValue(ci, value, EValueType.SET);
            return;
        }
        for (Object elem : col) {
            if (elem == null) continue;
            addResourceSpecValue(ci, valueToString(elem), null, true, ClassTypeResolver.resolveEValueType(elem.getClass()));
        }
    }

    /**
     * ARRAY: one value per element, integer index as alias (i.fieldName for object elements).
     */
    private static void addArraySpecValues(ResourceSpecificationCharacteristic ci, Object value) {
        if (!(value instanceof Collection<?> col)) {
            addScalarSpecValue(ci, value, EValueType.ARRAY);
            return;
        }
        int i = 0;
        for (Object elem : col) {
            if (elem == null) {
                i++;
                continue;
            }
            EValueType elemType = ClassTypeResolver.resolveEValueType(elem.getClass());
            if (elemType == EValueType.OBJECT) {
                expandObjectFields(ci, elem, i + ".");
            } else {
                addResourceSpecValue(ci, valueToString(elem), String.valueOf(i), true, elemType);
            }
            i++;
        }
    }

    /**
     * MAP: map key stored in alias, map value stored in value.
     */
    private static void addMapSpecValues(ResourceSpecificationCharacteristic ci, Object value) {
        if (!(value instanceof Map<?, ?> map)) {
            addScalarSpecValue(ci, value, EValueType.MAP);
            return;
        }
        for (Map.Entry<?, ?> entry : map.entrySet()) {
            Object val = entry.getValue();
            if (val == null) continue;
            addResourceSpecValue(ci, valueToString(val), String.valueOf(entry.getKey()), true, ClassTypeResolver.resolveEValueType(val.getClass()));
        }
    }

    /**
     * ENUM: registers every constant (value=ordinal, alias=name).
     * The current default value is marked isDefault=true; cardinality spans all constants.
     */
    private static void addEnumSpecValues(ResourceSpecificationCharacteristic ci, Object value) {
        if (!(value instanceof Enum<?> enumValue)) return;
        Object[] constants = enumValue.getClass().getEnumConstants();
        for (int i = 0; i < constants.length; i++) {
            addResourceSpecValue(ci, String.valueOf(i), constants[i].toString(), constants[i].equals(enumValue), EValueType.TEXT);
        }
        ci.minCardinality(0);
        ci.maxCardinality(constants.length - 1);
    }

    /**
     * OBJECT: expands each declared field as a separate value (alias=fieldName).
     */
    private static void addObjectSpecValues(ResourceSpecificationCharacteristic ci, Object value) {
        if (value == null) return;
        expandObjectFields(ci, value, "");
    }

    /**
     * Adds each serialisable property of {@code obj} as a characteristic value under
     * {@code prefix + jsonPropertyName}. Uses Jackson for JSON names (honours {@code @JsonProperty}
     * on getters and fields) and {@link ClassTypeResolver#getClassFieldTypesMap} for proper types.
     */
    private static void expandObjectFields(ResourceSpecificationCharacteristic ci, Object obj, String prefix) {
        if (obj == null) return;
        String pkg = obj.getClass().getPackageName();
        if (pkg.startsWith("java.") || pkg.startsWith("javax.") || pkg.startsWith("sun.")) {
            addScalarSpecValue(ci, obj, ClassTypeResolver.resolveEValueType(obj.getClass()));
            return;
        }
        Map<String, Type> typeMap = ClassTypeResolver.getClassFieldTypesMap(obj.getClass());
        JsonNode node = mapper.valueToTree(obj);
        node.fields().forEachRemaining(entry -> {
            JsonNode fieldNode = entry.getValue();
            if (fieldNode.isNull() || fieldNode.isMissingNode()) return;
            String jsonName = entry.getKey();
            Type fieldType = typeMap.getOrDefault(jsonName, Object.class);
            Object fieldValue = mapper.convertValue(fieldNode, mapper.constructType(fieldType));
            if (fieldValue == null) return;
            EValueType fieldEType = ClassTypeResolver.resolveEValueType(fieldType);
            addResourceSpecValue(ci, valueToString(fieldValue), prefix + jsonName, true, fieldEType);
        });
    }

    /**
     * Scalar fallback (BOOLEAN, INTEGER, FLOAT, TEXT, …): single value, no alias.
     */
    private static void addScalarSpecValue(ResourceSpecificationCharacteristic ci, Object value, EValueType valueType) {
        if (value == null) return;
        addResourceSpecValue(ci, valueToString(value), null, true, valueType);
    }

    private static void addResourceSpecValue(
        ResourceSpecificationCharacteristic ci,
        String value,
        String alias,
        boolean isDefault,
        EValueType type
    ) {
        addResourceSpecValue(ci, new Any(value, alias), isDefault, type);
    }

    private static void addResourceSpecValue(
        ResourceSpecificationCharacteristic ci,
        Any value,
        boolean isDefault,
        EValueType type
    ) {
        ResourceSpecificationCharacteristicValue val = new ResourceSpecificationCharacteristicValue();
        val.setValue(value);
        val.valueType(type.getValue());
        val.isDefault(isDefault);
        ci.addResourceSpecCharacteristicValueItem(val);
    }

    /**
     * Converts a value to its string representation, avoiding scientific notation for BigDecimal.
     */
    private static String valueToString(Object value) {
        if (value instanceof BigDecimal bd) return bd.toPlainString();
        return value.toString();
    }

}