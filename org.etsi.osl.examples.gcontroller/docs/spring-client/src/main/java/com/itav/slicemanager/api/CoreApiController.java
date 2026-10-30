package com.itav.slicemanager.api;

import com.itav.slicemanager.models.ErrorResponse;
import org.springframework.lang.Nullable;
import com.itav.slicemanager.models.OperationStatusResponse;
import com.itav.slicemanager.models.Slice;
import com.itav.slicemanager.models.SliceListResponse;
import com.itav.slicemanager.models.SliceResponse;
import com.itav.slicemanager.models.UeControlInfoResponse;
import com.itav.slicemanager.models.UePduSessionResponse;
import com.itav.slicemanager.models.UeSliceAssociationCreateRequest;
import com.itav.slicemanager.models.UeSliceAssociationListResponse;
import com.itav.slicemanager.models.UeSliceAssociationResponse;
import com.itav.slicemanager.models.UeSliceAssociationUpdateRequest;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.context.request.NativeWebRequest;

import jakarta.validation.constraints.*;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import jakarta.annotation.Generated;

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-08-20T10:16:24.997387378+01:00[Europe/Lisbon]", comments = "Generator version: 7.19.0")
@Controller
@RequestMapping("${openapi.sliceManager.base-path:}")
public class CoreApiController implements CoreApi {

    private final NativeWebRequest request;

    @Autowired
    public CoreApiController(NativeWebRequest request) {
        this.request = request;
    }

    @Override
    public Optional<NativeWebRequest> getRequest() {
        return Optional.ofNullable(request);
    }

}
