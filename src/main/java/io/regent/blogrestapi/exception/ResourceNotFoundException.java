package io.regent.blogrestapi.exception;

import org.springframework.core.NestedExceptionUtils;
import org.springframework.core.NestedRuntimeException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.Getter;

/**
 * Created by @author Ifeanyichukwu Otiwa
 * 16/10/2022
 */


@Getter
@SuppressWarnings("all")
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends NestedRuntimeException {
    private String resourceName;
    private String fieldName;
    private String fieldValue;
    private static final int STATUS = 404;

    public ResourceNotFoundException(final String msg) {
        super(msg, null);

    }

    public ResourceNotFoundException(final String resourceName, final String fieldName, final String fieldValue) {
        this(String.format("%s not found with %s: %s", resourceName, fieldName, fieldValue));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

    @Override
    public String getMessage() {
        HttpStatus code = HttpStatus.resolve(STATUS);
        final String reason = String.format("%s not found with %s: %s", this.getResourceName(), this.getFieldName(),
                this.getFieldValue());
        final String msg = (code != null ? code : STATUS) + (reason != null ? " \"" + reason + "\"" : "");
        return NestedExceptionUtils.buildMessage(msg, getCause());
    }
}
