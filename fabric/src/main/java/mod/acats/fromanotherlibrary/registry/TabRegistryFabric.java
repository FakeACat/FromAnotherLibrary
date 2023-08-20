package mod.acats.fromanotherlibrary.registry;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;

public class TabRegistryFabric {
    public static void register(CommonMod mod) {
        mod.getTabRegister().ifPresent(tabRegister -> tabRegister.registerAll((id, sup) -> {

            CreativeModeTab tab = sup.get();

            Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB,
                    ResourceKey.create(Registries.CREATIVE_MODE_TAB, new ResourceLocation(mod.getID(), id)),
                    tab);

        }));
    }

    public static void populate(CommonMod mod) {
        mod.getTabPopulator().ifPresent(populator -> populator.forEach((tab, items) -> ItemGroupEvents.modifyEntriesEvent(tab).register(entries -> items.forEach(sup -> entries.accept(sup.get())))));
    }
}
