package code.tournoi.squash.controler;

import metier.model.Level;
import metier.service.Service;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class ActionGetUserLevel extends Action {

    @Override
    public void execute(HttpServletRequest request) {
        Long id = Long.parseLong(request.getParameter("id"), 10);

        Service service = new Service();

        List<Level> l = service.getLevels(id);
        
        request.setAttribute("liste", l);
    }

}

