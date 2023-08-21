package mod.acats.fromanotherlibrary.registry;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class BlockEntityRegistryForge {
    public static void register(CommonMod mod, IEventBus eventBus){
        mod.getBlockEntityRegister().ifPresent(blockEntityFALRegister -> {

            final DeferredRegister<BlockEntityType<?>> blockEntityRegister = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, mod.getID());

            blockEntityFALRegister.registerAll(blockEntityRegister::register);

            blockEntityRegister.register(eventBus);

        });
    }
}
