package com.itav.slicemanager.models;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.itav.slicemanager.models.OperationComponentStatus;
import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.Nullable;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * Status returned for an asynchronous core operation.  Core write operations return a &#x60;request_id&#x60;.  Clients can use that UUID with &#x60;GET /operations/{request_id}&#x60; to follow progress until the state becomes &#x60;completed&#x60; or &#x60;failed&#x60;. 
 */

@Schema(name = "OperationStatusResponse", description = "Status returned for an asynchronous core operation.  Core write operations return a `request_id`.  Clients can use that UUID with `GET /operations/{request_id}` to follow progress until the state becomes `completed` or `failed`. ")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-08-21T10:29:42.258422300+01:00[Europe/Lisbon]", comments = "Generator version: 7.19.0")
public class OperationStatusResponse {

  private UUID requestId;

  /**
   * Current operation state.
   */
  public enum StateEnum {
    PENDING("pending"),
    
    PUBLISHED("published"),
    
    PROCESSING("processing"),
    
    COMPLETED("completed"),
    
    FAILED("failed");

    private final String value;

    StateEnum(String value) {
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
    public static StateEnum fromValue(String value) {
      for (StateEnum b : StateEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
  }

  private StateEnum state;

  private @Nullable String operation;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private @Nullable OffsetDateTime createdAt;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private @Nullable OffsetDateTime updatedAt;

  private @Nullable Integer durationMs;

  @Valid
  private Map<String, OperationComponentStatus> components = new HashMap<>();

  public OperationStatusResponse() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public OperationStatusResponse(UUID requestId, StateEnum state) {
    this.requestId = requestId;
    this.state = state;
  }

  public OperationStatusResponse requestId(UUID requestId) {
    this.requestId = requestId;
    return this;
  }

  /**
   * Identifier of the asynchronous operation request.
   * @return requestId
   */
  @NotNull @Valid 
  @Schema(name = "request_id", description = "Identifier of the asynchronous operation request.", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("request_id")
  public UUID getRequestId() {
    return requestId;
  }

  public void setRequestId(UUID requestId) {
    this.requestId = requestId;
  }

  public OperationStatusResponse state(StateEnum state) {
    this.state = state;
    return this;
  }

  /**
   * Current operation state.
   * @return state
   */
  @NotNull 
  @Schema(name = "state", description = "Current operation state.", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("state")
  public StateEnum getState() {
    return state;
  }

  public void setState(StateEnum state) {
    this.state = state;
  }

  public OperationStatusResponse operation(@Nullable String operation) {
    this.operation = operation;
    return this;
  }

  /**
   * Operation that was requested.
   * @return operation
   */
  
  @Schema(name = "operation", example = "create_slice", description = "Operation that was requested.", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("operation")
  public @Nullable String getOperation() {
    return operation;
  }

  public void setOperation(@Nullable String operation) {
    this.operation = operation;
  }

  public OperationStatusResponse createdAt(@Nullable OffsetDateTime createdAt) {
    this.createdAt = createdAt;
    return this;
  }

  /**
   * When the API received the request.
   * @return createdAt
   */
  @Valid 
  @Schema(name = "created_at", description = "When the API received the request.", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("created_at")
  public @Nullable OffsetDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(@Nullable OffsetDateTime createdAt) {
    this.createdAt = createdAt;
  }

  public OperationStatusResponse updatedAt(@Nullable OffsetDateTime updatedAt) {
    this.updatedAt = updatedAt;
    return this;
  }

  /**
   * When the operation last changed state.
   * @return updatedAt
   */
  @Valid 
  @Schema(name = "updated_at", description = "When the operation last changed state.", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("updated_at")
  public @Nullable OffsetDateTime getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(@Nullable OffsetDateTime updatedAt) {
    this.updatedAt = updatedAt;
  }

  public OperationStatusResponse durationMs(@Nullable Integer durationMs) {
    this.durationMs = durationMs;
    return this;
  }

  /**
   * Elapsed time in milliseconds, measured from the moment the API received the request.  For the terminal states `completed` and `failed` this is the total end-to-end time, including configuration of every network element. While the operation is still in flight it is the time elapsed so far, so it grows between polls. 
   * @return durationMs
   */
  
  @Schema(name = "duration_ms", description = "Elapsed time in milliseconds, measured from the moment the API received the request.  For the terminal states `completed` and `failed` this is the total end-to-end time, including configuration of every network element. While the operation is still in flight it is the time elapsed so far, so it grows between polls. ", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("duration_ms")
  public @Nullable Integer getDurationMs() {
    return durationMs;
  }

  public void setDurationMs(@Nullable Integer durationMs) {
    this.durationMs = durationMs;
  }

  public OperationStatusResponse components(Map<String, OperationComponentStatus> components) {
    this.components = components;
    return this;
  }

  public OperationStatusResponse putComponentsItem(String key, OperationComponentStatus componentsItem) {
    if (this.components == null) {
      this.components = new HashMap<>();
    }
    this.components.put(key, componentsItem);
    return this;
  }

  /**
   * Per-worker breakdown of the operation, keyed by component name (`core` for UDM/UDG/SMF/AMF, `ran` for the gNB).  The overall `state` aggregates these: `failed` if any component failed, `completed` only once every component completed. 
   * @return components
   */
  @Valid 
  @Schema(name = "components", description = "Per-worker breakdown of the operation, keyed by component name (`core` for UDM/UDG/SMF/AMF, `ran` for the gNB).  The overall `state` aggregates these: `failed` if any component failed, `completed` only once every component completed. ", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("components")
  public Map<String, OperationComponentStatus> getComponents() {
    return components;
  }

  public void setComponents(Map<String, OperationComponentStatus> components) {
    this.components = components;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    OperationStatusResponse operationStatusResponse = (OperationStatusResponse) o;
    return Objects.equals(this.requestId, operationStatusResponse.requestId) &&
        Objects.equals(this.state, operationStatusResponse.state) &&
        Objects.equals(this.operation, operationStatusResponse.operation) &&
        Objects.equals(this.createdAt, operationStatusResponse.createdAt) &&
        Objects.equals(this.updatedAt, operationStatusResponse.updatedAt) &&
        Objects.equals(this.durationMs, operationStatusResponse.durationMs) &&
        Objects.equals(this.components, operationStatusResponse.components);
  }

  @Override
  public int hashCode() {
    return Objects.hash(requestId, state, operation, createdAt, updatedAt, durationMs, components);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class OperationStatusResponse {\n");
    sb.append("    requestId: ").append(toIndentedString(requestId)).append("\n");
    sb.append("    state: ").append(toIndentedString(state)).append("\n");
    sb.append("    operation: ").append(toIndentedString(operation)).append("\n");
    sb.append("    createdAt: ").append(toIndentedString(createdAt)).append("\n");
    sb.append("    updatedAt: ").append(toIndentedString(updatedAt)).append("\n");
    sb.append("    durationMs: ").append(toIndentedString(durationMs)).append("\n");
    sb.append("    components: ").append(toIndentedString(components)).append("\n");
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

