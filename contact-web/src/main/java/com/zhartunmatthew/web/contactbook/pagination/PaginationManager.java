package com.zhartunmatthew.web.contactbook.pagination;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

public class PaginationManager {

    private static final int CONTACTS_PER_PAGE = 15;
    private static final String PAGE_PARAMETER = "page";
    private int pageCount = 0;
    private HttpServletRequest request;

    public PaginationManager(HttpServletRequest request, long itemsCount) {
        this.pageCount = (int) (itemsCount + CONTACTS_PER_PAGE - 1) / CONTACTS_PER_PAGE;
        this.request = request;
    }

    public int getOffset() {
        int pageNumber = getActivePage();
        return (pageNumber - 1) * CONTACTS_PER_PAGE;
    }

    public int getLimit () {
        return CONTACTS_PER_PAGE;
    }

    public Pagination getPagination() {
        Pagination pagination = new Pagination();
        pagination.setActivePage(getActivePage());
        pagination.setPageCount(pageCount);
        return pagination;
    }

    private int getActivePage() {
        int pageNumber = 1;
        String pageParameter = request.getParameter(PAGE_PARAMETER);
        if(!Objects.isNull(pageParameter)) {
            try {
                pageNumber = Integer.parseInt(pageParameter);
            } catch (NumberFormatException ex) {
                ex.printStackTrace();
                pageNumber = 1;
            }
        }
        pageNumber = pageNumber >= pageCount ? pageCount : pageNumber;
        pageNumber = pageNumber < 1 ? 1 : pageNumber;

        return pageNumber;
    }

}
