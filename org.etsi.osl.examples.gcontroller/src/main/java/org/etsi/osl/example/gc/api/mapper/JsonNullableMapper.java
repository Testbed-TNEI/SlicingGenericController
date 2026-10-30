package org.etsi.osl.example.gc.api.mapper;

import org.mapstruct.Mapper;
import org.openapitools.jackson.nullable.JsonNullable;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Mapper(componentModel = "spring")
public interface JsonNullableMapper {

    default <T> T unwrap(JsonNullable<T> value) {
        return value != null && value.isPresent() ? value.get() : null;
    }

    default Integer toInteger(JsonNullable<String> value) {
        return value != null && value.isPresent() && value.get() != null
            ? Integer.parseInt(value.get()) : null;
    }

    default BigDecimal toBigDecimal(JsonNullable<String> value) {
        return value != null && value.isPresent() && value.get() != null
            ? new BigDecimal(value.get()) : null;
    }

    // TODO for coverageArea only -> might introduce future problems
    default List<String> mapToList(JsonNullable<Map<String, String>> value) {
        return value != null && value.isPresent() && value.get() != null
            ? new ArrayList<>(value.get().keySet()) : new ArrayList<>();
    }

}
