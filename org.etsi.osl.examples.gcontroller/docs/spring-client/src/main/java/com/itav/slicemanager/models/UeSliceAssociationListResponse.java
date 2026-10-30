package com.itav.slicemanager.models;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.itav.slicemanager.models.UeSliceAssociation;
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
 * Response envelope containing all slice associations available to a UE. 
 */

@Schema(name = "UeSliceAssociationListResponse", description = "Response envelope containing all slice associations available to a UE. ")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-08-21T10:29:42.258422300+01:00[Europe/Lisbon]", comments = "Generator version: 7.19.0")
public class UeSliceAssociationListResponse {

  @Valid
  private List<@Valid UeSliceAssociation> associations = new ArrayList<>();

  public UeSliceAssociationListResponse() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public UeSliceAssociationListResponse(List<@Valid UeSliceAssociation> associations) {
    this.associations = associations;
  }

  public UeSliceAssociationListResponse associations(List<@Valid UeSliceAssociation> associations) {
    this.associations = associations;
    return this;
  }

  public UeSliceAssociationListResponse addAssociationsItem(UeSliceAssociation associationsItem) {
    if (this.associations == null) {
      this.associations = new ArrayList<>();
    }
    this.associations.add(associationsItem);
    return this;
  }

  /**
   * Get associations
   * @return associations
   */
  @NotNull @Valid 
  @Schema(name = "associations", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("associations")
  public List<@Valid UeSliceAssociation> getAssociations() {
    return associations;
  }

  public void setAssociations(List<@Valid UeSliceAssociation> associations) {
    this.associations = associations;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UeSliceAssociationListResponse ueSliceAssociationListResponse = (UeSliceAssociationListResponse) o;
    return Objects.equals(this.associations, ueSliceAssociationListResponse.associations);
  }

  @Override
  public int hashCode() {
    return Objects.hash(associations);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class UeSliceAssociationListResponse {\n");
    sb.append("    associations: ").append(toIndentedString(associations)).append("\n");
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

