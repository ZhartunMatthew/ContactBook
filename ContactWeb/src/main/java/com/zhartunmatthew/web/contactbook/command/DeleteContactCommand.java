package com.zhartunmatthew.web.contactbook.command;

import com.zhartunmatthew.web.contactbook.dao.AttachmentDAO;
import com.zhartunmatthew.web.contactbook.dao.ContactDAO;
import com.zhartunmatthew.web.contactbook.dao.PhoneDAO;
import com.zhartunmatthew.web.contactbook.dao.daofactory.DAOFactory;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

public class DeleteContactCommand implements AbstractCommand {

    private static Logger log = Logger.getLogger(DeleteContactCommand.class);

    @Override
    public String execute(HttpServletRequest request) {
        String[] items = request.getParameterValues("contact-check");
        ArrayList<Long> checkedItems = new ArrayList<>();

        if(items != null) {
            for(String val : items) {
                checkedItems.add(Long.parseLong(val));
                log.info("Checked: " + Integer.parseInt(val));
            }
        } else {
            log.info("NO CHECKED ITEMS");
        }

        try(AttachmentDAO attachmentDAO = (AttachmentDAO) DAOFactory.createDAO(AttachmentDAO.class)){
            checkedItems.forEach(attachmentDAO::delete);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        try(PhoneDAO phoneDAO = (PhoneDAO) DAOFactory.createDAO(PhoneDAO.class)){
            checkedItems.forEach(phoneDAO::delete);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        try(ContactDAO contactDAO = (ContactDAO) DAOFactory.createDAO(ContactDAO.class)) {
            for(Long id : checkedItems) {
                contactDAO.deleteContactAddress(id);
                contactDAO.delete(id);
            }
        } catch (Exception ex){
            ex.printStackTrace();
        }

        return null;
    }

    @Override
    public boolean isRedirectedCommand() {
        return true;
    }
}
