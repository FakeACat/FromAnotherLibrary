package mod.acats.fromanotherlibrary;

import mod.acats.fromanotherlibrary.content.FromAnotherLibraryMod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FromAnotherLibrary {
    public static final String MOD_ID = "fromanotherlibrary";
    public static final String NAME = "From Another Library";
    public static final Logger LOGGER = LoggerFactory.getLogger(NAME);

    public static void init() {
        new FromAnotherLibraryMod().init();
    }
}
