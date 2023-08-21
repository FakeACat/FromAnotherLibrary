package mod.acats.fromanotherlibrary.registry;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;

public class EntityRegistryFabric {
    public static void register(CommonMod mod) {
        mod.getEntityRegister().ifPresent(entityRegister -> entityRegister.registerAll((id, sup) ->
                Registry.register(BuiltInRegistries.ENTITY_TYPE, new ResourceLocation(mod.getID(), id), sup.get())
        ));

        mod.getEntityAttributeRegister().ifPresent(attributeRegister ->
                attributeRegister.forEach((entityType, attributeBuilder) -> FabricDefaultAttributeRegistry.register(entityType, attributeBuilder.get()))
        );
    }
}
