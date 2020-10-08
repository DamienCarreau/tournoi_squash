package code.tournoi.squash.controler;

import metier.model.Sport;
import metier.service.Service;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class ActionGetSportLevel extends Action {

    @Override
    public void execute(HttpServletRequest request) {
        Long id = Long.parseLong(request.getParameter("id"), 10) ;
        Service service = new Service();

        List<String> list = service.getSportLevel(id);
        request.setAttribute("levels", list);
        Sport sport = service.getSport(id);
        request.setAttribute("sportName", sport.getNom());
    }

}

