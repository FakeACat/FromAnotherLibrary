package mod.acats.fromanotherlibrary.registry;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;

public class SoundEventRegistryFabric {
    public static void register(CommonMod mod) {
        mod.getSoundEventRegister().ifPresent(soundEventRegister ->
                soundEventRegister.registerAll((id, sup) -> Registry.register(BuiltInRegistries.SOUND_EVENT, id, sup.get())));
    }
}
