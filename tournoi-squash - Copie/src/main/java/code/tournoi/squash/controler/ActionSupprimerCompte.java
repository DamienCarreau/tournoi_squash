package code.tournoi.squash.controler;

import metier.service.Service;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class ActionSupprimerCompte extends Action {

    @Override
    public void execute(HttpServletRequest request) {
        Long id = Long.parseLong(request.getParameter("id"), 10);

        Service service = new Service();
        boolean b = service.supprimerJoueur(id);
        
        if(!b){
            request.setAttribute("etat", "0");
        }else
            request.setAttribute("etat", "1");
    }

}


