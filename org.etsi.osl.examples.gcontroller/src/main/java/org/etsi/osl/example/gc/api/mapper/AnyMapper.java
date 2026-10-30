package org.etsi.osl.example.gc.api.mapper;

import org.etsi.osl.tmf.common.model.Any;
import org.etsi.osl.tmf.ri639.model.Characteristic;
import org.mapstruct.Mapper;

import java.math.BigDecimal;

@Mapper(componentModel = "spring")
public abstract class AnyMapper {

    public Any toAny(String value) {
        return new Any(value);
    }

    public Any toAny(Integer value) {
        return new Any(value);
    }

    public Any toAny(BigDecimal value) {
        return new Any(value.toString());
    }

    public Any toAny(Boolean value) {
        return new Any(value.toString());
    }

    public Any toAny(Enum<?> value) {
        return new Any(value.name());
    }

    public Any fromCharacteristicToAny(Characteristic c) {
        if (c == null) return null;
        return c.getValue();
    }

}
