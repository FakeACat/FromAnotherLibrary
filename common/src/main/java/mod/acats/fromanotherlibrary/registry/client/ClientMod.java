package mod.acats.fromanotherlibrary.registry.client;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.SharedSuggestionProvider;

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
    default void registerClientCommands(CommandDispatcher<? extends SharedSuggestionProvider> dispatcher, CommandBuildContext buildContext) {
    }
    default void registerShaders() {
    }
}
