package dongeun.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

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

    // @valid 유효성 검사 에러
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("handleMethodArgumentNotValidException : {}", e);
        BaseException baseException = new BaseException(ErrorCode.INVALID_METHOD_ARGUMENT, "파라미터 값을 다시 확인해주세요.");
        return ResponseEntity
                .status(baseException.errorCode.getErrorCode())
                .body(new ErrorResponse(baseException));
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
}