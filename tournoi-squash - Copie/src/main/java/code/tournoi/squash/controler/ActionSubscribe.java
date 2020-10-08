package code.tournoi.squash.controler;

import metier.model.Joueur;
import metier.service.Service;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class ActionSubscribe extends Action {

    @Override
    public void execute(HttpServletRequest request) {
        String name = request.getParameter("nom");
        String firstname = request.getParameter("prenom");
        String date = request.getParameter("date");
        String numeroDeTelephone = request.getParameter("numeroDeTelephone");
        String email = request.getParameter("email");
        String cemail = request.getParameter("cemail");
        String mdp = request.getParameter("password");
        String cmdp = request.getParameter("cpassword");
        char sexe = request.getParameter("sexe").charAt(0);

        if(!mdp.equals(cmdp) || !email.equals(cemail)){
            request.setAttribute("user", new Joueur("","","","",new Date("01/01/2020"),"",'H'));
            return;
        }
            
        Date d = new Date();
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            d = simpleDateFormat.parse(date);
        } catch (Exception e) {
            System.err.println(e);
            request.setAttribute("user", new Joueur("","","","",new Date("01/01/2020"),"",'H'));
            return;
        }
        
        Service service = new Service();

        Joueur joueur = new Joueur(email, name, firstname, numeroDeTelephone, d, mdp,sexe);
        Long id = service.inscrireUtilisateur(joueur);
        if (id == null) {
            return;
        } else {
            HttpSession session = request.getSession(true);
            session.setAttribute("user", joueur);
            request.setAttribute("user", joueur);
        }
    }

}
