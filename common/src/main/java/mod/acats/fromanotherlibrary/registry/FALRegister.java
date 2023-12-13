package mod.acats.fromanotherlibrary.registry;

import java.util.HashMap;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

public class FALRegister<T> {
    public FALRegister(){
    }

    private final HashMap<String, FALRegistryObject<? extends T>> map = new HashMap<>();

    public <E extends T> FALRegistryObject<E> register(String id, Supplier<E> supplier){
        FALRegistryObject<E> falRegistryObject = new FALRegistryObject<>(supplier);
        map.put(id, falRegistryObject);
        return falRegistryObject;
    }

    final void registerAll(BiConsumer<String, Supplier<? extends T>> registerer){
        map.forEach((id, registryObject) -> registerer.accept(id, registryObject::build));
    }

    public final void forEach(BiConsumer<String, Supplier<? extends T>> action){
        map.forEach(action);
    }

    public final Optional<T> get(String id) {
        if (this.map.containsKey(id)) {
            return Optional.of(this.map.get(id).get());
        }
        return Optional.empty();
    }
}
