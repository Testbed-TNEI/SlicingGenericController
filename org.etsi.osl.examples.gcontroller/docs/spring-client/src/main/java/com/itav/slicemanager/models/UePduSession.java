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
 * UE PDU session, read from the SMF via &#x60;DSP PDUSESSION&#x60;. 
 */

@Schema(name = "UePduSession", description = "UE PDU session, read from the SMF via `DSP PDUSESSION`. ")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-08-21T10:29:42.258422300+01:00[Europe/Lisbon]", comments = "Generator version: 7.19.0")
public class UePduSession {

  private @Nullable String imsi;

  private @Nullable String dnn;

  private @Nullable String fiveQi;

  private @Nullable String qosFlowArpPriorityLevel;

  private @Nullable String snssai;

  private @Nullable String qosFlow5qiPriorityLevel;

  private @Nullable String ambrUplink;

  private @Nullable String ambrDownlink;

  private @Nullable String pduSessionType;

  private @Nullable String sscMode;

  private @Nullable String userIpType;

  private @Nullable String userIp;

  public UePduSession imsi(@Nullable String imsi) {
    this.imsi = imsi;
    return this;
  }

  /**
   * Get imsi
   * @return imsi
   */
  
  @Schema(name = "imsi", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("imsi")
  public @Nullable String getImsi() {
    return imsi;
  }

  public void setImsi(@Nullable String imsi) {
    this.imsi = imsi;
  }

  public UePduSession dnn(@Nullable String dnn) {
    this.dnn = dnn;
    return this;
  }

  /**
   * APN or DNN.
   * @return dnn
   */
  
  @Schema(name = "dnn", description = "APN or DNN.", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("dnn")
  public @Nullable String getDnn() {
    return dnn;
  }

  public void setDnn(@Nullable String dnn) {
    this.dnn = dnn;
  }

  public UePduSession fiveQi(@Nullable String fiveQi) {
    this.fiveQi = fiveQi;
    return this;
  }

  /**
   * QCI or 5QI.
   * @return fiveQi
   */
  
  @Schema(name = "five_qi", description = "QCI or 5QI.", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("five_qi")
  public @Nullable String getFiveQi() {
    return fiveQi;
  }

  public void setFiveQi(@Nullable String fiveQi) {
    this.fiveQi = fiveQi;
  }

  public UePduSession qosFlowArpPriorityLevel(@Nullable String qosFlowArpPriorityLevel) {
    this.qosFlowArpPriorityLevel = qosFlowArpPriorityLevel;
    return this;
  }

  /**
   * Get qosFlowArpPriorityLevel
   * @return qosFlowArpPriorityLevel
   */
  
  @Schema(name = "qos_flow_arp_priority_level", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("qos_flow_arp_priority_level")
  public @Nullable String getQosFlowArpPriorityLevel() {
    return qosFlowArpPriorityLevel;
  }

  public void setQosFlowArpPriorityLevel(@Nullable String qosFlowArpPriorityLevel) {
    this.qosFlowArpPriorityLevel = qosFlowArpPriorityLevel;
  }

  public UePduSession snssai(@Nullable String snssai) {
    this.snssai = snssai;
    return this;
  }

  /**
   * SNssaiSst/SNssaiSd.
   * @return snssai
   */
  
  @Schema(name = "snssai", description = "SNssaiSst/SNssaiSd.", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("snssai")
  public @Nullable String getSnssai() {
    return snssai;
  }

  public void setSnssai(@Nullable String snssai) {
    this.snssai = snssai;
  }

  public UePduSession qosFlow5qiPriorityLevel(@Nullable String qosFlow5qiPriorityLevel) {
    this.qosFlow5qiPriorityLevel = qosFlow5qiPriorityLevel;
    return this;
  }

  /**
   * Get qosFlow5qiPriorityLevel
   * @return qosFlow5qiPriorityLevel
   */
  
  @Schema(name = "qos_flow_5qi_priority_level", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("qos_flow_5qi_priority_level")
  public @Nullable String getQosFlow5qiPriorityLevel() {
    return qosFlow5qiPriorityLevel;
  }

  public void setQosFlow5qiPriorityLevel(@Nullable String qosFlow5qiPriorityLevel) {
    this.qosFlow5qiPriorityLevel = qosFlow5qiPriorityLevel;
  }

  public UePduSession ambrUplink(@Nullable String ambrUplink) {
    this.ambrUplink = ambrUplink;
    return this;
  }

  /**
   * Negotiated session AMBR uplink (detailed view only).
   * @return ambrUplink
   */
  
  @Schema(name = "ambr_uplink", description = "Negotiated session AMBR uplink (detailed view only).", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("ambr_uplink")
  public @Nullable String getAmbrUplink() {
    return ambrUplink;
  }

  public void setAmbrUplink(@Nullable String ambrUplink) {
    this.ambrUplink = ambrUplink;
  }

  public UePduSession ambrDownlink(@Nullable String ambrDownlink) {
    this.ambrDownlink = ambrDownlink;
    return this;
  }

  /**
   * Negotiated session AMBR downlink (detailed view only).
   * @return ambrDownlink
   */
  
  @Schema(name = "ambr_downlink", description = "Negotiated session AMBR downlink (detailed view only).", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("ambr_downlink")
  public @Nullable String getAmbrDownlink() {
    return ambrDownlink;
  }

  public void setAmbrDownlink(@Nullable String ambrDownlink) {
    this.ambrDownlink = ambrDownlink;
  }

  public UePduSession pduSessionType(@Nullable String pduSessionType) {
    this.pduSessionType = pduSessionType;
    return this;
  }

  /**
   * Get pduSessionType
   * @return pduSessionType
   */
  
  @Schema(name = "pdu_session_type", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("pdu_session_type")
  public @Nullable String getPduSessionType() {
    return pduSessionType;
  }

  public void setPduSessionType(@Nullable String pduSessionType) {
    this.pduSessionType = pduSessionType;
  }

  public UePduSession sscMode(@Nullable String sscMode) {
    this.sscMode = sscMode;
    return this;
  }

  /**
   * Get sscMode
   * @return sscMode
   */
  
  @Schema(name = "ssc_mode", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("ssc_mode")
  public @Nullable String getSscMode() {
    return sscMode;
  }

  public void setSscMode(@Nullable String sscMode) {
    this.sscMode = sscMode;
  }

  public UePduSession userIpType(@Nullable String userIpType) {
    this.userIpType = userIpType;
    return this;
  }

  /**
   * Get userIpType
   * @return userIpType
   */
  
  @Schema(name = "user_ip_type", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("user_ip_type")
  public @Nullable String getUserIpType() {
    return userIpType;
  }

  public void setUserIpType(@Nullable String userIpType) {
    this.userIpType = userIpType;
  }

  public UePduSession userIp(@Nullable String userIp) {
    this.userIp = userIp;
    return this;
  }

  /**
   * Get userIp
   * @return userIp
   */
  
  @Schema(name = "user_ip", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("user_ip")
  public @Nullable String getUserIp() {
    return userIp;
  }

  public void setUserIp(@Nullable String userIp) {
    this.userIp = userIp;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UePduSession uePduSession = (UePduSession) o;
    return Objects.equals(this.imsi, uePduSession.imsi) &&
        Objects.equals(this.dnn, uePduSession.dnn) &&
        Objects.equals(this.fiveQi, uePduSession.fiveQi) &&
        Objects.equals(this.qosFlowArpPriorityLevel, uePduSession.qosFlowArpPriorityLevel) &&
        Objects.equals(this.snssai, uePduSession.snssai) &&
        Objects.equals(this.qosFlow5qiPriorityLevel, uePduSession.qosFlow5qiPriorityLevel) &&
        Objects.equals(this.ambrUplink, uePduSession.ambrUplink) &&
        Objects.equals(this.ambrDownlink, uePduSession.ambrDownlink) &&
        Objects.equals(this.pduSessionType, uePduSession.pduSessionType) &&
        Objects.equals(this.sscMode, uePduSession.sscMode) &&
        Objects.equals(this.userIpType, uePduSession.userIpType) &&
        Objects.equals(this.userIp, uePduSession.userIp);
  }

  @Override
  public int hashCode() {
    return Objects.hash(imsi, dnn, fiveQi, qosFlowArpPriorityLevel, snssai, qosFlow5qiPriorityLevel, ambrUplink, ambrDownlink, pduSessionType, sscMode, userIpType, userIp);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class UePduSession {\n");
    sb.append("    imsi: ").append(toIndentedString(imsi)).append("\n");
    sb.append("    dnn: ").append(toIndentedString(dnn)).append("\n");
    sb.append("    fiveQi: ").append(toIndentedString(fiveQi)).append("\n");
    sb.append("    qosFlowArpPriorityLevel: ").append(toIndentedString(qosFlowArpPriorityLevel)).append("\n");
    sb.append("    snssai: ").append(toIndentedString(snssai)).append("\n");
    sb.append("    qosFlow5qiPriorityLevel: ").append(toIndentedString(qosFlow5qiPriorityLevel)).append("\n");
    sb.append("    ambrUplink: ").append(toIndentedString(ambrUplink)).append("\n");
    sb.append("    ambrDownlink: ").append(toIndentedString(ambrDownlink)).append("\n");
    sb.append("    pduSessionType: ").append(toIndentedString(pduSessionType)).append("\n");
    sb.append("    sscMode: ").append(toIndentedString(sscMode)).append("\n");
    sb.append("    userIpType: ").append(toIndentedString(userIpType)).append("\n");
    sb.append("    userIp: ").append(toIndentedString(userIp)).append("\n");
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

