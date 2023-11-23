package mod.acats.fromanotherlibrary.registry.client;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.builders.LayerDefinition;

import java.util.HashMap;
import java.util.Optional;
import java.util.function.Supplier;

public interface ClientMod {
    default Optional<Iterable<BlockEntityRendererEntry<?>>> getBlockEntityRendererEntries() {
        return Optional.empty();
    }
    default Optional<Iterable<EntityRendererEntry<?>>> getEntityRendererEntries() {
        return Optional.empty();
    }
    default Optional<Iterable<ParticleClientEntry<?>>> getParticleClientEntries() {
        return Optional.empty();
    }
    default Optional<HashMap<
            ModelLayerLocation, Supplier<LayerDefinition>
            >> getModelLayerRegister() {
        return Optional.empty();
    }
}
