package io.regent.blogrestapi.interceptor.payload;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by @author Ifeanyichukwu Otiwa
 * 19/10/2022
 */

@Builder
@Getter
@Setter
public class ErrorDetails {
    private String timeStamp;
    private int status;
    private String errorMessage;
    private String path;
}
