package code.tournoi.squash.controler;

import metier.model.Matchs;
import metier.service.Service;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class ActionAnnoncerResultats extends Action {

    @Override
    public void execute(HttpServletRequest request) {
        Long idMatch = Long.parseLong(request.getParameter("idMatch"), 10);
        Long idVainqueur = Long.parseLong(request.getParameter("idVainqueur"), 10);
        int scoreJ1 = -1;
        int scoreJ2 = -1;
        try {
            scoreJ1 = Integer.parseInt(request.getParameter("scoreJ1"),10);
            scoreJ2 = Integer.parseInt(request.getParameter("scoreJ2"),10);
        } catch (Exception ex) {
            
        }
                    
        Service service = new Service();

        boolean b = service.annoncerResultats(idMatch,idVainqueur,scoreJ1,scoreJ2);
        if(!b)
            request.setAttribute("etat", "0");
        else
            request.setAttribute("etat", "1");
    }

}

