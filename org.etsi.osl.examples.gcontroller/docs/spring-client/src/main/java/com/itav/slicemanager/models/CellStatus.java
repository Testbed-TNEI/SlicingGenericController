package com.itav.slicemanager.models;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import org.springframework.lang.Nullable;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * Current status and transmission properties for a cell.
 */

@Schema(name = "CellStatus", description = "Current status and transmission properties for a cell.")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-08-21T10:29:42.258422300+01:00[Europe/Lisbon]", comments = "Generator version: 7.19.0")
public class CellStatus {

  private Integer nrCellId;

  private String frequencyBand;

  private String duplexMode;

  private String userLabel;

  /**
   * Gets or Sets activationState
   */
  public enum ActivationStateEnum {
    ACTIVATED("Activated"),
    
    DEACTIVATED("Deactivated");

    private final String value;

    ActivationStateEnum(String value) {
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
    public static ActivationStateEnum fromValue(String value) {
      for (ActivationStateEnum b : ActivationStateEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
  }

  private ActivationStateEnum activationState;

  private Integer maxTransmitPower;

  public CellStatus() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public CellStatus(Integer nrCellId, String frequencyBand, String duplexMode, String userLabel, ActivationStateEnum activationState, Integer maxTransmitPower) {
    this.nrCellId = nrCellId;
    this.frequencyBand = frequencyBand;
    this.duplexMode = duplexMode;
    this.userLabel = userLabel;
    this.activationState = activationState;
    this.maxTransmitPower = maxTransmitPower;
  }

  public CellStatus nrCellId(Integer nrCellId) {
    this.nrCellId = nrCellId;
    return this;
  }

  /**
   * Get nrCellId
   * @return nrCellId
   */
  @NotNull 
  @Schema(name = "nr_cell_id", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("nr_cell_id")
  public Integer getNrCellId() {
    return nrCellId;
  }

  public void setNrCellId(Integer nrCellId) {
    this.nrCellId = nrCellId;
  }

  public CellStatus frequencyBand(String frequencyBand) {
    this.frequencyBand = frequencyBand;
    return this;
  }

  /**
   * Get frequencyBand
   * @return frequencyBand
   */
  @NotNull 
  @Schema(name = "frequency_band", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("frequency_band")
  public String getFrequencyBand() {
    return frequencyBand;
  }

  public void setFrequencyBand(String frequencyBand) {
    this.frequencyBand = frequencyBand;
  }

  public CellStatus duplexMode(String duplexMode) {
    this.duplexMode = duplexMode;
    return this;
  }

  /**
   * Get duplexMode
   * @return duplexMode
   */
  @NotNull 
  @Schema(name = "duplex_mode", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("duplex_mode")
  public String getDuplexMode() {
    return duplexMode;
  }

  public void setDuplexMode(String duplexMode) {
    this.duplexMode = duplexMode;
  }

  public CellStatus userLabel(String userLabel) {
    this.userLabel = userLabel;
    return this;
  }

  /**
   * Get userLabel
   * @return userLabel
   */
  @NotNull 
  @Schema(name = "user_label", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("user_label")
  public String getUserLabel() {
    return userLabel;
  }

  public void setUserLabel(String userLabel) {
    this.userLabel = userLabel;
  }

  public CellStatus activationState(ActivationStateEnum activationState) {
    this.activationState = activationState;
    return this;
  }

  /**
   * Get activationState
   * @return activationState
   */
  @NotNull 
  @Schema(name = "activation_state", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("activation_state")
  public ActivationStateEnum getActivationState() {
    return activationState;
  }

  public void setActivationState(ActivationStateEnum activationState) {
    this.activationState = activationState;
  }

  public CellStatus maxTransmitPower(Integer maxTransmitPower) {
    this.maxTransmitPower = maxTransmitPower;
    return this;
  }

  /**
   * Get maxTransmitPower
   * @return maxTransmitPower
   */
  @NotNull 
  @Schema(name = "max_transmit_power", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("max_transmit_power")
  public Integer getMaxTransmitPower() {
    return maxTransmitPower;
  }

  public void setMaxTransmitPower(Integer maxTransmitPower) {
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
    CellStatus cellStatus = (CellStatus) o;
    return Objects.equals(this.nrCellId, cellStatus.nrCellId) &&
        Objects.equals(this.frequencyBand, cellStatus.frequencyBand) &&
        Objects.equals(this.duplexMode, cellStatus.duplexMode) &&
        Objects.equals(this.userLabel, cellStatus.userLabel) &&
        Objects.equals(this.activationState, cellStatus.activationState) &&
        Objects.equals(this.maxTransmitPower, cellStatus.maxTransmitPower);
  }

  @Override
  public int hashCode() {
    return Objects.hash(nrCellId, frequencyBand, duplexMode, userLabel, activationState, maxTransmitPower);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CellStatus {\n");
    sb.append("    nrCellId: ").append(toIndentedString(nrCellId)).append("\n");
    sb.append("    frequencyBand: ").append(toIndentedString(frequencyBand)).append("\n");
    sb.append("    duplexMode: ").append(toIndentedString(duplexMode)).append("\n");
    sb.append("    userLabel: ").append(toIndentedString(userLabel)).append("\n");
    sb.append("    activationState: ").append(toIndentedString(activationState)).append("\n");
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

