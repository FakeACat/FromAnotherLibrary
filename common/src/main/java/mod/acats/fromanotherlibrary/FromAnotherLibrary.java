package mod.acats.fromanotherlibrary;

import mod.acats.fromanotherlibrary.platform.ModLoaderSpecific;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ServiceLoader;

public class FromAnotherLibrary {
    public static final String MOD_ID = "fromanotherlibrary";
    public static final String NAME = "From Another Library";
    public static final Logger LOGGER = LoggerFactory.getLogger(NAME);
    public static final ModLoaderSpecific ML_SPECIFIC = ServiceLoader.load(ModLoaderSpecific.class)
            .findFirst()
            .orElseThrow(() -> new NullPointerException("Failed to load service for " + ModLoaderSpecific.class.getName()));

    public static void init() {

    }
}
