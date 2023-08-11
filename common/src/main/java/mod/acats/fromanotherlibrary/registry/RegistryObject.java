package mod.acats.fromanotherlibrary.registry;

import java.util.function.Supplier;

public class RegistryObject<T> {
    RegistryObject(Supplier<T> supplier){
        this.supplier = supplier;
    }
    private final Supplier<T> supplier;
    private T object;

    T build(){
        this.object = supplier.get();
        return this.object;
    }

    public T get(){
        return this.object;
    }
}
