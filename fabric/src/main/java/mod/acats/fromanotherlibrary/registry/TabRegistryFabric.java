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
        if (mod.getTabRegister() == null) {
            return;
        }
        mod.getTabRegister().registerAll((id, sup) -> {
            CreativeModeTab tab = sup.get();
            Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB,
                    ResourceKey.create(Registries.CREATIVE_MODE_TAB, new ResourceLocation(mod.getID(), id)),
                    tab);
        });
    }

    public static void populate(CommonMod mod) {
        TabPopulator populator = mod.getTabPopulator();
        if (populator == null) {
            return;
        }
        populator.forEach((tab, items) -> ItemGroupEvents.modifyEntriesEvent(tab).register(entries -> items.forEach(sup -> entries.accept(sup.get()))));
    }
}
