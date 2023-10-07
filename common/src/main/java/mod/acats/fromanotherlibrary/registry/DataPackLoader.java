package mod.acats.fromanotherlibrary.registry;

import mod.acats.fromanotherlibrary.config.FALConfig;
import mod.acats.fromanotherlibrary.platform.ModLoaderSpecific;

import java.util.ArrayList;
import java.util.List;

public class DataPackLoader {
    final List<String> dataPacks = new ArrayList<>();

    /**
     * Adds a data pack to be automatically loaded on both Forge and Fabric
     * @param name The name of the folder that is the data pack. Looks in main/resources/resourcepacks.
     *             Automatically prefixed by your mod's ID to prevent mods adding identical names.
     */
    public void addDataPack(String name) {
        dataPacks.add(name);
    }

    /**
     * Adds a data pack that is only loaded when a mod with the given ID is loaded
     * @param id The ID of the other mod. The data pack must have the file name "id1_compat_id2" where id1 is your mod's ID and id2 is the other mod's ID. Looks in main/resources/resourcepacks
     */
    public void addModCompat(String id) {
        if (ModLoaderSpecific.INSTANCE.isModLoaded(id)) {
            this.addDataPack("compat_" + id);
        }
    }

    /**
     * Adds a data pack that is only loaded when enabled in a config
     * @param configOption The config option that controls whether this data pack is enabled.
     */
    public void addOptional(FALConfig.FALConfigBooleanProperty configOption) {
        if (configOption.get()) {
            this.addDataPack(configOption.getName());
        }
    }
}
