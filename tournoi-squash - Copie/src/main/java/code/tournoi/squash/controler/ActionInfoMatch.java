package code.tournoi.squash.controler;

import metier.model.Matchs;
import metier.service.Service;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class ActionInfoMatch extends Action {

    @Override
    public void execute(HttpServletRequest request) {
        Long idMatch = Long.parseLong(request.getParameter("idMatch"), 10);
        Service service = new Service();

        Matchs m = service.infoMatch(idMatch);
        Long classementJ1 = service.getLevel(m.getJoueur1().getId(),m.getSport().getId()).getClassement();
        Long classementJ2 = service.getLevel(m.getJoueur2().getId(),m.getSport().getId()).getClassement();
        request.setAttribute("match", m);
        request.setAttribute("classementJ1", classementJ1);
        request.setAttribute("classementJ2", classementJ2);
    }

}

