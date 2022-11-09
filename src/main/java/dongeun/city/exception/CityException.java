package dongeun.city.exception;

import dongeun.common.exception.BaseException;
import dongeun.common.exception.ErrorCode;

public class CityException extends BaseException {

    public CityException(ErrorCode errorCode) {
        super(errorCode);
    }

    public CityException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }
}
