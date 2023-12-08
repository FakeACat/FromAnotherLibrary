package mod.acats.fromanotherlibrary.compat;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import mod.acats.fromanotherlibrary.FromAnotherLibrary;
import mod.acats.fromanotherlibrary.client.screen.ConfigListScreen;
import mod.acats.fromanotherlibrary.content.FromAnotherLibraryMod;

public class FALModMenu implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return prev -> new ConfigListScreen(FromAnotherLibrary.MOD_ID, new FromAnotherLibraryMod().getConfigs().orElseThrow(), prev);
    }
}
