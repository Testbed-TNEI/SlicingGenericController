package org.etsi.osl.example.gc.api.mapper;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itav.slicemanager.models.SliceN6ProtectionRule;
import org.etsi.osl.tmf.common.model.Any;
import org.etsi.osl.tmf.common.model.EValueType;
import org.etsi.osl.tmf.rcm634.model.ResourceSpecificationCharacteristicValue;
import org.etsi.osl.tmf.ri639.model.Characteristic;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Mapper(componentModel = "spring", uses = {AnyMapper.class})
public abstract class CharacteristicsValueMapper {

    @Autowired
    AnyMapper anyMapper;

    private static final Logger logger = LoggerFactory.getLogger("org.etsi.osl.example.gc");

    private final ObjectMapper objectMapper = new ObjectMapper()
        .setSerializationInclusion(JsonInclude.Include.NON_ABSENT)
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    public abstract ResourceSpecificationCharacteristicValue toResourceSpecCharacteristicsValue(String value);

    public abstract ResourceSpecificationCharacteristicValue toResourceSpecCharacteristicsValue(Integer value);

    public abstract ResourceSpecificationCharacteristicValue toResourceSpecCharacteristicsValue(BigDecimal value);

    public abstract ResourceSpecificationCharacteristicValue toResourceSpecCharacteristicsValue(Boolean value);

    public Set<ResourceSpecificationCharacteristicValue> toResourceSpecCharacteristicsValue(Enum<?> value) {
        if (value == null) {
            return Collections.emptySet();
        }

        Enum<?>[] constants = (Enum<?>[]) value.getClass().getEnumConstants();
        return IntStream.range(0, constants.length)
            .mapToObj(idx -> {
                Enum<?> e = constants[idx];
                ResourceSpecificationCharacteristicValue ciValue = new ResourceSpecificationCharacteristicValue();
                // value must be the enum constant name so fromCharacteristicToEnum's Enum.valueOf can
                // round-trip it; the display string (e.g. lowercase @JsonValue) goes in the alias.
                ciValue.setValue(new Any(e.name(), e.toString()));
                ciValue.valueType(EValueType.ENUM.getValue());
                ciValue.isDefault(idx == 0);
                return ciValue;
            })
            .collect(Collectors.toSet());
    }

    /**
     * SliceN6ProtectionRule: one value per populated field, aliased as {@code idx.field_name}.
     * Absent (null) fields are skipped rather than forced to an empty string, since every field
     * on the new model is a genuine optional.
     */
    public Set<ResourceSpecificationCharacteristicValue> toResourceSpecCharacteristicsValue(List<SliceN6ProtectionRule> col) {
        if (col == null || col.isEmpty()) {
            return Collections.emptySet();
        }

        return IntStream.range(0, col.size())
            .boxed()
            .flatMap(idx -> {
                SliceN6ProtectionRule rule = col.get(idx);
                boolean isDefault = idx == 0;
                return Stream.of(
                    n6ProtectionRuleValue(idx, "type", rule.getType(), isDefault),
                    n6ProtectionRuleValue(idx, "name", rule.getName(), isDefault),
                    n6ProtectionRuleValue(idx, "priority", rule.getPriority(), isDefault),
                    n6ProtectionRuleValue(idx, "server_ip", rule.getServerIp(), isDefault),
                    n6ProtectionRuleValue(idx, "mask_len", rule.getMaskLen(), isDefault),
                    n6ProtectionRuleValue(idx, "start_port", rule.getStartPort(), isDefault),
                    n6ProtectionRuleValue(idx, "end_port", rule.getEndPort(), isDefault),
                    n6ProtectionRuleValue(idx, "uplink_guaranteed_bitrate_kbps", rule.getUplinkGuaranteedBitrateKbps(), isDefault),
                    n6ProtectionRuleValue(idx, "downlink_guaranteed_bitrate_kbps", rule.getDownlinkGuaranteedBitrateKbps(), isDefault),
                    n6ProtectionRuleValue(idx, "uplink_maximum_bitrate_kbps", rule.getUplinkMaximumBitrateKbps(), isDefault),
                    n6ProtectionRuleValue(idx, "downlink_maximum_bitrate_kbps", rule.getDownlinkMaximumBitrateKbps(), isDefault),
                    n6ProtectionRuleValue(idx, "protocol", rule.getProtocol(), isDefault),
                    n6ProtectionRuleValue(idx, "pass_block", rule.getPassBlock(), isDefault)
                ).filter(Objects::nonNull);
            })
            .collect(Collectors.toSet());
    }

    private ResourceSpecificationCharacteristicValue n6ProtectionRuleValue(int idx, String field, Object value, boolean isDefault) {
        if (value == null) return null;
        ResourceSpecificationCharacteristicValue ciValue = new ResourceSpecificationCharacteristicValue();
        ciValue.setValue(new Any(String.valueOf(value), idx + "." + field));
        ciValue.valueType((value instanceof Integer ? EValueType.INTEGER : EValueType.TEXT).getValue());
        ciValue.isDefault(isDefault);
        return ciValue;
    }

    public Set<ResourceSpecificationCharacteristicValue> toResourceSpecCharacteristicsValue(Collection<?> col) {
        if (col == null || col.isEmpty()) {
            return Collections.emptySet();
        }

        return col.stream()
            .map(item -> switch (item) {
                case String s -> toResourceSpecCharacteristicsValue(s);
                case Integer i -> toResourceSpecCharacteristicsValue(i);
                case BigDecimal bd -> toResourceSpecCharacteristicsValue(bd);
                case Boolean b -> toResourceSpecCharacteristicsValue(b);
                default -> {
                    logger.warn("Unsupported type in Set: {}", item.getClass().getName());
                    yield null;
                }
            })
            .filter(Objects::nonNull)
            .collect(Collectors.toSet());
    }

    public String fromCharacteristic(Characteristic c) {
        Any value = anyMapper.fromCharacteristicToAny(c);
        return value == null ? null : value.getValue();
    }

    public Integer fromCharacteristicToInteger(Characteristic c) {
        String raw = fromCharacteristic(c);
        return (raw == null || raw.isBlank()) ? null : Integer.parseInt(raw);
    }

    public BigDecimal fromCharacteristicToBigDecimal(Characteristic c) {
        String raw = fromCharacteristic(c);
        return (raw == null || raw.isBlank()) ? null : new BigDecimal(raw);
    }

    public <T extends Enum<T>> T fromCharacteristicToEnum(Characteristic c, Class<T> type) {
        String raw = fromCharacteristic(c);
        return (raw == null || raw.isBlank()) ? null : Enum.valueOf(type, raw);
    }

    public Boolean fromCharacteristicToBoolean(Characteristic c) {
        String raw = fromCharacteristic(c);
        return raw == null ? null : Boolean.valueOf(raw);
    }

    public List<String> fromCharacteristicToStringList(Characteristic c) {
        String raw = fromCharacteristic(c);
        if (!Objects.equals(c.getValueType(), EValueType.ARRAY.getValue())) {
            throw new IllegalArgumentException("Characteristic valueType is not ARRAY, cannot parse as List<Any>");
        }
        try {
            List<Any> anyList = objectMapper.readValue(
                raw,
                objectMapper.getTypeFactory().constructCollectionType(List.class, Any.class)
            );
            List<String> stringList = anyList.stream()
                .map(Any::getValue)
                .collect(Collectors.toList());
//            logger.info("PARSED STRING LIST result {} for characteristic {}", stringList, c.getName());
            return stringList;
        } catch (Exception e) {
            logger.warn("Could not parse string list for characteristic {}, raw: {}", c.getName(), raw);
            return Collections.emptyList();
        }
    }

    /**
     * Reverse of {@link #toResourceSpecCharacteristicsValue(List)}: reconstructs the
     * {@code SliceN6ProtectionRule} list from the {@code idx.field_name} aliased JSON array.
     */
    public List<SliceN6ProtectionRule> fromCharacteristicToSliceN6ProtectionList(Characteristic c) {
        Any any = anyMapper.fromCharacteristicToAny(c);
        if (any == null || any.getValue() == null || any.getValue().isBlank()) return null;

        List<Any> anyList;
        try {
            anyList = objectMapper.readValue(
                any.getValue(),
                objectMapper.getTypeFactory().constructCollectionType(List.class, Any.class)
            );
        } catch (Exception e) {
            logger.warn("Could not parse n6_protection list for characteristic {}, raw: {}", c.getName(), any.getValue());
            return null;
        }

        Map<String, SliceN6ProtectionRule> rules = new LinkedHashMap<>();
        for (Any item : anyList) {
            String alias = item.getAlias();
            if (alias == null) continue;
            int dot = alias.indexOf('.');
            if (dot < 0) continue;
            String prefix = alias.substring(0, dot);
            String field = alias.substring(dot + 1);
            SliceN6ProtectionRule rule = rules.computeIfAbsent(prefix, k -> new SliceN6ProtectionRule());
            String value = item.getValue();
            switch (field) {
                case "type" -> rule.setType(value);
                case "name" -> rule.setName(value);
                case "priority" -> rule.setPriority(Integer.valueOf(value));
                case "server_ip" -> rule.setServerIp(value);
                case "mask_len" -> rule.setMaskLen(Integer.valueOf(value));
                case "start_port" -> rule.setStartPort(Integer.valueOf(value));
                case "end_port" -> rule.setEndPort(Integer.valueOf(value));
                case "uplink_guaranteed_bitrate_kbps" -> rule.setUplinkGuaranteedBitrateKbps(Integer.valueOf(value));
                case "downlink_guaranteed_bitrate_kbps" ->
                    rule.setDownlinkGuaranteedBitrateKbps(Integer.valueOf(value));
                case "uplink_maximum_bitrate_kbps" -> rule.setUplinkMaximumBitrateKbps(Integer.valueOf(value));
                case "downlink_maximum_bitrate_kbps" -> rule.setDownlinkMaximumBitrateKbps(Integer.valueOf(value));
                case "protocol" -> rule.setProtocol(value);
                case "pass_block" -> rule.setPassBlock(value);
            }
        }
        return rules.isEmpty() ? null : new ArrayList<>(rules.values());
    }

    private Characteristic buildCharacteristic(String name, EValueType type, Any value) {
        Characteristic c = new Characteristic();
        c.setName(name);
        c.setValueType(type.getValue());
        c.setValue(value);
        return c;
    }

    public Characteristic toCharacteristic(String name, String value) {
        return buildCharacteristic(name, EValueType.TEXT, new Any(value, null));
    }

    public Characteristic toCharacteristic(String name, Integer value) {
        return buildCharacteristic(name, EValueType.INTEGER, new Any(value != null ? value.toString() : null, null));
    }

    public Characteristic toCharacteristic(String name, BigDecimal value) {
        return buildCharacteristic(name, EValueType.FLOAT, new Any(value != null ? value.toPlainString() : null, null));
    }

    public Characteristic toCharacteristic(String name, Enum<?> value) {
        String enumName = value != null ? value.name() : null;
        return buildCharacteristic(name, EValueType.ENUM, new Any(enumName, enumName));
    }

    public Characteristic toCharacteristicStringList(String name, List<String> values) {
        String json = null;
        if (values != null && !values.isEmpty()) {
            try {
                List<Any> anyList = values.stream().map(Any::new).collect(Collectors.toList());
                json = objectMapper.writeValueAsString(anyList);
            } catch (Exception e) {
                logger.warn("Could not serialize string list for characteristic {}", name);
            }
        }
        return buildCharacteristic(name, EValueType.ARRAY, new Any(json, null));
    }

    @AfterMapping
    protected void afterMapping(Object source, @MappingTarget ResourceSpecificationCharacteristicValue target) {
        boolean isDefault = false;
        switch (source) {
            case String s -> {
                target.valueType(EValueType.TEXT.getValue());
                isDefault = true;
            }
            case Integer i -> {
                target.valueType(EValueType.INTEGER.getValue());
                isDefault = true;
            }
            case BigDecimal bd -> {
                target.valueType(EValueType.FLOAT.getValue());
                isDefault = true;
            }
            case Boolean b -> {
                target.valueType(EValueType.BOOLEAN.getValue());
                isDefault = true;
            }
            case Enum<?> e -> target.valueType(EValueType.ENUM.getValue());
            default -> {
                logger.warn("Unsupported type for valueType: {}", source.getClass().getName());
            }
        }


        target.isDefault(isDefault);
    }


}
