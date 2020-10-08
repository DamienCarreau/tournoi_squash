package code.tournoi.squash.view;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import metier.model.Admin;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SerialisationGetAllAdmin extends Serialisation {

    @Override
    public void serialise(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JsonObject container = new JsonObject();

        JsonArray adminsJson = new JsonArray();

        List<Admin> admins = (List<Admin>) request.getAttribute("admins");
        
        for (Admin admin : admins) {
            JsonObject adminJson = new JsonObject();

            adminJson.addProperty("id", admin.getId());
            adminJson.addProperty("nom", admin.getNom());
            adminJson.addProperty("prenom", admin.getPrenom());
            adminJson.addProperty("telephone", admin.getTelephone());
            adminJson.addProperty("email", admin.getEmail());

            adminsJson.add(adminJson);
        }

        container.add("admins", adminsJson);

        
        PrintWriter out = getWriter(response);
        Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
        gson.toJson(container, out);
        out.close();
    }

}
