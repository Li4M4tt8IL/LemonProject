package me.pm.lemon.gui.alts;

import me.pm.lemon.gui.alts.widgets.PasswordFieldWidget;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.AbstractButtonWidget;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

public class GuiAltLogin extends Screen {
    private PasswordFieldWidget password;
    private final Screen previousScreen;
    private AltLoginThread thread;
    private TextFieldWidget username;
    private ButtonWidget login;
    private MinecraftClient mc = MinecraftClient.getInstance();

    protected GuiAltLogin(Screen previousScreen) {
        super(Text.of("Gui Alt Login"));
        this.previousScreen = previousScreen;
    }

    @Override
    protected void init() {
        int var3 = height / 4 + 24;
        this.login = new ButtonWidget(width / 2 - 100, var3 + 72 + 12, 200, 20, Text.of("Login"), button -> {
            this.thread = new AltLoginThread(this.username.getText(), this.password.getText());
            this.thread.start();
        });
        addButton(login);
        addButton(new ButtonWidget(width / 2 - 100, var3 + 72 + 12 + 24, 200, 20, Text.of("Back"), button -> {
            mc.openScreen(previousScreen);
        }));
        this.username = new TextFieldWidget(textRenderer, width / 2 - 100, 60, 200, 20, Text.of("Username"));
        this.password = new PasswordFieldWidget(textRenderer, width / 2 - 100, 100, 200, 20, Text.of("Password"));
        super.init();
    }

    private int mouseX;
    private int mouseY;

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.mouseX = mouseX;
        this.mouseY = mouseY;

        super.renderBackground(matrices);
        this.username.render(matrices, mouseX, mouseY, delta);
        this.password.render(matrices, mouseX, mouseY, delta);
        this.drawCenteredString(matrices, textRenderer, "Add Alt", width / 2, 20, -1);
        if (this.username.getText().isEmpty()) {
            textRenderer.draw(matrices, "Username / E-Mail", width / 2 - 96, 66, -7829368);
        }
        if (this.password.getText().isEmpty()) {
            textRenderer.draw(matrices, "Password", width / 2 - 96, 106, -7829368);
        }
        super.render(matrices, mouseX, mouseY, delta);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        this.username.keyPressed(keyCode, scanCode, modifiers);
        this.password.keyPressed(keyCode, scanCode, modifiers);
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean charTyped(char chr, int modifiers) {
        this.username.charTyped(chr, modifiers);
        this.password.charTyped(chr, modifiers);
        if (chr == '\t' && (this.username.isFocused() || this.password.isFocused())) {
            this.username.setTextFieldFocused(!this.username.isFocused());
            this.password.setTextFieldFocused(!this.password.isFocused());
        }
        if (chr == '\r') {
            login.onPress();
        }
        return false;
    }

    public boolean mouseOver(int minX, int minY, int maxX, int maxY) {
        return mouseX >= minX && mouseX <= maxX && mouseY >= minY && mouseY < maxY;
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        for(AbstractButtonWidget abstractButtonWidget : buttons) {
            if(mouseOver(abstractButtonWidget.x, abstractButtonWidget.y,
                    abstractButtonWidget.x+abstractButtonWidget.getWidth(), abstractButtonWidget.y+abstractButtonWidget.getHeight())) {
                if(abstractButtonWidget.active && abstractButtonWidget.visible) {
                    abstractButtonWidget.onClick(mouseX, mouseY);
                }
            }
        }

        this.username.mouseClicked(mouseX, mouseY, button);
        this.password.mouseClicked(mouseX, mouseY, button);
        return false;
    }
}
