package me.pm.lemon.gui.alts;

import me.pm.lemon.mixin.IMinecraftClient;
import me.pm.lemon.utils.generalUtils.RenderUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.AbstractButtonWidget;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import org.lwjgl.opengl.GL11;


public class GuiAltManager extends Screen {
    private Screen prevScreen;

    public GuiAltManager(Screen screen) {
        super(Text.of("Gui Alt Manager"));
        prevScreen = screen;
    }

    private ButtonWidget login;
    private ButtonWidget remove;
    private ButtonWidget rename;
    private AltLoginThread loginThread;
    private int offset;
    public Alt selectedAlt = null;
    private String status = "\2477No alts selected";
    private MinecraftClient mc = MinecraftClient.getInstance();

    public int keyDown = -1;
    public int scroll = 0;
    public boolean lmDown = false;

    @Override
    protected void init() {
        this.addButton(new ButtonWidget(width / 2 + 4 + 50, height - 24, 100, 20, Text.of("Cancel"), (button -> {
            if (this.loginThread == null) {
                this.mc.openScreen(prevScreen);
                return;
            }
            if (!this.loginThread.getStatus().equals("\247eAttempting to log in") && !this.loginThread.getStatus().equals("\2474Do not hit back!" + " \247eLogging in...")) {
                this.mc.openScreen(prevScreen);
                return;
            }
            this.loginThread.setStatus("\2474Failed to login! Please try again!" + " \247eLogging in...");
            return;
        })));
        this.login = new ButtonWidget(width / 2 - 154, height - 48, 100, 20, Text.of("Login"), button -> {
            String user = this.selectedAlt.getUsername();
            String pass = this.selectedAlt.getPassword();
            this.loginThread = new AltLoginThread(user, pass);
            this.loginThread.start();
        });
        this.addButton(login);
        this.remove = new ButtonWidget(width / 2 - 154, height - 24, 100, 20, Text.of("Remove"), button -> {
            if (this.loginThread != null) {
                this.loginThread = null;
            }
            AltManager.registry.remove(this.selectedAlt);
            this.status = "\u00a7aRemoved.";
            this.selectedAlt = null;
        });
        this.addButton(remove);
        this.addButton(new ButtonWidget(width / 2 + 4 + 50, height - 48, 100, 20, Text.of("Add"), button -> {
            this.mc.openScreen(new GuiAddAlt(this));
        }));
        this.addButton(new ButtonWidget(width / 2 - 50, height - 48, 100, 20, Text.of("Direct Login"), button -> {
            this.mc.openScreen(new GuiAltLogin(this));
        }));
        this.rename = new ButtonWidget(width / 2 - 50, height - 24, 100, 20, Text.of("Edit"), button -> {
            this.mc.openScreen(new GuiRenameAlt(this));
        });
        this.addButton(rename);
        this.login.active = false;
        this.remove.active = false;
        this.rename.active = false;
    }

    private int mouseX;
    private int mouseY;

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.mouseX = mouseX;
        this.mouseY = mouseY;
        super.renderBackground(matrices);
        int wheel = scroll;
        if (wheel < 0) {
            this.offset += 26;
            if (this.offset < 0) {
                this.offset = 0;
            }
        } else if (wheel > 0) {
            this.offset -= 26;
            if (this.offset < 0) {
                this.offset = 0;
            }
        }

        mc.textRenderer.draw(matrices, ((IMinecraftClient) this.mc).getSession().getUsername(), 10, 10, -7829368);
        StringBuilder sb2 = new StringBuilder("Account Manager - ");

        this.drawCenteredString(matrices, mc.textRenderer, sb2.append(AltManager.registry.size()).append(" alts").toString(), width / 2, 10, -1);
        this.drawCenteredString(matrices, mc.textRenderer, this.loginThread == null ? this.status : this.loginThread.getStatus(), width / 2, 20, -1);
        RenderUtils.drawBorderedRect(50.0f, 33.0f, width - 50, height - 50, 1.0f, -16777216, Integer.MIN_VALUE);
        GL11.glPushMatrix();
        this.prepareScissorBox(0.0f, 33.0f, width, height - 50);
        GL11.glEnable(3089);
        int y2 = 38;
        for (Alt alt2 : AltManager.registry) {
            if (!this.isAltInArea(y2)) continue;
            String name = alt2.getMask().equals("") ? alt2.getUsername() : alt2.getMask();
            String pass = alt2.getPassword().equals("cracked") ? "\u00a7cCracked" : alt2.getPassword().replaceAll(".", "*");
            if (alt2 == this.selectedAlt) {
                if (this.isMouseOverAlt(mouseX, mouseY, y2 - this.offset) && lmDown) {
                    RenderUtils.drawBorderedRect(52.0f, y2 - this.offset - 4, width - 52, y2 - this.offset + 20, 1.0f, -16777216, -2142943931);
                } else if (this.isMouseOverAlt(mouseX, mouseY, y2 - this.offset)) {
                    RenderUtils.drawBorderedRect(52.0f, y2 - this.offset - 4, width - 52, y2 - this.offset + 20, 1.0f, -16777216, -2142088622);
                } else {
                    RenderUtils.drawBorderedRect(52.0f, y2 - this.offset - 4, width - 52, y2 - this.offset + 20, 1.0f, -16777216, -2144259791);
                }
            } else if (this.isMouseOverAlt(mouseX, mouseY, y2 - this.offset) && lmDown) {
                RenderUtils.drawBorderedRect(52.0f, y2 - this.offset - 4, width - 52, y2 - this.offset + 20, 1.0f, -16777216, -2146101995);
            } else if (this.isMouseOverAlt(mouseX, mouseY, y2 - this.offset)) {
                RenderUtils.drawBorderedRect(52.0f, y2 - this.offset - 4, width - 52, y2 - this.offset + 20, 1.0f, -16777216, -2145180893);
            }
            this.drawCenteredString(matrices, mc.textRenderer, name, width / 2, y2 - this.offset, -1);
            this.drawCenteredString(matrices, mc.textRenderer, pass, width / 2, y2 - this.offset + 10, 5592405);
            y2 += 26;
        }
        GL11.glDisable(3089);
        GL11.glPopMatrix();
        if (this.selectedAlt == null) {
            this.login.active = false;
            this.remove.active = false;
            this.rename.active = false;
        } else {
            this.login.active = true;
            this.remove.active = true;
            this.rename.active = true;
        }
        if(keyDown == 200) {
            this.offset -= 26;
            if(this.offset < 0) {
                this.offset = 0;
            }
        } else if(keyDown == 208) {
            this.offset += 26;
            if (this.offset < 0) {
                this.offset = 0;
            }
        }
        super.render(matrices, mouseX, mouseY, delta);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        keyDown = keyCode;
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double amount) {
        scroll = (int) amount;
        return false;
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        if(button == 0) {
            lmDown = false;
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

        if(button == 0) {
            lmDown = true;
        }

        if (this.offset < 0) {
            this.offset = 0;
        }
        int y2 = 38 - this.offset;
        for (Alt alt2 : AltManager.registry) {
            if (this.isMouseOverAlt((int)mouseX, (int) mouseY, y2)) {
                if (alt2 == this.selectedAlt) {
                    this.login.onPress();
                    return false;
                }
                this.selectedAlt = alt2;
            }
            y2 += 26;
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    private boolean isAltInArea(int y2) {
        if (y2 - this.offset <= height - 50) {
            return true;
        }
        return false;
    }

    private boolean isMouseOverAlt(int x2, int y2, int y1) {
        if (x2 >= 52 && y2 >= y1 - 4 && x2 <= width - 52 && y2 <= y1 + 20 && x2 >= 0 && y2 >= 33 && x2 <= width && y2 <= height - 50) {
            return true;
        }
        return false;
    }

    public void prepareScissorBox(float x2, float y2, float x22, float y22) {
        int scaledHeight = mc.getWindow().getScaledHeight();
        int scaledWidth = mc.getWindow().getScaledWidth();
        int factor = (int) mc.getWindow().getScaleFactor();
        GL11.glScissor((int)(x2 * (float)factor), (int)(((float)scaledHeight - y22) * (float)factor), (int)((x22 - x2) * (float)factor), (int)((y22 - y2) * (float)factor));
    }
}
