package dongeun.common.exception;

import lombok.Getter;

@Getter
public class BaseException extends RuntimeException{

    public ErrorCode errorCode;

    public BaseException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public BaseException(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }
}
