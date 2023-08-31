package mod.acats.fromanotherlibrary.registry;

import mod.acats.fromanotherlibrary.platform.ModLoaderSpecific;

import java.util.HashMap;
import java.util.function.BooleanSupplier;

public class DataPackLoader {
    final HashMap<String, BooleanSupplier> dataPacks = new HashMap<>();

    /**
     * Adds a data pack that is only loaded when the criteria are met upon loading the game.
     * @param name The name of the folder that is the data pack. Looks in main/resources/resourcepacks.
     *             Automatically prefixed by your mod's ID to prevent mods adding identical names.
     * @param criteria The criteria for the data pack to be enabled
     */
    public void addDataPack(String name, BooleanSupplier criteria) {
        dataPacks.put(name, criteria);
    }

    /**
     * Adds a data pack that is only loaded when a mod with the given ID is loaded
     * @param id The ID of the other mod. The data pack must have the file name "id1_compat_id2" where id1 is your mod's ID and id2 is the other mod's ID. Looks in main/resources/resourcepacks
     */
    public void addModCompat(String id) {
        this.addDataPack("compat_" + id, () -> ModLoaderSpecific.INSTANCE.isModLoaded(id));
    }
}
