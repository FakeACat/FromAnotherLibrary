package mod.acats.fromanotherlibrary.registry;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;

public class BlockRegistryFabric {
    public static void register(CommonMod mod) {
        if (mod.getBlockRegister() == null) {
            return;
        }

        mod.getBlockRegister().registerAll((id, sup) -> {
            Block block = Registry.register(BuiltInRegistries.BLOCK, new ResourceLocation(mod.getID(), id), sup.get());
            if (mod.getItemRegister() != null) {
                mod.getItemRegister().register(id, () -> new BlockItem(block, new FabricItemSettings()));
            }
        });
    }
}
