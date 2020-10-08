package code.tournoi.squash.controler;

import metier.model.Matchs;
import metier.service.Service;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ActionCreerMatch extends Action {

    @Override
    public void execute(HttpServletRequest request) {
        Long idSport = Long.parseLong(request.getParameter("idSport"), 10);
        Long idJ1 = Long.parseLong(request.getParameter("idJ1"), 10);
        Long idJ2 = Long.parseLong(request.getParameter("idJ2"), 10);
        String date = request.getParameter("date");

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

        Matchs m = new Matchs(service.getSport(idSport),service.getJoueur(idJ1),service.getJoueur(idJ2),d);
        Long id = service.creerMatch(m);
        request.setAttribute("match", m);
    }

}

