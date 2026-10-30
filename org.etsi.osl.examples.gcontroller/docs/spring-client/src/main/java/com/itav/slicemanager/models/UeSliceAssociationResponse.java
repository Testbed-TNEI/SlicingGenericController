package com.itav.slicemanager.models;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.itav.slicemanager.models.UeSliceAssociation;
import org.springframework.lang.Nullable;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * Response envelope containing one UE slice association. 
 */

@Schema(name = "UeSliceAssociationResponse", description = "Response envelope containing one UE slice association. ")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-08-21T10:29:42.258422300+01:00[Europe/Lisbon]", comments = "Generator version: 7.19.0")
public class UeSliceAssociationResponse {

  private UeSliceAssociation association;

  public UeSliceAssociationResponse() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public UeSliceAssociationResponse(UeSliceAssociation association) {
    this.association = association;
  }

  public UeSliceAssociationResponse association(UeSliceAssociation association) {
    this.association = association;
    return this;
  }

  /**
   * Get association
   * @return association
   */
  @NotNull @Valid 
  @Schema(name = "association", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("association")
  public UeSliceAssociation getAssociation() {
    return association;
  }

  public void setAssociation(UeSliceAssociation association) {
    this.association = association;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UeSliceAssociationResponse ueSliceAssociationResponse = (UeSliceAssociationResponse) o;
    return Objects.equals(this.association, ueSliceAssociationResponse.association);
  }

  @Override
  public int hashCode() {
    return Objects.hash(association);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class UeSliceAssociationResponse {\n");
    sb.append("    association: ").append(toIndentedString(association)).append("\n");
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

