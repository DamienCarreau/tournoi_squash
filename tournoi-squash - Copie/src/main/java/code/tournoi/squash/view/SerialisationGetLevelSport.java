package code.tournoi.squash.view;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SerialisationGetLevelSport extends Serialisation {

    @Override
    public void serialise(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JsonObject container = new JsonObject();

        JsonArray levelsJson = new JsonArray();

        List<String> levels = (List<String>) request.getAttribute("levels");
        
        for (String level : levels) {
            JsonObject levelJson = new JsonObject();

            levelJson.addProperty("level", level);

            levelsJson.add(levelJson);
        }

        container.add("levels", levelsJson);
        container.addProperty("sportName", (String)request.getAttribute("sportName"));

        
        PrintWriter out = getWriter(response);
        Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
        gson.toJson(container, out);
        out.close();
    }

}
