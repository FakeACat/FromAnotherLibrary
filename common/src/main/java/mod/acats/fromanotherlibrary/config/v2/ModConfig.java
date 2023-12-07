package mod.acats.fromanotherlibrary.config.v2;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import mod.acats.fromanotherlibrary.FromAnotherLibrary;
import mod.acats.fromanotherlibrary.config.v2.properties.BooleanProperty;
import mod.acats.fromanotherlibrary.config.v2.properties.IntegerProperty;

import java.io.*;
import java.util.Arrays;

public abstract class ModConfig {
    private static final int GLOBAL_VERSION = 1;
    public abstract String name();
    protected abstract int version();
    private ConfigProperty<?>[] properties = new ConfigProperty<?>[]{};

    protected <V, T extends ConfigProperty<V>> T addProperty(T property) {
        properties = Arrays.copyOf(properties, properties.length + 1);
        properties[properties.length - 1] = property;
        return property;
    }

    public ConfigProperty<?>[] getProperties() {
        return properties;
    }

    private String quotedName(String folderName) {
        return '"' + folderName + '/' + this.name() + '"';
    }

    private int actualVersion() {
        return this.version() + GLOBAL_VERSION;
    }

    private final BooleanProperty autoRegenOutdated = new BooleanProperty(
            "auto_regen_outdated",
            "Should this file automatically regenerate whenever the default options are changed?",
            true,
            false);
    private final IntegerProperty version = new IntegerProperty(
            "version",
            "Config version number used for " + autoRegenOutdated.getName() + ".\nDo not modify.",
            this.actualVersion(),
            false);

    private File parentFile;

    public void load(File parent){
        this.parentFile = parent;
        JsonObject cfgJson = null;
        if (this.getFile().exists()) {
            try {
                Reader reader = new FileReader(getFile());
                cfgJson = new Gson().fromJson(reader, JsonObject.class);
            }
            catch (FileNotFoundException e) {
                FromAnotherLibrary.LOGGER.error("FileNotFoundException while trying to read config file " + this.quotedName(parent.getName()) + ": " + e.getMessage());
            }
            catch (JsonSyntaxException e) {
                FromAnotherLibrary.LOGGER.error("Invalid syntax in config file " + this.quotedName(parent.getName()) + ": " + e.getMessage());
            }

            this.autoRegenOutdated.set(cfgJson, quotedName(parent.getName()));
            if (this.autoRegenOutdated.get()) {
                this.version.set(cfgJson, quotedName(parent.getName()));
                if (this.version.get() < this.actualVersion() || !this.version.isLoaded()) {
                    FromAnotherLibrary.LOGGER.info("Config file " + this.quotedName(parent.getName()) + " is outdated or invalid. Regenerating.");
                    this.version.set(this.actualVersion());
                    this.genFile(false);
                }
            }
        }
        else {
            FromAnotherLibrary.LOGGER.info("Config file " + this.quotedName(parent.getName()) + " does not exist. Generating.");
            this.genFile(true);
        }

        this.setValues(cfgJson, parent.getName());
    }

    private void addProperties(JsonObject object) {
        addProperty(object, version);
        addProperty(object, autoRegenOutdated);

        for (ConfigProperty<?> property:
                properties) {
            addProperty(object, property);
        }
    }

    private static void addProperty(JsonObject object, ConfigProperty<?> property) {
        JsonObject obj = new JsonObject();
        if (property.description != null) {
            String[] descriptionLines = property.description.split("\n");
            for (int i = 0; i < descriptionLines.length; i++) {
                String numer = descriptionLines.length > 1 ? String.valueOf(i + 1) : "";
                obj.addProperty("description" + numer, descriptionLines[i]);
            }
        }
        property.addTo(obj);
        object.add(property.getName(), obj);
    }

    private void setValues(JsonObject cfg, String folderName) {
        for (ConfigProperty<?> property:
                properties) {
            property.set(cfg, quotedName(folderName));
        }
    }

    public File getFile(){
        return new File(parentFile, this.name() + ".json");
    }

    public void genFile(boolean create) {
        JsonObject cfg = new JsonObject();
        this.addProperties(cfg);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        if (create){
            try {
                if (!getFile().createNewFile()) {
                    FromAnotherLibrary.LOGGER.error("Unable to create config file " + this.quotedName(parentFile.getName()));
                }
            } catch (IOException e) {
                FromAnotherLibrary.LOGGER.error("IOException while attempting to generate config file " + this.quotedName(parentFile.getName()) + ": " + e.getMessage());
            }
        }
        try {
            FileWriter fileWriter = new FileWriter(getFile());
            fileWriter.write(gson.toJson(cfg));
            fileWriter.close();
        } catch (IOException e) {
            FromAnotherLibrary.LOGGER.error("Failed writing to config file " + this.quotedName(parentFile.getName()) + ": " + e.getMessage());
        }
    }
}
