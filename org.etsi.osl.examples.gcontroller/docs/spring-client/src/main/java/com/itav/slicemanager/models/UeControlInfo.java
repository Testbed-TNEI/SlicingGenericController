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
 * UE NG mobility-management context, read from the AMF via &#x60;DSP NGMMCTX&#x60;. 
 */

@Schema(name = "UeControlInfo", description = "UE NG mobility-management context, read from the AMF via `DSP NGMMCTX`. ")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-08-21T10:29:42.258422300+01:00[Europe/Lisbon]", comments = "Generator version: 7.19.0")
public class UeControlInfo {

  private @Nullable String guti;

  private @Nullable Long fiveGMTmsi;

  private @Nullable String property;

  private @Nullable String mmStatus;

  private @Nullable String state;

  private @Nullable String nssai;

  private @Nullable Integer nrCellId;

  public UeControlInfo guti(@Nullable String guti) {
    this.guti = guti;
    return this;
  }

  /**
   * Globally Unique Temporary Identifier assigned to the UE.
   * @return guti
   */
  
  @Schema(name = "guti", description = "Globally Unique Temporary Identifier assigned to the UE.", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("guti")
  public @Nullable String getGuti() {
    return guti;
  }

  public void setGuti(@Nullable String guti) {
    this.guti = guti;
  }

  public UeControlInfo fiveGMTmsi(@Nullable Long fiveGMTmsi) {
    this.fiveGMTmsi = fiveGMTmsi;
    return this;
  }

  /**
   * 5G-mTMSI, decoded from the trailing bytes of the GUTI.
   * @return fiveGMTmsi
   */
  
  @Schema(name = "five_g_m_tmsi", description = "5G-mTMSI, decoded from the trailing bytes of the GUTI.", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("five_g_m_tmsi")
  public @Nullable Long getFiveGMTmsi() {
    return fiveGMTmsi;
  }

  public void setFiveGMTmsi(@Nullable Long fiveGMTmsi) {
    this.fiveGMTmsi = fiveGMTmsi;
  }

  public UeControlInfo property(@Nullable String property) {
    this.property = property;
    return this;
  }

  /**
   * Get property
   * @return property
   */
  
  @Schema(name = "property", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("property")
  public @Nullable String getProperty() {
    return property;
  }

  public void setProperty(@Nullable String property) {
    this.property = property;
  }

  public UeControlInfo mmStatus(@Nullable String mmStatus) {
    this.mmStatus = mmStatus;
    return this;
  }

  /**
   * Mobility-management status.
   * @return mmStatus
   */
  
  @Schema(name = "mm_status", description = "Mobility-management status.", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("mm_status")
  public @Nullable String getMmStatus() {
    return mmStatus;
  }

  public void setMmStatus(@Nullable String mmStatus) {
    this.mmStatus = mmStatus;
  }

  public UeControlInfo state(@Nullable String state) {
    this.state = state;
    return this;
  }

  /**
   * Registration / connection state.
   * @return state
   */
  
  @Schema(name = "state", description = "Registration / connection state.", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("state")
  public @Nullable String getState() {
    return state;
  }

  public void setState(@Nullable String state) {
    this.state = state;
  }

  public UeControlInfo nssai(@Nullable String nssai) {
    this.nssai = nssai;
    return this;
  }

  /**
   * Allowed NSSAI for the UE.
   * @return nssai
   */
  
  @Schema(name = "nssai", description = "Allowed NSSAI for the UE.", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("nssai")
  public @Nullable String getNssai() {
    return nssai;
  }

  public void setNssai(@Nullable String nssai) {
    this.nssai = nssai;
  }

  public UeControlInfo nrCellId(@Nullable Integer nrCellId) {
    this.nrCellId = nrCellId;
    return this;
  }

  /**
   * Serving NR Cell ID, decoded from the trailing bytes.
   * @return nrCellId
   */
  
  @Schema(name = "nr_cell_id", description = "Serving NR Cell ID, decoded from the trailing bytes.", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("nr_cell_id")
  public @Nullable Integer getNrCellId() {
    return nrCellId;
  }

  public void setNrCellId(@Nullable Integer nrCellId) {
    this.nrCellId = nrCellId;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UeControlInfo ueControlInfo = (UeControlInfo) o;
    return Objects.equals(this.guti, ueControlInfo.guti) &&
        Objects.equals(this.fiveGMTmsi, ueControlInfo.fiveGMTmsi) &&
        Objects.equals(this.property, ueControlInfo.property) &&
        Objects.equals(this.mmStatus, ueControlInfo.mmStatus) &&
        Objects.equals(this.state, ueControlInfo.state) &&
        Objects.equals(this.nssai, ueControlInfo.nssai) &&
        Objects.equals(this.nrCellId, ueControlInfo.nrCellId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(guti, fiveGMTmsi, property, mmStatus, state, nssai, nrCellId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class UeControlInfo {\n");
    sb.append("    guti: ").append(toIndentedString(guti)).append("\n");
    sb.append("    fiveGMTmsi: ").append(toIndentedString(fiveGMTmsi)).append("\n");
    sb.append("    property: ").append(toIndentedString(property)).append("\n");
    sb.append("    mmStatus: ").append(toIndentedString(mmStatus)).append("\n");
    sb.append("    state: ").append(toIndentedString(state)).append("\n");
    sb.append("    nssai: ").append(toIndentedString(nssai)).append("\n");
    sb.append("    nrCellId: ").append(toIndentedString(nrCellId)).append("\n");
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

