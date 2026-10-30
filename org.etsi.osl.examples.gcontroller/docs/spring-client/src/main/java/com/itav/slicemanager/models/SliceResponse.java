package com.itav.slicemanager.models;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.itav.slicemanager.models.Slice;
import org.springframework.lang.Nullable;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * Response envelope containing one &#x60;Slice&#x60;.
 */

@Schema(name = "SliceResponse", description = "Response envelope containing one `Slice`.")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-08-21T10:29:42.258422300+01:00[Europe/Lisbon]", comments = "Generator version: 7.19.0")
public class SliceResponse {

  private Slice slice;

  public SliceResponse() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public SliceResponse(Slice slice) {
    this.slice = slice;
  }

  public SliceResponse slice(Slice slice) {
    this.slice = slice;
    return this;
  }

  /**
   * Get slice
   * @return slice
   */
  @NotNull @Valid 
  @Schema(name = "slice", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("slice")
  public Slice getSlice() {
    return slice;
  }

  public void setSlice(Slice slice) {
    this.slice = slice;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SliceResponse sliceResponse = (SliceResponse) o;
    return Objects.equals(this.slice, sliceResponse.slice);
  }

  @Override
  public int hashCode() {
    return Objects.hash(slice);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class SliceResponse {\n");
    sb.append("    slice: ").append(toIndentedString(slice)).append("\n");
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

