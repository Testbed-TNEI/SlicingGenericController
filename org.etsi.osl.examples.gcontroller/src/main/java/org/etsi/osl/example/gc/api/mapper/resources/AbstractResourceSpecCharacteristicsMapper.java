package org.etsi.osl.example.gc.api.mapper.resources;

import org.etsi.osl.example.gc.api.mapper.CharacteristicsMapper;
import org.etsi.osl.example.gc.api.mapper.CharacteristicsValueMapper;
import org.etsi.osl.tmf.common.model.EValueType;
import org.etsi.osl.tmf.rcm634.model.ResourceSpecificationCharacteristic;
import org.etsi.osl.tmf.rcm634.model.ResourceSpecificationCharacteristicValue;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Shared helpers for building {@link ResourceSpecificationCharacteristic} lists without reflection,
 * on top of {@link CharacteristicsMapper} and {@link CharacteristicsValueMapper}.
 */
public abstract class AbstractResourceSpecCharacteristicsMapper {

    @Autowired
    protected CharacteristicsMapper characteristicsMapper;
    @Autowired
    protected CharacteristicsValueMapper characteristicsValueMapper;

    protected ResourceSpecificationCharacteristic createResourceSpecCharacteristic(
        String field, EValueType type, ResourceSpecificationCharacteristicValue value
    ) {
        ResourceSpecificationCharacteristic ci = characteristicsMapper.toResourceSpecCharacteristic(
            field, "Parameter: " + field, type, true, ""
        );
        if (value != null) {
            ci.addResourceSpecCharacteristicValueItem(value);
        }
        return ci;
    }

    protected ResourceSpecificationCharacteristic createResourceSpecCharacteristic(
        String field, EValueType type, Set<ResourceSpecificationCharacteristicValue> values
    ) {
        ResourceSpecificationCharacteristic ci = characteristicsMapper.toResourceSpecCharacteristic(
            field, "Parameter: " + field, type, true, ""
        );
        Set<ResourceSpecificationCharacteristicValue> filteredValues = values
            .stream()
            .filter(Objects::nonNull)
            .collect(Collectors.toSet());
        if (!filteredValues.isEmpty()) {
            ci.setResourceSpecificationCharacteristicValue(filteredValues);
        }
        return ci;
    }

}