package code.tournoi.squash.view;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import metier.model.Matchs;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SerialisationListeMatch extends Serialisation {

    @Override
    public void serialise(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JsonObject container = new JsonObject();

        JsonArray matchsJson = new JsonArray();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        List<Matchs> matchs = (List<Matchs>) request.getAttribute("matchs");
        
        for (Matchs match : matchs) {
            JsonObject matchJson = new JsonObject();

            
            matchJson.addProperty("nomJoueur2", match.getJoueur2().getNom());
            matchJson.addProperty("prenomJoueur2", match.getJoueur2().getPrenom());
            matchJson.addProperty("nomJoueur1", match.getJoueur1().getNom());
            matchJson.addProperty("prenomJoueur1", match.getJoueur1().getPrenom());
            matchJson.addProperty("scoreJ1", match.getScoreJ1());
            matchJson.addProperty("scoreJ2", match.getScoreJ2());
            matchJson.addProperty("id", match.getId());
            matchJson.addProperty("date", simpleDateFormat.format(match.getDateMatch()));

            matchsJson.add(matchJson);
        }

        container.add("matchs", matchsJson);
        
        PrintWriter out = getWriter(response);
        Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
        gson.toJson(container, out);
        out.close();
    }

}
