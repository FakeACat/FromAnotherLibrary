package mod.acats.fromanotherlibrary.registry.client;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.builders.LayerDefinition;

import java.util.Collection;
import java.util.HashMap;
import java.util.Optional;
import java.util.function.Supplier;

public interface ClientMod {
    Optional<Collection<BlockEntityRendererEntry<?>>> getBlockEntityRendererEntries();
    Optional<Collection<EntityRendererEntry<?>>> getEntityRendererEntries();
    Optional<HashMap<ModelLayerLocation, Supplier<LayerDefinition>>> getModelLayerRegister();
}
