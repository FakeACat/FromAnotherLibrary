package mod.acats.fromanotherlibrary.content.config;

import mod.acats.fromanotherlibrary.config.v2.ModConfig;
import mod.acats.fromanotherlibrary.config.v2.properties.ArrayProperty;

public class WardenConfig extends ModConfig {
    public static final WardenConfig INSTANCE = new WardenConfig();
    @Override
    public String name() {
        return "warden";
    }

    @Override
    protected int version() {
        return 0;
    }

    public final ArrayProperty wardenIgnored = addProperty(new ArrayProperty(
            "warden_ignored",
            "Mobs that should not be attacked by the Warden",
            new String[0],
            false
    ));
}
