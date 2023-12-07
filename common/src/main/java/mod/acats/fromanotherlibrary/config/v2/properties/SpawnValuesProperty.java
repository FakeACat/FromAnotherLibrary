package mod.acats.fromanotherlibrary.config.v2.properties;

import com.google.gson.JsonObject;
import mod.acats.fromanotherlibrary.config.v2.ConfigProperty;
import mod.acats.fromanotherlibrary.config.v2.SpawnValues;
import org.jetbrains.annotations.Nullable;

public class SpawnValuesProperty extends ConfigProperty<SpawnValues> {
    public SpawnValuesProperty(String name, @Nullable String description, SpawnValues defaultValue) {
        super(name, description, defaultValue, true);
    }

    @Override
    protected SpawnValues setValueFrom(JsonObject object) {
        return new SpawnValues(
                object.get("enabled").getAsBoolean(),
                object.get("spawn_weight").getAsInt(),
                object.get("minimum_group_size").getAsInt(),
                object.get("maximum_group_size").getAsInt()
        );
    }

    @Override
    public void addTo(JsonObject object) {
        object.addProperty("enabled", this.get().enabled());
        object.addProperty("spawn_weight", this.get().weight());
        object.addProperty("minimum_group_size", this.get().groupMin());
        object.addProperty("maximum_group_size", this.get().groupMax());
    }
}
