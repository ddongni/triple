package dongeun.trip.exception;

import dongeun.common.exception.BaseException;
import dongeun.common.exception.ErrorCode;

public class TripException extends BaseException {

    public TripException(ErrorCode errorCode) {
        super(errorCode);
    }

    public TripException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }
}
