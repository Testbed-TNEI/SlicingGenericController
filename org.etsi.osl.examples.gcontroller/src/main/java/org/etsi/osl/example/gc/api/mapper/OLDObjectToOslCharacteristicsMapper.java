//package org.etsi.osl.example.gc.api.mapper;
//
//import com.fasterxml.jackson.annotation.JsonInclude;
//import com.fasterxml.jackson.databind.DeserializationFeature;
//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.etsi.osl.tmf.common.model.Any;
//import org.etsi.osl.tmf.common.model.EValueType;
//import org.etsi.osl.tmf.rcm634.model.ResourceSpecificationCharacteristic;
//import org.etsi.osl.tmf.rcm634.model.ResourceSpecificationCharacteristicValue;
//import org.etsi.osl.tmf.rcm634.model.ResourceSpecificationCreate;
//import org.etsi.osl.tmf.ri639.model.Characteristic;
//import org.etsi.osl.tmf.ri639.model.ResourceUpdate;
//import org.openapitools.jackson.nullable.JsonNullable;
//import org.openapitools.jackson.nullable.JsonNullableModule;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.lang.reflect.Field;
//import java.lang.reflect.Type;
//import java.util.*;
//import java.util.stream.Collectors;
//
//public class OLDObjectToOslCharacteristicsMapper {
//
//    private static final Logger logger = LoggerFactory.getLogger("org.etsi.osl.example.gc");
//    private static final ObjectMapper mapper = new ObjectMapper()
//        .setSerializationInclusion(JsonInclude.Include.NON_ABSENT)
//        .registerModule(new JsonNullableModule())
//        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//
//    public static <T> void addToResourceSpecCharacteristics(
//        ResourceSpecificationCreate rsc, T object
//    ) throws IllegalArgumentException {
//        Map<String, Type> fieldTypeMap = ClassTypeResolver.getClassFieldTypesMap(object.getClass());
//
//        JsonNode jsonNode = mapper.valueToTree(object);
//        jsonNode.fields().forEachRemaining(entry -> {
//            String name = entry.getKey();
//            JsonNode valueNode = entry.getValue();
//            if (valueNode.isNull() || valueNode.isMissingNode()) return;
//
//            Type fieldType = fieldTypeMap.getOrDefault(name, Object.class);
//            EValueType eValueType = ClassTypeResolver.resolveEValueType(fieldType);
//
//            logger.info("unwrap field {} type {} evalueType {}", name, fieldType, eValueType);
//
//            ResourceSpecificationCharacteristic ci = addResourceSpecCharacteristicItemShort(
//                name,
//                null,
//                null,
//                eValueType,
//                "Parameter: " + name,
//                "",
//                true
//            );
//            Object actualValue = unwrapJsonNullable(mapper.convertValue(valueNode, mapper.constructType(fieldType)));
//            addResourceSpecValues(ci, actualValue, eValueType);
//            rsc.addResourceSpecificationCharacteristicItem(ci);
//        });
//    }
//
//    public static <T> void addToResourceCharacteristics(T object, ResourceUpdate rsu) {
//        Map<String, Type> fieldTypeMap = ClassTypeResolver.getClassFieldTypesMap(object.getClass());
//
//        JsonNode jsonNode = mapper.valueToTree(object);
//        jsonNode.fields().forEachRemaining(entry -> {
//            String name = entry.getKey();
//            JsonNode valueNode = entry.getValue();
//            if (valueNode.isNull() || valueNode.isMissingNode()) return;
//
//            Type fieldType = fieldTypeMap.getOrDefault(name, Object.class);
//            Object actualValue = mapper.convertValue(valueNode, mapper.constructType(fieldType));
//            EValueType type = ClassTypeResolver.resolveEValueType(fieldType);
//
//            logger.info("unwrap field {} value {}", name, actualValue);
//            logger.info("set characteristic {} value {}", name, actualValue);
//
//            Characteristic characteristic = new Characteristic();
//            characteristic.setName(name);
//            characteristic.setType(type.getValue());
//
//            if (type == EValueType.ARRAY) {
//                // Serialise each element as an Any{value, alias=fieldName} so that
//                // fromResourceCharacteristicsListToClassInstance can reconstruct the object.
//                Collection<?> collection = actualValue instanceof Collection<?> c ? c : List.of(actualValue);
//                List<Any> anyList = new ArrayList<>();
//                for (Object element : collection) {
//                    JsonNode elementNode = mapper.valueToTree(element);
//                    if (elementNode.isObject()) {
//                        elementNode.fields().forEachRemaining(f ->
//                            anyList.add(new Any(f.getValue().asText(), f.getKey())));
//                    } else if (!elementNode.isNull()) {
//                        anyList.add(new Any(elementNode.asText(), null));
//                    }
//                }
//                try {
//                    characteristic.setValue(new Any(mapper.writeValueAsString(anyList), null));
//                    rsu.addResourceCharacteristicItem(characteristic);
//                } catch (Exception e) {
//                    logger.error("Error serialising array field {} to JSON", name, e);
//                }
//            } else {
//                rsu.addResourceCharacteristicItem(characteristic);
//            }
//        });
//    }
//
//    /**
//     * @throws IllegalArgumentException if the characteristics cannot be deserialized
//     * OSL doesn't enforce validation according to the characteristic defined types
//     * TODO: maybe we should enforce some validation beforehand instead of just expecting the exception here
//     */
//    public static <T> T fromResourceCharacteristicsListToClassInstance(
//        List<Characteristic> characteristics, Class<T> targetClass
//    ) throws IllegalArgumentException {
//        Map<String, Object> map = characteristics
//            .stream()
//            // .filter(c -> !Objects.equals(c.getName(), "n6protection"))
//            .collect(Collectors.toMap(
//                Characteristic::getName,
//                c -> {
//                    Any value = c.getValue();
//                    String stringValue = value.getValue();
//
//                    if (Objects.equals(c.getValueType(), EValueType.ENUM.getValue())) {
//                        // IN OSL enums alias correspond to the actual value, while value is the ordinal
//                        return value.getAlias();
//                    } else if (stringValue.startsWith("[") && stringValue.endsWith("]")) {
//                        // Parse as list of Anys using ObjectMapper
//                        try {
//                            List<Any> anyList = mapper.readValue(
//                                stringValue,
//                                mapper.getTypeFactory().constructCollectionType(List.class, Any.class)
//                            );
//
//                            if (anyList.isEmpty()) {
//                                return new ArrayList<>();
//                            }
//
//                            // Peek at the first element to check for alias
//                            // Is there an alias -> Map
//                            // No alias -> List
//                            Any first = anyList.get(0);
//
//                            if (first.getAlias() != null && !first.getAlias().isEmpty() && !first.getAlias().equals("null")) {
//                                // TODO: can we check this with getValueType instead (MAP vs ARRAY)
//                                // Has alias - create map with {alias1:value1, alias2:value2, ...}
//                                logger.info("Parsing list with alias for characteristic {}", c.getName());
//                                // Convert each Any to a map and collect as a list of maps
//                                // This allows ObjectMapper to deserialize to List<Object> properly
//                                List<Map<String, Object>> resultList = new ArrayList<>();
//                                Map<String, Object> itemMap = new HashMap<>();
//                                for (Any any : anyList) {
//                                    itemMap.put(any.getAlias(), any.getValue());
//                                }
//                                resultList.add(itemMap);
//                                return resultList;
//
//                            } else {
//                                // No alias - create list of values
//                                logger.info("Parsing list without alias for characteristic {}", c.getName());
//                                return anyList.stream()
//                                    .map(Any::getValue)
//                                    .collect(Collectors.toList());
//                            }
//                        } catch (Exception e) {
//                            logger.error("Error parsing list of Anys", e);
//                            return stringValue;
//                        }
//                    } else {
//                        // Not a list format - return simple string value
//                        return stringValue;
//                    }
//                }
//            ));
//
//        map.forEach((k, v) -> logger.info("Mapped characteristic {} to value {}", k, v));
//        return mapper.convertValue(map, targetClass);
//    }
//
//    public static ResourceSpecificationCharacteristic addResourceSpecCharacteristicItemShort(
//        String name,
//        Object defaultValue,
//        String defaultAlias,
//        EValueType valueType,
//        String description,
//        String regex,
//        Boolean isConfigurable
//    ) {
//        ResourceSpecificationCharacteristic ci = new ResourceSpecificationCharacteristic();
//        ci
//            .name( name )
//            .description(description)
//            .valueType(valueType.getValue());
//        if (defaultValue!=null) {
//            ResourceSpecificationCharacteristicValue val = new ResourceSpecificationCharacteristicValue();
//            val.setValue(getAnyValue(defaultValue, valueType, defaultAlias));
//            val.isDefault(true);
//            ci.getResourceSpecCharacteristicValue().add( val );
//        }
//        ci.configurable( isConfigurable );
//        ci.regex(regex);
//        return ci;
//    }
//
//    /**
//     * Recursively processes values, handling nested arrays and objects
//     */
//    private static void processValue(
//        BaseRootNamedEntity baseRootNamedEntity,
//        Object value,
//        EValueType valueType,
//        String alias,
//        ResourceUpdate rsu
//    ) {
/// /        if (List.of(EValueType.ARRAY, EValueType.SET).contains(valueType)) {
/// /            // Handle collections by iterating and recursively processing each element
/// /            if (value instanceof Collection<?> collection) {
/// /                for (Object element : collection) {
/// /                    if (element != null) {
/// /                        EValueType elementType = ClassTypeResolver.resolveEValueType(valueType);
/// /
/// /                        if (rsu != null && baseRootNamedEntity instanceof Characteristic c) {
/// /                            // add array elements
/// /                            logger.info("creating new characteristic for array element {} of type {}", element, elementType);
/// /                            Characteristic newC = new Characteristic();
/// /                            newC.setName(c.getName());
/// /                            newC.setType(c.getType());
/// /                            newC.setValueType(elementType.getValue());
/// /                            setValue(newC, element, elementType, alias, true);
/// /                        } else {
/// /                            processValue(baseRootNamedEntity, element, elementType, alias, rsu);
/// /                        }
/// /                    }
/// /                }
/// /            } else {
/// /                // Not actually a collection, treat as single value
/// /                setValue(baseRootNamedEntity, value, valueType, alias, true);
/// /            }
/// /        } else if (valueType == EValueType.OBJECT) {
/// /            // Handle complex objects by unpacking their fields recursively
/// /            if (value == null) {
/// /                setValue(baseRootNamedEntity, null, valueType, alias, true);
/// /                return;
/// /            }
/// /            try {
/// /                Field[] fields = value.getClass().getDeclaredFields();
/// /                for (Field field : fields) {
/// /                    field.setAccessible(true);
/// /                    String fieldName = field.getName();
/// /                    Object fieldValue = field.get(value);
/// /
/// /                    // Unwrap JsonNullable if present
/// /                    if (fieldValue instanceof JsonNullable<?> jsonNullable) {
/// /                        if (jsonNullable.isPresent()) {
/// /                            fieldValue = jsonNullable.get();
/// /                        } else {
/// /                            continue; // Skip undefined fields
/// /                        }
/// /                    }
/// /
/// /                    if (fieldValue != null) {
/// /                        EValueType fieldType = ClassTypeResolver.resolveEValueType(fieldValue);
/// /                        // Build nested alias path (e.g., "parent.child.grandchild")
/// /                        String nestedAlias = alias != null ? alias + "." + fieldName : fieldName;
/// /                        // Recursive call to handle nested structures
/// /                        processValue(baseRootNamedEntity, fieldValue, fieldType, nestedAlias, rsu);
/// /                    }
/// /                }
/// /            } catch (IllegalAccessException e) {
/// /                logger.error("Error unpacking object fields", e);
/// /                setValue(baseRootNamedEntity, value, valueType, alias, true);
/// /            }
/// /        } else if (valueType == EValueType.ENUM) {
/// /
/// /            if (value instanceof Enum<?> enumValue) {
/// /                if (baseRootNamedEntity instanceof ResourceSpecificationCharacteristic ci){
/// /                    // Add all other possible enum values as non-default
/// /                    Class<?> enumClass = enumValue.getClass();
/// /                    Object[] enumConstants = enumClass.getEnumConstants();
/// /                    for (int i = 0; i < enumConstants.length; i++) {
/// /                        Object constant = enumConstants[i];
/// /                        setValue(baseRootNamedEntity, i, EValueType.TEXT, constant.toString(), constant.equals(value));
/// /                    }
/// /                    ci.minCardinality(0);
/// /                    ci.maxCardinality(enumConstants.length-1);
/// /                } else if (baseRootNamedEntity instanceof Characteristic c) {
/// /                    setValue(baseRootNamedEntity, enumValue.ordinal(), EValueType.TEXT, alias, true);
/// /                }
/// /
/// /
/// /            }
/// /        } else if (valueType == EValueType.MAP) {
/// /            // let's see if this works
/// /            if (value instanceof Map<?, ?> map) {
/// /                for (Map.Entry<?, ?> entry : map.entrySet()) {
/// /                    String key = String.valueOf(entry.getKey());
/// /                    Object val = entry.getValue();
/// /                    EValueType valType = getEValueType(val);
/// /                    String nestedAlias = alias != null ? alias + "." + key : key;
/// /                    processValue(baseRootNamedEntity, val, valType, nestedAlias, rsu);
/// /                }
/// /            } else {
/// /                setValue(baseRootNamedEntity, value, valueType, alias, true);
/// /            }
/// /        }
/// /
/// /        else {
/// /            // Base case: simple value (INTEGER, STRING, BOOLEAN, etc.)
/// /            setValue(baseRootNamedEntity, value, valueType, alias, true);
/// /        }
//    }
//
//    private static void setValue(
//        BaseRootNamedEntity baseRootNamedEntity,
//        Object defaultValue,
//        EValueType valueType,
//        String alias,
//        boolean isDefault
//    ) {
/// /        Any value = getTypedValue(defaultValue, valueType, alias);
/// /        if (baseRootNamedEntity instanceof ResourceSpecificationCharacteristic ci) {
/// /            logger.info("Setting characteristic value (ci): {} (type: {}, alias: {}, default: {})", value.getValue(), valueType, alias, isDefault);
/// /            addResourceSpecValue(ci, value, isDefault, valueType);
/// /        } else if (baseRootNamedEntity instanceof Characteristic c) {
/// /            logger.info("Setting characteristic value (c): {} (type: {}, alias: {})", value.getValue(), valueType, alias);
/// /            // don't know how it is the behavior for enums as I can't set the following in characteristic
/// /            // cardinality
/// /            // default
/// /            c.setValue( value );
/// /            c.setValueType( valueType.getValue() );
/// /        }
//
//    }
//
//    private static Any getAnyValue(Object value, EValueType valueType, String alias) {
//        if (value == null) {
//            return new Any(null, alias);
//        }
//        String raw = value.toString();
//        return switch (valueType) {
//            case EValueType.lONGINT -> new Any(Long.parseLong(raw), alias);
//            case EValueType.INTEGER -> new Any(Integer.parseInt(raw), alias);
//            default -> new Any(raw, alias);
//        };
//    }
//
//    /**
//     * Dispatches to the appropriate handler based on the OSL EValueType of the field.
//     */
//    private static void addResourceSpecValues(ResourceSpecificationCharacteristic ci, Object value, EValueType eValueType) {
//        // When the type map fell back to Object.class the static eValueType is OBJECT even for
//        // simple values (String, Integer, …). Re-resolve from the actual runtime class so that
//        // e.g. a String does not end up in expandObjectFields.
//        if (value != null && eValueType == EValueType.OBJECT) {
//            EValueType runtimeType = ClassTypeResolver.resolveEValueType(value.getClass());
//            if (runtimeType != EValueType.OBJECT) eValueType = runtimeType;
//        }
//        switch (eValueType) {
//            case SET    -> addSetSpecValues(ci, value);
//            case ARRAY  -> addArraySpecValues(ci, value);
//            case MAP    -> addMapSpecValues(ci, value);
//            case ENUM   -> addEnumSpecValues(ci, value);
//            case OBJECT -> addObjectSpecValues(ci, value);
//            default     -> addScalarSpecValue(ci, value, eValueType);
//        }
//    }
//
//    /** SET: one value per element, no index in alias (unordered). */
//    private static void addSetSpecValues(ResourceSpecificationCharacteristic ci, Object value) {
//        if (!(value instanceof Collection<?> col)) { addScalarSpecValue(ci, value, EValueType.SET); return; }
//        for (Object elem : col) {
//            if (elem == null) continue;
//            addResourceSpecValue(ci, elem.toString(), null, true, ClassTypeResolver.resolveEValueType(elem.getClass()));
//        }
//    }
//
//    /** ARRAY: one value per element, integer index as alias (0.fieldName for object elements). */
//    private static void addArraySpecValues(ResourceSpecificationCharacteristic ci, Object value) {
//        if (!(value instanceof Collection<?> col)) { addScalarSpecValue(ci, value, EValueType.ARRAY); return; }
//        int i = 0;
//        for (Object elem : col) {
//            if (elem == null) { i++; continue; }
//            EValueType elemType = ClassTypeResolver.resolveEValueType(elem.getClass());
//            if (elemType == EValueType.OBJECT) {
//                expandObjectFields(ci, elem, i + ".");
//            } else {
//                addResourceSpecValue(ci, elem.toString(), String.valueOf(i), true, elemType);
//            }
//            i++;
//        }
//    }
//
//    /** MAP: map key stored in alias, map value stored in value. */
//    private static void addMapSpecValues(ResourceSpecificationCharacteristic ci, Object value) {
//        if (!(value instanceof Map<?, ?> map)) { addScalarSpecValue(ci, value, EValueType.MAP); return; }
//        for (Map.Entry<?, ?> entry : map.entrySet()) {
//            Object val = entry.getValue();
//            if (val == null) continue;
//            addResourceSpecValue(ci, val.toString(), String.valueOf(entry.getKey()), true, ClassTypeResolver.resolveEValueType(val.getClass()));
//        }
//    }
//
//    /**
//     * ENUM: registers every constant (value=ordinal, alias=name).
//     * The current default value is marked isDefault=true; cardinality spans all constants.
//     */
//    private static void addEnumSpecValues(ResourceSpecificationCharacteristic ci, Object value) {
//        if (!(value instanceof Enum<?> enumValue)) return;
//        Object[] constants = enumValue.getClass().getEnumConstants();
//        for (int i = 0; i < constants.length; i++) {
//            addResourceSpecValue(ci, String.valueOf(i), constants[i].toString(), constants[i].equals(enumValue), EValueType.TEXT);
//        }
//        ci.minCardinality(0);
//        ci.maxCardinality(constants.length - 1);
//    }
//
//    /** OBJECT: expands each declared field as a separate value (alias=fieldName). */
//    private static void addObjectSpecValues(ResourceSpecificationCharacteristic ci, Object value) {
//        if (value == null) return;
//        expandObjectFields(ci, value, "");
//    }
//
//    /**
//     * Iterates declared fields of {@code obj} via reflection, unwraps JsonNullable,
//     * and adds each non-null field as a characteristic value with alias={@code prefix+fieldName}.
//     * Used for OBJECT elements and for the per-element expansion inside ARRAY.
//     */
//    private static void expandObjectFields(ResourceSpecificationCharacteristic ci, Object obj, String prefix) {
//        if (obj == null) return;
//        // JDK/framework classes (java.*, javax.*, sun.*) block reflective access under the module system
//        String pkg = obj.getClass().getPackageName();
//        if (pkg.startsWith("java.") || pkg.startsWith("javax.") || pkg.startsWith("sun.")) {
//            addScalarSpecValue(ci, obj, ClassTypeResolver.resolveEValueType(obj.getClass()));
//            return;
//        }
//        for (Field field : obj.getClass().getDeclaredFields()) {
//            field.setAccessible(true);
//            try {
//                Object fieldValue = unwrapJsonNullable(field.get(obj));
//                if (fieldValue == null) continue;
//                EValueType fieldType = ClassTypeResolver.resolveEValueType(fieldValue.getClass());
//                addResourceSpecValue(ci, fieldValue.toString(), prefix + field.getName(), true, fieldType);
//            } catch (IllegalAccessException e) {
//                logger.error("Error reading field {} of {}", field.getName(), obj.getClass().getSimpleName(), e);
//            }
//        }
//    }
//
//    /** Scalar fallback (BOOLEAN, INTEGER, FLOAT, TEXT, …): single value, no alias. */
//    private static void addScalarSpecValue(ResourceSpecificationCharacteristic ci, Object value, EValueType valueType) {
//        if (value == null) return;
//        addResourceSpecValue(ci, value.toString(), null, true, valueType);
//    }
//
//    private static Object unwrapJsonNullable(Object value) {
//        if (value instanceof JsonNullable<?> jn) return jn.isPresent() ? jn.get() : null;
//        return value;
//    }
//
//    private static void addResourceSpecValue(
//        ResourceSpecificationCharacteristic ci,
//        String value,
//        String alias,
//        boolean isDefault,
//        EValueType type
//    ) {
//        addResourceSpecValue(ci, new Any(value, alias), isDefault, type);
//    }
//
//    private static void addResourceSpecValue(
//        ResourceSpecificationCharacteristic ci,
//        Any value,
//        boolean isDefault,
//        EValueType type
//    ) {
//        ResourceSpecificationCharacteristicValue val = new ResourceSpecificationCharacteristicValue();
//        val.setValue(value);
//        val.valueType(type.getValue());
//        val.isDefault(isDefault);
//        ci.addResourceSpecCharacteristicValueItem(val);
//    }
//
//    private static EValueType nodeToEValueType(JsonNode node) {
//        if (node.isInt() || node.isShort()) return EValueType.INTEGER;
//        if (node.isLong())                  return EValueType.lONGINT;
//        if (node.isFloatingPointNumber())   return EValueType.FLOAT;
//        if (node.isBinary())                return EValueType.BINARY;
//        if (node.isBoolean())               return EValueType.BOOLEAN;
//        if (node.isArray())                 return EValueType.ARRAY;
//        if (node.isObject())                return EValueType.OBJECT;
//        return EValueType.TEXT;
//    }
//
//}