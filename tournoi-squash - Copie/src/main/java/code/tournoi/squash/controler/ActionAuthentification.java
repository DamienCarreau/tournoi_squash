package code.tournoi.squash.controler;

import metier.model.Users;
import metier.service.Service;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class ActionAuthentification extends Action {

    @Override
    public void execute(HttpServletRequest request) {
        String mail = request.getParameter("mail");
        String password = request.getParameter("password");
        Users user = null;

        Service service = new Service();

        if (mail != null && password != null) {
            user = service.authentifierUtilisateur(mail, password);
        }

        if (user != null) {
            service.majDate();
            HttpSession session = request.getSession(true);
            session.setAttribute("user", user);
        }else{
            //System.out.println("echec recherche du joueur");
        }
        
        request.setAttribute("user", user);
    }

}
