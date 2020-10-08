package code.tournoi.squash.controler;

import metier.model.Admin;
import metier.service.Service;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class ActionGetAllAdmin extends Action {

    @Override
    public void execute(HttpServletRequest request) {
        Service service = new Service();

        List<Admin> l = service.getAdmins();
        request.setAttribute("admins", l);
    }

}

