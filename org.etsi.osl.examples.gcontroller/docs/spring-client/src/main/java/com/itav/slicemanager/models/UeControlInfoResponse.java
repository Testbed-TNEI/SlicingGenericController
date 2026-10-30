package com.itav.slicemanager.models;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.itav.slicemanager.models.UeControlInfo;
import org.springframework.lang.Nullable;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * Response envelope containing one &#x60;UeControlInfo&#x60;.
 */

@Schema(name = "UeControlInfoResponse", description = "Response envelope containing one `UeControlInfo`.")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-08-21T10:29:42.258422300+01:00[Europe/Lisbon]", comments = "Generator version: 7.19.0")
public class UeControlInfoResponse {

  private UeControlInfo controlInfo;

  public UeControlInfoResponse() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public UeControlInfoResponse(UeControlInfo controlInfo) {
    this.controlInfo = controlInfo;
  }

  public UeControlInfoResponse controlInfo(UeControlInfo controlInfo) {
    this.controlInfo = controlInfo;
    return this;
  }

  /**
   * Get controlInfo
   * @return controlInfo
   */
  @NotNull @Valid 
  @Schema(name = "control_info", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("control_info")
  public UeControlInfo getControlInfo() {
    return controlInfo;
  }

  public void setControlInfo(UeControlInfo controlInfo) {
    this.controlInfo = controlInfo;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UeControlInfoResponse ueControlInfoResponse = (UeControlInfoResponse) o;
    return Objects.equals(this.controlInfo, ueControlInfoResponse.controlInfo);
  }

  @Override
  public int hashCode() {
    return Objects.hash(controlInfo);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class UeControlInfoResponse {\n");
    sb.append("    controlInfo: ").append(toIndentedString(controlInfo)).append("\n");
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

