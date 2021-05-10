package me.pm.lemon.gui.loginPage;

import me.pm.lemon.Main;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

public class LoginScreen extends Screen {
    public LoginScreen() {
        super(Text.of("Screen"));
    }

    private TextFieldWidget email;
    private TextFieldWidget password;

    private ButtonWidget loginButton;
    private ButtonWidget successButton;

    private boolean errorVisible = false;
    private boolean successVisible = false;
    private boolean invalidVisible = false;

    private MinecraftClient mc = MinecraftClient.getInstance();

    @Override
    protected void init() {
        int width = mc.getWindow().getScaledWidth();
        int height = mc.getWindow().getScaledHeight();

        email = new TextFieldWidget(mc.textRenderer, width / 2-250 / 2, height / 2 - 20, 250, 20, Text.of("Email"));
        password = new TextFieldWidget(mc.textRenderer, width / 2-250 / 2, height / 2 + 20, 250, 20, Text.of("Password"));
        loginButton = new ButtonWidget(width / 2-255 / 2, height / 2 + 50, 255, 20, Text.of("Login!"), (button -> {
            email.setText(email.getText().replace(" ", ""));
            password.setText(password.getText().replace(" ", ""));
            if(email.getText().equals("") || password.getText().equals("")) {
                errorVisible = true;
                successVisible = false;
                return;
            }
            System.out.println(email.getText() + ":" + password.getText());
            if(email.getText().equals("admin@lemon.com") && password.getText().equals("LemonClient")) {
                errorVisible = false;
                successVisible = true;
                Main.loggedIn = true;
            }
            email.setText("");
            password.setText("");
            errorVisible = false;
        }));
        successButton = new ButtonWidget(width / 2-255 / 2, height / 2 + 80, 255, 20, Text.of("Proceed."), button -> {
            mc.openScreen(null);
        });
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        super.renderBackground(matrices);
        if(errorVisible) {
            mc.textRenderer.draw(matrices, "Please fill all fields!",width / 2-250 / 2, height / 2 - 50, 0x00FF0000);
        }
        if(successVisible) {
            mc.textRenderer.draw(matrices, "Logged in!",width / 2-250 / 2, height / 2 - 50, 0x0000FF00);
            successButton.visible = true;
        } else {
            successButton.visible = false;
        }

        mc.textRenderer.draw(matrices, "E-Mail", width/2-250/2, height / 2 - 30, -1);
        mc.textRenderer.draw(matrices, "Password", width/2-250/2, height / 2 + 10, -1);

        email.render(matrices, mouseX, mouseY, delta);
        password.render(matrices, mouseX, mouseY, delta);
        loginButton.render(matrices, mouseX, mouseY, delta);
        successButton.render(matrices, mouseX, mouseY, delta);
        super.render(matrices, mouseX, mouseY, delta);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        email.mouseClicked(mouseX, mouseY, button);
        password.mouseClicked(mouseX, mouseY, button);
        loginButton.mouseClicked(mouseX, mouseY, button);
        successButton.mouseClicked(mouseX, mouseY, button);
        return false;
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        email.mouseReleased(mouseX, mouseY, button);
        password.mouseReleased(mouseX, mouseY, button);
        loginButton.mouseReleased(mouseX, mouseY, button);
        successButton.mouseReleased(mouseX, mouseY, button);
        return false;
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        email.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
        password.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
        loginButton.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
        successButton.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
        return false;
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double amount) {
        email.mouseScrolled(mouseX, mouseY, amount);
        password.mouseScrolled(mouseX, mouseY, amount);
        loginButton.mouseScrolled(mouseX, mouseY, amount);
        successButton.mouseScrolled(mouseX, mouseY, amount);
        return false;
    }

    @Override
    public boolean keyReleased(int keyCode, int scanCode, int modifiers) {
        email.keyReleased(keyCode, scanCode, modifiers);
        password.keyReleased(keyCode, scanCode, modifiers);
        loginButton.keyReleased(keyCode, scanCode, modifiers);
        successButton.keyReleased(keyCode, scanCode, modifiers);
        return false;
    }

    @Override
    public boolean charTyped(char chr, int modifiers) {
        email.charTyped(chr, modifiers);
        password.charTyped(chr, modifiers);
        loginButton.charTyped(chr, modifiers);
        successButton.charTyped(chr, modifiers);
        return false;
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        email.keyPressed(keyCode, scanCode, modifiers);
        password.keyPressed(keyCode, scanCode, modifiers);
        loginButton.keyPressed(keyCode, scanCode, modifiers);
        successButton.keyPressed(keyCode, scanCode, modifiers);
        return super.keyPressed(keyCode, scanCode, modifiers);
    }
}
