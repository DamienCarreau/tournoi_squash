package code.tournoi.squash.controler;

import metier.service.Service;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class ActionModifieNiveau extends Action {

    @Override
    public void execute(HttpServletRequest request) {
        Long idLevel = Long.parseLong(request.getParameter("idLevel"), 10);
        String niveau = request.getParameter("niveau");

        Service service = new Service();

        boolean b = service.modifieNiveau(idLevel,niveau);
        if(!b){
            request.setAttribute("etat", "0");
        }else
            request.setAttribute("etat", "1");
    }

}


