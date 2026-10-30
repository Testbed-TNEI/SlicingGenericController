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
 * Observed UE slice association.  The association records the selected &#x60;dnn&#x60;, &#x60;snssai&#x60;, and &#x60;slice_id&#x60; values, plus the resolved UDM **DNN QoS template** identity when available. 
 */

@Schema(name = "UeSliceAssociation", description = "Observed UE slice association.  The association records the selected `dnn`, `snssai`, and `slice_id` values, plus the resolved UDM **DNN QoS template** identity when available. ")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-08-21T10:29:42.258422300+01:00[Europe/Lisbon]", comments = "Generator version: 7.19.0")
public class UeSliceAssociation {

  private String ueId;

  private String dnn;

  private String snssai;

  private String sliceId;

  private @Nullable Integer dnnQosTemplateId;

  private @Nullable String dnnQosTemplateName;

  private @Nullable String staticIpv4Address;

  private @Nullable Boolean accessMobilityData;

  private @Nullable Boolean snssaiAdvertisementAllowed;

  private @Nullable Integer uplinkAggregateMaximumBitrateKbps;

  private @Nullable Integer downlinkAggregateMaximumBitrateKbps;

  public UeSliceAssociation() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public UeSliceAssociation(String ueId, String dnn, String snssai, String sliceId) {
    this.ueId = ueId;
    this.dnn = dnn;
    this.snssai = snssai;
    this.sliceId = sliceId;
  }

  public UeSliceAssociation ueId(String ueId) {
    this.ueId = ueId;
    return this;
  }

  /**
   * IMSI encoded as exactly 15 decimal digits.
   * @return ueId
   */
  @NotNull @Pattern(regexp = "^[0-9]{15}$") @Size(min = 15, max = 15) 
  @Schema(name = "ue_id", example = "999080100001134", description = "IMSI encoded as exactly 15 decimal digits.", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("ue_id")
  public String getUeId() {
    return ueId;
  }

  public void setUeId(String ueId) {
    this.ueId = ueId;
  }

  public UeSliceAssociation dnn(String dnn) {
    this.dnn = dnn;
    return this;
  }

  /**
   * DNN associated with the UE.
   * @return dnn
   */
  @NotNull @Size(min = 3) 
  @Schema(name = "dnn", description = "DNN associated with the UE.", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("dnn")
  public String getDnn() {
    return dnn;
  }

  public void setDnn(String dnn) {
    this.dnn = dnn;
  }

  public UeSliceAssociation snssai(String snssai) {
    this.snssai = snssai;
    return this;
  }

  /**
   * S-NSSAI associated with the UE.
   * @return snssai
   */
  @NotNull @Pattern(regexp = "^[0-9]{1,3}-[0-9]{6}$") 
  @Schema(name = "snssai", description = "S-NSSAI associated with the UE.", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("snssai")
  public String getSnssai() {
    return snssai;
  }

  public void setSnssai(String snssai) {
    this.snssai = snssai;
  }

  public UeSliceAssociation sliceId(String sliceId) {
    this.sliceId = sliceId;
    return this;
  }

  /**
   * Slice identifier and QoS profile selector matching `Slice.slice_id`.
   * @return sliceId
   */
  @NotNull @Size(min = 3) 
  @Schema(name = "slice_id", description = "Slice identifier and QoS profile selector matching `Slice.slice_id`.", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("slice_id")
  public String getSliceId() {
    return sliceId;
  }

  public void setSliceId(String sliceId) {
    this.sliceId = sliceId;
  }

  public UeSliceAssociation dnnQosTemplateId(@Nullable Integer dnnQosTemplateId) {
    this.dnnQosTemplateId = dnnQosTemplateId;
    return this;
  }

  /**
   * Numeric UDM **DNN QoS template** identifier resolved from UDM state.
   * @return dnnQosTemplateId
   */
  
  @Schema(name = "dnn_qos_template_id", description = "Numeric UDM **DNN QoS template** identifier resolved from UDM state.", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("dnn_qos_template_id")
  public @Nullable Integer getDnnQosTemplateId() {
    return dnnQosTemplateId;
  }

  public void setDnnQosTemplateId(@Nullable Integer dnnQosTemplateId) {
    this.dnnQosTemplateId = dnnQosTemplateId;
  }

  public UeSliceAssociation dnnQosTemplateName(@Nullable String dnnQosTemplateName) {
    this.dnnQosTemplateName = dnnQosTemplateName;
    return this;
  }

  /**
   * UDM **DNN QoS template** name resolved from UDM state.
   * @return dnnQosTemplateName
   */
  
  @Schema(name = "dnn_qos_template_name", description = "UDM **DNN QoS template** name resolved from UDM state.", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("dnn_qos_template_name")
  public @Nullable String getDnnQosTemplateName() {
    return dnnQosTemplateName;
  }

  public void setDnnQosTemplateName(@Nullable String dnnQosTemplateName) {
    this.dnnQosTemplateName = dnnQosTemplateName;
  }

  public UeSliceAssociation staticIpv4Address(@Nullable String staticIpv4Address) {
    this.staticIpv4Address = staticIpv4Address;
    return this;
  }

  /**
   * Static IPv4 address assigned to the UE.
   * @return staticIpv4Address
   */
  
  @Schema(name = "static_ipv4_address", description = "Static IPv4 address assigned to the UE.", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("static_ipv4_address")
  public @Nullable String getStaticIpv4Address() {
    return staticIpv4Address;
  }

  public void setStaticIpv4Address(@Nullable String staticIpv4Address) {
    this.staticIpv4Address = staticIpv4Address;
  }

  public UeSliceAssociation accessMobilityData(@Nullable Boolean accessMobilityData) {
    this.accessMobilityData = accessMobilityData;
    return this;
  }

  /**
   * Whether UDM Access and Mobility data is configured for the UE.
   * @return accessMobilityData
   */
  
  @Schema(name = "access_mobility_data", description = "Whether UDM Access and Mobility data is configured for the UE.", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("access_mobility_data")
  public @Nullable Boolean getAccessMobilityData() {
    return accessMobilityData;
  }

  public void setAccessMobilityData(@Nullable Boolean accessMobilityData) {
    this.accessMobilityData = accessMobilityData;
  }

  public UeSliceAssociation snssaiAdvertisementAllowed(@Nullable Boolean snssaiAdvertisementAllowed) {
    this.snssaiAdvertisementAllowed = snssaiAdvertisementAllowed;
    return this;
  }

  /**
   * Whether the UE is allowed to receive the configured `snssai`.
   * @return snssaiAdvertisementAllowed
   */
  
  @Schema(name = "snssai_advertisement_allowed", description = "Whether the UE is allowed to receive the configured `snssai`.", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("snssai_advertisement_allowed")
  public @Nullable Boolean getSnssaiAdvertisementAllowed() {
    return snssaiAdvertisementAllowed;
  }

  public void setSnssaiAdvertisementAllowed(@Nullable Boolean snssaiAdvertisementAllowed) {
    this.snssaiAdvertisementAllowed = snssaiAdvertisementAllowed;
  }

  public UeSliceAssociation uplinkAggregateMaximumBitrateKbps(@Nullable Integer uplinkAggregateMaximumBitrateKbps) {
    this.uplinkAggregateMaximumBitrateKbps = uplinkAggregateMaximumBitrateKbps;
    return this;
  }

  /**
   * Uplink aggregate maximum bitrate for the UE, in kbps.
   * @return uplinkAggregateMaximumBitrateKbps
   */
  
  @Schema(name = "uplink_aggregate_maximum_bitrate_kbps", description = "Uplink aggregate maximum bitrate for the UE, in kbps.", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("uplink_aggregate_maximum_bitrate_kbps")
  public @Nullable Integer getUplinkAggregateMaximumBitrateKbps() {
    return uplinkAggregateMaximumBitrateKbps;
  }

  public void setUplinkAggregateMaximumBitrateKbps(@Nullable Integer uplinkAggregateMaximumBitrateKbps) {
    this.uplinkAggregateMaximumBitrateKbps = uplinkAggregateMaximumBitrateKbps;
  }

  public UeSliceAssociation downlinkAggregateMaximumBitrateKbps(@Nullable Integer downlinkAggregateMaximumBitrateKbps) {
    this.downlinkAggregateMaximumBitrateKbps = downlinkAggregateMaximumBitrateKbps;
    return this;
  }

  /**
   * Downlink aggregate maximum bitrate for the UE, in kbps.
   * @return downlinkAggregateMaximumBitrateKbps
   */
  
  @Schema(name = "downlink_aggregate_maximum_bitrate_kbps", description = "Downlink aggregate maximum bitrate for the UE, in kbps.", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("downlink_aggregate_maximum_bitrate_kbps")
  public @Nullable Integer getDownlinkAggregateMaximumBitrateKbps() {
    return downlinkAggregateMaximumBitrateKbps;
  }

  public void setDownlinkAggregateMaximumBitrateKbps(@Nullable Integer downlinkAggregateMaximumBitrateKbps) {
    this.downlinkAggregateMaximumBitrateKbps = downlinkAggregateMaximumBitrateKbps;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UeSliceAssociation ueSliceAssociation = (UeSliceAssociation) o;
    return Objects.equals(this.ueId, ueSliceAssociation.ueId) &&
        Objects.equals(this.dnn, ueSliceAssociation.dnn) &&
        Objects.equals(this.snssai, ueSliceAssociation.snssai) &&
        Objects.equals(this.sliceId, ueSliceAssociation.sliceId) &&
        Objects.equals(this.dnnQosTemplateId, ueSliceAssociation.dnnQosTemplateId) &&
        Objects.equals(this.dnnQosTemplateName, ueSliceAssociation.dnnQosTemplateName) &&
        Objects.equals(this.staticIpv4Address, ueSliceAssociation.staticIpv4Address) &&
        Objects.equals(this.accessMobilityData, ueSliceAssociation.accessMobilityData) &&
        Objects.equals(this.snssaiAdvertisementAllowed, ueSliceAssociation.snssaiAdvertisementAllowed) &&
        Objects.equals(this.uplinkAggregateMaximumBitrateKbps, ueSliceAssociation.uplinkAggregateMaximumBitrateKbps) &&
        Objects.equals(this.downlinkAggregateMaximumBitrateKbps, ueSliceAssociation.downlinkAggregateMaximumBitrateKbps);
  }

  @Override
  public int hashCode() {
    return Objects.hash(ueId, dnn, snssai, sliceId, dnnQosTemplateId, dnnQosTemplateName, staticIpv4Address, accessMobilityData, snssaiAdvertisementAllowed, uplinkAggregateMaximumBitrateKbps, downlinkAggregateMaximumBitrateKbps);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class UeSliceAssociation {\n");
    sb.append("    ueId: ").append(toIndentedString(ueId)).append("\n");
    sb.append("    dnn: ").append(toIndentedString(dnn)).append("\n");
    sb.append("    snssai: ").append(toIndentedString(snssai)).append("\n");
    sb.append("    sliceId: ").append(toIndentedString(sliceId)).append("\n");
    sb.append("    dnnQosTemplateId: ").append(toIndentedString(dnnQosTemplateId)).append("\n");
    sb.append("    dnnQosTemplateName: ").append(toIndentedString(dnnQosTemplateName)).append("\n");
    sb.append("    staticIpv4Address: ").append(toIndentedString(staticIpv4Address)).append("\n");
    sb.append("    accessMobilityData: ").append(toIndentedString(accessMobilityData)).append("\n");
    sb.append("    snssaiAdvertisementAllowed: ").append(toIndentedString(snssaiAdvertisementAllowed)).append("\n");
    sb.append("    uplinkAggregateMaximumBitrateKbps: ").append(toIndentedString(uplinkAggregateMaximumBitrateKbps)).append("\n");
    sb.append("    downlinkAggregateMaximumBitrateKbps: ").append(toIndentedString(downlinkAggregateMaximumBitrateKbps)).append("\n");
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

