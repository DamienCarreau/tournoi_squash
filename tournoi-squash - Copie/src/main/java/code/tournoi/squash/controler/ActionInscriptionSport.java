package code.tournoi.squash.controler;

import metier.service.Service;
import metier.model.Level;
import metier.model.Sport;
import metier.model.Joueur;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class ActionInscriptionSport extends Action {

    @Override
    public void execute(HttpServletRequest request) {
        Long idUser = Long.parseLong(request.getParameter("idUser"), 10);
        Long idSport = Long.parseLong(request.getParameter("idSport"), 10);
        String niveau = request.getParameter("niveau");

        Service service = new Service();

        Sport sport = service.getSport(idSport);
        Joueur joueur = service.getJoueur(idUser);
        boolean b = service.inscriptionSport(new Level(joueur,sport,niveau));
        if(!b){
            request.setAttribute("etat", "0");
        }else
            request.setAttribute("etat", "1");
    }

}


