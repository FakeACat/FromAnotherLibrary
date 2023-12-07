package mod.acats.fromanotherlibrary.config.v2.properties;

import com.google.gson.JsonObject;
import mod.acats.fromanotherlibrary.config.v2.ConfigProperty;
import org.jetbrains.annotations.Nullable;

public class FloatProperty extends ConfigProperty<Float> {
    public FloatProperty(String name, @Nullable String description, float defaultValue, boolean requiresRestart) {
        super(name, description, defaultValue, requiresRestart);
    }

    @Override
    protected Float setValueFrom(JsonObject object) {
        return object.get("value").getAsFloat();
    }

    @Override
    public void addTo(JsonObject object) {
        object.addProperty("value", this.get());
    }
}
