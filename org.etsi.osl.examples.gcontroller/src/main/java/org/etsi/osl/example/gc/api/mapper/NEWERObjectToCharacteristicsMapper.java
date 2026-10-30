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
import org.openapitools.jackson.nullable.JsonNullableModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;

public class NEWERObjectToCharacteristicsMapper {

    private static final Logger logger = LoggerFactory.getLogger("org.etsi.osl.example.gc");

    private static final ObjectMapper mapper = new ObjectMapper()
        .setSerializationInclusion(JsonInclude.Include.NON_ABSENT)
        .registerModule(new JsonNullableModule())
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    // ========================= PUBLIC API =========================

    /**
     * Populates a ResourceSpecificationCreate from a POJO's fields.
     */
    public static <T> void addSpecCharacteristics(T object, ResourceSpecificationCreate rsc) {
        mapper.valueToTree(object).fields().forEachRemaining(entry -> {
            JsonNode node = entry.getValue();
            if (node.isNull()) return;
            ResourceSpecificationCharacteristic ci = new ResourceSpecificationCharacteristic();
            ci.name(entry.getKey()).valueType(nodeToEValueType(node).getValue()).configurable(true);
            addSpecValues(ci, node);
            rsc.addResourceSpecificationCharacteristicItem(ci);
        });
    }

    /**
     * Populates a ResourceUpdate from a POJO's fields.
     */
    public static <T> void addResourceCharacteristics(T object, ResourceUpdate rsu) {
        mapper.valueToTree(object).fields().forEachRemaining(entry -> {
            JsonNode node = entry.getValue();
            if (node.isNull()) return;
            Characteristic c = new Characteristic();
            c.setName(entry.getKey());
            c.setValueType(nodeToEValueType(node).getValue());
            c.setValue(new Any(nodeToOslString(node), null));
            rsu.addResourceCharacteristicItem(c);
        });
    }

    /**
     * Builds a single ResourceSpecificationCharacteristic.
     */
    public static ResourceSpecificationCharacteristic buildSpecCharacteristic(
        String name, Object defaultValue, EValueType valueType,
        String description, String regex, Boolean isConfigurable
    ) {
        ResourceSpecificationCharacteristic ci = new ResourceSpecificationCharacteristic();
        ci.name(name).description(description).valueType(valueType.getValue())
            .configurable(isConfigurable).regex(regex);
        if (defaultValue != null) {
            addSpecValues(ci, mapper.valueToTree(defaultValue));
        }
        return ci;
    }

    /**
     * Deserializes an OSL Characteristic list into a typed Java object.
     *
     * @throws IllegalArgumentException if characteristics cannot be mapped to targetClass
     */
    public static <T> T fromCharacteristics(List<Characteristic> characteristics, Class<T> targetClass) {
        Map<String, Object> map = new HashMap<>();
        for (Characteristic c : characteristics) {
            String raw = c.getValue().getValue();
            Object parsed;
            if (Objects.equals(c.getValueType(), EValueType.ENUM.getValue())) {
                parsed = c.getValue().getAlias();
            } else if (raw.startsWith("[")) {
                try {
                    List<Any> anyList = mapper.readValue(raw, mapper.getTypeFactory().constructCollectionType(List.class, Any.class));
                    if (!anyList.isEmpty() && anyList.get(0).getAlias() != null && !anyList.get(0).getAlias().isEmpty()) {
                        // alias = field name, value = field value → reconstruct as object map
                        Map<String, Object> itemMap = new HashMap<>();
                        for (Any any : anyList) itemMap.put(any.getAlias(), any.getValue());
                        parsed = List.of(itemMap);
                    } else {
                        parsed = anyList.stream().map(Any::getValue).collect(Collectors.toList());
                    }
                } catch (Exception e) {
                    logger.error("Error parsing characteristic value for {}", c.getName(), e);
                    parsed = raw;
                }
            } else {
                parsed = raw;
            }
            map.put(c.getName(), parsed);
        }
        logger.info("Mapped characteristics to: {}", map);
        return mapper.convertValue(map, targetClass);
    }

    // ========================= PRIVATE HELPERS =========================

    /**
     * Adds ResourceSpecificationCharacteristicValue items to a spec characteristic.
     * Simple values get a single value; arrays/objects produce one entry per element/field
     * with alias = field name and value = field value.
     */
    private static void addSpecValues(ResourceSpecificationCharacteristic ci, JsonNode node) {
        if (node.isArray()) {
            node.forEach(element -> {
                if (element.isObject())
                    element.fields().forEachRemaining(f -> addSpecValue(ci, f.getValue().asText(), f.getKey(), EValueType.OBJECT));
                else
                    addSpecValue(ci, element.asText(), null, nodeToEValueType(element));
            });
        } else if (node.isObject()) {
            node.fields().forEachRemaining(f -> addSpecValue(ci, f.getValue().asText(), f.getKey(), EValueType.OBJECT));
        } else {
            addSpecValue(ci, node.asText(), null, nodeToEValueType(node));
        }
    }

    private static void addSpecValue(ResourceSpecificationCharacteristic ci, String value, String alias, EValueType type) {
        ResourceSpecificationCharacteristicValue val = new ResourceSpecificationCharacteristicValue();
        val.setValue(new Any(value, alias));
        val.valueType(type.getValue());
        val.isDefault(true);
        ci.addResourceSpecCharacteristicValueItem(val);
    }

    /**
     * Serializes a JsonNode to its OSL string representation.
     * Simple values → plain string. Arrays/objects → JSON array of Any objects
     * where alias = field name and value = field value.
     */
    private static String nodeToOslString(JsonNode node) {
        if (node.isValueNode()) return node.asText();
        List<Any> anyList = new ArrayList<>();
        if (node.isArray()) {
            node.forEach(element -> {
                if (element.isObject())
                    element.fields().forEachRemaining(f -> anyList.add(new Any(f.getValue().asText(), f.getKey())));
                else
                    anyList.add(new Any(element.asText(), null));
            });
        } else if (node.isObject()) {
            node.fields().forEachRemaining(f -> anyList.add(new Any(f.getValue().asText(), f.getKey())));
        }
        try {
            return mapper.writeValueAsString(anyList);
        } catch (Exception e) {
            return node.toString();
        }
    }

    private static EValueType nodeToEValueType(JsonNode node) {
        if (node.isInt() || node.isShort()) return EValueType.INTEGER;
        if (node.isLong()) return EValueType.lONGINT;
        if (node.isFloatingPointNumber()) return EValueType.FLOAT;
        if (node.isBinary()) return EValueType.BINARY;
        if (node.isBoolean()) return EValueType.BOOLEAN;
        if (node.isArray()) return EValueType.ARRAY;
        if (node.isObject()) return EValueType.OBJECT;
        return EValueType.TEXT;
    }
}