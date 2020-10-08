package code.tournoi.squash.controler;

import metier.model.Sport;
import metier.service.Service;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

public class ActionAfficheSports extends Action {

    @Override
    public void execute(HttpServletRequest request) {
        Service service = new Service();
        List<Sport> sports = service.afficheSports();
        
        request.setAttribute("sports", sports);
    }

}
