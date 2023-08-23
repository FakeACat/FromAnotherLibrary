package mod.acats.fromanotherlibrary.registry.client;

import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;

import java.util.function.BiConsumer;
import java.util.function.Function;

public record ParticleClientEntry<P extends ParticleOptions>(ParticleType<P> type, Function<SpriteSet, ParticleProvider<P>> provider) {
    public void register(BiConsumer<ParticleType<P>, Function<SpriteSet, ParticleProvider<P>>> registerer) {
        registerer.accept(type, provider);
    }
}
