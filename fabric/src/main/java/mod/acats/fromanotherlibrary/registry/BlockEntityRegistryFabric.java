package mod.acats.fromanotherlibrary.registry;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class BlockEntityRegistryFabric {
    public static void register(CommonMod mod) {
        mod.getBlockEntityRegister().ifPresent(blockEntityRegister -> blockEntityRegister.registerAll((id, sup) -> {
            BlockEntityType<?> blockEntityType = sup.get();
            Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, new ResourceLocation(mod.getID(), id), blockEntityType);
        }));
    }
}
