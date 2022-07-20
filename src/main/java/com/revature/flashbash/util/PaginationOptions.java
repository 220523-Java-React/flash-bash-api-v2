package com.revature.flashbash.util;
import lombok.Data;

@Data
public class PaginationOptions {
    private Integer page;
    private Integer size;
    private Enum<?> sortBy;
    private Enum<?> order;

    public PaginationOptions(Integer page, Integer size, Enum<?> sortBy, Enum<?> order) {
        this.page = page;
        this.size = size;
        this.sortBy = sortBy;
        this.order = order;
    }
}
