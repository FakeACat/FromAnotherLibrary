package mod.acats.fromanotherlibrary.registry;

import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class MobEffectRegistryForge {
    public static void register(CommonMod mod, IEventBus eventBus) {
        final DeferredRegister<MobEffect> mobEffects = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, mod.getID());
        mod.getMobEffectRegister().ifPresent(mobEffectRegister -> mobEffectRegister.registerAll(mobEffects::register));
        mobEffects.register(eventBus);
    }
}
