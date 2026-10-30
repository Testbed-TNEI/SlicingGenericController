package org.etsi.osl.example.gc.api.services;

import com.itav.slicemanager.api.CoreApi;
import com.itav.slicemanager.api.OperationsApi;
import com.itav.slicemanager.models.OperationStatusResponse;
import com.itav.slicemanager.models.Slice;
import com.itav.slicemanager.models.SliceListResponse;
import com.itav.slicemanager.models.SliceResponse;
import com.itav.slicemanager.models.UeSliceAssociationCreateRequest;
import com.itav.slicemanager.models.UeSliceAssociationResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.UnknownContentTypeException;
import org.springframework.web.client.UnknownHttpStatusCodeException;

import java.util.UUID;
import java.util.function.Supplier;

@Service
public class SliceManagerService {

    private static final Logger logger = LoggerFactory.getLogger("org.etsi.osl.example.gc");

    private final RestClient client;

    public SliceManagerService(RestClient sliceManagerRestClient) {
        this.client = sliceManagerRestClient;
    }

    public ApiResult<SliceListResponse> listSlices() {
        return executeCall(() -> client
            .get()
            .uri(CoreApi.PATH_LIST_SLICES)
            .retrieve()
            .body(SliceListResponse.class), "listSlices");
    }

    public ApiResult<OperationStatusResponse> createSlice(Slice slice) {
        return executeCall(() -> client
            .post()
            .uri(CoreApi.PATH_CREATE_SLICE)
            .contentType(MediaType.APPLICATION_JSON)
            .body(slice)
            .retrieve()
            .body(OperationStatusResponse.class), "createSlice");
    }

    public ApiResult<SliceResponse> getSlice(String sliceId) {
        return executeCall(() -> client
            .get()
            .uri(CoreApi.PATH_GET_SLICE, sliceId)
            .retrieve()
            .body(SliceResponse.class), "getSlice(" + sliceId + ")");
    }

    public ApiResult<OperationStatusResponse> deleteSlice(String sliceId) {
        return executeCall(() -> client
            .delete()
            .uri(CoreApi.PATH_DELETE_SLICE, sliceId)
            .retrieve()
            .body(OperationStatusResponse.class), "deleteSlice(" + sliceId + ")");
    }

    public ApiResult<OperationStatusResponse> associateUeToSlice(String ueId, UeSliceAssociationCreateRequest ueSliceAssociationCreateRequest) {
        return executeCall(() -> client
            .post()
            .uri(CoreApi.PATH_CREATE_UE_SLICE_ASSOCIATION, ueId)
            .contentType(MediaType.APPLICATION_JSON)
            .body(ueSliceAssociationCreateRequest)
            .retrieve()
            .body(OperationStatusResponse.class), "associateUeToSlice(" + ueId + ")");
    }

    public ApiResult<UeSliceAssociationResponse> getUeSliceAssociation(String ueId, String sliceId) {
        return executeCall(() -> client
            .get()
            .uri(CoreApi.PATH_GET_UE_SLICE_ASSOCIATION, ueId, sliceId)
            .retrieve()
            .body(UeSliceAssociationResponse.class), "getUeSliceAssociation(" + ueId + ", " + sliceId + ")");
    }

    public ApiResult<OperationStatusResponse> disassociateUeFromSlice(String ueId, String sliceId) {
        return executeCall(() -> client
            .delete()
            .uri(CoreApi.PATH_DELETE_UE_SLICE_ASSOCIATION, ueId, sliceId)
            .retrieve()
            .body(OperationStatusResponse.class), "disassociateUeFromSlice(" + ueId + ", " + sliceId + ")");
    }

    public ApiResult<OperationStatusResponse> getOperationStatus(UUID requestId) {
        return executeCall(() -> client
            .get()
            .uri(OperationsApi.PATH_GET_OPERATION_STATUS, requestId)
            .retrieve()
            .body(OperationStatusResponse.class), "getOperationStatus(" + requestId + ")");
    }

    /**
     * Runs a single RestClient call, catching every failure mode it can throw and logging it with
     * enough detail to diagnose (status code / body / raw code / content type as applicable).
     *
     * <p>Order matters: {@link HttpClientErrorException.NotFound}, {@link HttpServerErrorException},
     * and {@link UnknownHttpStatusCodeException} are all subtypes of
     * {@link RestClientResponseException}, itself a subtype of {@link RestClientException} (as are
     * {@link ResourceAccessException} and {@link UnknownContentTypeException}), so the specific
     * cases must be caught first.
     */
    private <T> ApiResult<T> executeCall(Supplier<T> call, String context) {
        try {
            return new ApiResult.Success<>(call.get());
        } catch (HttpClientErrorException.NotFound e) {
            logger.info("{}: not found", context);
            return new ApiResult.NotFound<>();
        } catch (HttpClientErrorException e) {
            // Other 4xx: bad request, etc.
            logger.error("{}: client error {} - {}", context, e.getStatusCode(), e.getResponseBodyAsString());
        } catch (HttpServerErrorException e) {
            // 5xx: internal error, RAN session unavailable, etc.
            logger.error("{}: server error {} - {}", context, e.getStatusCode(), e.getResponseBodyAsString());
        } catch (UnknownHttpStatusCodeException e) {
            // A status code Spring doesn't recognize as a standard HTTP status.
            logger.error("{}: unknown status code {} - {}", context, e.getRawStatusCode(), e.getResponseBodyAsString());
        } catch (RestClientResponseException e) {
            // Catch-all for any other status-code-related exception not covered above.
            logger.error("{}: unexpected response error {} - {}", context, e.getStatusCode(), e.getResponseBodyAsString());
        } catch (UnknownContentTypeException e) {
            // Response body couldn't be converted (bad/missing Content-Type).
            logger.error("{}: could not parse response body, content-type {}", context, e.getContentType());
        } catch (ResourceAccessException e) {
            // DNS failure, timeout, connection refused, etc.
            logger.error("{}: network error - {}", context, e.getMessage());
        } catch (RestClientException e) {
            // Catch-all: serialization failures, or anything else RestClient can throw.
            logger.error("{}: call failed - {}", context, e.getMessage());
        }
        return new ApiResult.Error<>();
    }

}