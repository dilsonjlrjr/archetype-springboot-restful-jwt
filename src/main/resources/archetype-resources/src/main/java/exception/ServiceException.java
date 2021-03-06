package ${package}.exception;

import lombok.Getter;

public class ServiceException extends RuntimeException {

    @Getter
    private final String code;
    public ServiceException(String message, String code) {
        super(message);
        this.code = code;
    }
}
