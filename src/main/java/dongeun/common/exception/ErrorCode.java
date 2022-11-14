package dongeun.common.exception;

public enum ErrorCode {

    // 400 BAD_REQUEST : 잘못된 요청
    INVALID_METHOD_ARGUMENT(400, "유효하지 않는 파라미터 값입니다"),
    INVALID_REQUIRED_PARAMETER(400, "유효하지 않는 필수 파라미터 값입니다"),

    // 404 NOT_FOUND : 데이터를 찾을 수 없음
    NOT_FOUND_DATA(404,"데이터를 찾을 수 없습니다"),

    // 500 INTERNAL_SERVER_ERROR : 내부 서버 오류
    INTERNAL_SERVER_ERROR(500, "내부 서버 오류"),
    DATABASE_ERROR(500, "데이터베이스 오류");

    private int errorCode;
    private String errorMessage;

    public String getErrorMessage() {
        return errorMessage;
    }

    public int getErrorCode() {
        return errorCode;
    }

    ErrorCode(int errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
}
