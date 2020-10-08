package code.tournoi.squash.controler;

import metier.model.Users;
import metier.model.Joueur;
import metier.service.Service;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class ActionChangeMdp extends Action {

    @Override
    public void execute(HttpServletRequest request) {
        String date = request.getParameter("date");
        String numeroDeTelephone = request.getParameter("numeroDeTelephone");
        String email = request.getParameter("email");
        String mdp = request.getParameter("password");
        String cmdp = request.getParameter("cpassword");

        if(!mdp.equals(cmdp)){
            request.setAttribute("etat", "0");
            return;
        }
            
        Date d = new Date();
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            d = simpleDateFormat.parse(date);
        } catch (Exception e) {
            System.err.println(e);
        }
        
        Service service = new Service();

        boolean b = service.modificationMDP(d,numeroDeTelephone,email,mdp);
        if(!b)
            request.setAttribute("etat", "0");
        else
            request.setAttribute("etat", "1");
    }

}

