package site.nonestep.idontwantwalk.road.sk;

import com.nimbusds.jose.shaded.gson.*;
import com.nimbusds.jose.shaded.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class GeometryDeserializer implements JsonDeserializer<Geometry> {
    @Override
    public Geometry deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        Geometry geometry = new Geometry();
        geometry.type = jsonObject.get("type").getAsString();

        JsonArray coordinatesArray = jsonObject.get("coordinates").getAsJsonArray();
        Type listType = new TypeToken<List<Object>>() {}.getType();
        geometry.coordinates = new Gson().fromJson(coordinatesArray, listType);

        return geometry;
    }
}
