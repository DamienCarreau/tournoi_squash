package code.tournoi.squash.controler;

import metier.model.Joueur;
import metier.service.Service;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class ActionGetUserInfo extends Action {

    @Override
    public void execute(HttpServletRequest request) {
        Long idUser = Long.parseLong(request.getParameter("idUser"), 10);

        Service service = new Service();

        Joueur joueur = service.getJoueur(idUser);
        if(joueur != null)
            request.setAttribute("user", joueur);
    }

}

