package com.itav.slicemanager.models;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.itav.slicemanager.models.Slice;
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
 * Response envelope containing multiple &#x60;Slice&#x60; entries.  A response may include several slices with the same &#x60;dnn&#x60; and &#x60;snssai&#x60; and different &#x60;slice_id&#x60; values. Each &#x60;slice_id&#x60; selects a different **DNN QoS template**. 
 */

@Schema(name = "SliceListResponse", description = "Response envelope containing multiple `Slice` entries.  A response may include several slices with the same `dnn` and `snssai` and different `slice_id` values. Each `slice_id` selects a different **DNN QoS template**. ")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-08-21T10:29:42.258422300+01:00[Europe/Lisbon]", comments = "Generator version: 7.19.0")
public class SliceListResponse {

  @Valid
  private List<@Valid Slice> slices = new ArrayList<>();

  public SliceListResponse() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public SliceListResponse(List<@Valid Slice> slices) {
    this.slices = slices;
  }

  public SliceListResponse slices(List<@Valid Slice> slices) {
    this.slices = slices;
    return this;
  }

  public SliceListResponse addSlicesItem(Slice slicesItem) {
    if (this.slices == null) {
      this.slices = new ArrayList<>();
    }
    this.slices.add(slicesItem);
    return this;
  }

  /**
   * Get slices
   * @return slices
   */
  @NotNull @Valid 
  @Schema(name = "slices", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("slices")
  public List<@Valid Slice> getSlices() {
    return slices;
  }

  public void setSlices(List<@Valid Slice> slices) {
    this.slices = slices;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SliceListResponse sliceListResponse = (SliceListResponse) o;
    return Objects.equals(this.slices, sliceListResponse.slices);
  }

  @Override
  public int hashCode() {
    return Objects.hash(slices);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class SliceListResponse {\n");
    sb.append("    slices: ").append(toIndentedString(slices)).append("\n");
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

