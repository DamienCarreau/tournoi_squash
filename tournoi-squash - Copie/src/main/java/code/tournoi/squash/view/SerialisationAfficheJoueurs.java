package code.tournoi.squash.view;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import metier.model.Joueur;
import metier.model.Level;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javafx.util.Pair;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SerialisationAfficheJoueurs extends Serialisation {

    @Override
    public void serialise(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JsonObject container = new JsonObject();

        JsonArray joueursJson = new JsonArray();

        List<Pair<Joueur,List<Level>>> liste = (List<Pair<Joueur,List<Level>>>) request.getAttribute("liste");
        
        for (Pair<Joueur,List<Level>> pair : liste) {
            JsonArray jJson = new JsonArray();
            JsonObject joueurJson = new JsonObject();

            joueurJson.addProperty("id", pair.getKey().getId());
            joueurJson.addProperty("prenom", pair.getKey().getPrenom());
            joueurJson.addProperty("nom", pair.getKey().getNom());
            joueurJson.addProperty("email", pair.getKey().getEmail());
            joueurJson.addProperty("telephone", pair.getKey().getTelephone());
            joueurJson.addProperty("pause", pair.getKey().getPause());
            joueurJson.addProperty("autorisation", pair.getKey().getAutorisation());
            joueurJson.addProperty("cpt", pair.getValue().size());
            joueurJson.addProperty("sexe", pair.getKey().getSexe());

            jJson.add(joueurJson);

            for(Level l : pair.getValue()){
                JsonObject levelJson = new JsonObject();
                levelJson.addProperty("idLevel", l.getId());
                levelJson.addProperty("idSport", l.getSport().getId());
                levelJson.addProperty("sport", l.getSport().getNom());
                levelJson.addProperty("niveau", l.getNiveau());
                jJson.add(levelJson);
            }
            
            
            joueursJson.add(jJson);
        }

        container.add("joueurs", joueursJson);

        
        PrintWriter out = getWriter(response);
        Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
        gson.toJson(container, out);
        out.close();
    }

}
