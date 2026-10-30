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
 * Fields used to modify a UE slice association.  The &#x60;dnn&#x60;, &#x60;snssai&#x60;, and &#x60;slice_id&#x60; fields select the target **DNN QoS template** for the UE. 
 */

@Schema(name = "UeSliceAssociationUpdateRequest", description = "Fields used to modify a UE slice association.  The `dnn`, `snssai`, and `slice_id` fields select the target **DNN QoS template** for the UE. ")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-08-21T10:29:42.258422300+01:00[Europe/Lisbon]", comments = "Generator version: 7.19.0")
public class UeSliceAssociationUpdateRequest {

  private String dnn;

  private String snssai;

  private String sliceId;

  public UeSliceAssociationUpdateRequest() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public UeSliceAssociationUpdateRequest(String dnn, String snssai, String sliceId) {
    this.dnn = dnn;
    this.snssai = snssai;
    this.sliceId = sliceId;
  }

  public UeSliceAssociationUpdateRequest dnn(String dnn) {
    this.dnn = dnn;
    return this;
  }

  /**
   * Target DNN.
   * @return dnn
   */
  @NotNull @Size(min = 3) 
  @Schema(name = "dnn", description = "Target DNN.", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("dnn")
  public String getDnn() {
    return dnn;
  }

  public void setDnn(String dnn) {
    this.dnn = dnn;
  }

  public UeSliceAssociationUpdateRequest snssai(String snssai) {
    this.snssai = snssai;
    return this;
  }

  /**
   * Target S-NSSAI.
   * @return snssai
   */
  @NotNull @Pattern(regexp = "^[0-9]{1,3}-[0-9]{6}$") 
  @Schema(name = "snssai", description = "Target S-NSSAI.", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("snssai")
  public String getSnssai() {
    return snssai;
  }

  public void setSnssai(String snssai) {
    this.snssai = snssai;
  }

  public UeSliceAssociationUpdateRequest sliceId(String sliceId) {
    this.sliceId = sliceId;
    return this;
  }

  /**
   * Target slice identifier and QoS profile selector.
   * @return sliceId
   */
  @NotNull @Size(min = 3) 
  @Schema(name = "slice_id", description = "Target slice identifier and QoS profile selector.", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("slice_id")
  public String getSliceId() {
    return sliceId;
  }

  public void setSliceId(String sliceId) {
    this.sliceId = sliceId;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UeSliceAssociationUpdateRequest ueSliceAssociationUpdateRequest = (UeSliceAssociationUpdateRequest) o;
    return Objects.equals(this.dnn, ueSliceAssociationUpdateRequest.dnn) &&
        Objects.equals(this.snssai, ueSliceAssociationUpdateRequest.snssai) &&
        Objects.equals(this.sliceId, ueSliceAssociationUpdateRequest.sliceId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(dnn, snssai, sliceId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class UeSliceAssociationUpdateRequest {\n");
    sb.append("    dnn: ").append(toIndentedString(dnn)).append("\n");
    sb.append("    snssai: ").append(toIndentedString(snssai)).append("\n");
    sb.append("    sliceId: ").append(toIndentedString(sliceId)).append("\n");
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

