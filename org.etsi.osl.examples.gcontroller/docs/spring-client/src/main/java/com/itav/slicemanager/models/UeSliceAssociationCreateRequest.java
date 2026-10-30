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
 * Client request for associating a UE with a slice profile.  The &#x60;dnn&#x60;, &#x60;snssai&#x60;, and &#x60;slice_id&#x60; fields select the UDM **DNN QoS template** to assign to the UE. 
 */

@Schema(name = "UeSliceAssociationCreateRequest", description = "Client request for associating a UE with a slice profile.  The `dnn`, `snssai`, and `slice_id` fields select the UDM **DNN QoS template** to assign to the UE. ")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-08-21T10:29:42.258422300+01:00[Europe/Lisbon]", comments = "Generator version: 7.19.0")
public class UeSliceAssociationCreateRequest {

  private String dnn;

  private String snssai;

  private String sliceId;

  private @Nullable String staticIpv4Address;

  private @Nullable Boolean accessMobilityData;

  private @Nullable Boolean snssaiAdvertisementAllowed;

  private @Nullable Integer uplinkAggregateMaximumBitrateKbps;

  private @Nullable Integer downlinkAggregateMaximumBitrateKbps;

  public UeSliceAssociationCreateRequest() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public UeSliceAssociationCreateRequest(String dnn, String snssai, String sliceId) {
    this.dnn = dnn;
    this.snssai = snssai;
    this.sliceId = sliceId;
  }

  public UeSliceAssociationCreateRequest dnn(String dnn) {
    this.dnn = dnn;
    return this;
  }

  /**
   * `dnn` value used to select the UDM **DNN QoS template**.
   * @return dnn
   */
  @NotNull @Size(min = 3) 
  @Schema(name = "dnn", description = "`dnn` value used to select the UDM **DNN QoS template**.", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("dnn")
  public String getDnn() {
    return dnn;
  }

  public void setDnn(String dnn) {
    this.dnn = dnn;
  }

  public UeSliceAssociationCreateRequest snssai(String snssai) {
    this.snssai = snssai;
    return this;
  }

  /**
   * `snssai` value used to select the UDM **DNN QoS template**.
   * @return snssai
   */
  @NotNull @Pattern(regexp = "^[0-9]{1,3}-[0-9]{6}$") 
  @Schema(name = "snssai", description = "`snssai` value used to select the UDM **DNN QoS template**.", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("snssai")
  public String getSnssai() {
    return snssai;
  }

  public void setSnssai(String snssai) {
    this.snssai = snssai;
  }

  public UeSliceAssociationCreateRequest sliceId(String sliceId) {
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

  public UeSliceAssociationCreateRequest staticIpv4Address(@Nullable String staticIpv4Address) {
    this.staticIpv4Address = staticIpv4Address;
    return this;
  }

  /**
   * Static IPv4 address assigned to the UE.
   * @return staticIpv4Address
   */
  @Pattern(regexp = "^((25[0-5]|(2[0-4]|1\\d|[1-9]|)\\d)\\.?\\b){4}$") 
  @Schema(name = "static_ipv4_address", example = "10.16.9.5", description = "Static IPv4 address assigned to the UE.", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("static_ipv4_address")
  public @Nullable String getStaticIpv4Address() {
    return staticIpv4Address;
  }

  public void setStaticIpv4Address(@Nullable String staticIpv4Address) {
    this.staticIpv4Address = staticIpv4Address;
  }

  public UeSliceAssociationCreateRequest accessMobilityData(@Nullable Boolean accessMobilityData) {
    this.accessMobilityData = accessMobilityData;
    return this;
  }

  /**
   * Whether to create or update UDM Access and Mobility data for the UE.
   * @return accessMobilityData
   */
  
  @Schema(name = "access_mobility_data", description = "Whether to create or update UDM Access and Mobility data for the UE.", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("access_mobility_data")
  public @Nullable Boolean getAccessMobilityData() {
    return accessMobilityData;
  }

  public void setAccessMobilityData(@Nullable Boolean accessMobilityData) {
    this.accessMobilityData = accessMobilityData;
  }

  public UeSliceAssociationCreateRequest snssaiAdvertisementAllowed(@Nullable Boolean snssaiAdvertisementAllowed) {
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

  public UeSliceAssociationCreateRequest uplinkAggregateMaximumBitrateKbps(@Nullable Integer uplinkAggregateMaximumBitrateKbps) {
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

  public UeSliceAssociationCreateRequest downlinkAggregateMaximumBitrateKbps(@Nullable Integer downlinkAggregateMaximumBitrateKbps) {
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
    UeSliceAssociationCreateRequest ueSliceAssociationCreateRequest = (UeSliceAssociationCreateRequest) o;
    return Objects.equals(this.dnn, ueSliceAssociationCreateRequest.dnn) &&
        Objects.equals(this.snssai, ueSliceAssociationCreateRequest.snssai) &&
        Objects.equals(this.sliceId, ueSliceAssociationCreateRequest.sliceId) &&
        Objects.equals(this.staticIpv4Address, ueSliceAssociationCreateRequest.staticIpv4Address) &&
        Objects.equals(this.accessMobilityData, ueSliceAssociationCreateRequest.accessMobilityData) &&
        Objects.equals(this.snssaiAdvertisementAllowed, ueSliceAssociationCreateRequest.snssaiAdvertisementAllowed) &&
        Objects.equals(this.uplinkAggregateMaximumBitrateKbps, ueSliceAssociationCreateRequest.uplinkAggregateMaximumBitrateKbps) &&
        Objects.equals(this.downlinkAggregateMaximumBitrateKbps, ueSliceAssociationCreateRequest.downlinkAggregateMaximumBitrateKbps);
  }

  @Override
  public int hashCode() {
    return Objects.hash(dnn, snssai, sliceId, staticIpv4Address, accessMobilityData, snssaiAdvertisementAllowed, uplinkAggregateMaximumBitrateKbps, downlinkAggregateMaximumBitrateKbps);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class UeSliceAssociationCreateRequest {\n");
    sb.append("    dnn: ").append(toIndentedString(dnn)).append("\n");
    sb.append("    snssai: ").append(toIndentedString(snssai)).append("\n");
    sb.append("    sliceId: ").append(toIndentedString(sliceId)).append("\n");
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

