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
 * Immediate result returned by synchronous RAN write operations.  RAN operations target a single device and are treated as completed once the device accepts and applies the requested command. 
 */

@Schema(name = "OperationResultResponse", description = "Immediate result returned by synchronous RAN write operations.  RAN operations target a single device and are treated as completed once the device accepts and applies the requested command. ")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-08-21T10:29:42.258422300+01:00[Europe/Lisbon]", comments = "Generator version: 7.19.0")
public class OperationResultResponse {

  private String state;

  public OperationResultResponse() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public OperationResultResponse(String state) {
    this.state = state;
  }

  public OperationResultResponse state(String state) {
    this.state = state;
    return this;
  }

  /**
   * Final synchronous operation state1
   * @return state
   */
  @NotNull 
  @Schema(name = "state", description = "Final synchronous operation state1", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("state")
  public String getState() {
    return state;
  }

  public void setState(String state) {
    this.state = state;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    OperationResultResponse operationResultResponse = (OperationResultResponse) o;
    return Objects.equals(this.state, operationResultResponse.state);
  }

  @Override
  public int hashCode() {
    return Objects.hash(state);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class OperationResultResponse {\n");
    sb.append("    state: ").append(toIndentedString(state)).append("\n");
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

