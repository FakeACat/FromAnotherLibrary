package mod.acats.fromanotherlibrary.registry;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;

public class ItemRegistryFabric {
    public static void register(CommonMod mod) {
        if (mod.getItemRegister() != null) {
            mod.getItemRegister().registerAll((id, sup) -> Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(mod.getID(), id), sup.get()));
        }
    }
}
