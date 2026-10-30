package com.itav.slicemanager.models;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import java.time.OffsetDateTime;
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
 * Outcome of a single worker&#39;s share of an asynchronous operation. 
 */

@Schema(name = "OperationComponentStatus", description = "Outcome of a single worker's share of an asynchronous operation. ")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-08-21T10:29:42.258422300+01:00[Europe/Lisbon]", comments = "Generator version: 7.19.0")
public class OperationComponentStatus {

  /**
   * Current state of this component's work.
   */
  public enum StateEnum {
    PENDING("pending"),
    
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

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private @Nullable OffsetDateTime startedAt;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private @Nullable OffsetDateTime finishedAt;

  private @Nullable Integer durationMs;

  private @Nullable String error;

  public OperationComponentStatus() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public OperationComponentStatus(StateEnum state) {
    this.state = state;
  }

  public OperationComponentStatus state(StateEnum state) {
    this.state = state;
    return this;
  }

  /**
   * Current state of this component's work.
   * @return state
   */
  @NotNull 
  @Schema(name = "state", description = "Current state of this component's work.", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("state")
  public StateEnum getState() {
    return state;
  }

  public void setState(StateEnum state) {
    this.state = state;
  }

  public OperationComponentStatus startedAt(@Nullable OffsetDateTime startedAt) {
    this.startedAt = startedAt;
    return this;
  }

  /**
   * When the worker picked the operation up.
   * @return startedAt
   */
  @Valid 
  @Schema(name = "started_at", description = "When the worker picked the operation up.", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("started_at")
  public @Nullable OffsetDateTime getStartedAt() {
    return startedAt;
  }

  public void setStartedAt(@Nullable OffsetDateTime startedAt) {
    this.startedAt = startedAt;
  }

  public OperationComponentStatus finishedAt(@Nullable OffsetDateTime finishedAt) {
    this.finishedAt = finishedAt;
    return this;
  }

  /**
   * When the worker finished applying its commands.
   * @return finishedAt
   */
  @Valid 
  @Schema(name = "finished_at", description = "When the worker finished applying its commands.", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("finished_at")
  public @Nullable OffsetDateTime getFinishedAt() {
    return finishedAt;
  }

  public void setFinishedAt(@Nullable OffsetDateTime finishedAt) {
    this.finishedAt = finishedAt;
  }

  public OperationComponentStatus durationMs(@Nullable Integer durationMs) {
    this.durationMs = durationMs;
    return this;
  }

  /**
   * Time this component spent applying its commands, in milliseconds.
   * @return durationMs
   */
  
  @Schema(name = "duration_ms", description = "Time this component spent applying its commands, in milliseconds.", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("duration_ms")
  public @Nullable Integer getDurationMs() {
    return durationMs;
  }

  public void setDurationMs(@Nullable Integer durationMs) {
    this.durationMs = durationMs;
  }

  public OperationComponentStatus error(@Nullable String error) {
    this.error = error;
    return this;
  }

  /**
   * Failure reason, present only when `state` is `failed`.
   * @return error
   */
  
  @Schema(name = "error", description = "Failure reason, present only when `state` is `failed`.", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("error")
  public @Nullable String getError() {
    return error;
  }

  public void setError(@Nullable String error) {
    this.error = error;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    OperationComponentStatus operationComponentStatus = (OperationComponentStatus) o;
    return Objects.equals(this.state, operationComponentStatus.state) &&
        Objects.equals(this.startedAt, operationComponentStatus.startedAt) &&
        Objects.equals(this.finishedAt, operationComponentStatus.finishedAt) &&
        Objects.equals(this.durationMs, operationComponentStatus.durationMs) &&
        Objects.equals(this.error, operationComponentStatus.error);
  }

  @Override
  public int hashCode() {
    return Objects.hash(state, startedAt, finishedAt, durationMs, error);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class OperationComponentStatus {\n");
    sb.append("    state: ").append(toIndentedString(state)).append("\n");
    sb.append("    startedAt: ").append(toIndentedString(startedAt)).append("\n");
    sb.append("    finishedAt: ").append(toIndentedString(finishedAt)).append("\n");
    sb.append("    durationMs: ").append(toIndentedString(durationMs)).append("\n");
    sb.append("    error: ").append(toIndentedString(error)).append("\n");
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

