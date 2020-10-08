package code.tournoi.squash.controler;

import metier.model.Joueur;
import metier.model.Level;
import metier.service.Service;
import java.util.List;
import java.util.ArrayList;
import javafx.util.Pair;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class ActionAfficheJoueurs extends Action {

    @Override
    public void execute(HttpServletRequest request) {

        Service service = new Service();
        List<Pair<Joueur,List<Level>>> queue = new ArrayList<Pair<Joueur,List<Level>>>();

        List<Joueur> list = service.afficheJoueurs();
        for(Joueur j : list){
            List<Level> l = service.getLevels(j.getId());
            queue.add(new Pair(j,l));
        }
        
        request.setAttribute("liste", queue);
    }

}

