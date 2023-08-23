package mod.acats.fromanotherlibrary.registry;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;

public class ParticleRegistryFabric {
    public static void register(CommonMod mod) {
        mod.getParticleRegister().ifPresent(particleRegister ->
                particleRegister.registerAll((id, sup) ->
                        Registry.register(BuiltInRegistries.PARTICLE_TYPE, new ResourceLocation(mod.getID(), id), sup.get())));
    }
}
