package org.etsi.osl.example.gc.api.mapper.resources;

import com.itav.slicemanager.models.UeSliceAssociationCreateRequest;
import org.etsi.osl.tmf.common.model.EValueType;
import org.etsi.osl.tmf.rcm634.model.ResourceSpecificationCharacteristic;
import org.etsi.osl.tmf.ri639.model.Characteristic;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Builds {@link ResourceSpecificationCharacteristic} lists for the new
 * {@link UeSliceAssociationCreateRequest} model without reflection, using the same field names and
 * units as the Slice Manager API (snake_case, kbps).
 */
@Mapper(componentModel = "spring", uses = {org.etsi.osl.example.gc.api.mapper.CharacteristicsMapper.class,
    org.etsi.osl.example.gc.api.mapper.CharacteristicsValueMapper.class})
public abstract class UeSliceAssociationMapper extends AbstractResourceSpecCharacteristicsMapper {

    public List<ResourceSpecificationCharacteristic> toResourceSpecCharacteristics(UeSliceAssociationCreateRequest ue) {
        return List.of(
            createResourceSpecCharacteristic("dnn", EValueType.TEXT,
                characteristicsValueMapper.toResourceSpecCharacteristicsValue(ue.getDnn())),
            createResourceSpecCharacteristic("snssai", EValueType.TEXT,
                characteristicsValueMapper.toResourceSpecCharacteristicsValue(ue.getSnssai())),
            createResourceSpecCharacteristic("slice_id", EValueType.TEXT,
                characteristicsValueMapper.toResourceSpecCharacteristicsValue(ue.getSliceId())),
            createResourceSpecCharacteristic("static_ipv4_address", EValueType.TEXT,
                characteristicsValueMapper.toResourceSpecCharacteristicsValue(ue.getStaticIpv4Address())),
            createResourceSpecCharacteristic("access_mobility_data", EValueType.BOOLEAN,
                characteristicsValueMapper.toResourceSpecCharacteristicsValue(ue.getAccessMobilityData())),
            createResourceSpecCharacteristic("snssai_advertisement_allowed", EValueType.BOOLEAN,
                characteristicsValueMapper.toResourceSpecCharacteristicsValue(ue.getSnssaiAdvertisementAllowed())),
            createResourceSpecCharacteristic("uplink_aggregate_maximum_bitrate_kbps", EValueType.INTEGER,
                characteristicsValueMapper.toResourceSpecCharacteristicsValue(ue.getUplinkAggregateMaximumBitrateKbps())),
            createResourceSpecCharacteristic("downlink_aggregate_maximum_bitrate_kbps", EValueType.INTEGER,
                characteristicsValueMapper.toResourceSpecCharacteristicsValue(ue.getDownlinkAggregateMaximumBitrateKbps()))
        );
    }

    public UeSliceAssociationCreateRequest fromResourceCharacteristics(List<Characteristic> characteristics) {
        Map<String, Characteristic> charMap = characteristics.stream()
            .collect(Collectors.toMap(Characteristic::getName, c -> c, (first, last) -> last));

        UeSliceAssociationCreateRequest ue = new UeSliceAssociationCreateRequest();
        ue.setDnn(characteristicsValueMapper.fromCharacteristic(charMap.get("dnn")));
        ue.setSnssai(characteristicsValueMapper.fromCharacteristic(charMap.get("snssai")));
        ue.setSliceId(characteristicsValueMapper.fromCharacteristic(charMap.get("slice_id")));
        ue.setStaticIpv4Address(characteristicsValueMapper.fromCharacteristic(charMap.get("static_ipv4_address")));
        ue.setAccessMobilityData(characteristicsValueMapper.fromCharacteristicToBoolean(charMap.get("access_mobility_data")));
        ue.setSnssaiAdvertisementAllowed(characteristicsValueMapper.fromCharacteristicToBoolean(charMap.get("snssai_advertisement_allowed")));
        ue.setUplinkAggregateMaximumBitrateKbps(characteristicsValueMapper.fromCharacteristicToInteger(charMap.get("uplink_aggregate_maximum_bitrate_kbps")));
        ue.setDownlinkAggregateMaximumBitrateKbps(characteristicsValueMapper.fromCharacteristicToInteger(charMap.get("downlink_aggregate_maximum_bitrate_kbps")));

        return ue;
    }

    /**
     * ue_id (IMSI) is the POST /core/ues/{ue_id}/slice-associations path parameter, so it is not a
     * field on UeSliceAssociationCreateRequest and has to be pulled out of the characteristics
     * separately (see ResourceUE#toRSpecCreate_InitRepo).
     */
    public String extractUeId(List<Characteristic> characteristics) {
        return characteristics.stream()
            .filter(c -> "ue_id".equals(c.getName()))
            .findFirst()
            .map(characteristicsValueMapper::fromCharacteristic)
            .orElse(null);
    }

}