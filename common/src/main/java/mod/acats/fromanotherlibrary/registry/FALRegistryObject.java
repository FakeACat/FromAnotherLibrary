package mod.acats.fromanotherlibrary.registry;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.function.Supplier;

public class FALRegistryObject<T> {
    FALRegistryObject(Supplier<T> supplier){
        this.supplier = supplier;
    }
    private final Supplier<T> supplier;
    @Nullable private T object;

    T build(){
        this.object = supplier.get();
        return this.object;
    }

    @NotNull public T get(){
        return Objects.requireNonNull(this.object, "Attempted to get From Another Library registry object before registering it");
    }
}
