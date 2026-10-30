package com.itav.slicemanager.models;
import com.fasterxml.jackson.annotation.JsonValue;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.itav.slicemanager.models.CellProperties;
import org.springframework.lang.Nullable;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * Cell update payload.  Include &#x60;operation&#x60;, &#x60;properties&#x60;, or both. 
 */

@Schema(name = "CellUpdateRequest", description = "Cell update payload.  Include `operation`, `properties`, or both. ")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-08-21T10:29:42.258422300+01:00[Europe/Lisbon]", comments = "Generator version: 7.19.0")
public class CellUpdateRequest {

  /**
   * Allowed state operation for a cell.
   */
  public enum OperationEnum {
    ACTIVATE("activate"),
    
    DEACTIVATE("deactivate");

    private final String value;

    OperationEnum(String value) {
      this.value = value;
    }

    @JsonValue
    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static OperationEnum fromValue(String value) {
      for (OperationEnum b : OperationEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
  }

  private @Nullable OperationEnum operation;

  private @Nullable CellProperties properties;

  public CellUpdateRequest operation(@Nullable OperationEnum operation) {
    this.operation = operation;
    return this;
  }

  /**
   * Allowed state operation for a cell.
   * @return operation
   */
  
  @Schema(name = "operation", example = "activate", description = "Allowed state operation for a cell.", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("operation")
  public @Nullable OperationEnum getOperation() {
    return operation;
  }

  public void setOperation(@Nullable OperationEnum operation) {
    this.operation = operation;
  }

  public CellUpdateRequest properties(@Nullable CellProperties properties) {
    this.properties = properties;
    return this;
  }

  /**
   * Get properties
   * @return properties
   */
  @Valid 
  @Schema(name = "properties", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("properties")
  public @Nullable CellProperties getProperties() {
    return properties;
  }

  public void setProperties(@Nullable CellProperties properties) {
    this.properties = properties;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CellUpdateRequest cellUpdateRequest = (CellUpdateRequest) o;
    return Objects.equals(this.operation, cellUpdateRequest.operation) &&
        Objects.equals(this.properties, cellUpdateRequest.properties);
  }

  @Override
  public int hashCode() {
    return Objects.hash(operation, properties);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CellUpdateRequest {\n");
    sb.append("    operation: ").append(toIndentedString(operation)).append("\n");
    sb.append("    properties: ").append(toIndentedString(properties)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

