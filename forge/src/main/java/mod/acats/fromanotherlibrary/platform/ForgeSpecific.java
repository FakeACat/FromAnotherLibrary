package mod.acats.fromanotherlibrary.platform;

import mod.acats.fromanotherlibrary.registry.CommonMod;
import mod.acats.fromanotherlibrary.registry.ItemRegistryForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLLoader;

public class ForgeSpecific implements ModLoaderSpecific {
    @Override
    public boolean isInDev() {
        return FMLLoader.isProduction();
    }

    @Override
    public void registerAllCommonModContent(CommonMod mod) {

        final IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

        if (mod.getItemRegister() != null) {
            ItemRegistryForge.register(mod.getItemRegister(), eventBus);
        }
    }
}
