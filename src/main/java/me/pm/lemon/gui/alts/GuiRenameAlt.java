package me.pm.lemon.gui.alts;

import me.pm.lemon.gui.alts.widgets.PasswordFieldWidget;
import me.pm.lemon.utils.FileManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.AbstractButtonWidget;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

public class GuiRenameAlt extends Screen {
    private final GuiAltManager manager;
    private TextFieldWidget nameField;
    private PasswordFieldWidget pwField;
    private String status = "\2477Waiting...";
    private ButtonWidget edit;
    private MinecraftClient mc = MinecraftClient.getInstance();

    protected GuiRenameAlt(GuiAltManager manager) {
        super(Text.of("Gui Rename Alt"));
        this.manager = manager;
    }

    @Override
    protected void init() {
        edit = new ButtonWidget(width / 2 - 100, height / 4 + 92 + 12, 200, 20, Text.of("Edit"), button -> {
            this.manager.selectedAlt.setMask(this.nameField.getText());
            this.manager.selectedAlt.setPassword(this.pwField.getText());
            this.status = "Edited!";
            FileManager.saveAlts();
        });
        this.addButton(edit);
        this.addButton(new ButtonWidget(width / 2 - 100, height / 4 + 116 + 12, 200, 20, Text.of("Cancel"), button -> {
            mc.openScreen(this.manager);
        }));
        this.nameField = new TextFieldWidget(textRenderer, width / 2 - 100, 60, 200, 20, Text.of("Username"));
        this.pwField = new PasswordFieldWidget(textRenderer, width / 2 - 100, 100, 200, 20, Text.of("Password"));
        super.init();
    }

    private int mouseX;
    private int mouseY;

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.mouseX = mouseX;
        this.mouseY = mouseY;
        super.renderBackground(matrices);
        this.drawCenteredString(matrices, textRenderer, "Edit Alt", width / 2, 10, -1);
        this.drawCenteredString(matrices, textRenderer, this.status, width / 2, 20, -1);
        this.nameField.render(matrices, mouseX, mouseY, delta);
        this.pwField.render(matrices, mouseX, mouseY, delta);
        if (this.nameField.getText().isEmpty()) {
            this.textRenderer.draw(matrices, "New name", width / 2 - 96, 66, -7829368);
        }
        if (this.pwField.getText().isEmpty()) {
            this.textRenderer.draw(matrices, "New password", width / 2 - 96, 106, -7829368);
        }
        super.render(matrices, mouseX, mouseY, delta);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        this.nameField.keyPressed(keyCode, scanCode, modifiers);
        this.pwField.keyPressed(keyCode, scanCode, modifiers);
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean charTyped(char chr, int modifiers) {
        this.nameField.charTyped(chr, modifiers);
        this.pwField.charTyped(chr, modifiers);
        if (chr == '\t' && (this.nameField.isFocused() || this.pwField.isFocused())) {
            this.nameField.setFocusUnlocked(!this.nameField.isFocused());
            this.pwField.setFocusUnlocked(!this.pwField.isFocused());
        }
        if (chr == '\r') {
            edit.onPress();
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

        this.nameField.mouseClicked(mouseX, mouseY, button);
        this.pwField.mouseClicked(mouseX, mouseY, button);
        return false;
    }

}
