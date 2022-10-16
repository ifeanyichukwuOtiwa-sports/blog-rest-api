package io.regent.blogrestapi.dtos;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Created by @author Ifeanyichukwu Otiwa
 * 16/10/2022
 */

@Getter
@Setter
@Builder
@ToString
public class PagedListDTO<T> {
    private int pageNo;
    private int pageSize;
    private long totalElements;
    private int totalPages;
    private  boolean last;
    private List<? extends T> contents;
}
