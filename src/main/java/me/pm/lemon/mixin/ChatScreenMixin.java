package me.pm.lemon.mixin;


import me.pm.lemon.command.Command;
import me.pm.lemon.command.CommandManager;
import me.pm.lemon.utils.generalUtils.ColorUtils;
import net.minecraft.client.gui.screen.ChatScreen;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ChatScreen.class)
public class ChatScreenMixin extends Screen {
    @Shadow
    protected TextFieldWidget chatField;

    protected ChatScreenMixin(Text title) {
        super(title);
    }

    @Inject(at = @At("INVOKE"), method = "render")
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta, CallbackInfo callbackInfo) {

        if(chatField.getText().startsWith(Command.PREFIX)) {
            drawHollowRect(matrices, 2, this.height - 14, this.width - 2, this.height - 2, ColorUtils.guiColor());
            for(Command cmd : CommandManager.getCommands()) {
                if(!cmd.isCommandEnabled()) return;
                if(cmd.getAlias().startsWith(chatField.getText().substring(Command.PREFIX.length()))) {
                    chatField.setSuggestion(cmd.getAlias().substring(chatField.getText().length() - Command.PREFIX.length()));
                }
                String [] split = chatField.getText().substring(Command.PREFIX.length()).split(" ");
                if(split[0].equalsIgnoreCase(cmd.getAlias())) {
                    if(cmd.getSuggestedArgs() != null) {
                        for(String suggestion : cmd.getSuggestedArgs()) {
                            if(split.length > 1) {
                                if(suggestion.startsWith(chatField.getText().substring(split[0].length() + Command.PREFIX.length() + 1))) {
                                    chatField.setSuggestion(suggestion.substring(split[1].length()));
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public void drawHollowRect(MatrixStack matrixStack, int x1, int y1, int x2, int y2, int color) {
        this.drawHorizontalLine(matrixStack, x1, x2, y1, color);
        this.drawHorizontalLine(matrixStack, x1, x2, y2, color);

        this.drawVerticalLine(matrixStack, x1, y1, y2, color);
        this.drawVerticalLine(matrixStack, x2, y1, y2, color);
    }
}
