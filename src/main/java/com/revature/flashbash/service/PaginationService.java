package com.revature.flashbash.service;

import com.revature.flashbash.model.Order;
import com.revature.flashbash.util.PaginationOptions;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

public abstract class PaginationService<S>{

    Integer validateSize(int size){
        return size;
    }

    Integer validatePage(int page){
        return Math.max(page, 0);
    }

    Sort validateSortBy(Enum<?> sortBy){
        return !sortBy.name().equals("ID") ?
                Sort.by(sortBy.name().toLowerCase()) :
                Sort.by(sortBy.getDeclaringClass().getEnclosingClass().getSimpleName().toLowerCase() + "Id");
    }


    PageRequest buildPageRequest(PaginationOptions paginationOptions){
        int validatedPage = validatePage(paginationOptions.getPage());
        int validatedSize = validateSize(paginationOptions.getSize());
        Sort sort = validateSortBy(paginationOptions.getSortBy()).ascending();
        if (paginationOptions.getOrder() == Order.DESCENDING) sort = sort.descending();

        return PageRequest.of(validatedPage, validatedSize, sort);
    }
}
