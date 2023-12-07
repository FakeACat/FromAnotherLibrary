package mod.acats.fromanotherlibrary.config.v2;

import com.google.gson.JsonObject;
import mod.acats.fromanotherlibrary.FromAnotherLibrary;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.function.BiConsumer;

public abstract class ConfigProperty<T> {
    private final String name;
    public final @Nullable String description;
    public final T defaultValue;
    private T value;
    public T updatedValue;
    private boolean loaded;
    public final boolean requiresRestart;
    private @Nullable BiConsumer<T, T> onChangeValue;

    public ConfigProperty(String name, @Nullable String description, T defaultValue, boolean requiresRestart) {
        this.name = name;
        this.description = description;
        this.defaultValue = defaultValue;
        this.value = defaultValue;
        this.loaded = false;
        this.requiresRestart = requiresRestart;
        this.updatedValue = null;
    }

    public T get(){
        return this.value;
    }

    @ApiStatus.Internal
    public void set(@Nullable JsonObject cfg, String quotedName) {
        if (cfg == null) {
            return;
        }

        if (cfg.get(this.name) instanceof JsonObject jsonObject) {
            try {
                this.value = this.setValueFrom(jsonObject);
            }
            catch (NullPointerException e) {
                FromAnotherLibrary.LOGGER.warn("Missing value for " + '"' + this.getName() + '"' + " in config file " + quotedName + ". Using default value. It is recommended to delete this config file to regenerate the option.");
            }
            this.loaded = true;
        }
        else {
            FromAnotherLibrary.LOGGER.warn("Missing " + '"' + this.getName() + '"' + " in config file " + quotedName + ". Using default value. It is recommended to delete this config file to regenerate the option.");
        }
    }

    @ApiStatus.Internal
    public void set(T value){
        this.value = value;
    }

    public String getName(){
        return this.name;
    }

    protected abstract T setValueFrom(JsonObject object);

    public abstract void addTo(JsonObject object);

    public boolean isLoaded() {
        return this.loaded;
    }

    public boolean changedButUnsaved() {
        return updatedValue != null && !valueEquals(updatedValue);
    }

    @SuppressWarnings("unchecked")
    public <V extends ConfigProperty<T>> V addOnChangeBehaviour(BiConsumer<T, T> consumer) {
        this.onChangeValue = consumer;
        return (V) this;
    }

    public T menuValue() {
        return updatedValue == null ? get() : updatedValue;
    }

    public boolean usingDefaultValue() {
        return valueEquals(defaultValue);
    }

    public void changeValueInGame(T newValue) {
        if (onChangeValue != null) {
            onChangeValue.accept(value, newValue);
        }
        value = newValue;
    }

    public boolean valueEquals(T other) {
        return Objects.equals(value, other);
    }
}
