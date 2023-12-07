package mod.acats.fromanotherlibrary;

import mod.acats.fromanotherlibrary.content.FromAnotherLibraryMod;
import mod.acats.fromanotherlibrary.event.client.ClientLevelTick;
import mod.acats.fromanotherlibrary.registry.client.ClientRegistryFabric;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;

public class FromAnotherLibraryClientFabric implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ClientRegistryFabric.registerClient(new FromAnotherLibraryMod());

        ClientTickEvents.START_WORLD_TICK.register(level -> ClientLevelTick.EVENT.execute(e -> e.tick(level)));
    }
}
