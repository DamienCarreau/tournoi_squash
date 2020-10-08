package code.tournoi.squash.controler;

import metier.model.Joueur;
import metier.service.Service;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class ActionAccepteJoueur extends Action {

    @Override
    public void execute(HttpServletRequest request) {
        Long idUser = Long.parseLong(request.getParameter("id"), 10);
        
        Service service = new Service();

        boolean b = service.accepteJoueur(idUser);
        if(!b){
            request.setAttribute("etat", "0");
        }else
            request.setAttribute("etat", "1");
    }

}

