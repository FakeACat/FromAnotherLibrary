package mod.acats.fromanotherlibrary.events;

import mod.acats.fromanotherlibrary.FromAnotherLibrary;
import mod.acats.fromanotherlibrary.registry.CommonMod;
import mod.acats.fromanotherlibrary.utilities.block.BlockUtils;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod.EventBusSubscriber(modid = FromAnotherLibrary.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CommonEvents {
    @SubscribeEvent
    public static void fmlCommonSetup(FMLCommonSetupEvent event){
        event.enqueueWork(() -> CommonMod.ALL.forEach((id, mod) -> BlockUtils.setFlammableBlocks(mod)));
    }
}
