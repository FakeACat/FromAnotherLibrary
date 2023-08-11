package mod.acats.fromanotherlibrary.registry;

import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

public class Register<T> {
    Register(){
    }

    private final HashMap<ResourceLocation, RegistryObject<? extends T>> map = new HashMap<>();

    public <E extends T> RegistryObject<E> register(ResourceLocation id, Supplier<E> supplier){
        RegistryObject<E> fawRegistryObject = new RegistryObject<>(supplier);
        map.put(id, fawRegistryObject);
        return fawRegistryObject;
    }

    final void registerAll(BiConsumer<ResourceLocation, Supplier<? extends T>> registerer){
        map.forEach((id, registryObject) -> registerer.accept(id, registryObject::build));
    }

    public final void forEach(BiConsumer<ResourceLocation, Supplier<? extends T>> action){
        map.forEach((id, registryObject) -> action.accept(id, registryObject::get));
    }

    public final T get(ResourceLocation id) {
        return this.map.get(id).get();
    }

}
