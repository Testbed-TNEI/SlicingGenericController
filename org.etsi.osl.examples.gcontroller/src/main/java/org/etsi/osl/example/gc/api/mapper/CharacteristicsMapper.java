package org.etsi.osl.example.gc.api.mapper;

import org.etsi.osl.tmf.common.model.EValueType;
import org.etsi.osl.tmf.rcm634.model.ResourceSpecificationCharacteristic;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class CharacteristicsMapper {

    public ResourceSpecificationCharacteristic toResourceSpecCharacteristic(
        String name, String description, EValueType valueType, Boolean configurable, String regex
    ) {
        return new ResourceSpecificationCharacteristic()
            .name(name)
            .description(description)
            .valueType(valueType.getValue())
            .configurable(configurable)
            .regex(regex);
    }

}
