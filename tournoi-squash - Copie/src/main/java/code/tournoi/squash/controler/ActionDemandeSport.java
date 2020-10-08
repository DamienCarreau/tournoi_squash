package code.tournoi.squash.controler;

import metier.model.Joueur;
import metier.service.Service;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class ActionDemandeSport extends Action {

    @Override
    public void execute(HttpServletRequest request) {
        Service service = new Service();
        Long idUser = Long.parseLong(request.getParameter("idUser"), 10);
        Long idSport = Long.parseLong(request.getParameter("idSport"), 10);
        boolean b = service.consulterSport(idUser,idSport);
        if(!b){
            request.setAttribute("etat", "0");
        }else{
            request.setAttribute("etat", "1");
        }

    }

}
