package code.tournoi.squash.controler;

import metier.model.Matchs;
import metier.service.Service;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class ActionAfficheMatch extends Action {

    @Override
    public void execute(HttpServletRequest request) {
        Long idSport = Long.parseLong(request.getParameter("idSport"), 10);
        Service service = new Service();

        List<Matchs> list = service.afficheMatchs(idSport);
        request.setAttribute("matchs", list);
    }

}

