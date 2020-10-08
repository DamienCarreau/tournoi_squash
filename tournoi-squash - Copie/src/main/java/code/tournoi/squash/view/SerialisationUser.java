package code.tournoi.squash.view;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import metier.model.Users;
import metier.model.Joueur;
import metier.model.Admin;
import java.text.SimpleDateFormat;  
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SerialisationUser extends Serialisation {

    @Override
    public void serialise(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JsonObject container = new JsonObject();

        Users user = (Users) request.getAttribute("user");
        if (user == null) {
            //System.out.println("error");
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }else if(user.getNom().equals("")){
            container.addProperty("etat","0");
        }else if (user instanceof Joueur) {
            Joueur joueur = (Joueur) user;
            container.addProperty("type", "joueur");
            container.addProperty("id", joueur.getId());
            container.addProperty("nom", joueur.getNom());
            container.addProperty("prenom", joueur.getPrenom());
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String date = simpleDateFormat.format(joueur.getDateDeNaissance());
            container.addProperty("date", date);
            container.addProperty("email", joueur.getEmail());
            container.addProperty("telephone", joueur.getTelephone());
            container.addProperty("pause", joueur.getPause());
            container.addProperty("autorisation", joueur.getAutorisation());
            container.addProperty("sexe", joueur.getSexe());
        } else if (user instanceof Admin) {
            Admin admin = (Admin) user;
            container.addProperty("type", "admin");
            container.addProperty("id", admin.getId());
            container.addProperty("nom", admin.getNom());
            container.addProperty("prenom", admin.getPrenom());
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String date = simpleDateFormat.format(admin.getDateDeNaissance());
            container.addProperty("date", date);
            container.addProperty("email", admin.getEmail());
            container.addProperty("telephone", admin.getTelephone());
        }
        
        PrintWriter out = getWriter(response);
        Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
        gson.toJson(container, out);
        out.close();
    }

}
