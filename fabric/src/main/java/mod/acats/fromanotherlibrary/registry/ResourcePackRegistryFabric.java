package mod.acats.fromanotherlibrary.registry;

import mod.acats.fromanotherlibrary.FromAnotherLibrary;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class ResourcePackRegistryFabric {
    public static void register(CommonMod mod) {
        mod.getResourcePacks().ifPresent(dataPackLoader -> dataPackLoader.resourcePacks.forEach(pack -> register(pack, mod)));
    }

    private static void register(ResourcePackLoader.FALResourcePack pack, CommonMod mod) {
        String realName = mod.getID() + '_' + pack.id();
        FromAnotherLibrary.LOGGER.info("Attempting to load resource pack " + '"' + realName + '"');
        FabricLoader.getInstance().getModContainer(mod.getID()).ifPresent(modContainer -> ResourceManagerHelper.registerBuiltinResourcePack(new ResourceLocation(mod.getID(), realName),
                modContainer, Component.translatable("falresourcepack." + mod.getID() + "." + pack.id()), pack.enabledByDefault() ? ResourcePackActivationType.DEFAULT_ENABLED : ResourcePackActivationType.NORMAL));
    }
}
