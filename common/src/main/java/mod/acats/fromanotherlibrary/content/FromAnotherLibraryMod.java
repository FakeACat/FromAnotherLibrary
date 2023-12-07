package mod.acats.fromanotherlibrary.content;

import mod.acats.fromanotherlibrary.FromAnotherLibrary;
import mod.acats.fromanotherlibrary.config.v2.ModConfig;
import mod.acats.fromanotherlibrary.content.config.ExampleConfig;
import mod.acats.fromanotherlibrary.content.config.ShaderConfig;
import mod.acats.fromanotherlibrary.content.config.WardenConfig;
import mod.acats.fromanotherlibrary.registry.CommonMod;
import mod.acats.fromanotherlibrary.registry.client.ClientMod;

import java.util.Optional;

public class FromAnotherLibraryMod implements CommonMod {
    @Override
    public String getID() {
        return FromAnotherLibrary.MOD_ID;
    }

    @Override
    public Optional<ClientMod> getClientMod() {
        return Optional.of(new FromAnotherLibraryClientMod());
    }

    @Override
    public Optional<ModConfig[]> getConfigs() {
        return Optional.of(new ModConfig[] {
                ShaderConfig.INSTANCE,
                ExampleConfig.INSTANCE,
                WardenConfig.INSTANCE
        });
    }
}
