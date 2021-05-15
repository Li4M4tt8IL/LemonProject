package me.pm.lemon.module.modules.combat;

import me.pm.lemon.event.EventTarget;
import me.pm.lemon.event.events.TickEvent;
import me.pm.lemon.gui.clickGui.settings.SettingSlider;
import me.pm.lemon.gui.clickGui.settings.SettingToggle;
import me.pm.lemon.module.Category;
import me.pm.lemon.module.Module;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.slot.SlotActionType;

import java.awt.*;
import java.util.List;


public class AutoTotem extends Module {
    public AutoTotem(String name, Category category, String description, int keyCode, Color color) {
        super(name, category, description, keyCode, color,
                new SettingSlider("Cooldown",0, 10, 3, 1));
    }

    private int timer;

    private int tickCounter = 0;
    private long time = System.currentTimeMillis();

    @EventTarget
    public void onTick(TickEvent event) {
        if (timer > 0) {
            timer--;
            return;
        }
        if(getTime() > getSetting(0).asSlider().getValue()*1000) {
            List<ItemStack> inv;
            assert mc.player != null;
            ItemStack offhand = mc.player.getOffHandStack();
            int inventoryIndex;
            inv = mc.player.inventory.main;
            for (inventoryIndex = 0; inventoryIndex < inv.size(); inventoryIndex++) {
                if (inv.get(inventoryIndex) != ItemStack.EMPTY) { //ItemStack.EMPTY
                    if (offhand.getItem() != Items.TOTEM_OF_UNDYING) { //ItemStack.TOTEM
                        if (inv.get(inventoryIndex).getItem() == Items.TOTEM_OF_UNDYING) { //ItemStack.TOTEM
                            replaceTotem(inventoryIndex);
                            break;
                        }
                    }
                }
                timer = 3;
            }
            time = System.currentTimeMillis();
        }
    }

    public void replaceTotem(int inventoryIndex) {
        mc.interactionManager.clickSlot(mc.player.currentScreenHandler.syncId, inventoryIndex < 9 ? inventoryIndex + 36 : inventoryIndex, 0, SlotActionType.PICKUP, mc.player);
        mc.interactionManager.clickSlot(mc.player.currentScreenHandler.syncId, 45, 0, SlotActionType.PICKUP, mc.player);
        mc.interactionManager.clickSlot(mc.player.currentScreenHandler.syncId, inventoryIndex < 9 ? inventoryIndex + 36 : inventoryIndex, 0, SlotActionType.PICKUP, mc.player);
        mc.player.tick();
    }

    private long getTime() {
        return System.currentTimeMillis() - time;
    }
}
