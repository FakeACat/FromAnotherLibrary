package mod.acats.fromanotherlibrary.platform;

import mod.acats.fromanotherlibrary.registry.CommonMod;
import mod.acats.fromanotherlibrary.registry.ItemRegistryFabric;
import net.fabricmc.loader.api.FabricLoader;

public class FabricSpecific implements ModLoaderSpecific {
    @Override
    public boolean isInDev() {
        return FabricLoader.getInstance().isDevelopmentEnvironment();
    }

    @Override
    public void registerAllCommonModContent(CommonMod mod) {

        if (mod.getItemRegister() != null) {
            ItemRegistryFabric.register(mod.getItemRegister());
        }
    }
}
