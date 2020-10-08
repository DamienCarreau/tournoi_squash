package code.tournoi.squash.controler;

import metier.model.Matchs;
import metier.service.Service;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class ActionMatchAccepte extends Action {

    @Override
    public void execute(HttpServletRequest request) {
        Long idMatch = Long.parseLong(request.getParameter("idMatch"), 10);
                   
        Service service = new Service();

        boolean b = service.matchAccepte(idMatch);
        if(!b)
            request.setAttribute("etat", "0");
        else
            request.setAttribute("etat", "1");
    }

}

