package me.pm.lemon.gui.hud;

import com.google.common.collect.Sets;
import me.pm.lemon.event.EventTarget;
import me.pm.lemon.event.events.DrawOverlayEvent;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ChatScreen;

import java.util.Arrays;
import java.util.Collection;
import java.util.Set;

public class HUDManager {

    private HUDManager() {
    }

    private static HUDManager instance = null;

    public static HUDManager getInstance() {
        if(instance != null) {
            return instance;
        }
        instance = new HUDManager();
        return instance;
    }

    private final Set<IRenderer> registeredRenderers = Sets.newHashSet();
    private final MinecraftClient mc = MinecraftClient.getInstance();

    public void register(IRenderer... renderers) {
        this.registeredRenderers.addAll(Arrays.asList(renderers));
    }

    public void unregister(IRenderer... renderers) {
        for(IRenderer renderer : renderers) {
            this.registeredRenderers.remove(renderer);
        }
    }

    public Collection<IRenderer> getRegisteredRenderers() {
        return Sets.newHashSet(registeredRenderers);
    }

    public void openConfigScreen() {
        mc.openScreen(new HUDConfigScreen(this));
    }

    @EventTarget
    public void onRender(DrawOverlayEvent e) {
        if(mc.currentScreen == null || mc.currentScreen instanceof ChatScreen) {
            for(IRenderer renderer : registeredRenderers) {
                callRenderer(renderer);
            }
        }
    }

    public void callRenderer(IRenderer renderer) {
        if(!renderer.isEnabled()) {
            return;
        }

        ScreenPosition pos = renderer.load();

        if(pos == null) {
            pos = ScreenPosition.fromRelativePosition(0.5, 0.5);
        }
        if(pos.getAbsoluteX()+renderer.getWidth() >= mc.getWindow().getScaledWidth()) {
            int screenWidth = MinecraftClient.getInstance().getWindow().getScaledWidth();
            int screenHeight = MinecraftClient.getInstance().getWindow().getScaledHeight();

            int absoluteX = Math.max(0, Math.min(pos.getAbsoluteX(), Math.max(screenWidth - renderer.getWidth(), 0)));
            int absoluteY = Math.max(0, Math.min(pos.getAbsoluteY(), Math.max(screenHeight - renderer.getHeight(), 0)));

            pos.setAbsolute(absoluteX, absoluteY);
        }

        renderer.render(pos);
    }
}
