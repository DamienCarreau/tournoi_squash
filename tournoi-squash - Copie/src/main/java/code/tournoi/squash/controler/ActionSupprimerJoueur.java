package code.tournoi.squash.controler;

import metier.model.Joueur;
import metier.service.Service;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class ActionSupprimerJoueur extends Action {

    @Override
    public void execute(HttpServletRequest request) {
        Long id = Long.parseLong(request.getParameter("id"), 10) ;
        String date = request.getParameter("date");
        String numeroDeTelephone = request.getParameter("numeroDeTelephone");
        String email = request.getParameter("email");
        String mdp = request.getParameter("password");

        Date d = new Date();
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            d = simpleDateFormat.parse(date);
        } catch (Exception e) {
            System.err.println(e);
            request.setAttribute("etat", "0");
            return;
        }

        Service service = new Service();

        boolean b = service.supprimerCompte(id,d,numeroDeTelephone,email,mdp);
        if(!b)
            request.setAttribute("etat", "0");
        else
            request.setAttribute("etat", "1");
    }

}

