package mod.acats.fromanotherlibrary.events;

import mod.acats.fromanotherlibrary.FromAnotherLibrary;
import mod.acats.fromanotherlibrary.client.screen.ConfigListScreen;
import mod.acats.fromanotherlibrary.registry.CommonMod;
import mod.acats.fromanotherlibrary.registry.client.ClientMod;
import mod.acats.fromanotherlibrary.utilities.block.Colourable;
import net.minecraft.client.Minecraft;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import java.util.function.Supplier;

@Mod.EventBusSubscriber(modid = FromAnotherLibrary.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEvents {
    @SubscribeEvent
    public static void clientSetupEvent(FMLClientSetupEvent event) {
        CommonMod.ALL.forEach((id, mod) -> {
            mod.getClientMod().ifPresent(ClientMod::setupClient);

            mod.getConfigs().ifPresent(
                    configs -> ModLoadingContext.get().registerExtensionPoint(ConfigScreenHandler.ConfigScreenFactory.class,
                            () -> new ConfigScreenHandler.ConfigScreenFactory(
                                    (mc, screen) -> new ConfigListScreen(mod.getID(), configs, Minecraft.getInstance().screen))));
        });
    }

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        CommonMod.ALL.forEach((id, mod) -> mod.getClientMod().ifPresent(clientMod -> {

            clientMod.getEntityRendererEntries().ifPresent(rendererEntries ->
                    rendererEntries.forEach(renderer -> renderer.register(event::registerEntityRenderer)));

            clientMod.getBlockEntityRendererEntries().ifPresent(rendererEntries ->
                    rendererEntries.forEach(renderer -> renderer.register(event::registerBlockEntityRenderer)));

        }));
    }

    @SubscribeEvent
    public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event){
        CommonMod.ALL.forEach((id, mod) -> mod.getClientMod().flatMap(ClientMod::getModelLayerRegister).ifPresent(layerRegister ->
                layerRegister.forEach(event::registerLayerDefinition)));
    }

    @SubscribeEvent
    public static void registerColorHandlersItemEvent(RegisterColorHandlersEvent.Item event) {
        CommonMod.ALL.forEach((id, mod) -> {
            if (mod.getBlockRegister().isPresent()) {

                mod.getItemRegister().ifPresent(itemRegister ->
                        itemRegister.forEach((id2, sup) ->
                                registerColourableItem(mod, id2, sup, event)));
            }
        });
    }

    private static void registerColourableItem(CommonMod mod, String itemID, Supplier<? extends Item> itemSupplier, RegisterColorHandlersEvent.Item event) {
        Item item = itemSupplier.get();

        mod.getBlockRegister().orElseThrow().get(itemID).ifPresent(block -> {
                    if (block instanceof Colourable colourable) {
                        event.register(colourable.getItemColour(), item);
                    }
                });
    }

    @SubscribeEvent
    public static void registerColorHandlersBlockEvent(RegisterColorHandlersEvent.Block event) {
        CommonMod.ALL.forEach((id, mod) ->
                mod.getBlockRegister().ifPresent(br ->
                        br.forEach((id2, sup) ->
                                registerColourableBlock(sup, event))));
    }

    private static void registerColourableBlock(Supplier<? extends Block> blockSupplier, RegisterColorHandlersEvent.Block event) {
        Block block = blockSupplier.get();
        if (block instanceof Colourable colourable) {
            event.register(colourable.getBlockColour(), block);
        }
    }

    @SubscribeEvent
    public static void registerParticleProviders(RegisterParticleProvidersEvent event){
        CommonMod.ALL.forEach((id, mod) ->
                mod.getClientMod().flatMap(ClientMod::getParticleClientEntries).ifPresent(entries ->
                        entries.forEach(entry -> entry.register((type, fun) -> event.registerSpriteSet(type, fun::apply)))));
    }
}
