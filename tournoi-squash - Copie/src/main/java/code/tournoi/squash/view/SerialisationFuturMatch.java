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

public class SerialisationFuturMatch extends Serialisation {

    @Override
    public void serialise(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JsonObject container = new JsonObject();

        JsonArray matchsJson = new JsonArray();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        List<Matchs> matchs = (List<Matchs>) request.getAttribute("matchs");
        Long idUser = (Long) request.getAttribute("idUser");
        
        for (Matchs match : matchs) {
            JsonObject matchJson = new JsonObject();

            if(match.getJoueur1().getId() == idUser){
                matchJson.addProperty("nom", match.getJoueur2().getNom());
                matchJson.addProperty("prenom", match.getJoueur2().getPrenom());
            }else{
                matchJson.addProperty("nom", match.getJoueur1().getNom());
                matchJson.addProperty("prenom", match.getJoueur1().getPrenom());
            }
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
