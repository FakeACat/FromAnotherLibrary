package mod.acats.fromanotherlibrary.event;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.function.Consumer;

public class FALEvent<T> {
    private T[] methods;
    @SuppressWarnings("unchecked")
    public FALEvent() {
        this.methods = (T[]) new Object[] {};
    }

    public void add(@NotNull T method) {
        methods = Arrays.copyOf(methods, methods.length + 1);
        methods[methods.length - 1] = method;
    }

    public void execute(@NotNull Consumer<T> executor) {
        if (methods.length == 0) {
            return;
        }

        if (methods.length == 1) {
            executor.accept(methods[0]);
            return;
        }

        for (T method: methods) {
            executor.accept(method);
        }
    }
}
