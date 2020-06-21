package net.gentledot.keepsd.models.board.response;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.http.HttpStatus;

@Hidden
public class ApiError {
    @Schema(name = "에러 메시지", description = "(요청 실패 시) 발생한 에러의 메시지")
    private final String message;

    @Schema(name = "HTTP Status", description = "(요청 실패 시) 서버 상태 전달")
    private final HttpStatus status;

    public ApiError(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public Integer getStatusCode() {
        return status.value();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("message", message)
                .append("status", status)
                .toString();
    }
}
