package me.pm.lemon.gui.autoUpdate;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

import java.io.IOException;

public class AutoUpdateScreen extends Screen {
    public AutoUpdateScreen(Text title) throws IOException {
        super(title);
    }

    private MinecraftClient mc = MinecraftClient.getInstance();
    private AutoUpdate autoUpdate = new AutoUpdate();
    private int contentLengh = autoUpdate.getContentLength();

    private boolean switch1 = false;

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        super.render(matrices, mouseX, mouseY, delta);
        super.renderBackground(matrices);

        if(!switch1) {
            Thread thread = new Thread(() -> autoUpdate.newDownload());
            System.out.println("AutoUpdateScreen init");
            thread.start();
            switch1 = true;
        }

        mc.textRenderer.drawWithShadow(matrices, "Downloading new version | " + autoUpdate.getDownloadProgress() / 1024 + " / " + contentLengh / 1024, (this.width / 2) - (mc.textRenderer.getWidth("Downloading new version | " + autoUpdate.getDownloadProgress() / 1024 + " / " + contentLengh / 1024) / 2), (this.height / 2) - 5, -1);
        if(autoUpdate.getDownloadProgress() >= contentLengh) {
            mc.textRenderer.drawWithShadow(matrices, "Please restart your minecraft client!", (this.width / 2) - (mc.textRenderer.getWidth("Please restart your minecraft client!") / 2), (this.height / 2) + 5, -1);
        }
    }
}
