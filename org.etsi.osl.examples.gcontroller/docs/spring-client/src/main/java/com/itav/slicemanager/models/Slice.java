package com.itav.slicemanager.models;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.itav.slicemanager.models.SliceN6ProtectionRule;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.lang.Nullable;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * Slice Manager representation of a network slice profile.  The &#x60;dnn&#x60; and &#x60;snssai&#x60; fields define the UE service context. The &#x60;slice_id&#x60; field selects the specific **DNN QoS template** inside that context.  Latency field names include the &#x60;ms&#x60; unit. Bitrate field names include the &#x60;kbps&#x60; unit.  Together, &#x60;dnn&#x60;, &#x60;snssai&#x60;, and &#x60;slice_id&#x60; identify the slice profile. 
 */

@Schema(name = "Slice", description = "Slice Manager representation of a network slice profile.  The `dnn` and `snssai` fields define the UE service context. The `slice_id` field selects the specific **DNN QoS template** inside that context.  Latency field names include the `ms` unit. Bitrate field names include the `kbps` unit.  Together, `dnn`, `snssai`, and `slice_id` identify the slice profile. ")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-08-21T10:29:42.258422300+01:00[Europe/Lisbon]", comments = "Generator version: 7.19.0")
public class Slice {

  private String sliceId;

  /**
   * Gets or Sets coverageArea
   */
  public enum CoverageAreaEnum {
    BBU_99903("bbu-99903"),
    
    BBU_99904("bbu-99904"),
    
    IT("it"),
    
    AVEIRO_SEAPORT("aveiro-seaport");

    private final String value;

    CoverageAreaEnum(String value) {
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
    public static CoverageAreaEnum fromValue(String value) {
      for (CoverageAreaEnum b : CoverageAreaEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
  }

  @Valid
  private List<CoverageAreaEnum> coverageArea = new ArrayList<>();

  private Integer sst;

  private String sd;

  private String dnn;

  private @Nullable Integer priorityLabel;

  /**
   * Gets or Sets ueMobilityLevel
   */
  public enum UeMobilityLevelEnum {
    STATIONARY("stationary"),
    
    NOMADIC("nomadic"),
    
    RESTRICTED_MOBILITY("restricted_mobility"),
    
    FULLY_MOBILITY("fully_mobility");

    private final String value;

    UeMobilityLevelEnum(String value) {
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
    public static UeMobilityLevelEnum fromValue(String value) {
      for (UeMobilityLevelEnum b : UeMobilityLevelEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
  }

  private @Nullable UeMobilityLevelEnum ueMobilityLevel;

  private @Nullable BigDecimal reliability;

  private @Nullable Integer uplinkMaxPacketSizeBytes;

  private @Nullable Integer downlinkMaxPacketSizeBytes;

  /**
   * Gets or Sets schedulingType
   */
  public enum SchedulingTypeEnum {
    BASIC_SCH("BASIC_SCH"),
    
    HIGH_RELIABILITY_SCH("HIGH_RELIABILITY_SCH"),
    
    LOW_LATENCY_SCH("LOW_LATENCY_SCH");

    private final String value;

    SchedulingTypeEnum(String value) {
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
    public static SchedulingTypeEnum fromValue(String value) {
      for (SchedulingTypeEnum b : SchedulingTypeEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
  }

  private @Nullable SchedulingTypeEnum schedulingType;

  private @Nullable Integer downlinkMinResourceBlockRatio;

  private @Nullable Integer downlinkMaxResourceBlockRatio;

  private @Nullable Integer uplinkMinResourceBlockRatio;

  private @Nullable Integer uplinkMaxResourceBlockRatio;

  private @Nullable Integer downlinkLatencyMs;

  private @Nullable Integer uplinkLatencyMs;

  /**
   * Gets or Sets delayTolerance
   */
  public enum DelayToleranceEnum {
    SUPPORTED("SUPPORTED"),
    
    NOT_SUPPORTED("NOT_SUPPORTED");

    private final String value;

    DelayToleranceEnum(String value) {
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
    public static DelayToleranceEnum fromValue(String value) {
      for (DelayToleranceEnum b : DelayToleranceEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
  }

  private @Nullable DelayToleranceEnum delayTolerance;

  /**
   * Gets or Sets downlinkDeterministicCommunication
   */
  public enum DownlinkDeterministicCommunicationEnum {
    SUPPORTED("SUPPORTED"),
    
    NOT_SUPPORTED("NOT_SUPPORTED");

    private final String value;

    DownlinkDeterministicCommunicationEnum(String value) {
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
    public static DownlinkDeterministicCommunicationEnum fromValue(String value) {
      for (DownlinkDeterministicCommunicationEnum b : DownlinkDeterministicCommunicationEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
  }

  private @Nullable DownlinkDeterministicCommunicationEnum downlinkDeterministicCommunication;

  private @Nullable Integer downlinkDeterministicPeriodicity;

  /**
   * Gets or Sets uplinkDeterministicCommunication
   */
  public enum UplinkDeterministicCommunicationEnum {
    SUPPORTED("SUPPORTED"),
    
    NOT_SUPPORTED("NOT_SUPPORTED");

    private final String value;

    UplinkDeterministicCommunicationEnum(String value) {
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
    public static UplinkDeterministicCommunicationEnum fromValue(String value) {
      for (UplinkDeterministicCommunicationEnum b : UplinkDeterministicCommunicationEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
  }

  private @Nullable UplinkDeterministicCommunicationEnum uplinkDeterministicCommunication;

  private @Nullable Integer uplinkDeterministicPeriodicity;

  private @Nullable Integer downlinkGuaranteedBitratePerUeKbps;

  private @Nullable Integer uplinkGuaranteedBitratePerUeKbps;

  private @Nullable Integer downlinkMaximumBitratePerUeKbps;

  private @Nullable Integer uplinkMaximumBitratePerUeKbps;

  private @Nullable Integer downlinkGuaranteedBitratePerSliceKbps;

  private @Nullable Integer uplinkGuaranteedBitratePerSliceKbps;

  private @Nullable Integer downlinkMaximumBitratePerSliceKbps;

  private @Nullable Integer uplinkMaximumBitratePerSliceKbps;

  private @Nullable Integer terminalDensity;

  private @Nullable Integer maxNumberOfPduSessions;

  private @Nullable Integer maxNumberOfUes;

  @Valid
  private List<@Valid SliceN6ProtectionRule> n6Protection = new ArrayList<>();

  public Slice() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public Slice(String sliceId, Integer sst, String sd, String dnn) {
    this.sliceId = sliceId;
    this.sst = sst;
    this.sd = sd;
    this.dnn = dnn;
  }

  public Slice sliceId(String sliceId) {
    this.sliceId = sliceId;
    return this;
  }

  /**
   * Slice identifier and QoS profile selector.
   * @return sliceId
   */
  @NotNull @Size(min = 3) 
  @Schema(name = "slice_id", description = "Slice identifier and QoS profile selector.", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("slice_id")
  public String getSliceId() {
    return sliceId;
  }

  public void setSliceId(String sliceId) {
    this.sliceId = sliceId;
  }

  public Slice coverageArea(List<CoverageAreaEnum> coverageArea) {
    this.coverageArea = coverageArea;
    return this;
  }

  public Slice addCoverageAreaItem(CoverageAreaEnum coverageAreaItem) {
    if (this.coverageArea == null) {
      this.coverageArea = new ArrayList<>();
    }
    this.coverageArea.add(coverageAreaItem);
    return this;
  }

  /**
   * What RAN this slice will be accessed on. Needs a `bbu_id`.
   * @return coverageArea
   */
  
  @Schema(name = "coverage_area", description = "What RAN this slice will be accessed on. Needs a `bbu_id`.", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("coverage_area")
  public List<CoverageAreaEnum> getCoverageArea() {
    return coverageArea;
  }

  public void setCoverageArea(List<CoverageAreaEnum> coverageArea) {
    this.coverageArea = coverageArea;
  }

  public Slice sst(Integer sst) {
    this.sst = sst;
    return this;
  }

  /**
   * Get sst
   * minimum: 0
   * maximum: 255
   * @return sst
   */
  @NotNull @Min(value = 0) @Max(value = 255) 
  @Schema(name = "sst", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("sst")
  public Integer getSst() {
    return sst;
  }

  public void setSst(Integer sst) {
    this.sst = sst;
  }

  public Slice sd(String sd) {
    this.sd = sd;
    return this;
  }

  /**
   * Get sd
   * @return sd
   */
  @NotNull @Pattern(regexp = "^[0-9]{6}$") 
  @Schema(name = "sd", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("sd")
  public String getSd() {
    return sd;
  }

  public void setSd(String sd) {
    this.sd = sd;
  }

  public Slice dnn(String dnn) {
    this.dnn = dnn;
    return this;
  }

  /**
   * Get dnn
   * @return dnn
   */
  @NotNull @Size(min = 3) 
  @Schema(name = "dnn", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("dnn")
  public String getDnn() {
    return dnn;
  }

  public void setDnn(String dnn) {
    this.dnn = dnn;
  }

  public Slice priorityLabel(@Nullable Integer priorityLabel) {
    this.priorityLabel = priorityLabel;
    return this;
  }

  /**
   * Get priorityLabel
   * minimum: 0
   * maximum: 100
   * @return priorityLabel
   */
  @Min(value = 0) @Max(value = 100) 
  @Schema(name = "priority_label", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("priority_label")
  public @Nullable Integer getPriorityLabel() {
    return priorityLabel;
  }

  public void setPriorityLabel(@Nullable Integer priorityLabel) {
    this.priorityLabel = priorityLabel;
  }

  public Slice ueMobilityLevel(@Nullable UeMobilityLevelEnum ueMobilityLevel) {
    this.ueMobilityLevel = ueMobilityLevel;
    return this;
  }

  /**
   * Get ueMobilityLevel
   * @return ueMobilityLevel
   */
  
  @Schema(name = "ue_mobility_level", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("ue_mobility_level")
  public @Nullable UeMobilityLevelEnum getUeMobilityLevel() {
    return ueMobilityLevel;
  }

  public void setUeMobilityLevel(@Nullable UeMobilityLevelEnum ueMobilityLevel) {
    this.ueMobilityLevel = ueMobilityLevel;
  }

  public Slice reliability(@Nullable BigDecimal reliability) {
    this.reliability = reliability;
    return this;
  }

  /**
   * Get reliability
   * minimum: 0
   * maximum: 100
   * @return reliability
   */
  @Valid @DecimalMin(value = "0") @DecimalMax(value = "100") 
  @Schema(name = "reliability", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("reliability")
  public @Nullable BigDecimal getReliability() {
    return reliability;
  }

  public void setReliability(@Nullable BigDecimal reliability) {
    this.reliability = reliability;
  }

  public Slice uplinkMaxPacketSizeBytes(@Nullable Integer uplinkMaxPacketSizeBytes) {
    this.uplinkMaxPacketSizeBytes = uplinkMaxPacketSizeBytes;
    return this;
  }

  /**
   * Maximum uplink packet size, in bytes.
   * @return uplinkMaxPacketSizeBytes
   */
  
  @Schema(name = "uplink_max_packet_size_bytes", description = "Maximum uplink packet size, in bytes.", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("uplink_max_packet_size_bytes")
  public @Nullable Integer getUplinkMaxPacketSizeBytes() {
    return uplinkMaxPacketSizeBytes;
  }

  public void setUplinkMaxPacketSizeBytes(@Nullable Integer uplinkMaxPacketSizeBytes) {
    this.uplinkMaxPacketSizeBytes = uplinkMaxPacketSizeBytes;
  }

  public Slice downlinkMaxPacketSizeBytes(@Nullable Integer downlinkMaxPacketSizeBytes) {
    this.downlinkMaxPacketSizeBytes = downlinkMaxPacketSizeBytes;
    return this;
  }

  /**
   * Maximum downlink packet size, in bytes.
   * @return downlinkMaxPacketSizeBytes
   */
  
  @Schema(name = "downlink_max_packet_size_bytes", description = "Maximum downlink packet size, in bytes.", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("downlink_max_packet_size_bytes")
  public @Nullable Integer getDownlinkMaxPacketSizeBytes() {
    return downlinkMaxPacketSizeBytes;
  }

  public void setDownlinkMaxPacketSizeBytes(@Nullable Integer downlinkMaxPacketSizeBytes) {
    this.downlinkMaxPacketSizeBytes = downlinkMaxPacketSizeBytes;
  }

  public Slice schedulingType(@Nullable SchedulingTypeEnum schedulingType) {
    this.schedulingType = schedulingType;
    return this;
  }

  /**
   * Get schedulingType
   * @return schedulingType
   */
  
  @Schema(name = "scheduling_type", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("scheduling_type")
  public @Nullable SchedulingTypeEnum getSchedulingType() {
    return schedulingType;
  }

  public void setSchedulingType(@Nullable SchedulingTypeEnum schedulingType) {
    this.schedulingType = schedulingType;
  }

  public Slice downlinkMinResourceBlockRatio(@Nullable Integer downlinkMinResourceBlockRatio) {
    this.downlinkMinResourceBlockRatio = downlinkMinResourceBlockRatio;
    return this;
  }

  /**
   * Minimum downlink resource block ratio.
   * @return downlinkMinResourceBlockRatio
   */
  
  @Schema(name = "downlink_min_resource_block_ratio", description = "Minimum downlink resource block ratio.", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("downlink_min_resource_block_ratio")
  public @Nullable Integer getDownlinkMinResourceBlockRatio() {
    return downlinkMinResourceBlockRatio;
  }

  public void setDownlinkMinResourceBlockRatio(@Nullable Integer downlinkMinResourceBlockRatio) {
    this.downlinkMinResourceBlockRatio = downlinkMinResourceBlockRatio;
  }

  public Slice downlinkMaxResourceBlockRatio(@Nullable Integer downlinkMaxResourceBlockRatio) {
    this.downlinkMaxResourceBlockRatio = downlinkMaxResourceBlockRatio;
    return this;
  }

  /**
   * Maximum downlink resource block ratio.
   * @return downlinkMaxResourceBlockRatio
   */
  
  @Schema(name = "downlink_max_resource_block_ratio", description = "Maximum downlink resource block ratio.", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("downlink_max_resource_block_ratio")
  public @Nullable Integer getDownlinkMaxResourceBlockRatio() {
    return downlinkMaxResourceBlockRatio;
  }

  public void setDownlinkMaxResourceBlockRatio(@Nullable Integer downlinkMaxResourceBlockRatio) {
    this.downlinkMaxResourceBlockRatio = downlinkMaxResourceBlockRatio;
  }

  public Slice uplinkMinResourceBlockRatio(@Nullable Integer uplinkMinResourceBlockRatio) {
    this.uplinkMinResourceBlockRatio = uplinkMinResourceBlockRatio;
    return this;
  }

  /**
   * Minimum uplink resource block ratio.
   * @return uplinkMinResourceBlockRatio
   */
  
  @Schema(name = "uplink_min_resource_block_ratio", description = "Minimum uplink resource block ratio.", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("uplink_min_resource_block_ratio")
  public @Nullable Integer getUplinkMinResourceBlockRatio() {
    return uplinkMinResourceBlockRatio;
  }

  public void setUplinkMinResourceBlockRatio(@Nullable Integer uplinkMinResourceBlockRatio) {
    this.uplinkMinResourceBlockRatio = uplinkMinResourceBlockRatio;
  }

  public Slice uplinkMaxResourceBlockRatio(@Nullable Integer uplinkMaxResourceBlockRatio) {
    this.uplinkMaxResourceBlockRatio = uplinkMaxResourceBlockRatio;
    return this;
  }

  /**
   * Maximum uplink resource block ratio.
   * @return uplinkMaxResourceBlockRatio
   */
  
  @Schema(name = "uplink_max_resource_block_ratio", description = "Maximum uplink resource block ratio.", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("uplink_max_resource_block_ratio")
  public @Nullable Integer getUplinkMaxResourceBlockRatio() {
    return uplinkMaxResourceBlockRatio;
  }

  public void setUplinkMaxResourceBlockRatio(@Nullable Integer uplinkMaxResourceBlockRatio) {
    this.uplinkMaxResourceBlockRatio = uplinkMaxResourceBlockRatio;
  }

  public Slice downlinkLatencyMs(@Nullable Integer downlinkLatencyMs) {
    this.downlinkLatencyMs = downlinkLatencyMs;
    return this;
  }

  /**
   * Downlink latency budget, in milliseconds.
   * @return downlinkLatencyMs
   */
  
  @Schema(name = "downlink_latency_ms", description = "Downlink latency budget, in milliseconds.", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("downlink_latency_ms")
  public @Nullable Integer getDownlinkLatencyMs() {
    return downlinkLatencyMs;
  }

  public void setDownlinkLatencyMs(@Nullable Integer downlinkLatencyMs) {
    this.downlinkLatencyMs = downlinkLatencyMs;
  }

  public Slice uplinkLatencyMs(@Nullable Integer uplinkLatencyMs) {
    this.uplinkLatencyMs = uplinkLatencyMs;
    return this;
  }

  /**
   * Uplink latency budget, in milliseconds.
   * @return uplinkLatencyMs
   */
  
  @Schema(name = "uplink_latency_ms", description = "Uplink latency budget, in milliseconds.", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("uplink_latency_ms")
  public @Nullable Integer getUplinkLatencyMs() {
    return uplinkLatencyMs;
  }

  public void setUplinkLatencyMs(@Nullable Integer uplinkLatencyMs) {
    this.uplinkLatencyMs = uplinkLatencyMs;
  }

  public Slice delayTolerance(@Nullable DelayToleranceEnum delayTolerance) {
    this.delayTolerance = delayTolerance;
    return this;
  }

  /**
   * Get delayTolerance
   * @return delayTolerance
   */
  
  @Schema(name = "delay_tolerance", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("delay_tolerance")
  public @Nullable DelayToleranceEnum getDelayTolerance() {
    return delayTolerance;
  }

  public void setDelayTolerance(@Nullable DelayToleranceEnum delayTolerance) {
    this.delayTolerance = delayTolerance;
  }

  public Slice downlinkDeterministicCommunication(@Nullable DownlinkDeterministicCommunicationEnum downlinkDeterministicCommunication) {
    this.downlinkDeterministicCommunication = downlinkDeterministicCommunication;
    return this;
  }

  /**
   * Get downlinkDeterministicCommunication
   * @return downlinkDeterministicCommunication
   */
  
  @Schema(name = "downlink_deterministic_communication", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("downlink_deterministic_communication")
  public @Nullable DownlinkDeterministicCommunicationEnum getDownlinkDeterministicCommunication() {
    return downlinkDeterministicCommunication;
  }

  public void setDownlinkDeterministicCommunication(@Nullable DownlinkDeterministicCommunicationEnum downlinkDeterministicCommunication) {
    this.downlinkDeterministicCommunication = downlinkDeterministicCommunication;
  }

  public Slice downlinkDeterministicPeriodicity(@Nullable Integer downlinkDeterministicPeriodicity) {
    this.downlinkDeterministicPeriodicity = downlinkDeterministicPeriodicity;
    return this;
  }

  /**
   * Get downlinkDeterministicPeriodicity
   * @return downlinkDeterministicPeriodicity
   */
  
  @Schema(name = "downlink_deterministic_periodicity", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("downlink_deterministic_periodicity")
  public @Nullable Integer getDownlinkDeterministicPeriodicity() {
    return downlinkDeterministicPeriodicity;
  }

  public void setDownlinkDeterministicPeriodicity(@Nullable Integer downlinkDeterministicPeriodicity) {
    this.downlinkDeterministicPeriodicity = downlinkDeterministicPeriodicity;
  }

  public Slice uplinkDeterministicCommunication(@Nullable UplinkDeterministicCommunicationEnum uplinkDeterministicCommunication) {
    this.uplinkDeterministicCommunication = uplinkDeterministicCommunication;
    return this;
  }

  /**
   * Get uplinkDeterministicCommunication
   * @return uplinkDeterministicCommunication
   */
  
  @Schema(name = "uplink_deterministic_communication", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("uplink_deterministic_communication")
  public @Nullable UplinkDeterministicCommunicationEnum getUplinkDeterministicCommunication() {
    return uplinkDeterministicCommunication;
  }

  public void setUplinkDeterministicCommunication(@Nullable UplinkDeterministicCommunicationEnum uplinkDeterministicCommunication) {
    this.uplinkDeterministicCommunication = uplinkDeterministicCommunication;
  }

  public Slice uplinkDeterministicPeriodicity(@Nullable Integer uplinkDeterministicPeriodicity) {
    this.uplinkDeterministicPeriodicity = uplinkDeterministicPeriodicity;
    return this;
  }

  /**
   * Get uplinkDeterministicPeriodicity
   * @return uplinkDeterministicPeriodicity
   */
  
  @Schema(name = "uplink_deterministic_periodicity", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("uplink_deterministic_periodicity")
  public @Nullable Integer getUplinkDeterministicPeriodicity() {
    return uplinkDeterministicPeriodicity;
  }

  public void setUplinkDeterministicPeriodicity(@Nullable Integer uplinkDeterministicPeriodicity) {
    this.uplinkDeterministicPeriodicity = uplinkDeterministicPeriodicity;
  }

  public Slice downlinkGuaranteedBitratePerUeKbps(@Nullable Integer downlinkGuaranteedBitratePerUeKbps) {
    this.downlinkGuaranteedBitratePerUeKbps = downlinkGuaranteedBitratePerUeKbps;
    return this;
  }

  /**
   * Downlink guaranteed bitrate per UE, in kbps.
   * @return downlinkGuaranteedBitratePerUeKbps
   */
  
  @Schema(name = "downlink_guaranteed_bitrate_per_ue_kbps", description = "Downlink guaranteed bitrate per UE, in kbps.", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("downlink_guaranteed_bitrate_per_ue_kbps")
  public @Nullable Integer getDownlinkGuaranteedBitratePerUeKbps() {
    return downlinkGuaranteedBitratePerUeKbps;
  }

  public void setDownlinkGuaranteedBitratePerUeKbps(@Nullable Integer downlinkGuaranteedBitratePerUeKbps) {
    this.downlinkGuaranteedBitratePerUeKbps = downlinkGuaranteedBitratePerUeKbps;
  }

  public Slice uplinkGuaranteedBitratePerUeKbps(@Nullable Integer uplinkGuaranteedBitratePerUeKbps) {
    this.uplinkGuaranteedBitratePerUeKbps = uplinkGuaranteedBitratePerUeKbps;
    return this;
  }

  /**
   * Uplink guaranteed bitrate per UE, in kbps.
   * @return uplinkGuaranteedBitratePerUeKbps
   */
  
  @Schema(name = "uplink_guaranteed_bitrate_per_ue_kbps", description = "Uplink guaranteed bitrate per UE, in kbps.", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("uplink_guaranteed_bitrate_per_ue_kbps")
  public @Nullable Integer getUplinkGuaranteedBitratePerUeKbps() {
    return uplinkGuaranteedBitratePerUeKbps;
  }

  public void setUplinkGuaranteedBitratePerUeKbps(@Nullable Integer uplinkGuaranteedBitratePerUeKbps) {
    this.uplinkGuaranteedBitratePerUeKbps = uplinkGuaranteedBitratePerUeKbps;
  }

  public Slice downlinkMaximumBitratePerUeKbps(@Nullable Integer downlinkMaximumBitratePerUeKbps) {
    this.downlinkMaximumBitratePerUeKbps = downlinkMaximumBitratePerUeKbps;
    return this;
  }

  /**
   * Downlink maximum bitrate per UE, in kbps.
   * @return downlinkMaximumBitratePerUeKbps
   */
  
  @Schema(name = "downlink_maximum_bitrate_per_ue_kbps", description = "Downlink maximum bitrate per UE, in kbps.", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("downlink_maximum_bitrate_per_ue_kbps")
  public @Nullable Integer getDownlinkMaximumBitratePerUeKbps() {
    return downlinkMaximumBitratePerUeKbps;
  }

  public void setDownlinkMaximumBitratePerUeKbps(@Nullable Integer downlinkMaximumBitratePerUeKbps) {
    this.downlinkMaximumBitratePerUeKbps = downlinkMaximumBitratePerUeKbps;
  }

  public Slice uplinkMaximumBitratePerUeKbps(@Nullable Integer uplinkMaximumBitratePerUeKbps) {
    this.uplinkMaximumBitratePerUeKbps = uplinkMaximumBitratePerUeKbps;
    return this;
  }

  /**
   * Uplink maximum bitrate per UE, in kbps.
   * @return uplinkMaximumBitratePerUeKbps
   */
  
  @Schema(name = "uplink_maximum_bitrate_per_ue_kbps", description = "Uplink maximum bitrate per UE, in kbps.", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("uplink_maximum_bitrate_per_ue_kbps")
  public @Nullable Integer getUplinkMaximumBitratePerUeKbps() {
    return uplinkMaximumBitratePerUeKbps;
  }

  public void setUplinkMaximumBitratePerUeKbps(@Nullable Integer uplinkMaximumBitratePerUeKbps) {
    this.uplinkMaximumBitratePerUeKbps = uplinkMaximumBitratePerUeKbps;
  }

  public Slice downlinkGuaranteedBitratePerSliceKbps(@Nullable Integer downlinkGuaranteedBitratePerSliceKbps) {
    this.downlinkGuaranteedBitratePerSliceKbps = downlinkGuaranteedBitratePerSliceKbps;
    return this;
  }

  /**
   * Downlink guaranteed bitrate per slice, in kbps.
   * @return downlinkGuaranteedBitratePerSliceKbps
   */
  
  @Schema(name = "downlink_guaranteed_bitrate_per_slice_kbps", description = "Downlink guaranteed bitrate per slice, in kbps.", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("downlink_guaranteed_bitrate_per_slice_kbps")
  public @Nullable Integer getDownlinkGuaranteedBitratePerSliceKbps() {
    return downlinkGuaranteedBitratePerSliceKbps;
  }

  public void setDownlinkGuaranteedBitratePerSliceKbps(@Nullable Integer downlinkGuaranteedBitratePerSliceKbps) {
    this.downlinkGuaranteedBitratePerSliceKbps = downlinkGuaranteedBitratePerSliceKbps;
  }

  public Slice uplinkGuaranteedBitratePerSliceKbps(@Nullable Integer uplinkGuaranteedBitratePerSliceKbps) {
    this.uplinkGuaranteedBitratePerSliceKbps = uplinkGuaranteedBitratePerSliceKbps;
    return this;
  }

  /**
   * Uplink guaranteed bitrate per slice, in kbps.
   * @return uplinkGuaranteedBitratePerSliceKbps
   */
  
  @Schema(name = "uplink_guaranteed_bitrate_per_slice_kbps", description = "Uplink guaranteed bitrate per slice, in kbps.", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("uplink_guaranteed_bitrate_per_slice_kbps")
  public @Nullable Integer getUplinkGuaranteedBitratePerSliceKbps() {
    return uplinkGuaranteedBitratePerSliceKbps;
  }

  public void setUplinkGuaranteedBitratePerSliceKbps(@Nullable Integer uplinkGuaranteedBitratePerSliceKbps) {
    this.uplinkGuaranteedBitratePerSliceKbps = uplinkGuaranteedBitratePerSliceKbps;
  }

  public Slice downlinkMaximumBitratePerSliceKbps(@Nullable Integer downlinkMaximumBitratePerSliceKbps) {
    this.downlinkMaximumBitratePerSliceKbps = downlinkMaximumBitratePerSliceKbps;
    return this;
  }

  /**
   * Downlink maximum bitrate per slice, in kbps.
   * @return downlinkMaximumBitratePerSliceKbps
   */
  
  @Schema(name = "downlink_maximum_bitrate_per_slice_kbps", description = "Downlink maximum bitrate per slice, in kbps.", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("downlink_maximum_bitrate_per_slice_kbps")
  public @Nullable Integer getDownlinkMaximumBitratePerSliceKbps() {
    return downlinkMaximumBitratePerSliceKbps;
  }

  public void setDownlinkMaximumBitratePerSliceKbps(@Nullable Integer downlinkMaximumBitratePerSliceKbps) {
    this.downlinkMaximumBitratePerSliceKbps = downlinkMaximumBitratePerSliceKbps;
  }

  public Slice uplinkMaximumBitratePerSliceKbps(@Nullable Integer uplinkMaximumBitratePerSliceKbps) {
    this.uplinkMaximumBitratePerSliceKbps = uplinkMaximumBitratePerSliceKbps;
    return this;
  }

  /**
   * Uplink maximum bitrate per slice, in kbps.
   * @return uplinkMaximumBitratePerSliceKbps
   */
  
  @Schema(name = "uplink_maximum_bitrate_per_slice_kbps", description = "Uplink maximum bitrate per slice, in kbps.", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("uplink_maximum_bitrate_per_slice_kbps")
  public @Nullable Integer getUplinkMaximumBitratePerSliceKbps() {
    return uplinkMaximumBitratePerSliceKbps;
  }

  public void setUplinkMaximumBitratePerSliceKbps(@Nullable Integer uplinkMaximumBitratePerSliceKbps) {
    this.uplinkMaximumBitratePerSliceKbps = uplinkMaximumBitratePerSliceKbps;
  }

  public Slice terminalDensity(@Nullable Integer terminalDensity) {
    this.terminalDensity = terminalDensity;
    return this;
  }

  /**
   * Get terminalDensity
   * @return terminalDensity
   */
  
  @Schema(name = "terminal_density", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("terminal_density")
  public @Nullable Integer getTerminalDensity() {
    return terminalDensity;
  }

  public void setTerminalDensity(@Nullable Integer terminalDensity) {
    this.terminalDensity = terminalDensity;
  }

  public Slice maxNumberOfPduSessions(@Nullable Integer maxNumberOfPduSessions) {
    this.maxNumberOfPduSessions = maxNumberOfPduSessions;
    return this;
  }

  /**
   * Get maxNumberOfPduSessions
   * @return maxNumberOfPduSessions
   */
  
  @Schema(name = "max_number_of_pdu_sessions", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("max_number_of_pdu_sessions")
  public @Nullable Integer getMaxNumberOfPduSessions() {
    return maxNumberOfPduSessions;
  }

  public void setMaxNumberOfPduSessions(@Nullable Integer maxNumberOfPduSessions) {
    this.maxNumberOfPduSessions = maxNumberOfPduSessions;
  }

  public Slice maxNumberOfUes(@Nullable Integer maxNumberOfUes) {
    this.maxNumberOfUes = maxNumberOfUes;
    return this;
  }

  /**
   * Get maxNumberOfUes
   * @return maxNumberOfUes
   */
  
  @Schema(name = "max_number_of_ues", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("max_number_of_ues")
  public @Nullable Integer getMaxNumberOfUes() {
    return maxNumberOfUes;
  }

  public void setMaxNumberOfUes(@Nullable Integer maxNumberOfUes) {
    this.maxNumberOfUes = maxNumberOfUes;
  }

  public Slice n6Protection(List<@Valid SliceN6ProtectionRule> n6Protection) {
    this.n6Protection = n6Protection;
    return this;
  }

  public Slice addN6ProtectionItem(SliceN6ProtectionRule n6ProtectionItem) {
    if (this.n6Protection == null) {
      this.n6Protection = new ArrayList<>();
    }
    this.n6Protection.add(n6ProtectionItem);
    return this;
  }

  /**
   * Get n6Protection
   * @return n6Protection
   */
  @Valid 
  @Schema(name = "n6_protection", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("n6_protection")
  public List<@Valid SliceN6ProtectionRule> getN6Protection() {
    return n6Protection;
  }

  public void setN6Protection(List<@Valid SliceN6ProtectionRule> n6Protection) {
    this.n6Protection = n6Protection;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Slice slice = (Slice) o;
    return Objects.equals(this.sliceId, slice.sliceId) &&
        Objects.equals(this.coverageArea, slice.coverageArea) &&
        Objects.equals(this.sst, slice.sst) &&
        Objects.equals(this.sd, slice.sd) &&
        Objects.equals(this.dnn, slice.dnn) &&
        Objects.equals(this.priorityLabel, slice.priorityLabel) &&
        Objects.equals(this.ueMobilityLevel, slice.ueMobilityLevel) &&
        Objects.equals(this.reliability, slice.reliability) &&
        Objects.equals(this.uplinkMaxPacketSizeBytes, slice.uplinkMaxPacketSizeBytes) &&
        Objects.equals(this.downlinkMaxPacketSizeBytes, slice.downlinkMaxPacketSizeBytes) &&
        Objects.equals(this.schedulingType, slice.schedulingType) &&
        Objects.equals(this.downlinkMinResourceBlockRatio, slice.downlinkMinResourceBlockRatio) &&
        Objects.equals(this.downlinkMaxResourceBlockRatio, slice.downlinkMaxResourceBlockRatio) &&
        Objects.equals(this.uplinkMinResourceBlockRatio, slice.uplinkMinResourceBlockRatio) &&
        Objects.equals(this.uplinkMaxResourceBlockRatio, slice.uplinkMaxResourceBlockRatio) &&
        Objects.equals(this.downlinkLatencyMs, slice.downlinkLatencyMs) &&
        Objects.equals(this.uplinkLatencyMs, slice.uplinkLatencyMs) &&
        Objects.equals(this.delayTolerance, slice.delayTolerance) &&
        Objects.equals(this.downlinkDeterministicCommunication, slice.downlinkDeterministicCommunication) &&
        Objects.equals(this.downlinkDeterministicPeriodicity, slice.downlinkDeterministicPeriodicity) &&
        Objects.equals(this.uplinkDeterministicCommunication, slice.uplinkDeterministicCommunication) &&
        Objects.equals(this.uplinkDeterministicPeriodicity, slice.uplinkDeterministicPeriodicity) &&
        Objects.equals(this.downlinkGuaranteedBitratePerUeKbps, slice.downlinkGuaranteedBitratePerUeKbps) &&
        Objects.equals(this.uplinkGuaranteedBitratePerUeKbps, slice.uplinkGuaranteedBitratePerUeKbps) &&
        Objects.equals(this.downlinkMaximumBitratePerUeKbps, slice.downlinkMaximumBitratePerUeKbps) &&
        Objects.equals(this.uplinkMaximumBitratePerUeKbps, slice.uplinkMaximumBitratePerUeKbps) &&
        Objects.equals(this.downlinkGuaranteedBitratePerSliceKbps, slice.downlinkGuaranteedBitratePerSliceKbps) &&
        Objects.equals(this.uplinkGuaranteedBitratePerSliceKbps, slice.uplinkGuaranteedBitratePerSliceKbps) &&
        Objects.equals(this.downlinkMaximumBitratePerSliceKbps, slice.downlinkMaximumBitratePerSliceKbps) &&
        Objects.equals(this.uplinkMaximumBitratePerSliceKbps, slice.uplinkMaximumBitratePerSliceKbps) &&
        Objects.equals(this.terminalDensity, slice.terminalDensity) &&
        Objects.equals(this.maxNumberOfPduSessions, slice.maxNumberOfPduSessions) &&
        Objects.equals(this.maxNumberOfUes, slice.maxNumberOfUes) &&
        Objects.equals(this.n6Protection, slice.n6Protection);
  }

  @Override
  public int hashCode() {
    return Objects.hash(sliceId, coverageArea, sst, sd, dnn, priorityLabel, ueMobilityLevel, reliability, uplinkMaxPacketSizeBytes, downlinkMaxPacketSizeBytes, schedulingType, downlinkMinResourceBlockRatio, downlinkMaxResourceBlockRatio, uplinkMinResourceBlockRatio, uplinkMaxResourceBlockRatio, downlinkLatencyMs, uplinkLatencyMs, delayTolerance, downlinkDeterministicCommunication, downlinkDeterministicPeriodicity, uplinkDeterministicCommunication, uplinkDeterministicPeriodicity, downlinkGuaranteedBitratePerUeKbps, uplinkGuaranteedBitratePerUeKbps, downlinkMaximumBitratePerUeKbps, uplinkMaximumBitratePerUeKbps, downlinkGuaranteedBitratePerSliceKbps, uplinkGuaranteedBitratePerSliceKbps, downlinkMaximumBitratePerSliceKbps, uplinkMaximumBitratePerSliceKbps, terminalDensity, maxNumberOfPduSessions, maxNumberOfUes, n6Protection);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Slice {\n");
    sb.append("    sliceId: ").append(toIndentedString(sliceId)).append("\n");
    sb.append("    coverageArea: ").append(toIndentedString(coverageArea)).append("\n");
    sb.append("    sst: ").append(toIndentedString(sst)).append("\n");
    sb.append("    sd: ").append(toIndentedString(sd)).append("\n");
    sb.append("    dnn: ").append(toIndentedString(dnn)).append("\n");
    sb.append("    priorityLabel: ").append(toIndentedString(priorityLabel)).append("\n");
    sb.append("    ueMobilityLevel: ").append(toIndentedString(ueMobilityLevel)).append("\n");
    sb.append("    reliability: ").append(toIndentedString(reliability)).append("\n");
    sb.append("    uplinkMaxPacketSizeBytes: ").append(toIndentedString(uplinkMaxPacketSizeBytes)).append("\n");
    sb.append("    downlinkMaxPacketSizeBytes: ").append(toIndentedString(downlinkMaxPacketSizeBytes)).append("\n");
    sb.append("    schedulingType: ").append(toIndentedString(schedulingType)).append("\n");
    sb.append("    downlinkMinResourceBlockRatio: ").append(toIndentedString(downlinkMinResourceBlockRatio)).append("\n");
    sb.append("    downlinkMaxResourceBlockRatio: ").append(toIndentedString(downlinkMaxResourceBlockRatio)).append("\n");
    sb.append("    uplinkMinResourceBlockRatio: ").append(toIndentedString(uplinkMinResourceBlockRatio)).append("\n");
    sb.append("    uplinkMaxResourceBlockRatio: ").append(toIndentedString(uplinkMaxResourceBlockRatio)).append("\n");
    sb.append("    downlinkLatencyMs: ").append(toIndentedString(downlinkLatencyMs)).append("\n");
    sb.append("    uplinkLatencyMs: ").append(toIndentedString(uplinkLatencyMs)).append("\n");
    sb.append("    delayTolerance: ").append(toIndentedString(delayTolerance)).append("\n");
    sb.append("    downlinkDeterministicCommunication: ").append(toIndentedString(downlinkDeterministicCommunication)).append("\n");
    sb.append("    downlinkDeterministicPeriodicity: ").append(toIndentedString(downlinkDeterministicPeriodicity)).append("\n");
    sb.append("    uplinkDeterministicCommunication: ").append(toIndentedString(uplinkDeterministicCommunication)).append("\n");
    sb.append("    uplinkDeterministicPeriodicity: ").append(toIndentedString(uplinkDeterministicPeriodicity)).append("\n");
    sb.append("    downlinkGuaranteedBitratePerUeKbps: ").append(toIndentedString(downlinkGuaranteedBitratePerUeKbps)).append("\n");
    sb.append("    uplinkGuaranteedBitratePerUeKbps: ").append(toIndentedString(uplinkGuaranteedBitratePerUeKbps)).append("\n");
    sb.append("    downlinkMaximumBitratePerUeKbps: ").append(toIndentedString(downlinkMaximumBitratePerUeKbps)).append("\n");
    sb.append("    uplinkMaximumBitratePerUeKbps: ").append(toIndentedString(uplinkMaximumBitratePerUeKbps)).append("\n");
    sb.append("    downlinkGuaranteedBitratePerSliceKbps: ").append(toIndentedString(downlinkGuaranteedBitratePerSliceKbps)).append("\n");
    sb.append("    uplinkGuaranteedBitratePerSliceKbps: ").append(toIndentedString(uplinkGuaranteedBitratePerSliceKbps)).append("\n");
    sb.append("    downlinkMaximumBitratePerSliceKbps: ").append(toIndentedString(downlinkMaximumBitratePerSliceKbps)).append("\n");
    sb.append("    uplinkMaximumBitratePerSliceKbps: ").append(toIndentedString(uplinkMaximumBitratePerSliceKbps)).append("\n");
    sb.append("    terminalDensity: ").append(toIndentedString(terminalDensity)).append("\n");
    sb.append("    maxNumberOfPduSessions: ").append(toIndentedString(maxNumberOfPduSessions)).append("\n");
    sb.append("    maxNumberOfUes: ").append(toIndentedString(maxNumberOfUes)).append("\n");
    sb.append("    n6Protection: ").append(toIndentedString(n6Protection)).append("\n");
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

