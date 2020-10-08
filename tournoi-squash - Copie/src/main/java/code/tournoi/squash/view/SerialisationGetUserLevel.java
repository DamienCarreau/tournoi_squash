package code.tournoi.squash.view;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import metier.model.Level;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SerialisationGetUserLevel extends Serialisation {

    @Override
    public void serialise(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JsonObject container = new JsonObject();

        JsonArray levelsJson = new JsonArray();

        List<Level> levels = (List<Level>) request.getAttribute("liste");
        
        for (Level level : levels) {
            JsonObject levelJson = new JsonObject();

            levelJson.addProperty("id", level.getId());
            levelJson.addProperty("niveau", level.getNiveau());
            levelJson.addProperty("classement", level.getClassement());
            levelJson.addProperty("sport", level.getSport().getNom());

            levelsJson.add(levelJson);
        }

        container.add("levels", levelsJson);

        
        PrintWriter out = getWriter(response);
        Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
        gson.toJson(container, out);
        out.close();
    }

}
