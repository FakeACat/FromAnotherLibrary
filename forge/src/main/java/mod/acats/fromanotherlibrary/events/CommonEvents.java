package mod.acats.fromanotherlibrary.events;

import mod.acats.fromanotherlibrary.FromAnotherLibrary;
import mod.acats.fromanotherlibrary.registry.CommonMod;
import mod.acats.fromanotherlibrary.spawning.ForgeSimpleSpawns;
import mod.acats.fromanotherlibrary.spawning.SpawnEntry;
import mod.acats.fromanotherlibrary.utilities.block.BlockUtils;
import net.minecraft.world.entity.Mob;
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod.EventBusSubscriber(modid = FromAnotherLibrary.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CommonEvents {
    @SubscribeEvent
    public static void fmlCommonSetup(FMLCommonSetupEvent event){
        event.enqueueWork(() -> CommonMod.ALL.forEach((id, mod) -> BlockUtils.setFlammableBlocks(mod)));
    }

    @SubscribeEvent
    public static void spawnPlacementRegister(SpawnPlacementRegisterEvent event) {
        ForgeSimpleSpawns.ENTRIES.forEach(e -> add(event, e));
    }

    private static <T extends Mob> void add(SpawnPlacementRegisterEvent event, SpawnEntry<T> e){
        event.register(e.entityTypeSupplier().get(), e.placementType(), e.heightmapType(), e.spawnPredicate(), SpawnPlacementRegisterEvent.Operation.REPLACE);
    }
}
