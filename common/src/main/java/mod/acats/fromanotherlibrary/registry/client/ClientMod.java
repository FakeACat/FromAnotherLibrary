package mod.acats.fromanotherlibrary.registry.client;

import java.util.Collection;
import java.util.Optional;

public interface ClientMod {
    Optional<Collection<BlockEntityRendererEntry<?>>> getBlockRendererEntries();
}
