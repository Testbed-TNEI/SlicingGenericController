package org.etsi.osl.example.gc.api.services;

/**
 * Outcome of a single {@link SliceManagerService} call: {@link Success}, {@link NotFound}, or any
 * other {@link Error} (already logged with detail by {@code SliceManagerService.executeCall}).
 */
public sealed interface ApiResult<T> {

    record Success<T>(T value) implements ApiResult<T> {}

    record NotFound<T>() implements ApiResult<T> {}

    record Error<T>() implements ApiResult<T> {}

}