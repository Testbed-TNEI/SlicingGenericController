package com.itav.slicemanager.models;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.itav.slicemanager.models.Bbu;
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
 * Response envelope containing all known BBUs.
 */

@Schema(name = "BbuListResponse", description = "Response envelope containing all known BBUs.")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-08-21T10:29:42.258422300+01:00[Europe/Lisbon]", comments = "Generator version: 7.19.0")
public class BbuListResponse {

  @Valid
  private List<@Valid Bbu> bbus = new ArrayList<>();

  public BbuListResponse() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public BbuListResponse(List<@Valid Bbu> bbus) {
    this.bbus = bbus;
  }

  public BbuListResponse bbus(List<@Valid Bbu> bbus) {
    this.bbus = bbus;
    return this;
  }

  public BbuListResponse addBbusItem(Bbu bbusItem) {
    if (this.bbus == null) {
      this.bbus = new ArrayList<>();
    }
    this.bbus.add(bbusItem);
    return this;
  }

  /**
   * Get bbus
   * @return bbus
   */
  @NotNull @Valid 
  @Schema(name = "bbus", example = "[{\"bbu_id\":\"bbu-99904\",\"location\":\"muttley\",\"plmn_id\":\"99904\",\"ip_address\":\"10.255.0.1\",\"status\":\"Available\"},{\"bbu_id\":\"bbu-99903\",\"location\":\"penelope\",\"plmn_id\":\"99903\",\"ip_address\":\"10.255.0.1\",\"status\":\"Available\"},{\"bbu_id\":\"aveiro-seaport\",\"location\":\"aveiro-seaport\",\"plmn_id\":\"99908\",\"ip_address\":\"10.255.0.1\",\"status\":\"Unavailable\"}]", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("bbus")
  public List<@Valid Bbu> getBbus() {
    return bbus;
  }

  public void setBbus(List<@Valid Bbu> bbus) {
    this.bbus = bbus;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    BbuListResponse bbuListResponse = (BbuListResponse) o;
    return Objects.equals(this.bbus, bbuListResponse.bbus);
  }

  @Override
  public int hashCode() {
    return Objects.hash(bbus);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class BbuListResponse {\n");
    sb.append("    bbus: ").append(toIndentedString(bbus)).append("\n");
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

