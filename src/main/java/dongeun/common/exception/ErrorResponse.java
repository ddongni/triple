package dongeun.common.exception;

import lombok.Getter;

@Getter
public class ErrorResponse {

    private int code;

    private String message;

    private ErrorCode status;

    public ErrorResponse(BaseException e) {
        this.code = e.getErrorCode().getCode();
        this.message = e.getMessage();
        this.status = e.getErrorCode();
    }
}
