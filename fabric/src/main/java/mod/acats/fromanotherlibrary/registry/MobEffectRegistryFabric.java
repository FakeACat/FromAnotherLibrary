package mod.acats.fromanotherlibrary.registry;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;

public class MobEffectRegistryFabric {
    public static void register(CommonMod mod) {
        mod.getMobEffectRegister().ifPresent(mobEffectRegister -> mobEffectRegister.registerAll(
                (id, sup) -> Registry.register(BuiltInRegistries.MOB_EFFECT, new ResourceLocation(mod.getID(), id), sup.get())));
    }
}
