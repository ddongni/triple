package dongeun.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@Slf4j
@RestControllerAdvice
public class ControllerAdvice {

    // BaseException 에러
    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ErrorResponse> handleBaseException(BaseException e) {
        log.error("handleBaseException : {}", e);
        return ResponseEntity
                .status(e.errorCode.getErrorCode())
                .body(new ErrorResponse(e));
    }

    // Required Request Parameter 에러
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorResponse> handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
        log.error("handleMissingServletRequestParameterException : {}", e);
        BaseException baseException = new BaseException(ErrorCode.INVALID_REQUIRED_PARAMETER, "필수 파라미터 값을 다시 확인해주세요.");
        return ResponseEntity
                .status(baseException.errorCode.getErrorCode())
                .body(new ErrorResponse(baseException));
    }

    // Parameter Mismatch 에러 (Long 을 String 으로 입력시)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        log.error("handleMethodArgumentTypeMismatchException : {}", e);
        BaseException baseException = new BaseException(ErrorCode.INVALID_REQUIRED_PARAMETER, "파라미터 타입을 확인해주세요.");
        return ResponseEntity
                .status(baseException.errorCode.getErrorCode())
                .body(new ErrorResponse(baseException));
    }
}