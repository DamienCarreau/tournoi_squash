package code.tournoi.squash.controler;

import javax.servlet.http.HttpServletRequest;

public abstract class Action {
    
    public abstract void execute(HttpServletRequest request);
}
