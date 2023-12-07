package mod.acats.fromanotherlibrary.config.v2.properties;

import com.google.gson.JsonObject;
import mod.acats.fromanotherlibrary.config.v2.ConfigProperty;
import org.jetbrains.annotations.Nullable;

public class IntegerProperty extends ConfigProperty<Integer> {
    public IntegerProperty(String name, @Nullable String description, int defaultValue, boolean requiresRestart) {
        super(name, description, defaultValue, requiresRestart);
    }

    @Override
    protected Integer setValueFrom(JsonObject object) {
        return object.get("value").getAsInt();
    }

    @Override
    public void addTo(JsonObject object) {
        object.addProperty("value", this.get());
    }
}
