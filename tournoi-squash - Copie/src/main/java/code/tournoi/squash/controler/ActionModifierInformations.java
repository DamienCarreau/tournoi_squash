package code.tournoi.squash.controler;

import metier.model.Joueur;
import metier.model.Users;
import metier.service.Service;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class ActionModifierInformations extends Action {

    @Override
    public void execute(HttpServletRequest request) {
        Long id = Long.parseLong(request.getParameter("id"), 10);
        String name = request.getParameter("nom");
        String firstname = request.getParameter("prenom");
        String date = request.getParameter("date");
        String numeroDeTelephone = request.getParameter("numeroDeTelephone");
        String email = request.getParameter("email");
        String mdp = request.getParameter("motDePasse");
            
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
        boolean b = service.modifierInformations(id,name,firstname,d,numeroDeTelephone,email,mdp);
        if(!b){
            request.setAttribute("etat", "0");
            return;
        }else
            request.setAttribute("etat", "1");

        HttpSession session = request.getSession(true);
        Users user = (Users) service.getUserInformations(id);
        session.setAttribute("user", user);
    }

}
