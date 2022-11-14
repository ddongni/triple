package dongeun.common.exception;

import lombok.Getter;

@Getter
public class ErrorResponse {

    private int code;

    private ErrorCode status;

    private String message;

    public ErrorResponse(BaseException e) {
        this.code = e.errorCode.getErrorCode();
        this.status = e.errorCode;
        this.message = e.getMessage();
    }
}
