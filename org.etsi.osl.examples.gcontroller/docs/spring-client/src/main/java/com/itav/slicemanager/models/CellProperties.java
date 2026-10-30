package com.itav.slicemanager.models;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import org.springframework.lang.Nullable;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * Mutable transmission properties for a cell.
 */

@Schema(name = "CellProperties", description = "Mutable transmission properties for a cell.")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-08-21T10:29:42.258422300+01:00[Europe/Lisbon]", comments = "Generator version: 7.19.0")
public class CellProperties {

  private @Nullable Boolean mimo;

  private @Nullable Integer maxTransmitPower;

  public CellProperties mimo(@Nullable Boolean mimo) {
    this.mimo = mimo;
    return this;
  }

  /**
   * Whether MIMO is enabled for the cell.
   * @return mimo
   */
  
  @Schema(name = "mimo", description = "Whether MIMO is enabled for the cell.", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("mimo")
  public @Nullable Boolean getMimo() {
    return mimo;
  }

  public void setMimo(@Nullable Boolean mimo) {
    this.mimo = mimo;
  }

  public CellProperties maxTransmitPower(@Nullable Integer maxTransmitPower) {
    this.maxTransmitPower = maxTransmitPower;
    return this;
  }

  /**
   * Maximum transmit power for the cell.
   * minimum: 70
   * maximum: 242
   * @return maxTransmitPower
   */
  @Min(value = 70) @Max(value = 242) 
  @Schema(name = "max_transmit_power", description = "Maximum transmit power for the cell.", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("max_transmit_power")
  public @Nullable Integer getMaxTransmitPower() {
    return maxTransmitPower;
  }

  public void setMaxTransmitPower(@Nullable Integer maxTransmitPower) {
    this.maxTransmitPower = maxTransmitPower;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CellProperties cellProperties = (CellProperties) o;
    return Objects.equals(this.mimo, cellProperties.mimo) &&
        Objects.equals(this.maxTransmitPower, cellProperties.maxTransmitPower);
  }

  @Override
  public int hashCode() {
    return Objects.hash(mimo, maxTransmitPower);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CellProperties {\n");
    sb.append("    mimo: ").append(toIndentedString(mimo)).append("\n");
    sb.append("    maxTransmitPower: ").append(toIndentedString(maxTransmitPower)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(@Nullable Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

