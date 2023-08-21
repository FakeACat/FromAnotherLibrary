package mod.acats.fromanotherlibrary.registry.client;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;

import java.util.function.BiConsumer;

public record EntityRendererEntry<B extends Entity>(EntityType<B> type, EntityRendererProvider<B> renderer) {
    public void register(BiConsumer<EntityType<B>, EntityRendererProvider<B>> registerer) {
        registerer.accept(type, renderer);
    }
}
