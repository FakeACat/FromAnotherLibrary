package mod.acats.fromanotherlibrary.registry;

import mod.acats.fromanotherlibrary.config.FALConfig;
import mod.acats.fromanotherlibrary.config.v2.properties.BooleanProperty;
import mod.acats.fromanotherlibrary.platform.ModLoaderSpecific;

import java.util.ArrayList;
import java.util.List;

public class ResourcePackLoader {
    final List<FALResourcePack> resourcePacks = new ArrayList<>();

    /**
     * Adds a data pack to be automatically loaded on both Forge and Fabric
     * @param name The name of the folder that is the data pack. Looks in main/resources/resourcepacks.
     *             Automatically prefixed by your mod's ID to prevent mods adding identical names.
     * @param enabled Whether the data pack should be enabled by default
     */
    public void addDataPack(String name, boolean enabled) {
        resourcePacks.add(new FALResourcePack(name, enabled, true));
    }

    /**
     * Adds a resource pack to be automatically loaded on both Forge and Fabric
     * @param name The name of the folder that is the resource pack. Looks in main/resources/resourcepacks.
     *             Automatically prefixed by your mod's ID to prevent mods adding identical names.
     * @param enabled Whether the resource pack should be enabled by default
     */
    public void addResourcePack(String name, boolean enabled) {
        resourcePacks.add(new FALResourcePack(name, enabled, false));
    }

    /**
     * Adds a data pack that is only loaded when a mod with the given ID is loaded
     * @param id The ID of the other mod. The data pack must have the file name "id1_compat_id2" where id1 is your mod's ID and id2 is the other mod's ID. Looks in main/resources/resourcepacks
     */
    public void addModCompat(String id) {
        if (ModLoaderSpecific.INSTANCE.isModLoaded(id)) {
            this.addDataPack("compat_" + id, true);
        }
    }

    /**
     * Adds a data pack that is only loaded when enabled in a config
     * @param configOption The config option that controls whether this data pack is enabled.
     */
    public void addOptional(FALConfig.FALConfigBooleanProperty configOption) {
        if (configOption.get()) {
            this.addDataPack(configOption.getName(), true);
        }
    }

    /**
     * Adds a data pack that is only loaded when enabled in a config
     * @param configOption The config option that controls whether this data pack is enabled.
     */
    public void addOptional(BooleanProperty configOption) {
        if (configOption.get()) {
            this.addDataPack(configOption.getName(), true);
        }
    }

    public record FALResourcePack(String id, boolean enabledByDefault, boolean data) {
    }
}
