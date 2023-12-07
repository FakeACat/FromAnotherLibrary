package mod.acats.fromanotherlibrary.config.v2.properties;

import com.google.gson.JsonObject;
import mod.acats.fromanotherlibrary.config.v2.ConfigProperty;
import org.jetbrains.annotations.Nullable;

public class BooleanProperty extends ConfigProperty<Boolean> {
    public BooleanProperty(String name, @Nullable String description, boolean defaultValue, boolean requiresRestart) {
        super(name, description, defaultValue, requiresRestart);
    }

    @Override
    protected Boolean setValueFrom(JsonObject object) {
        return object.get("value").getAsBoolean();
    }

    @Override
    public void addTo(JsonObject object) {
        object.addProperty("value", this.get());
    }
}
