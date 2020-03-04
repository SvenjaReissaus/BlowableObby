package me.svreissaus.blobby.storage.adapters;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import org.bukkit.Bukkit;
import org.bukkit.Location;

public class LocationTypeAdapter implements JsonSerializer<Location>, JsonDeserializer<Location> {
    public JsonElement serialize(Location location, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject object = new JsonObject();
        try {
            object.add("x", (JsonElement)new JsonPrimitive(location.getX()));
            object.add("y", (JsonElement)new JsonPrimitive(location.getY()));
            object.add("z", (JsonElement)new JsonPrimitive(location.getZ()));
            object.add("world", (JsonElement)new JsonPrimitive(location.getWorld().getName()));
            return (JsonElement)object;
        } catch (Exception ex) {
            ex.printStackTrace();
            return (JsonElement)object;
        }
    }

    public Location deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) {
        JsonObject object = jsonElement.getAsJsonObject();
        try {
            return new Location(Bukkit.getWorld(object.get("world").getAsString()), object
                    .get("x").getAsDouble(), object
                    .get("y").getAsDouble(), object
                    .get("z").getAsDouble());
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}