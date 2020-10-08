package code.tournoi.squash.controler;

import metier.model.Matchs;
import metier.service.Service;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class ActionMatchDemandes extends Action {

    @Override
    public void execute(HttpServletRequest request) {
        Long idUser = Long.parseLong(request.getParameter("idUser"), 10);
        Long idSport = Long.parseLong(request.getParameter("idSport"), 10);
        Service service = new Service();

        List<Matchs> list = service.matchDemandes(idUser,idSport);
        request.setAttribute("matchs", list);
    }

}

