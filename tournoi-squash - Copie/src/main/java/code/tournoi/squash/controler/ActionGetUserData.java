package code.tournoi.squash.controler;

import metier.model.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ActionGetUserData extends Action {

    @Override
    public void execute(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) { //pas de session créée au préalable
            return;
        }
        Users user = (Users) session.getAttribute("user");
        if (user == null) {
            return;
        }

        request.setAttribute("user", user);
    }

}
