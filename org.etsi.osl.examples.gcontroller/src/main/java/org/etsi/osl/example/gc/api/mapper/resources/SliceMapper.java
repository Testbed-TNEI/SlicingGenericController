package org.etsi.osl.example.gc.api.mapper.resources;

import com.itav.slicemanager.models.Slice;
import org.etsi.osl.tmf.common.model.EValueType;
import org.etsi.osl.tmf.rcm634.model.ResourceSpecificationCharacteristic;
import org.etsi.osl.tmf.ri639.model.Characteristic;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Builds {@link ResourceSpecificationCharacteristic} lists for the new {@link Slice} model without
 * reflection, using the same field name and unit as the Slice Manager API (snake_case, kbps/ms/bytes).
 */
@Mapper(componentModel = "spring", uses = {org.etsi.osl.example.gc.api.mapper.CharacteristicsMapper.class,
    org.etsi.osl.example.gc.api.mapper.CharacteristicsValueMapper.class})
public abstract class SliceMapper extends AbstractResourceSpecCharacteristicsMapper {

    public List<ResourceSpecificationCharacteristic> toResourceSpecCharacteristics(Slice slice) {
        return List.of(
            createResourceSpecCharacteristic("slice_id", EValueType.TEXT,
                characteristicsValueMapper.toResourceSpecCharacteristicsValue(slice.getSliceId())),
            createResourceSpecCharacteristic("coverage_area", EValueType.ARRAY,
                characteristicsValueMapper.toResourceSpecCharacteristicsValue(toCoverageAreaStrings(slice.getCoverageArea()))),
            createResourceSpecCharacteristic("sst", EValueType.INTEGER,
                characteristicsValueMapper.toResourceSpecCharacteristicsValue(slice.getSst())),
            createResourceSpecCharacteristic("sd", EValueType.TEXT,
                characteristicsValueMapper.toResourceSpecCharacteristicsValue(slice.getSd())),
            createResourceSpecCharacteristic("dnn", EValueType.TEXT,
                characteristicsValueMapper.toResourceSpecCharacteristicsValue(slice.getDnn())),
            createResourceSpecCharacteristic("priority_label", EValueType.INTEGER,
                characteristicsValueMapper.toResourceSpecCharacteristicsValue(slice.getPriorityLabel())),
            createResourceSpecCharacteristic("ue_mobility_level", EValueType.ENUM,
                characteristicsValueMapper.toResourceSpecCharacteristicsValue(slice.getUeMobilityLevel())),
            createResourceSpecCharacteristic("reliability", EValueType.FLOAT,
                characteristicsValueMapper.toResourceSpecCharacteristicsValue(slice.getReliability())),
            createResourceSpecCharacteristic("uplink_max_packet_size_bytes", EValueType.INTEGER,
                characteristicsValueMapper.toResourceSpecCharacteristicsValue(slice.getUplinkMaxPacketSizeBytes())),
            createResourceSpecCharacteristic("downlink_max_packet_size_bytes", EValueType.INTEGER,
                characteristicsValueMapper.toResourceSpecCharacteristicsValue(slice.getDownlinkMaxPacketSizeBytes())),
            createResourceSpecCharacteristic("scheduling_type", EValueType.ENUM,
                characteristicsValueMapper.toResourceSpecCharacteristicsValue(slice.getSchedulingType())),
            createResourceSpecCharacteristic("downlink_min_resource_block_ratio", EValueType.INTEGER,
                characteristicsValueMapper.toResourceSpecCharacteristicsValue(slice.getDownlinkMinResourceBlockRatio())),
            createResourceSpecCharacteristic("downlink_max_resource_block_ratio", EValueType.INTEGER,
                characteristicsValueMapper.toResourceSpecCharacteristicsValue(slice.getDownlinkMaxResourceBlockRatio())),
            createResourceSpecCharacteristic("uplink_min_resource_block_ratio", EValueType.INTEGER,
                characteristicsValueMapper.toResourceSpecCharacteristicsValue(slice.getUplinkMinResourceBlockRatio())),
            createResourceSpecCharacteristic("uplink_max_resource_block_ratio", EValueType.INTEGER,
                characteristicsValueMapper.toResourceSpecCharacteristicsValue(slice.getUplinkMaxResourceBlockRatio())),
            createResourceSpecCharacteristic("downlink_latency_ms", EValueType.INTEGER,
                characteristicsValueMapper.toResourceSpecCharacteristicsValue(slice.getDownlinkLatencyMs())),
            createResourceSpecCharacteristic("uplink_latency_ms", EValueType.INTEGER,
                characteristicsValueMapper.toResourceSpecCharacteristicsValue(slice.getUplinkLatencyMs())),
            createResourceSpecCharacteristic("delay_tolerance", EValueType.ENUM,
                characteristicsValueMapper.toResourceSpecCharacteristicsValue(slice.getDelayTolerance())),
            createResourceSpecCharacteristic("downlink_deterministic_communication", EValueType.ENUM,
                characteristicsValueMapper.toResourceSpecCharacteristicsValue(slice.getDownlinkDeterministicCommunication())),
            createResourceSpecCharacteristic("downlink_deterministic_periodicity", EValueType.INTEGER,
                characteristicsValueMapper.toResourceSpecCharacteristicsValue(slice.getDownlinkDeterministicPeriodicity())),
            createResourceSpecCharacteristic("uplink_deterministic_communication", EValueType.ENUM,
                characteristicsValueMapper.toResourceSpecCharacteristicsValue(slice.getUplinkDeterministicCommunication())),
            createResourceSpecCharacteristic("uplink_deterministic_periodicity", EValueType.INTEGER,
                characteristicsValueMapper.toResourceSpecCharacteristicsValue(slice.getUplinkDeterministicPeriodicity())),
            createResourceSpecCharacteristic("downlink_guaranteed_bitrate_per_ue_kbps", EValueType.INTEGER,
                characteristicsValueMapper.toResourceSpecCharacteristicsValue(slice.getDownlinkGuaranteedBitratePerUeKbps())),
            createResourceSpecCharacteristic("uplink_guaranteed_bitrate_per_ue_kbps", EValueType.INTEGER,
                characteristicsValueMapper.toResourceSpecCharacteristicsValue(slice.getUplinkGuaranteedBitratePerUeKbps())),
            createResourceSpecCharacteristic("downlink_maximum_bitrate_per_ue_kbps", EValueType.INTEGER,
                characteristicsValueMapper.toResourceSpecCharacteristicsValue(slice.getDownlinkMaximumBitratePerUeKbps())),
            createResourceSpecCharacteristic("uplink_maximum_bitrate_per_ue_kbps", EValueType.INTEGER,
                characteristicsValueMapper.toResourceSpecCharacteristicsValue(slice.getUplinkMaximumBitratePerUeKbps())),
            createResourceSpecCharacteristic("downlink_guaranteed_bitrate_per_slice_kbps", EValueType.INTEGER,
                characteristicsValueMapper.toResourceSpecCharacteristicsValue(slice.getDownlinkGuaranteedBitratePerSliceKbps())),
            createResourceSpecCharacteristic("uplink_guaranteed_bitrate_per_slice_kbps", EValueType.INTEGER,
                characteristicsValueMapper.toResourceSpecCharacteristicsValue(slice.getUplinkGuaranteedBitratePerSliceKbps())),
            createResourceSpecCharacteristic("downlink_maximum_bitrate_per_slice_kbps", EValueType.INTEGER,
                characteristicsValueMapper.toResourceSpecCharacteristicsValue(slice.getDownlinkMaximumBitratePerSliceKbps())),
            createResourceSpecCharacteristic("uplink_maximum_bitrate_per_slice_kbps", EValueType.INTEGER,
                characteristicsValueMapper.toResourceSpecCharacteristicsValue(slice.getUplinkMaximumBitratePerSliceKbps())),
            createResourceSpecCharacteristic("terminal_density", EValueType.INTEGER,
                characteristicsValueMapper.toResourceSpecCharacteristicsValue(slice.getTerminalDensity())),
            createResourceSpecCharacteristic("max_number_of_pdu_sessions", EValueType.INTEGER,
                characteristicsValueMapper.toResourceSpecCharacteristicsValue(slice.getMaxNumberOfPduSessions())),
            createResourceSpecCharacteristic("max_number_of_ues", EValueType.INTEGER,
                characteristicsValueMapper.toResourceSpecCharacteristicsValue(slice.getMaxNumberOfUes())),
            createResourceSpecCharacteristic("n6_protection", EValueType.ARRAY,
                characteristicsValueMapper.toResourceSpecCharacteristicsValue(slice.getN6Protection()))
        );
    }

    private static List<String> toCoverageAreaStrings(List<Slice.CoverageAreaEnum> coverageArea) {
        if (coverageArea == null) return null;
        return coverageArea.stream().map(Slice.CoverageAreaEnum::getValue).toList();
    }

    public Slice fromResourceCharacteristics(List<Characteristic> characteristics) {
        // Characteristic names may not be unique in practice: OSL sends the specification defaults
        // alongside values written back by this controller. Keep the last occurrence.
        Map<String, Characteristic> charMap = characteristics.stream()
            .collect(Collectors.toMap(Characteristic::getName, c -> c, (first, last) -> last));

        Slice slice = new Slice();
        slice.setSliceId(characteristicsValueMapper.fromCharacteristic(charMap.get("slice_id")));
        slice.setCoverageArea(toCoverageAreaEnums(characteristicsValueMapper.fromCharacteristicToStringList(charMap.get("coverage_area"))));
        slice.setSst(characteristicsValueMapper.fromCharacteristicToInteger(charMap.get("sst")));
        slice.setSd(characteristicsValueMapper.fromCharacteristic(charMap.get("sd")));
        slice.setDnn(characteristicsValueMapper.fromCharacteristic(charMap.get("dnn")));
        slice.setPriorityLabel(characteristicsValueMapper.fromCharacteristicToInteger(charMap.get("priority_label")));
        slice.setUeMobilityLevel(characteristicsValueMapper.fromCharacteristicToEnum(charMap.get("ue_mobility_level"), Slice.UeMobilityLevelEnum.class));
        slice.setReliability(characteristicsValueMapper.fromCharacteristicToBigDecimal(charMap.get("reliability")));
        slice.setUplinkMaxPacketSizeBytes(characteristicsValueMapper.fromCharacteristicToInteger(charMap.get("uplink_max_packet_size_bytes")));
        slice.setDownlinkMaxPacketSizeBytes(characteristicsValueMapper.fromCharacteristicToInteger(charMap.get("downlink_max_packet_size_bytes")));
        slice.setSchedulingType(characteristicsValueMapper.fromCharacteristicToEnum(charMap.get("scheduling_type"), Slice.SchedulingTypeEnum.class));
        slice.setDownlinkMinResourceBlockRatio(characteristicsValueMapper.fromCharacteristicToInteger(charMap.get("downlink_min_resource_block_ratio")));
        slice.setDownlinkMaxResourceBlockRatio(characteristicsValueMapper.fromCharacteristicToInteger(charMap.get("downlink_max_resource_block_ratio")));
        slice.setUplinkMinResourceBlockRatio(characteristicsValueMapper.fromCharacteristicToInteger(charMap.get("uplink_min_resource_block_ratio")));
        slice.setUplinkMaxResourceBlockRatio(characteristicsValueMapper.fromCharacteristicToInteger(charMap.get("uplink_max_resource_block_ratio")));
        slice.setDownlinkLatencyMs(characteristicsValueMapper.fromCharacteristicToInteger(charMap.get("downlink_latency_ms")));
        slice.setUplinkLatencyMs(characteristicsValueMapper.fromCharacteristicToInteger(charMap.get("uplink_latency_ms")));
        slice.setDelayTolerance(characteristicsValueMapper.fromCharacteristicToEnum(charMap.get("delay_tolerance"), Slice.DelayToleranceEnum.class));
        slice.setDownlinkDeterministicCommunication(characteristicsValueMapper.fromCharacteristicToEnum(charMap.get("downlink_deterministic_communication"), Slice.DownlinkDeterministicCommunicationEnum.class));
        slice.setDownlinkDeterministicPeriodicity(characteristicsValueMapper.fromCharacteristicToInteger(charMap.get("downlink_deterministic_periodicity")));
        slice.setUplinkDeterministicCommunication(characteristicsValueMapper.fromCharacteristicToEnum(charMap.get("uplink_deterministic_communication"), Slice.UplinkDeterministicCommunicationEnum.class));
        slice.setUplinkDeterministicPeriodicity(characteristicsValueMapper.fromCharacteristicToInteger(charMap.get("uplink_deterministic_periodicity")));
        slice.setDownlinkGuaranteedBitratePerUeKbps(characteristicsValueMapper.fromCharacteristicToInteger(charMap.get("downlink_guaranteed_bitrate_per_ue_kbps")));
        slice.setUplinkGuaranteedBitratePerUeKbps(characteristicsValueMapper.fromCharacteristicToInteger(charMap.get("uplink_guaranteed_bitrate_per_ue_kbps")));
        slice.setDownlinkMaximumBitratePerUeKbps(characteristicsValueMapper.fromCharacteristicToInteger(charMap.get("downlink_maximum_bitrate_per_ue_kbps")));
        slice.setUplinkMaximumBitratePerUeKbps(characteristicsValueMapper.fromCharacteristicToInteger(charMap.get("uplink_maximum_bitrate_per_ue_kbps")));
        slice.setDownlinkGuaranteedBitratePerSliceKbps(characteristicsValueMapper.fromCharacteristicToInteger(charMap.get("downlink_guaranteed_bitrate_per_slice_kbps")));
        slice.setUplinkGuaranteedBitratePerSliceKbps(characteristicsValueMapper.fromCharacteristicToInteger(charMap.get("uplink_guaranteed_bitrate_per_slice_kbps")));
        slice.setDownlinkMaximumBitratePerSliceKbps(characteristicsValueMapper.fromCharacteristicToInteger(charMap.get("downlink_maximum_bitrate_per_slice_kbps")));
        slice.setUplinkMaximumBitratePerSliceKbps(characteristicsValueMapper.fromCharacteristicToInteger(charMap.get("uplink_maximum_bitrate_per_slice_kbps")));
        slice.setTerminalDensity(characteristicsValueMapper.fromCharacteristicToInteger(charMap.get("terminal_density")));
        slice.setMaxNumberOfPduSessions(characteristicsValueMapper.fromCharacteristicToInteger(charMap.get("max_number_of_pdu_sessions")));
        slice.setMaxNumberOfUes(characteristicsValueMapper.fromCharacteristicToInteger(charMap.get("max_number_of_ues")));
        slice.setN6Protection(characteristicsValueMapper.fromCharacteristicToSliceN6ProtectionList(charMap.get("n6_protection")));

        return slice;
    }

    private static List<Slice.CoverageAreaEnum> toCoverageAreaEnums(List<String> values) {
        if (values == null) return null;
        return values.stream().map(Slice.CoverageAreaEnum::fromValue).toList();
    }

}