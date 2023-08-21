package mod.acats.fromanotherlibrary.registry;

import net.minecraft.world.entity.EntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class EntityRegistryForge {
    public static void register(CommonMod mod, IEventBus eventBus) {
        mod.getEntityRegister().ifPresent(entityFALRegister -> {
            final DeferredRegister<EntityType<?>> entityRegister = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, mod.getID());

            entityFALRegister.registerAll(entityRegister::register);

            entityRegister.register(eventBus);
        });
    }
}
