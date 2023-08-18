package mod.acats.fromanotherlibrary.registry.client;

import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

import java.util.function.BiConsumer;

public record BlockEntityRendererEntry<B extends BlockEntity>(BlockEntityType<B> type, BlockEntityRendererProvider<B> renderer) {
    public void register(BiConsumer<BlockEntityType<B>, BlockEntityRendererProvider<B>> registerer) {
        registerer.accept(type, renderer);
    }
}
