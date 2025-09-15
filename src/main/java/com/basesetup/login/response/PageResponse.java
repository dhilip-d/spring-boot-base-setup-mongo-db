package com.basesetup.login.response;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Setter
@Getter
public class PageResponse<T> implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    boolean hasNext;
    boolean hasPrevious;
    private long totalRecordCount;
    private int totalPages;
    private T data;
}
