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
 * Baseband unit inventory entry.
 */

@Schema(name = "Bbu", description = "Baseband unit inventory entry.")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-08-21T10:29:42.258422300+01:00[Europe/Lisbon]", comments = "Generator version: 7.19.0")
public class Bbu {

  /**
   * Gets or Sets bbuId
   */
  public enum BbuIdEnum {
    BBU_99903("bbu-99903"),
    
    BBU_99904("bbu-99904"),
    
    IT("it"),
    
    AVEIRO_SEAPORT("aveiro-seaport");

    private final String value;

    BbuIdEnum(String value) {
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
    public static BbuIdEnum fromValue(String value) {
      for (BbuIdEnum b : BbuIdEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
  }

  private BbuIdEnum bbuId;

  /**
   * Gets or Sets location
   */
  public enum LocationEnum {
    PENELOPE("penelope"),
    
    MUTTLEY("muttley"),
    
    IT("it"),
    
    AVEIRO_SEAPORT("aveiro-seaport");

    private final String value;

    LocationEnum(String value) {
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
    public static LocationEnum fromValue(String value) {
      for (LocationEnum b : LocationEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
  }

  private @Nullable LocationEnum location;

  private String plmnId;

  private @Nullable String ipAddress;

  /**
   * Gets or Sets status
   */
  public enum StatusEnum {
    AVAILABLE("Available"),
    
    UNAVAILABLE("Unavailable");

    private final String value;

    StatusEnum(String value) {
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
    public static StatusEnum fromValue(String value) {
      for (StatusEnum b : StatusEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
  }

  private @Nullable StatusEnum status;

  public Bbu() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public Bbu(BbuIdEnum bbuId, String plmnId) {
    this.bbuId = bbuId;
    this.plmnId = plmnId;
  }

  public Bbu bbuId(BbuIdEnum bbuId) {
    this.bbuId = bbuId;
    return this;
  }

  /**
   * Get bbuId
   * @return bbuId
   */
  @NotNull 
  @Schema(name = "bbu_id", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("bbu_id")
  public BbuIdEnum getBbuId() {
    return bbuId;
  }

  public void setBbuId(BbuIdEnum bbuId) {
    this.bbuId = bbuId;
  }

  public Bbu location(@Nullable LocationEnum location) {
    this.location = location;
    return this;
  }

  /**
   * Get location
   * @return location
   */
  
  @Schema(name = "location", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("location")
  public @Nullable LocationEnum getLocation() {
    return location;
  }

  public void setLocation(@Nullable LocationEnum location) {
    this.location = location;
  }

  public Bbu plmnId(String plmnId) {
    this.plmnId = plmnId;
    return this;
  }

  /**
   * Public Land Mobile Network identifier for the BBU, encoded as MCC+MNC.
   * @return plmnId
   */
  @NotNull @Pattern(regexp = "^[0-9]{5,6}$") 
  @Schema(name = "plmn_id", description = "Public Land Mobile Network identifier for the BBU, encoded as MCC+MNC.", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("plmn_id")
  public String getPlmnId() {
    return plmnId;
  }

  public void setPlmnId(String plmnId) {
    this.plmnId = plmnId;
  }

  public Bbu ipAddress(@Nullable String ipAddress) {
    this.ipAddress = ipAddress;
    return this;
  }

  /**
   * IPv4 address of this baseband unit.
   * @return ipAddress
   */
  
  @Schema(name = "ip_address", description = "IPv4 address of this baseband unit.", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("ip_address")
  public @Nullable String getIpAddress() {
    return ipAddress;
  }

  public void setIpAddress(@Nullable String ipAddress) {
    this.ipAddress = ipAddress;
  }

  public Bbu status(@Nullable StatusEnum status) {
    this.status = status;
    return this;
  }

  /**
   * Get status
   * @return status
   */
  
  @Schema(name = "status", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("status")
  public @Nullable StatusEnum getStatus() {
    return status;
  }

  public void setStatus(@Nullable StatusEnum status) {
    this.status = status;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Bbu bbu = (Bbu) o;
    return Objects.equals(this.bbuId, bbu.bbuId) &&
        Objects.equals(this.location, bbu.location) &&
        Objects.equals(this.plmnId, bbu.plmnId) &&
        Objects.equals(this.ipAddress, bbu.ipAddress) &&
        Objects.equals(this.status, bbu.status);
  }

  @Override
  public int hashCode() {
    return Objects.hash(bbuId, location, plmnId, ipAddress, status);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Bbu {\n");
    sb.append("    bbuId: ").append(toIndentedString(bbuId)).append("\n");
    sb.append("    location: ").append(toIndentedString(location)).append("\n");
    sb.append("    plmnId: ").append(toIndentedString(plmnId)).append("\n");
    sb.append("    ipAddress: ").append(toIndentedString(ipAddress)).append("\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
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

