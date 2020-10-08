package code.tournoi.squash.controler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class ActionDisconnect extends Action {

    @Override
    public void execute(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        request.setAttribute("etat", "1");
    }

}