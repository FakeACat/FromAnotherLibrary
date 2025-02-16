package mod.acats.fromanotherlibrary.events;

import mod.acats.fromanotherlibrary.FromAnotherLibrary;
import mod.acats.fromanotherlibrary.registry.CommonMod;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = FromAnotherLibrary.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class InventoryEvents {
    @SubscribeEvent
    public static void buildCreativeModeTabContents(BuildCreativeModeTabContentsEvent event){
        CommonMod.ALL.forEach((id, mod) -> mod.getTabPopulator().ifPresent(tabPopulator -> tabPopulator.forEach((tab, items) -> {
            if (event.getTabKey() == tab) {
                items.forEach(sup -> event.accept(sup.get()));
            }
        })));
    }
}
