package net.gentledot.keepsd.models.board.response;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.http.HttpStatus;

public class ApiResult<T> {
    private final Boolean processResult;
    private final T response;
    private final ApiError error;

    public ApiResult(Boolean processResult, T response, ApiError error) {
        this.processResult = processResult;
        this.response = response;
        this.error = error;
    }

    public static <T> ApiResult<T> OK(T response) {
        return new ApiResult<>(true, response, null);
    }

    public static ApiResult ERROR(Throwable throwable, HttpStatus status) {
        return new ApiResult<>(false, throwable.getClass(), new ApiError(throwable.getMessage(), status));
    }

    public static ApiResult ERROR(String errorMessage, HttpStatus status) {
        return new ApiResult<>(false, null, new ApiError(errorMessage, status));
    }

    public Boolean getProcessResult() {
        return processResult;
    }

    public T getResponse() {
        return response;
    }

    public ApiError getError() {
        return error;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("processResult", processResult)
                .append("response", response)
                .append("error", error)
                .toString();
    }
}
