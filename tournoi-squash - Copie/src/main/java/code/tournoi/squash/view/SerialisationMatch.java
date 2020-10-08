package code.tournoi.squash.view;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import metier.model.Matchs;
import java.text.SimpleDateFormat;  
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SerialisationMatch extends Serialisation {

    @Override
    public void serialise(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JsonObject container = new JsonObject();

        JsonArray matchsJson = new JsonArray();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        Matchs m = (Matchs) request.getAttribute("match");
        
        container.addProperty("id", m.getId());
        String date = simpleDateFormat.format(m.getDateMatch());
        container.addProperty("date", date);
        container.addProperty("etat", m.getEtat());
        container.addProperty("scoreJ1", m.getScoreJ1());
        container.addProperty("scoreJ2", m.getScoreJ2());
        container.addProperty("idSport", m.getSport().getId());
        container.addProperty("idJoueur1", m.getJoueur1().getId());
        container.addProperty("idJoueur2", m.getJoueur2().getId());
        container.addProperty("nomJoueur1", m.getJoueur1().getNom());
        container.addProperty("prenomJoueur1", m.getJoueur1().getPrenom());
        container.addProperty("nomJoueur2", m.getJoueur2().getNom());
        container.addProperty("prenomJoueur2", m.getJoueur2().getPrenom());
        container.addProperty("emailJoueur1", m.getJoueur1().getEmail());
        container.addProperty("emailJoueur2", m.getJoueur2().getEmail());
        container.addProperty("telephoneJoueur1", m.getJoueur1().getTelephone());
        container.addProperty("telephoneJoueur2", m.getJoueur2().getTelephone());
        container.addProperty("classementJ1", (Long) request.getAttribute("classementJ1"));
        container.addProperty("classementJ2", (Long) request.getAttribute("classementJ2"));
        
        PrintWriter out = getWriter(response);
        Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
        gson.toJson(container, out);
        out.close();
    }

}