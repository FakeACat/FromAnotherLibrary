package mod.acats.fromanotherlibrary;

import mod.acats.fromanotherlibrary.content.FromAnotherLibraryMod;
import mod.acats.fromanotherlibrary.registry.client.ClientRegistryFabric;
import net.fabricmc.api.ClientModInitializer;

public class FromAnotherLibraryClientFabric implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ClientRegistryFabric.registerClient(new FromAnotherLibraryMod());
    }
}
