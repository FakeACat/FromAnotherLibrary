package mod.acats.fromanotherlibrary.events;

import mod.acats.fromanotherlibrary.FromAnotherLibrary;
import mod.acats.fromanotherlibrary.registry.CommonMod;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = FromAnotherLibrary.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class EntityEvents {
    @SubscribeEvent
    public static void entityAttributeCreationEvent(EntityAttributeCreationEvent event){
        CommonMod.ALL.forEach((id, mod) ->
                mod.getEntityAttributeRegister().ifPresent(attributeRegister ->
                        attributeRegister.forEach(((entityType, builderSupplier) ->
                                event.put(entityType, builderSupplier.get().build())))));
    }
}
