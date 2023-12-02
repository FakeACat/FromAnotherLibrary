package mod.acats.fromanotherlibrary.events;

import mod.acats.fromanotherlibrary.FromAnotherLibrary;
import mod.acats.fromanotherlibrary.registry.CommonMod;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = FromAnotherLibrary.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ForgeCommonEvents {
    @SubscribeEvent
    public static void registerCommands(RegisterCommandsEvent event) {
        CommonMod.ALL.forEach((id, mod) -> mod.registerCommands(event.getDispatcher(), event.getBuildContext(), event.getCommandSelection()));
    }
}
