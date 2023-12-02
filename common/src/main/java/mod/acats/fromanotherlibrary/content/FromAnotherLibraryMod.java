package mod.acats.fromanotherlibrary.content;

import mod.acats.fromanotherlibrary.FromAnotherLibrary;
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
}
