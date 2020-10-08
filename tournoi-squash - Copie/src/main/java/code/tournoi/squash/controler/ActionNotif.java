package code.tournoi.squash.controler;

import metier.service.Service;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.lang.StringBuffer;
import java.io.BufferedReader;

public class ActionNotif extends Action {

    @Override
    public void execute(HttpServletRequest request) {
        StringBuffer jb = new StringBuffer();
        String line = null;
        try {
         BufferedReader reader = request.getReader();
         while ((line = reader.readLine()) != null)
          jb.append(line);
        } catch (Exception e) { /*report an error*/ }

        /*try {
         JsonObject jsonObject = JsonObject.fromObject(jb.toString());
        } catch (ParseException e) {
         // crash and burn
         throw new IOException("Error parsing JSON request string");
        }*/
    }

}


