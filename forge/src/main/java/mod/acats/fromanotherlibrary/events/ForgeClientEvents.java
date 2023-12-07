package mod.acats.fromanotherlibrary.events;

import mod.acats.fromanotherlibrary.FromAnotherLibrary;
import mod.acats.fromanotherlibrary.event.client.ClientLevelTick;
import mod.acats.fromanotherlibrary.registry.CommonMod;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterClientCommandsEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = FromAnotherLibrary.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ForgeClientEvents {
    @SubscribeEvent
    public static void registerClientCommands(RegisterClientCommandsEvent event) {
        CommonMod.ALL.forEach((id, mod) ->
                mod.getClientMod().ifPresent(clientMod ->
                        clientMod.registerClientCommands(event.getDispatcher(), event.getBuildContext())));
    }

    @SubscribeEvent
    public static void clientTick(TickEvent.LevelTickEvent event) {
        if (event.side == LogicalSide.CLIENT && event.phase == TickEvent.Phase.START) {
            ClientLevelTick.EVENT.execute(e -> e.tick((ClientLevel) event.level));
        }
    }
}
