package org.etsi.osl.example.gc.api.resources;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.itav.slicemanager.models.OperationStatusResponse;
import org.etsi.osl.tmf.common.model.Any;
import org.etsi.osl.tmf.common.model.EValueType;
import org.etsi.osl.tmf.ri639.model.Characteristic;
import org.etsi.osl.tmf.ri639.model.ResourceUpdate;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.UUID;

/**
 * Reads and writes the {@code status.creation.requestId} / {@code status.deletion.requestId}
 * characteristics that carry a Slice Manager async operation's {@code request_id} on a
 * {@link ResourceUpdate}, and the {@code status.creation.<requestId>.outcome} /
 * {@code status.delete.<requestId>.outcome} characteristics that carry that operation's last known
 * {@link OperationStatusResponse}, serialized as JSON.
 *
 * <p>Set by {@code ResourceSlice}/{@code ResourceUE}'s create/delete logic (request id) and
 * {@code PollingService} (outcome) right after the Slice Manager accepts/settles a request, and
 * read by {@code PollingService} to know which operation to poll. Kept separate for creation and
 * deletion so both survive on the resource at once (e.g. a create that is retried after a delete,
 * or vice versa) and are never cleared once set, giving a durable record of the last request
 * submitted, and its outcome, for each operation.
 */
public final class LifecycleCharacteristics {

    public static final String CHAR_STATUS_CREATION_REQUEST_ID = "status.creation.requestId";
    public static final String CHAR_STATUS_DELETION_REQUEST_ID = "status.deletion.requestId";

    private static final DateTimeFormatter MICROS_FORMATTER = DateTimeFormatter
        .ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'")
        .withZone(ZoneOffset.UTC);

    private static final ObjectMapper OUTCOME_MAPPER = new ObjectMapper()
        .findAndRegisterModules()
        .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
        .setSerializationInclusion(JsonInclude.Include.NON_NULL);

    private LifecycleCharacteristics() {
    }

    public static void setCreationRequestId(ResourceUpdate resourceUpdate, UUID requestId) {
        setRequestId(resourceUpdate, CHAR_STATUS_CREATION_REQUEST_ID, requestId);
    }

    public static void setDeletionRequestId(ResourceUpdate resourceUpdate, UUID requestId) {
        setRequestId(resourceUpdate, CHAR_STATUS_DELETION_REQUEST_ID, requestId);
    }

    public static UUID getCreationRequestId(ResourceUpdate resourceUpdate) {
        return getRequestId(resourceUpdate, CHAR_STATUS_CREATION_REQUEST_ID);
    }

    public static UUID getDeletionRequestId(ResourceUpdate resourceUpdate) {
        return getRequestId(resourceUpdate, CHAR_STATUS_DELETION_REQUEST_ID);
    }

    /**
     * Records the last known status of a creation request, keyed by its own request id so every
     * request a resource ever submitted keeps its own outcome.
     */
    public static void setCreationOutcome(ResourceUpdate resourceUpdate, OperationStatusResponse outcome) {
        setOutcome(resourceUpdate, "status.creation.%s.outcome".formatted(outcome.getRequestId()), outcome);
    }

    /**
     * Same as {@link #setCreationOutcome}, for a deletion request.
     */
    public static void setDeletionOutcome(ResourceUpdate resourceUpdate, OperationStatusResponse outcome) {
        setOutcome(resourceUpdate, "status.delete.%s.outcome".formatted(outcome.getRequestId()), outcome);
    }

    /**
     * Stamps when the CREATE message for {@code requestId} was received, with microsecond precision
     * (e.g. {@code 2026-08-13T15:25:04.410898Z}). Keyed by request id, like {@link #setCreationOutcome},
     * so every request a resource ever submitted keeps its own arrival time.
     */
    public static void setCreationMessageTimestamp(ResourceUpdate resourceUpdate, UUID requestId, Instant timestamp) {
        setTimestamp(resourceUpdate, "status.creation.%s.message.timestamp".formatted(requestId), timestamp);
    }

    /**
     * Stamps the instant, with microsecond precision, immediately before the Slice Manager create
     * endpoint (slice or UE slice-association) was called for {@code requestId}. Set by
     * {@code ResourceSlice}/{@code ResourceUE}'s create logic right after the call returns the
     * request id it was captured for.
     */
    public static void setCreationStart(ResourceUpdate resourceUpdate, UUID requestId, Instant timestamp) {
        setTimestamp(resourceUpdate, "status.creation.%s.start".formatted(requestId), timestamp);
    }

    /**
     * Stamps the instant, with microsecond precision, immediately after the outcome for
     * {@code requestId} was obtained by polling {@code GET /operations/{request_id}} to a terminal
     * state. Set by {@code PollingService} right after {@code pollUntilTerminal} returns.
     */
    public static void setCreationEnd(ResourceUpdate resourceUpdate, UUID requestId, Instant timestamp) {
        setTimestamp(resourceUpdate, "status.creation.%s.end".formatted(requestId), timestamp);
    }

    private static void setTimestamp(ResourceUpdate resourceUpdate, String characteristicName, Instant timestamp) {
        if (resourceUpdate.getResourceCharacteristic() != null) {
            resourceUpdate.getResourceCharacteristic().removeIf(c -> characteristicName.equals(c.getName()));
        }
        resourceUpdate.addResourceCharacteristicItem(new Characteristic()
            .name(characteristicName)
            .valueType(EValueType.TEXT.getValue())
            .value(new Any(MICROS_FORMATTER.format(timestamp))));
    }

    private static void setRequestId(ResourceUpdate resourceUpdate, String characteristicName, UUID requestId) {
        if (resourceUpdate.getResourceCharacteristic() != null) {
            resourceUpdate.getResourceCharacteristic().removeIf(c -> characteristicName.equals(c.getName()));
        }
        resourceUpdate.addResourceCharacteristicItem(new Characteristic()
            .name(characteristicName)
            .valueType(EValueType.TEXT.getValue())
            .value(new Any(requestId.toString())));
    }

    private static UUID getRequestId(ResourceUpdate resourceUpdate, String characteristicName) {
        if (resourceUpdate.getResourceCharacteristic() == null) {
            return null;
        }
        return resourceUpdate.getResourceCharacteristic().stream()
            .filter(c -> characteristicName.equals(c.getName()))
            .findFirst()
            .map(c -> c.getValue() != null ? c.getValue().getValue() : null)
            .filter(Objects::nonNull)
            .map(UUID::fromString)
            .orElse(null);
    }

    private static void setOutcome(ResourceUpdate resourceUpdate, String characteristicName, OperationStatusResponse outcome) {
        String outcomeJson;
        try {
            outcomeJson = OUTCOME_MAPPER.writeValueAsString(outcome);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("Could not serialize operation outcome " + outcome.getRequestId(), e);
        }

        if (resourceUpdate.getResourceCharacteristic() != null) {
            resourceUpdate.getResourceCharacteristic().removeIf(c -> characteristicName.equals(c.getName()));
        }
        resourceUpdate.addResourceCharacteristicItem(new Characteristic()
            .name(characteristicName)
            .valueType(EValueType.TEXT.getValue())
            .value(new Any(outcomeJson)));
    }

}
