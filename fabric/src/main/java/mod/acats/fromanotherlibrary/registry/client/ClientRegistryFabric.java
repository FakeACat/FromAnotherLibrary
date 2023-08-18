package mod.acats.fromanotherlibrary.registry.client;

import mod.acats.fromanotherlibrary.registry.CommonMod;
import mod.acats.fromanotherlibrary.utilities.block.Colourable;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.world.level.block.Block;

public class ClientRegistryFabric {
    public static void registerClient(CommonMod commonMod) {
        ClientMod clientMod = commonMod.getClientMod();
        if (clientMod == null) {
            return;
        }

        if (clientMod.getBlockRendererEntries() != null) {
            clientMod.getBlockRendererEntries().forEach(renderer -> renderer.register(BlockEntityRenderers::register));
        }

        if (commonMod.getBlockRegister() != null) {
            commonMod.getBlockRegister().forEach((id, sup) -> {
                Block block = sup.get();
                if (block instanceof Colourable colourable) {
                    ColorProviderRegistry.BLOCK.register(colourable.getBlockColour(), block);
                    if (commonMod.getItemRegister() != null) {
                        commonMod.getItemRegister().get(id).ifPresent(item -> ColorProviderRegistry.ITEM.register(colourable.getItemColour(), item));
                    }
                }
            });
        }
    }
}
