package mod.acats.fromanotherlibrary.registry;

import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class SoundEventRegistryForge {
    public static void register(CommonMod mod, IEventBus eventBus) {
        final DeferredRegister<SoundEvent> register = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, mod.getID());
        mod.getSoundEventRegister().ifPresent(soundEventRegister -> soundEventRegister.registerAll(register::register));
        register.register(eventBus);
    }
}
