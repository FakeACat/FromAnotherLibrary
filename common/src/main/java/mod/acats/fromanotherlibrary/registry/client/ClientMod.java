package mod.acats.fromanotherlibrary.registry.client;

import org.jetbrains.annotations.Nullable;

import java.util.Collection;

public interface ClientMod {
    @Nullable Collection<BlockEntityRendererEntry<?>> getBlockRendererEntries();
}
