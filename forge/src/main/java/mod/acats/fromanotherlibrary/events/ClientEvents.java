package mod.acats.fromanotherlibrary.events;

import mod.acats.fromanotherlibrary.FromAnotherLibrary;
import mod.acats.fromanotherlibrary.registry.CommonMod;
import mod.acats.fromanotherlibrary.utilities.block.Colourable;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Objects;
import java.util.function.Supplier;

@Mod.EventBusSubscriber(modid = FromAnotherLibrary.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEvents {
    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        CommonMod.ALL.forEach((id, mod) -> {
            if (mod.getClientMod() != null && mod.getClientMod().getBlockRendererEntries() != null) {
                mod.getClientMod().getBlockRendererEntries().forEach(renderer -> renderer.register(event::registerBlockEntityRenderer));
            }
        });
    }

    @SubscribeEvent
    public static void registerColorHandlersItemEvent(RegisterColorHandlersEvent.Item event) {
        CommonMod.ALL.forEach((id, mod) -> {
            if (mod.getBlockRegister() != null && mod.getItemRegister() != null) {
                mod.getItemRegister().forEach((id2, sup) -> registerColourableItem(mod, id2, sup, event));
            }
        });
    }

    private static void registerColourableItem(CommonMod mod, String itemID, Supplier<? extends Item> itemSupplier, RegisterColorHandlersEvent.Item event) {
        Item item = itemSupplier.get();
        Objects.requireNonNull(mod.getBlockRegister()).get(itemID).ifPresent(block -> {
                    if (block instanceof Colourable colourable) {
                        event.register(colourable.getItemColour(), item);
                    }
                });
    }

    @SubscribeEvent
    public static void registerColorHandlersBlockEvent(RegisterColorHandlersEvent.Block event) {
        CommonMod.ALL.forEach((id, mod) -> {
            if (mod.getBlockRegister() != null) {
                mod.getBlockRegister().forEach((id2, sup) -> registerColourableBlock(sup, event));
            }
        });
    }

    private static void registerColourableBlock(Supplier<? extends Block> blockSupplier, RegisterColorHandlersEvent.Block event) {
        Block block = blockSupplier.get();
        if (block instanceof Colourable colourable) {
            event.register(colourable.getBlockColour(), block);
        }
    }
}
