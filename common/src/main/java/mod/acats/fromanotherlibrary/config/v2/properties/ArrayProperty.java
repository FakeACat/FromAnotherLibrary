package mod.acats.fromanotherlibrary.config.v2.properties;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import mod.acats.fromanotherlibrary.config.v2.ConfigProperty;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class ArrayProperty extends ConfigProperty<String[]> {
    public ArrayProperty(String name, @Nullable String description, String[] defaultValue, boolean requiresRestart) {
        super(name, description, defaultValue, requiresRestart);
    }

    private final ArrayList<String> mods = new ArrayList<>();

    private static JsonArray toJsonArray(String[] array){
        JsonArray jsonArray = new JsonArray();
        for (String string:
                array) {
            jsonArray.add(string);
        }
        return jsonArray;
    }

    @Override
    protected String[] setValueFrom(JsonObject object) {
        JsonArray jsonArray = object.get("value").getAsJsonArray();
        ArrayList<String> strings = new ArrayList<>();
        for (JsonElement e:
                jsonArray) {
            String s = e.getAsString();
            if (s.endsWith(":*")){
                mods.add(s.split(":")[0]);
            }
            else{
                strings.add(s);
            }
        }
        return strings.toArray(new String[0]);
    }

    @Override
    public void addTo(JsonObject object) {
        object.add("value", toJsonArray(this.get()));
    }

    public boolean contains(String s){
        return Arrays.asList(this.get()).contains(s) || this.mods.contains(s.split(":")[0]);
    }

    @Override
    public boolean valueEquals(String[] other) {
        if (other.length != get().length) {
            return false;
        }
        for (int i = 0; i < get().length; i++) {
            if (!Objects.equals(get()[i], other[i])) {
                return false;
            }
        }
        return true;
    }
}
