package code.tournoi.squash.controler;

import metier.model.Sport;
import metier.service.Service;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class ActionAjoutSport extends Action {

    @Override
    public void execute(HttpServletRequest request) {
        String nomSport = request.getParameter("nomSport");
        String niveaux = request.getParameter("niveaux");
                    
        Service service = new Service();

        Sport s = new Sport(nomSport,niveaux);
        Long id = service.creerSport(s);
        request.setAttribute("etat", "1");
    }

}

