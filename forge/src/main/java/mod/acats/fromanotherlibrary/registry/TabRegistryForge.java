package mod.acats.fromanotherlibrary.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;

public class TabRegistryForge {
    public static void register(CommonMod mod, IEventBus eventBus) {
        mod.getTabRegister().ifPresent(tabRegister -> {

            final DeferredRegister<CreativeModeTab> register = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, mod.getID());

            tabRegister.registerAll(register::register);

            register.register(eventBus);

        });
    }
}
