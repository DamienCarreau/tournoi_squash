package code.tournoi.squash.controler;

import metier.model.Matchs;
import metier.service.Service;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class ActionNouvelleDate extends Action {

    @Override
    public void execute(HttpServletRequest request) {
        String date = request.getParameter("date");
        Long idMatch = Long.parseLong(request.getParameter("idMatch"), 10);
            
        Date d = new Date();
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            d = simpleDateFormat.parse(date);
        } catch (Exception e) {
            System.err.println(e);
        }
        
        Service service = new Service();

        boolean b = service.nouvelleDate(idMatch,d);
        if(!b)
            request.setAttribute("etat", "0");
        else
            request.setAttribute("etat", "1");
    }

}

