package code.tournoi.squash.view;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import metier.model.Sport;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SerialisationAfficheSports extends Serialisation {

    @Override
    public void serialise(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JsonObject container = new JsonObject();

        JsonArray sportsJson = new JsonArray();

        List<Sport> sports = (List<Sport>) request.getAttribute("sports");
        
        for (Sport sport : sports) {
            JsonObject sportJson = new JsonObject();

            sportJson.addProperty("id", sport.getId());
            sportJson.addProperty("nom", sport.getNom());

            sportsJson.add(sportJson);
        }

        container.add("sports", sportsJson);

        
        PrintWriter out = getWriter(response);
        Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
        gson.toJson(container, out);
        out.close();
    }

}
