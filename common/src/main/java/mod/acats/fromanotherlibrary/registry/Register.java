package mod.acats.fromanotherlibrary.registry;

import java.util.HashMap;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

public class Register<T> {
    public Register(){
    }

    private final HashMap<String, RegistryObject<? extends T>> map = new HashMap<>();

    public <E extends T> RegistryObject<E> register(String id, Supplier<E> supplier){
        RegistryObject<E> fawRegistryObject = new RegistryObject<>(supplier);
        map.put(id, fawRegistryObject);
        return fawRegistryObject;
    }

    final void registerAll(BiConsumer<String, Supplier<? extends T>> registerer){
        map.forEach((id, registryObject) -> registerer.accept(id, registryObject::build));
    }

    public final void forEach(BiConsumer<String, Supplier<? extends T>> action){
        map.forEach((id, registryObject) -> action.accept(id, registryObject::get));
    }

    public final Optional<T> get(String id) {
        if (this.map.containsKey(id)) {
            return Optional.of(this.map.get(id).get());
        }
        return Optional.empty();
    }
}
