package io.regent.blogrestapi.contoller;

import lombok.experimental.UtilityClass;

/**
 * Created by @author Ifeanyichukwu Otiwa
 * 16/10/2022
 */

@UtilityClass
public class BlogsMapping {
    private static final String ROOT_URL = "/api/v1";
    public static final String POST_RESOURCE_V1= ROOT_URL + "/posts";

    public static final String COMMENT_RESOURCE_V1 = POST_RESOURCE_V1 +  "/{postId}/comments";
    public static final String AUTH = ROOT_URL + "/auth";
}
