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
 * N6 protection rule applied to traffic leaving the core network toward the data network.  Use this object to describe firewall-style pass/block behavior and optional QoS rate limits for a server, port range, and protocol. 
 */

@Schema(name = "SliceN6ProtectionRule", description = "N6 protection rule applied to traffic leaving the core network toward the data network.  Use this object to describe firewall-style pass/block behavior and optional QoS rate limits for a server, port range, and protocol. ")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-08-21T10:29:42.258422300+01:00[Europe/Lisbon]", comments = "Generator version: 7.19.0")
public class SliceN6ProtectionRule {

  private @Nullable String type;

  private @Nullable String name;

  private @Nullable Integer priority;

  private @Nullable String serverIp;

  private @Nullable Integer maskLen;

  private @Nullable Integer startPort;

  private @Nullable Integer endPort;

  private @Nullable Integer uplinkGuaranteedBitrateKbps;

  private @Nullable Integer downlinkGuaranteedBitrateKbps;

  private @Nullable Integer uplinkMaximumBitrateKbps;

  private @Nullable Integer downlinkMaximumBitrateKbps;

  private @Nullable String protocol;

  private @Nullable String passBlock;

  public SliceN6ProtectionRule type(@Nullable String type) {
    this.type = type;
    return this;
  }

  /**
   * Get type
   * @return type
   */
  
  @Schema(name = "type", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("type")
  public @Nullable String getType() {
    return type;
  }

  public void setType(@Nullable String type) {
    this.type = type;
  }

  public SliceN6ProtectionRule name(@Nullable String name) {
    this.name = name;
    return this;
  }

  /**
   * Get name
   * @return name
   */
  
  @Schema(name = "name", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("name")
  public @Nullable String getName() {
    return name;
  }

  public void setName(@Nullable String name) {
    this.name = name;
  }

  public SliceN6ProtectionRule priority(@Nullable Integer priority) {
    this.priority = priority;
    return this;
  }

  /**
   * Get priority
   * @return priority
   */
  
  @Schema(name = "priority", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("priority")
  public @Nullable Integer getPriority() {
    return priority;
  }

  public void setPriority(@Nullable Integer priority) {
    this.priority = priority;
  }

  public SliceN6ProtectionRule serverIp(@Nullable String serverIp) {
    this.serverIp = serverIp;
    return this;
  }

  /**
   * Get serverIp
   * @return serverIp
   */
  
  @Schema(name = "server_ip", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("server_ip")
  public @Nullable String getServerIp() {
    return serverIp;
  }

  public void setServerIp(@Nullable String serverIp) {
    this.serverIp = serverIp;
  }

  public SliceN6ProtectionRule maskLen(@Nullable Integer maskLen) {
    this.maskLen = maskLen;
    return this;
  }

  /**
   * Get maskLen
   * @return maskLen
   */
  
  @Schema(name = "mask_len", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("mask_len")
  public @Nullable Integer getMaskLen() {
    return maskLen;
  }

  public void setMaskLen(@Nullable Integer maskLen) {
    this.maskLen = maskLen;
  }

  public SliceN6ProtectionRule startPort(@Nullable Integer startPort) {
    this.startPort = startPort;
    return this;
  }

  /**
   * Get startPort
   * @return startPort
   */
  
  @Schema(name = "start_port", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("start_port")
  public @Nullable Integer getStartPort() {
    return startPort;
  }

  public void setStartPort(@Nullable Integer startPort) {
    this.startPort = startPort;
  }

  public SliceN6ProtectionRule endPort(@Nullable Integer endPort) {
    this.endPort = endPort;
    return this;
  }

  /**
   * Get endPort
   * @return endPort
   */
  
  @Schema(name = "end_port", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("end_port")
  public @Nullable Integer getEndPort() {
    return endPort;
  }

  public void setEndPort(@Nullable Integer endPort) {
    this.endPort = endPort;
  }

  public SliceN6ProtectionRule uplinkGuaranteedBitrateKbps(@Nullable Integer uplinkGuaranteedBitrateKbps) {
    this.uplinkGuaranteedBitrateKbps = uplinkGuaranteedBitrateKbps;
    return this;
  }

  /**
   * Uplink guaranteed bit rate, in kbps.
   * @return uplinkGuaranteedBitrateKbps
   */
  
  @Schema(name = "uplink_guaranteed_bitrate_kbps", description = "Uplink guaranteed bit rate, in kbps.", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("uplink_guaranteed_bitrate_kbps")
  public @Nullable Integer getUplinkGuaranteedBitrateKbps() {
    return uplinkGuaranteedBitrateKbps;
  }

  public void setUplinkGuaranteedBitrateKbps(@Nullable Integer uplinkGuaranteedBitrateKbps) {
    this.uplinkGuaranteedBitrateKbps = uplinkGuaranteedBitrateKbps;
  }

  public SliceN6ProtectionRule downlinkGuaranteedBitrateKbps(@Nullable Integer downlinkGuaranteedBitrateKbps) {
    this.downlinkGuaranteedBitrateKbps = downlinkGuaranteedBitrateKbps;
    return this;
  }

  /**
   * Downlink guaranteed bit rate, in kbps.
   * @return downlinkGuaranteedBitrateKbps
   */
  
  @Schema(name = "downlink_guaranteed_bitrate_kbps", description = "Downlink guaranteed bit rate, in kbps.", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("downlink_guaranteed_bitrate_kbps")
  public @Nullable Integer getDownlinkGuaranteedBitrateKbps() {
    return downlinkGuaranteedBitrateKbps;
  }

  public void setDownlinkGuaranteedBitrateKbps(@Nullable Integer downlinkGuaranteedBitrateKbps) {
    this.downlinkGuaranteedBitrateKbps = downlinkGuaranteedBitrateKbps;
  }

  public SliceN6ProtectionRule uplinkMaximumBitrateKbps(@Nullable Integer uplinkMaximumBitrateKbps) {
    this.uplinkMaximumBitrateKbps = uplinkMaximumBitrateKbps;
    return this;
  }

  /**
   * Uplink maximum bit rate, in kbps.
   * @return uplinkMaximumBitrateKbps
   */
  
  @Schema(name = "uplink_maximum_bitrate_kbps", description = "Uplink maximum bit rate, in kbps.", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("uplink_maximum_bitrate_kbps")
  public @Nullable Integer getUplinkMaximumBitrateKbps() {
    return uplinkMaximumBitrateKbps;
  }

  public void setUplinkMaximumBitrateKbps(@Nullable Integer uplinkMaximumBitrateKbps) {
    this.uplinkMaximumBitrateKbps = uplinkMaximumBitrateKbps;
  }

  public SliceN6ProtectionRule downlinkMaximumBitrateKbps(@Nullable Integer downlinkMaximumBitrateKbps) {
    this.downlinkMaximumBitrateKbps = downlinkMaximumBitrateKbps;
    return this;
  }

  /**
   * Downlink maximum bit rate, in kbps.
   * @return downlinkMaximumBitrateKbps
   */
  
  @Schema(name = "downlink_maximum_bitrate_kbps", description = "Downlink maximum bit rate, in kbps.", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("downlink_maximum_bitrate_kbps")
  public @Nullable Integer getDownlinkMaximumBitrateKbps() {
    return downlinkMaximumBitrateKbps;
  }

  public void setDownlinkMaximumBitrateKbps(@Nullable Integer downlinkMaximumBitrateKbps) {
    this.downlinkMaximumBitrateKbps = downlinkMaximumBitrateKbps;
  }

  public SliceN6ProtectionRule protocol(@Nullable String protocol) {
    this.protocol = protocol;
    return this;
  }

  /**
   * Get protocol
   * @return protocol
   */
  
  @Schema(name = "protocol", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("protocol")
  public @Nullable String getProtocol() {
    return protocol;
  }

  public void setProtocol(@Nullable String protocol) {
    this.protocol = protocol;
  }

  public SliceN6ProtectionRule passBlock(@Nullable String passBlock) {
    this.passBlock = passBlock;
    return this;
  }

  /**
   * Get passBlock
   * @return passBlock
   */
  
  @Schema(name = "pass_block", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("pass_block")
  public @Nullable String getPassBlock() {
    return passBlock;
  }

  public void setPassBlock(@Nullable String passBlock) {
    this.passBlock = passBlock;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SliceN6ProtectionRule sliceN6ProtectionRule = (SliceN6ProtectionRule) o;
    return Objects.equals(this.type, sliceN6ProtectionRule.type) &&
        Objects.equals(this.name, sliceN6ProtectionRule.name) &&
        Objects.equals(this.priority, sliceN6ProtectionRule.priority) &&
        Objects.equals(this.serverIp, sliceN6ProtectionRule.serverIp) &&
        Objects.equals(this.maskLen, sliceN6ProtectionRule.maskLen) &&
        Objects.equals(this.startPort, sliceN6ProtectionRule.startPort) &&
        Objects.equals(this.endPort, sliceN6ProtectionRule.endPort) &&
        Objects.equals(this.uplinkGuaranteedBitrateKbps, sliceN6ProtectionRule.uplinkGuaranteedBitrateKbps) &&
        Objects.equals(this.downlinkGuaranteedBitrateKbps, sliceN6ProtectionRule.downlinkGuaranteedBitrateKbps) &&
        Objects.equals(this.uplinkMaximumBitrateKbps, sliceN6ProtectionRule.uplinkMaximumBitrateKbps) &&
        Objects.equals(this.downlinkMaximumBitrateKbps, sliceN6ProtectionRule.downlinkMaximumBitrateKbps) &&
        Objects.equals(this.protocol, sliceN6ProtectionRule.protocol) &&
        Objects.equals(this.passBlock, sliceN6ProtectionRule.passBlock);
  }

  @Override
  public int hashCode() {
    return Objects.hash(type, name, priority, serverIp, maskLen, startPort, endPort, uplinkGuaranteedBitrateKbps, downlinkGuaranteedBitrateKbps, uplinkMaximumBitrateKbps, downlinkMaximumBitrateKbps, protocol, passBlock);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class SliceN6ProtectionRule {\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    priority: ").append(toIndentedString(priority)).append("\n");
    sb.append("    serverIp: ").append(toIndentedString(serverIp)).append("\n");
    sb.append("    maskLen: ").append(toIndentedString(maskLen)).append("\n");
    sb.append("    startPort: ").append(toIndentedString(startPort)).append("\n");
    sb.append("    endPort: ").append(toIndentedString(endPort)).append("\n");
    sb.append("    uplinkGuaranteedBitrateKbps: ").append(toIndentedString(uplinkGuaranteedBitrateKbps)).append("\n");
    sb.append("    downlinkGuaranteedBitrateKbps: ").append(toIndentedString(downlinkGuaranteedBitrateKbps)).append("\n");
    sb.append("    uplinkMaximumBitrateKbps: ").append(toIndentedString(uplinkMaximumBitrateKbps)).append("\n");
    sb.append("    downlinkMaximumBitrateKbps: ").append(toIndentedString(downlinkMaximumBitrateKbps)).append("\n");
    sb.append("    protocol: ").append(toIndentedString(protocol)).append("\n");
    sb.append("    passBlock: ").append(toIndentedString(passBlock)).append("\n");
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

