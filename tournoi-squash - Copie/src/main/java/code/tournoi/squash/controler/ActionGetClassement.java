package code.tournoi.squash.controler;

import metier.model.Level;
import metier.service.Service;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class ActionGetClassement extends Action {

    @Override
    public void execute(HttpServletRequest request) {
        Long idSport = Long.parseLong(request.getParameter("idSport"), 10);
        Service service = new Service();

        List<Level> list = service.getClassement(idSport);
        request.setAttribute("levels", list);
    }

}

