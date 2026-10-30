package com.itav.slicemanager.models;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.itav.slicemanager.models.CellStatus;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.lang.Nullable;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * Response envelope containing cell statuses for one BBU.
 */

@Schema(name = "CellStatusListResponse", description = "Response envelope containing cell statuses for one BBU.")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-08-21T10:29:42.258422300+01:00[Europe/Lisbon]", comments = "Generator version: 7.19.0")
public class CellStatusListResponse {

  @Valid
  private List<@Valid CellStatus> cells = new ArrayList<>();

  public CellStatusListResponse() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public CellStatusListResponse(List<@Valid CellStatus> cells) {
    this.cells = cells;
  }

  public CellStatusListResponse cells(List<@Valid CellStatus> cells) {
    this.cells = cells;
    return this;
  }

  public CellStatusListResponse addCellsItem(CellStatus cellsItem) {
    if (this.cells == null) {
      this.cells = new ArrayList<>();
    }
    this.cells.add(cellsItem);
    return this;
  }

  /**
   * Get cells
   * @return cells
   */
  @NotNull @Valid 
  @Schema(name = "cells", example = "[{\"nr_cell_id\":151,\"frequency_band\":\"n78\",\"duplex_mode\":\"TDD\",\"user_label\":\"muttley\",\"activation_state\":\"Activated\",\"max_transmit_power\":242},{\"nr_cell_id\":152,\"frequency_band\":\"n78\",\"duplex_mode\":\"TDD\",\"user_label\":\"penelope\",\"activation_state\":\"Deactivated\",\"max_transmit_power\":70},{\"nr_cell_id\":153,\"frequency_band\":\"n78\",\"duplex_mode\":\"TDD\",\"user_label\":\"it\",\"activation_state\":\"Activated\",\"max_transmit_power\":180}]", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("cells")
  public List<@Valid CellStatus> getCells() {
    return cells;
  }

  public void setCells(List<@Valid CellStatus> cells) {
    this.cells = cells;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CellStatusListResponse cellStatusListResponse = (CellStatusListResponse) o;
    return Objects.equals(this.cells, cellStatusListResponse.cells);
  }

  @Override
  public int hashCode() {
    return Objects.hash(cells);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CellStatusListResponse {\n");
    sb.append("    cells: ").append(toIndentedString(cells)).append("\n");
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

