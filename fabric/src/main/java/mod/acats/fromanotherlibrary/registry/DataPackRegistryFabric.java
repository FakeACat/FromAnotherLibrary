package mod.acats.fromanotherlibrary.registry;

import mod.acats.fromanotherlibrary.FromAnotherLibrary;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.util.function.BooleanSupplier;

public class DataPackRegistryFabric {
    public static void register(CommonMod mod) {
        mod.getDataPacks().ifPresent(dataPackLoader -> dataPackLoader.dataPacks.forEach((id, criteria) -> register(id, criteria, mod)));
    }

    private static void register(String id, BooleanSupplier criteria, CommonMod mod) {
        if (criteria.getAsBoolean()){
            String realName = mod.getID() + '_' + id;
            FromAnotherLibrary.LOGGER.info("Attempting to load data pack " + '"' + realName + '"');
            FabricLoader.getInstance().getModContainer(mod.getID()).ifPresent(modContainer -> ResourceManagerHelper.registerBuiltinResourcePack(new ResourceLocation(mod.getID(), realName),
                    modContainer, Component.literal(realName), ResourcePackActivationType.DEFAULT_ENABLED));
        }
    }
}
