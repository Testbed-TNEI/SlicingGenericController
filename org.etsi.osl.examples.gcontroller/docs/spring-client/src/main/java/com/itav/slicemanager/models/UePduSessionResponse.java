package com.itav.slicemanager.models;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.itav.slicemanager.models.UePduSession;
import org.springframework.lang.Nullable;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * Response envelope containing one &#x60;UePduSession&#x60;.
 */

@Schema(name = "UePduSessionResponse", description = "Response envelope containing one `UePduSession`.")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-08-21T10:29:42.258422300+01:00[Europe/Lisbon]", comments = "Generator version: 7.19.0")
public class UePduSessionResponse {

  private UePduSession pduSession;

  public UePduSessionResponse() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public UePduSessionResponse(UePduSession pduSession) {
    this.pduSession = pduSession;
  }

  public UePduSessionResponse pduSession(UePduSession pduSession) {
    this.pduSession = pduSession;
    return this;
  }

  /**
   * Get pduSession
   * @return pduSession
   */
  @NotNull @Valid 
  @Schema(name = "pdu_session", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("pdu_session")
  public UePduSession getPduSession() {
    return pduSession;
  }

  public void setPduSession(UePduSession pduSession) {
    this.pduSession = pduSession;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UePduSessionResponse uePduSessionResponse = (UePduSessionResponse) o;
    return Objects.equals(this.pduSession, uePduSessionResponse.pduSession);
  }

  @Override
  public int hashCode() {
    return Objects.hash(pduSession);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class UePduSessionResponse {\n");
    sb.append("    pduSession: ").append(toIndentedString(pduSession)).append("\n");
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

