package com.zhartunmatthew.web.contactbook.pagination;

import com.zhartunmatthew.web.contactbook.dao.ContactDAO;
import com.zhartunmatthew.web.contactbook.dao.daofactory.DAOFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

public class PaginationManager {

    private static final int CONTACTS_PER_PAGE = 3;
    public static final String PAGE_PARAMETER = "page";

    public static int getPageCount() {
        Long contactCount = 0L;
        try (ContactDAO contactDAO = (ContactDAO) DAOFactory.createDAO(ContactDAO.class)) {
            contactCount = contactDAO.getContactCount();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return (int)(contactCount + CONTACTS_PER_PAGE - 1) / CONTACTS_PER_PAGE;
    }

    public static int getOffset(HttpServletRequest request) {
        int pageNumber = getActivePage(request);
        return (pageNumber - 1) * CONTACTS_PER_PAGE;
    }

    public static int getActivePage(HttpServletRequest request) {
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
        pageNumber = pageNumber >= getPageCount() ? getPageCount() : pageNumber;
        pageNumber = pageNumber < 1 ? 1 : pageNumber;

        return pageNumber;
    }

    public static int getLimit () {
        return CONTACTS_PER_PAGE;
    }
}
