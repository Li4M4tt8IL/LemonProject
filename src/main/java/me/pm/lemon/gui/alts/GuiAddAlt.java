package me.pm.lemon.gui.alts;

import com.mojang.authlib.Agent;
import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication;
import me.pm.lemon.gui.alts.widgets.PasswordFieldWidget;
import me.pm.lemon.utils.FileManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.AbstractButtonWidget;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

import java.io.IOException;
import java.net.Proxy;

public class GuiAddAlt extends Screen {
    private final GuiAltManager manager;
    private PasswordFieldWidget password;
    private TextFieldWidget username;
    private ButtonWidget add;
    private String status = "\2477Idle...";
    private MinecraftClient mc = MinecraftClient.getInstance();

    protected GuiAddAlt(GuiAltManager manager) {
        super(Text.of("Gui Add Alt"));
        this.manager = manager;
    }

    @Override
    protected void init() {
        this.buttons.clear();
        add = new ButtonWidget(width / 2 - 100, height / 4 + 92 + 12, 200, 20, Text.of("Add"), button -> {
            AddAltThread login = new AddAltThread(this.username.getText(), this.password.getText());
            login.start();
        });
        this.addButton(add);
        this.addButton(new ButtonWidget(width / 2 - 100, height / 4 + 116 + 12, 200, 20, Text.of("Back"), button -> {
            this.mc.openScreen(this.manager);
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
        this.drawCenteredString(matrices, textRenderer, this.status, width / 2, 30, -1);
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
            add.onPress();
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
                    abstractButtonWidget.x+abstractButtonWidget.getWidth(), abstractButtonWidget.y+abstractButtonWidget.getHeight()))
                abstractButtonWidget.onClick(mouseX, mouseY);
        }

        this.username.mouseClicked(mouseX, mouseY, button);
        this.password.mouseClicked(mouseX, mouseY, button);
        return false;
    }

    static void access$0(GuiAddAlt guiAddAlt, String status) {
        guiAddAlt.status = status;
    }

    private class AddAltThread
            extends Thread {
        private final String password;
        private final String username;

        public AddAltThread(String username, String password) {
            this.username = username;
            this.password = password;
            GuiAddAlt.access$0(GuiAddAlt.this, "\2477Idle...");
        }

        private final void checkAndAddAlt(String username, String password) throws IOException {
            YggdrasilAuthenticationService service = new YggdrasilAuthenticationService(Proxy.NO_PROXY, "");
            YggdrasilUserAuthentication auth = (YggdrasilUserAuthentication)service.createUserAuthentication(Agent.MINECRAFT);
            auth.setUsername(username);
            auth.setPassword(password);
            try {
                auth.logIn();
                AltManager.registry.add(new Alt(username, password, auth.getSelectedProfile().getName()));
//                FileManager.saveAlts();
                GuiAddAlt.access$0(GuiAddAlt.this, "Alt added. (" + username + ")");

            }
            catch (AuthenticationException e) {
                GuiAddAlt.access$0(GuiAddAlt.this, "\2474Alt failed!");
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            if (this.password.equals("")) {
                AltManager.registry.add(new Alt(this.username, ""));
                GuiAddAlt.access$0(GuiAddAlt.this, "\247aAlt added. (" + this.username + " - offline name)");
                FileManager.saveAlts();
                return;
            }
            GuiAddAlt.access$0(GuiAddAlt.this, "\247eTrying alt...");
            try {
                this.checkAndAddAlt(this.username, this.password);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
