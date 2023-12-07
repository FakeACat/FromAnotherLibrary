package mod.acats.fromanotherlibrary.client.screen;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import org.apache.commons.compress.utils.Lists;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.system.NonnullDefault;

import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;

@NonnullDefault
public class ArrayEditorScreen extends ListScreen<String> {
    private final Screen prev;
    private final Consumer<String[]> setter;
    private final Supplier<String[]> getter;
    public ArrayEditorScreen(Component component, Screen prev, Consumer<String[]> setter, Supplier<String[]> getter) {
        super(component);
        this.prev = prev;
        this.setter = setter;
        this.getter = getter;
    }

    @Override
    protected void init() {
        super.init();
        addRenderableWidget(Button.builder(CommonComponents.GUI_DONE, button -> onClose())
                .size(300, 30)
                .pos(width / 2 - 150, height - 31)
                .build()
        );
        ScreenList list = getList();
        if (list != null) {
            set(list);
        }
    }

    @Override
    public void onClose() {
        Minecraft.getInstance().setScreen(prev);
    }

    @Override
    protected String[] defaultEntries() {
        return getter.get();
    }

    @Override
    protected int xOffset(int defaultX, AbstractWidget widget, String value) {
        if (widget instanceof Button) {
            return defaultX - 200;
        } else {
            return defaultX - 151;
        }
    }

    @Override
    public void render(GuiGraphics $$0, int $$1, int $$2, float $$3) {
        super.render($$0, $$1, $$2, $$3);
        $$0.drawCenteredString(this.font, this.title, this.width / 2, 20, 16777215);
    }

    @Override
    protected List<AbstractWidget> createChildrenFor(ScreenListEntry entry) {
        EditBox input = getEditBox(entry);
        List<AbstractWidget> list = Lists.newArrayList();
        list.add(input);
        list.add(Button.builder(Component.translatable("gui.fromanotherlibrary.config.delete"), button -> {
            ScreenList list1 = getList();
            if (list1 != null) {
                list1.children().remove(entry);
                set(list1);
            }
        }).size(44, 20).build());
        return list;
    }

    @NotNull
    private EditBox getEditBox(ListScreen<String>.ScreenListEntry entry) {
        EditBox input = new EditBox(Minecraft.getInstance().font, 10, 5, 150, 20, Component.literal("Array Entry"));
        input.setValue(entry.value);
        input.setResponder(str -> {
            if (!Objects.equals(str, entry.value)) {
                ScreenList list = getList();

                if (list != null) {
                    if (str.isEmpty()) {
                        list.children().remove(entry);
                    } else {
                        entry.value = str;
                    }
                    set(list);
                }
            }
        });
        return input;
    }

    private void set(ScreenList list) {
        if (list.children().isEmpty() || !list.children().get(list.children().size() - 1).value.isEmpty()) {
            list.children().add(new ScreenListEntry(""));
        }
        if (!list.children().isEmpty()) {
            list.children().forEach(e -> ((Button) e.children().get(1)).visible = !e.value.isEmpty());
        }
        setter.accept(list.children().stream().map(e -> e.value).filter(s -> !s.isEmpty()).toArray(String[]::new));
    }
}
