package com.itav.slicemanager.models;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.itav.slicemanager.models.CellStatus;
import org.springframework.lang.Nullable;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * Response envelope containing one cell status.
 */

@Schema(name = "CellStatusResponse", description = "Response envelope containing one cell status.")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-08-21T10:29:42.258422300+01:00[Europe/Lisbon]", comments = "Generator version: 7.19.0")
public class CellStatusResponse {

  private CellStatus cell;

  public CellStatusResponse() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public CellStatusResponse(CellStatus cell) {
    this.cell = cell;
  }

  public CellStatusResponse cell(CellStatus cell) {
    this.cell = cell;
    return this;
  }

  /**
   * Get cell
   * @return cell
   */
  @NotNull @Valid 
  @Schema(name = "cell", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("cell")
  public CellStatus getCell() {
    return cell;
  }

  public void setCell(CellStatus cell) {
    this.cell = cell;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CellStatusResponse cellStatusResponse = (CellStatusResponse) o;
    return Objects.equals(this.cell, cellStatusResponse.cell);
  }

  @Override
  public int hashCode() {
    return Objects.hash(cell);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CellStatusResponse {\n");
    sb.append("    cell: ").append(toIndentedString(cell)).append("\n");
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

