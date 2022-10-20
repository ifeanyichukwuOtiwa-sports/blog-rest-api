package io.regent.blogrestapi.interceptor.exception;

import org.springframework.core.NestedExceptionUtils;
import org.springframework.core.NestedRuntimeException;
import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by @author Ifeanyichukwu Otiwa
 * 16/10/2022
 */

@Getter
@Setter
public class BlogAPIException extends NestedRuntimeException {
    private final int status;
    private final String reason;
    public BlogAPIException(final HttpStatus status, final String reason) {
        super("");
        this.status = status.value();
        this.reason = reason;
    }

    @Override
    public String getMessage() {
        HttpStatus code = HttpStatus.resolve(this.status);
        String msg = (code != null ? code : this.status) + (this.reason != null ? " " + this.reason + "" : "");
        return NestedExceptionUtils.buildMessage(msg, getCause());
    }
}
