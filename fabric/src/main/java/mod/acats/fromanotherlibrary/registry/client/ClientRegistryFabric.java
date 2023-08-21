package mod.acats.fromanotherlibrary.registry.client;

import mod.acats.fromanotherlibrary.registry.CommonMod;
import mod.acats.fromanotherlibrary.utilities.block.Colourable;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.world.level.block.Block;

public class ClientRegistryFabric {
    public static void registerClient(CommonMod commonMod) {
        commonMod.getClientMod().ifPresent(clientMod -> {
            registerEntityRenderers(clientMod);
            registerBlockEntityRenderers(clientMod);
            registerModelLayers(clientMod);
        });

        registerColourProviders(commonMod);
    }

    private static void registerEntityRenderers(ClientMod clientMod) {
        clientMod.getEntityRendererEntries().ifPresent(entityRendererEntries ->
                entityRendererEntries.forEach(renderer ->
                        renderer.register(EntityRendererRegistry::register)));
    }

    private static void registerModelLayers(ClientMod clientMod) {
        clientMod.getModelLayerRegister().ifPresent(register -> register.forEach((location, definition) -> EntityModelLayerRegistry.registerModelLayer(location, definition::get)));
    }

    private static void registerBlockEntityRenderers(ClientMod clientMod) {
        clientMod.getBlockEntityRendererEntries().ifPresent(blockRendererEntries ->
                blockRendererEntries.forEach(renderer ->
                        renderer.register(BlockEntityRenderers::register)));
    }

    private static void registerColourProviders(CommonMod commonMod) {
        commonMod.getBlockRegister()
                .ifPresent(blockRegister ->
                blockRegister.forEach((id, sup) -> {

                    Block block = sup.get();

                    if (block instanceof Colourable colourable) {

                        ColorProviderRegistry.BLOCK.register(colourable.getBlockColour(), block);

                        commonMod.getItemRegister()
                                .flatMap(itemRegister -> itemRegister.get(id))
                                .ifPresent(item -> ColorProviderRegistry.ITEM.register(colourable.getItemColour(), item));

                    }
                }));
    }
}
