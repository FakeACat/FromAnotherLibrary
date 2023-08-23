package mod.acats.fromanotherlibrary.registry;

import net.minecraft.core.particles.ParticleType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ParticleRegistryForge {
    public static void register(CommonMod mod, IEventBus eventBus) {
        mod.getParticleRegister().ifPresent(particleRegister -> {
            final DeferredRegister<ParticleType<?>> particles = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, mod.getID());

            particleRegister.registerAll(particles::register);

            particles.register(eventBus);
        });
    }
}
