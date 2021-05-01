package me.pm.lemon.gui.hud;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;

import java.util.Collection;
import java.util.HashMap;
import java.util.Optional;
import java.util.function.Predicate;

public class HUDConfigScreen extends Screen {
    private static final HashMap<IRenderer, ScreenPosition> renderers = new HashMap<IRenderer, ScreenPosition>();

    private Optional<IRenderer> selectedRenderer = Optional.empty();

    private int prevX, prevY;


    public HUDConfigScreen(HUDManager api) {
        super(new LiteralText("gui.hud"));
        Collection<IRenderer> registeredRenderers = api.getRegisteredRenderers();

        for(IRenderer ren : registeredRenderers) {
            if(!ren.isEnabled()) {
                continue;
            }

            ScreenPosition pos = ren.load();

            if(pos == null) {
                pos = ScreenPosition.fromRelativePosition(0.5, 0.5);
            }

            adjustBounds(ren, pos);
            renderers.put(ren, pos);
        }
    }

    public static HashMap<IRenderer, ScreenPosition> getRenderers() {
        return renderers;
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        super.renderBackground(matrixStack);

//        final float zBackup = this.zLevel;
//        this.zLevel = 200;

        drawHollowRect(matrixStack, 0, 0, this.width - 1, this.height - 1, 0xFFFF0000);

        for(IRenderer renderer : renderers.keySet()) {
            ScreenPosition pos = renderers.get(renderer);

            renderer.renderDummy(pos);

            drawHollowRect(matrixStack, pos.getAbsoluteX(), pos.getAbsoluteY(), renderer.getWidth(), renderer.getHeight(), 0xFF00FFFF);
        }
    }

    private void drawHollowRect(MatrixStack matrixStack, int x, int y, int w, int h, int c) {
        this.drawHorizontalLine(matrixStack, x, x + w, y, c);
        this.drawHorizontalLine(matrixStack, x, x + w, y + h, c);

        this.drawVerticalLine(matrixStack, x, y + h, y, c);
        this.drawVerticalLine(matrixStack, x + w, y + h, y, c);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        handleKeyPressed(keyCode, scanCode, modifiers);
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    public void handleKeyPressed(int keyCode, int scanCode, int modifiers) {
        if(keyCode == 256) {
            renderers.entrySet().forEach((entry) -> {
                entry.getKey().save(entry.getValue());
            });
            MinecraftClient.getInstance().openScreen(null);
        }
    }

    @Override
    public boolean shouldCloseOnEsc() {
        return true;
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        handleMouseDragged(mouseX, mouseY, button);
        return super.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
    }

    private void handleMouseDragged(double x, double y, int button) {
        if(button == 0) {
            if(selectedRenderer.isPresent()) {
                moveSelectedRenderBy((int) x - prevX, (int) y - prevY);
            }
            this.prevX = (int) x;
            this.prevY = (int) y;
        }
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        handleMouseClicked((int) mouseX,(int) mouseY, button);
        return super.mouseClicked(mouseX, mouseY, button);
    }

    public void handleMouseClicked(int x, int y, int button) {
        this.prevX = x;
        this.prevY = y;
        loadMouseOver(x, y);
    }

    public void moveSelectedRenderBy(int offsetX, int offsetY) {
        if(selectedRenderer.isPresent()) {
            IRenderer renderer = selectedRenderer.get();
            ScreenPosition pos = renderers.get(renderer);

            pos.setAbsolute(pos.getAbsoluteX() + offsetX, pos.getAbsoluteY() + offsetY);

            adjustBounds(renderer, pos);
        }
    }

    @Override
    public void onClose() {
        for(IRenderer renderer : renderers.keySet()) {
            renderer.save(renderers.get(renderer));
        }
    }

    @Override
    public boolean isPauseScreen() {
        return true;
    }

    public static void adjustBounds(IRenderer renderer, ScreenPosition pos) {
        int screenWidth = MinecraftClient.getInstance().getWindow().getScaledWidth();
        int screenHeight = MinecraftClient.getInstance().getWindow().getScaledHeight();

        int absoluteX = Math.max(0, Math.min(pos.getAbsoluteX(), Math.max(screenWidth - renderer.getWidth(), 0)));
        int absoluteY = Math.max(0, Math.min(pos.getAbsoluteY(), Math.max(screenHeight - renderer.getHeight(), 0)));

        pos.setAbsolute(absoluteX, absoluteY);
    }

    private void loadMouseOver(int x, int y) {
        this.selectedRenderer = renderers.keySet().stream().filter(new MouseOverFinder(x, y)).findFirst();
    }

    private class MouseOverFinder implements Predicate<IRenderer> {

        private int mouseX, mouseY;

        public MouseOverFinder(int x, int y) {
            this.mouseX = x;
            this.mouseY = y;
        }

        @Override
        public boolean test(IRenderer renderer) {
            ScreenPosition pos = renderers.get(renderer);

            int absoluteX = pos.getAbsoluteX();
            int absoluteY = pos.getAbsoluteY();

            if(mouseX >= absoluteX && mouseX <= absoluteX + renderer.getWidth()) {
                if(mouseY >= absoluteY && mouseY <= absoluteY + renderer.getHeight()) {
                    return true;
                }
            }
            return false;
        }
    }
}
