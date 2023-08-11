package mod.acats.fromanotherlibrary;

import net.fabricmc.api.ModInitializer;

public class FromAnotherLibraryFabric implements ModInitializer {
    
    @Override
    public void onInitialize() {
        FromAnotherLibrary.init();
    }
}
