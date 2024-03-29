package me.pm.lemon.gui.alts.widgets;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.Drawable;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.Element;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.client.sound.SoundManager;
import net.minecraft.client.util.NarratorManager;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;
import java.util.Objects;

public class AbstractPasswordFieldWidget extends DrawableHelper implements Drawable, Element {
    public static final Identifier WIDGETS_LOCATION = new Identifier("textures/gui/widgets.png");
    protected int width;
    protected int height;
    public int x;
    public int y;
    private Text message;
    private boolean wasHovered;
    protected boolean hovered;
    public boolean active = true;
    public boolean visible = true;
    protected float alpha = 1.0F;
    protected long nextNarration = 9223372036854775807L;
    private boolean focused;
    public AbstractPasswordFieldWidget(int x, int y, int width, int height, Text message) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.message = message;
    }
    public int getHeight() {
        return this.height;
    }
    protected int getYImage(boolean hovered) {
        int i = 1;
        if (!this.active) {
            i = 0;
        } else if (hovered) {
            i = 2;
        }
        return i;
    }
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        if (this.visible) {
            this.hovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
            if (this.wasHovered != this.isHovered()) {
                if (this.isHovered()) {
                    if (this.focused) {
                        this.queueNarration(200);
                    } else {
                        this.queueNarration(750);
                    }
                } else {
                    this.nextNarration = 9223372036854775807L;
                }
            }
            if (this.visible) {
                this.renderButton(matrices, mouseX, mouseY, delta);
            }
            this.narrate();
            this.wasHovered = this.isHovered();
        }
    }
    protected void narrate() {
        if (this.active && this.isHovered() && Util.getMeasuringTimeMs() > this.nextNarration) {
            String string = this.getNarrationMessage().getString();
            if (!string.isEmpty()) {
                NarratorManager.INSTANCE.narrate(string);
                this.nextNarration = 9223372036854775807L;
            }
        }
    }
    protected MutableText getNarrationMessage() {
        return new TranslatableText("gui.narrate.button", new Object[]{this.getMessage()});
    }
    public void renderButton(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        MinecraftClient minecraftClient = MinecraftClient.getInstance();
        TextRenderer textRenderer = minecraftClient.textRenderer;
        minecraftClient.getTextureManager().bindTexture(WIDGETS_LOCATION);
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, this.alpha);
        int i = this.getYImage(this.isHovered());
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.enableDepthTest();
        this.drawTexture(matrices, this.x, this.y, 0, 46 + i * 20, this.width / 2, this.height);
        this.drawTexture(matrices, this.x + this.width / 2, this.y, 200 - this.width / 2, 46 + i * 20, this.width / 2, this.height);
        this.renderBg(matrices, minecraftClient, mouseX, mouseY);
        int j = this.active ? 16777215 : 10526880;
        drawCenteredText(matrices, textRenderer, Text.of(allStar(getMessage().getString())), this.x + this.width / 2, this.y + (this.height - 8) / 2, j | MathHelper.ceil(this.alpha * 255.0F) << 24);
    }

    public String allStar(String s) {
        StringBuilder sb = new StringBuilder(s.length());
        for (int i = 0; i < s.length(); i++) {
            sb.append('*');
        }
        return sb.toString();
    }

    protected void renderBg(MatrixStack matrices, MinecraftClient client, int mouseX, int mouseY) {
    }
    public void onClick(double mouseX, double mouseY) {
    }
    public void onRelease(double mouseX, double mouseY) {
    }
    protected void onDrag(double mouseX, double mouseY, double deltaX, double deltaY) {
    }
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (this.active && this.visible) {
            if (this.isValidClickButton(button)) {
                boolean bl = this.clicked(mouseX, mouseY);
                if (bl) {
                    this.playDownSound(MinecraftClient.getInstance().getSoundManager());
                    this.onClick(mouseX, mouseY);
                    return true;
                }
            }
            return false;
        } else {
            return false;
        }
    }
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        if (this.isValidClickButton(button)) {
            this.onRelease(mouseX, mouseY);
            return true;
        } else {
            return false;
        }
    }
    protected boolean isValidClickButton(int button) {
        return button == 0;
    }
    public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        if (this.isValidClickButton(button)) {
            this.onDrag(mouseX, mouseY, deltaX, deltaY);
            return true;
        } else {
            return false;
        }
    }
    protected boolean clicked(double mouseX, double mouseY) {
        return this.active && this.visible && mouseX >= (double)this.x && mouseY >= (double)this.y && mouseX < (double)(this.x + this.width) && mouseY < (double)(this.y + this.height);
    }
    public boolean isHovered() {
        return this.hovered || this.focused;
    }
    public boolean changeFocus(boolean lookForwards) {
        if (this.active && this.visible) {
            this.focused = !this.focused;
            this.onFocusedChanged(this.focused);
            return this.focused;
        } else {
            return false;
        }
    }
    protected void onFocusedChanged(boolean bl) {
    }
    public boolean isMouseOver(double mouseX, double mouseY) {
        return this.active && this.visible && mouseX >= (double)this.x && mouseY >= (double)this.y && mouseX < (double)(this.x + this.width) && mouseY < (double)(this.y + this.height);
    }
    public void renderToolTip(MatrixStack matrices, int mouseX, int mouseY) {
    }
    public void playDownSound(SoundManager soundManager) {
        soundManager.play(PositionedSoundInstance.master(SoundEvents.UI_BUTTON_CLICK, 1.0F));
    }
    public int getWidth() {
        return this.width;
    }
    public void setWidth(int value) {
        this.width = value;
    }
    public void setAlpha(float value) {
        this.alpha = value;
    }
    public void setMessage(Text text) {
        if (!Objects.equals(text.getString(), this.message.getString())) {
            this.queueNarration(250);
        }
        this.message = text;
    }
    public void queueNarration(int delay) {
        this.nextNarration = Util.getMeasuringTimeMs() + (long)delay;
    }
    public Text getMessage() {
        return this.message;
    }
    public boolean isFocused() {
        return this.focused;
    }
    protected void setFocused(boolean focused) {
            this.focused = focused;
        }
}
