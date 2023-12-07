package mod.acats.fromanotherlibrary.client.screen;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.ContainerObjectSelectionList;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.system.NonnullDefault;

@NonnullDefault
public abstract class ListScreen<T> extends Screen {
    protected ListScreen(Component component) {
        super(component);
    }

    private @Nullable ScreenList screenList;
    @Override
    protected void init() {
        screenList = new ScreenList(defaultEntries());
        addRenderableWidget(screenList);
    }

    public @Nullable ScreenList getList() {
        return screenList;
    }

    protected abstract T[] defaultEntries();

    protected abstract java.util.List<AbstractWidget> createChildrenFor(ScreenListEntry entry);

    public void render(java.util.List<AbstractWidget> children, T value, GuiGraphics var1, int var2, int y, int var4, int var5, int var6, int mouseX, int mouseY, boolean var9, float var10) {
        children.forEach(w -> {
            w.setX(xOffset(var4 + var5, w, value));
            w.setY(y);
            w.render(var1, mouseX, mouseY, var10);
        });
    }

    protected int xOffset(int defaultX, AbstractWidget widget, T value) {
        return defaultX;
    }

    public class ScreenListEntry extends ContainerObjectSelectionList.Entry<ScreenListEntry> {
        private final java.util.List<AbstractWidget> children;
        public T value;
        public ScreenListEntry(T value) {
            this.value = value;
            children = createChildrenFor(this);
        }
        @Override
        public java.util.List<? extends NarratableEntry> narratables() {
            return children;
        }

        @Override
        public void render(GuiGraphics var1, int var2, int y, int var4, int var5, int var6, int mouseX, int mouseY, boolean var9, float var10) {
            ListScreen.this.render(children, value, var1, var2, y, var4, var5, var6, mouseX, mouseY, var9, var10);
        }

        @Override
        public java.util.List<? extends GuiEventListener> children() {
            return children;
        }
    }

    public class ScreenList extends ContainerObjectSelectionList<ScreenListEntry> {
        public ScreenList(T[] entries) {
            super(
                    Minecraft.getInstance(),
                    ListScreen.this.width,
                    ListScreen.this.height,
                    43,
                    ListScreen.this.height - 32,
                    24
            );

            for (T value:
                 entries) {
                this.addEntry(new ScreenListEntry(value));
            }
        }

        @Nullable
        @Override
        public ScreenListEntry getHovered() {
            return super.getHovered();
        }
    }
}
