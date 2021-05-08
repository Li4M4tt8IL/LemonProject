package me.pm.lemon.module.modules.render;

import me.pm.lemon.event.EventTarget;
import me.pm.lemon.event.events.WorldRenderEvent;
import me.pm.lemon.gui.clickGui.settings.*;
import me.pm.lemon.module.Category;
import me.pm.lemon.module.Module;
import me.pm.lemon.utils.generalUtils.RenderUtils;
import net.minecraft.block.BlockState;
import net.minecraft.block.ChestBlock;
import net.minecraft.block.entity.*;
import net.minecraft.block.enums.ChestType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.entity.decoration.ItemFrameEntity;
import net.minecraft.entity.vehicle.ChestMinecartEntity;
import net.minecraft.entity.vehicle.FurnaceMinecartEntity;
import net.minecraft.entity.vehicle.HopperMinecartEntity;
import net.minecraft.item.Items;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class StorageESP extends Module {
    public StorageESP(String name, Category category, String description, int keyCode, Color color) {
        super(name, category, description, keyCode, color,
                new SettingToggle("Chests", true).withDesc("Show chests").withChildren( /* 0 */
                    new SettingColor("Color", 1F, 0.05F, 1F, false).withDesc("Color for chests")),
                new SettingToggle("Ender Chests", true).withDesc("Show ender chests").withChildren( /* 1 */
                        new SettingColor("Color", 1F, 0.05F, 1F, false).withDesc("Color for enderchests")),
                new SettingToggle("Furnace", true).withDesc("Show furnace").withChildren( /* 2 */
                        new SettingColor("Color", 0.5F, 0.5F, 0.5F, false).withDesc("Color for furnaces")),
                new SettingToggle("Dispenser", true).withDesc("Show dispensers").withChildren( /* 3 */
                        new SettingColor("Color", 0.55F, 0.55F, 0.7F, false).withDesc("Color for dispensers")),
                new SettingToggle("Hopper", true).withDesc("Show hoppers").withChildren( /* 4 */
                        new SettingColor("Color", 0.45F, 0.45F, 0.6F, false).withDesc("Color for hoppers")),
                new SettingToggle("Shulker", true).withDesc("Show shulkers").withChildren( /* 5 */
                        new SettingColor("Color", 0.5F, 0.2F, 1F, false).withDesc("Color for shulkers")),
                new SettingToggle("Brewing Stand", true).withDesc("Show brewing stands").withChildren( /* 6 */
                        new SettingColor("Color", 0.5F, 0.4F, 0.2F, false).withDesc("Color for brewing stands")),
                new SettingToggle("Minecart Chest", true).withDesc("Show minecart chests").withChildren( /* 7 */
                        new SettingColor("Color", 1F, 0.65F, 0.3F, false).withDesc("Color for minecart chests")),
                new SettingToggle("Minecart Furnace", true).withDesc("Show minecart furnaces").withChildren( /* 8 */
                        new SettingColor("Color", 0.5F, 0.5F, 0.5F, false).withDesc("Color for minecart furnaces")),
                new SettingToggle("Minecart Hopper", true).withDesc("Show minecart hoppers").withChildren( /* 9 */
                        new SettingColor("Color", 0.45F, 0.45F, 0.6F, false)),
                new SettingToggle("Item Frame", true).withDesc("Show item frames").withChildren( /* 10 */
                        new SettingColor("Empty", 0.45F, 0.1F, 0.1F, false).withDesc("Color for empty item frames"),
                        new SettingColor("Map", 0.1F, 0.1F, 0.5F, false).withDesc("Color for item frames with maps"),
                        new SettingColor("Other", 0.1F, 0.45F, 0.1F, false).withDesc("Color for item frames with other items")),
                new SettingToggle("Armor stand", true).withDesc("Show armor stands").withChildren( /* 11 */
                        new SettingColor("Color",0.5F, 0.4F, 0.1F, false).withDesc("Color for armor stands")),
                new SettingMode("Mode", "Box", "Outline")); /* 12 */
    }

    @EventTarget
    public void onRender(WorldRenderEvent event) {
        List<BlockPos> linkedChests = new ArrayList<>();

        if(getSetting(12).asMode().mode == 0) {
            this.setDisplayName("Storage ESP \2477Box");
            for(BlockEntity e : mc.world.blockEntities) {
                if(linkedChests.contains(e.getPos())) {
                    continue;
                }

                if((e instanceof ChestBlockEntity || e instanceof BarrelBlockEntity) && getSetting(0).asToggle().state) {
                    BlockPos p = drawChest(e.getPos(), true);
                    if (p != null) linkedChests.add(p);
                } else if(e instanceof EnderChestBlockEntity && getSetting(1).asToggle().state) {
                    float[] col = getSetting(1).asToggle().getChild(0).asColor().getRGBFloat();
                    RenderUtils.drawOutlineBox(new Box(
                            e.getPos().getX() + 0.06, e.getPos().getY(), e.getPos().getZ() + 0.06,
                            e.getPos().getX() + 0.94, e.getPos().getY() + 0.875, e.getPos().getZ() + 0.94), col[0], col[1], col[2], 1F);
                    RenderUtils.drawFilledBox(new Box(
                            e.getPos().getX() + 0.06, e.getPos().getY(), e.getPos().getZ() + 0.06,
                            e.getPos().getX() + 0.94, e.getPos().getY() + 0.875, e.getPos().getZ() + 0.94), col[0], col[1], col[2], 0.7f);
                } else if (e instanceof AbstractFurnaceBlockEntity && getSetting(2).asToggle().state) {
                    float[] col = getSetting(2).asToggle().getChild(0).asColor().getRGBFloat();
                    RenderUtils.drawOutlineBox(e.getPos(), col[0], col[1], col[2], 1F);
                    RenderUtils.drawFilledBox(e.getPos(), col[0], col[1], col[2], 0.7f);
                } else if (e instanceof DispenserBlockEntity && getSetting(3).asToggle().state) {
                    float[] col = getSetting(3).asToggle().getChild(0).asColor().getRGBFloat();
                    RenderUtils.drawOutlineBox(e.getPos(), col[0], col[1], col[2], 1F);
                    RenderUtils.drawFilledBox(e.getPos(), col[0], col[1], col[2], 0.7f);
                } else if (e instanceof HopperBlockEntity && getSetting(4).asToggle().state) {
                    float[] col = getSetting(4).asToggle().getChild(0).asColor().getRGBFloat();
                    RenderUtils.drawOutlineBox(e.getPos(), col[0], col[1], col[2], 1F);
                    RenderUtils.drawFilledBox(e.getPos(), col[0], col[1], col[2], 0.7f);
                } else if (e instanceof ShulkerBoxBlockEntity && getSetting(5).asToggle().state) {
                    float[] col = getSetting(5).asToggle().getChild(0).asColor().getRGBFloat();
                    RenderUtils.drawOutlineBox(e.getPos(), col[0], col[1], col[2], 1F);
                    RenderUtils.drawFilledBox(e.getPos(), col[0], col[1], col[2], 0.7f);
                } else if (e instanceof BrewingStandBlockEntity && getSetting(6).asToggle().state) {
                    float[] col = getSetting(6).asToggle().getChild(0).asColor().getRGBFloat();
                    RenderUtils.drawOutlineBox(e.getPos(), col[0], col[1], col[2], 1F);
                    RenderUtils.drawFilledBox(e.getPos(), col[0], col[1], col[2], 0.7f);
                }
            }

            for (Entity e : mc.world.getEntities()) {
                if (e instanceof ChestMinecartEntity && getSetting(7).asToggle().state) {
                    float[] col = getSetting(7).asToggle().getChild(0).asColor().getRGBFloat();
                    RenderUtils.drawOutlineBox(e.getBoundingBox(), col[0], col[1], col[2], 1F);
                    RenderUtils.drawFilledBox(e.getBoundingBox(), col[0], col[1], col[2], 0.7f);
                } else if (e instanceof FurnaceMinecartEntity && getSetting(8).asToggle().state) {
                    float[] col = getSetting(8).asToggle().getChild(0).asColor().getRGBFloat();
                    RenderUtils.drawOutlineBox(e.getBoundingBox(), col[0], col[1], col[2], 1F);
                    RenderUtils.drawFilledBox(e.getBoundingBox(), col[0], col[1], col[2], 0.7f);
                } else if (e instanceof HopperMinecartEntity && getSetting(9).asToggle().state) {
                    float[] col = getSetting(9).asToggle().getChild(0).asColor().getRGBFloat();
                    RenderUtils.drawOutlineBox(e.getBoundingBox(), col[0], col[1], col[2], 1F);
                    RenderUtils.drawFilledBox(e.getBoundingBox(), col[0], col[1], col[2], 0.7f);
                } else if (e instanceof ItemFrameEntity && getSetting(10).asToggle().state) {
                    if (((ItemFrameEntity) e).getHeldItemStack().getItem() == Items.AIR) {
                        float[] col = getSetting(10).asToggle().getChild(0).asColor().getRGBFloat();
                        RenderUtils.drawOutlineBox(e.getBoundingBox(), col[0], col[1], col[2], 1F);
                        RenderUtils.drawFilledBox(e.getBoundingBox(), col[0], col[1], col[2], 0.7f);
                    } else if (((ItemFrameEntity) e).getHeldItemStack().getItem() == Items.FILLED_MAP) {
                        int axis = e.getBoundingBox().maxX - e.getBoundingBox().minX < e.getBoundingBox().maxY - e.getBoundingBox().minY
                                ? 0 : e.getBoundingBox().maxY - e.getBoundingBox().minY < e.getBoundingBox().maxZ - e.getBoundingBox().minZ
                                ? 1 : 2;
                        float[] col = getSetting(10).asToggle().getChild(1).asColor().getRGBFloat();
                        RenderUtils.drawOutlineBox(e.getBoundingBox().expand(axis == 0 ? 0 : 0.12, axis == 1 ? 0 : 0.12, axis == 2 ? 0 : 0.12), col[0], col[1], col[2], 1F);
                        RenderUtils.drawFilledBox(e.getBoundingBox().expand(axis == 0 ? 0 : 0.12, axis == 1 ? 0 : 0.12, axis == 2 ? 0 : 0.12), col[0], col[1], col[2], 0.7f);
                    } else {
                        float[] col = getSetting(10).asToggle().getChild(2).asColor().getRGBFloat();
                        RenderUtils.drawOutlineBox(e.getBoundingBox(), col[0], col[1], col[2], 1F);
                        RenderUtils.drawFilledBox(e.getBoundingBox(), col[0], col[1], col[2], 0.7f);
                    }
                }

                if (e instanceof ArmorStandEntity && getSetting(11).asToggle().state) {
                    float[] col = getSetting(11).asToggle().getChild(0).asColor().getRGBFloat();
                    RenderUtils.drawOutlineBox(e.getBoundingBox(), col[0], col[1], col[2], 1F);
                    RenderUtils.drawFilledBox(e.getBoundingBox(), col[0], col[1], col[2], 0.7f);
                }
            }
        } else if(getSetting(12).asMode().mode == 1) {
            this.setDisplayName("Storage ESP \2477Outline");
            for(BlockEntity e : mc.world.blockEntities) {
                if(linkedChests.contains(e.getPos())) {
                    continue;
                }

                if((e instanceof ChestBlockEntity || e instanceof BarrelBlockEntity) && getSetting(0).asToggle().state) {
                    BlockPos p = drawChest(e.getPos(), false);
                    if (p != null) linkedChests.add(p);
                } else if(e instanceof EnderChestBlockEntity && getSetting(1).asToggle().state) {
                    float[] col = getSetting(1).asToggle().getChild(0).asColor().getRGBFloat();
                    RenderUtils.drawOutlineBox(new Box(
                            e.getPos().getX() + 0.06, e.getPos().getY(), e.getPos().getZ() + 0.06,
                            e.getPos().getX() + 0.94, e.getPos().getY() + 0.875, e.getPos().getZ() + 0.94), col[0], col[1], col[2], 1F);
                } else if (e instanceof AbstractFurnaceBlockEntity && getSetting(2).asToggle().state) {
                    float[] col = getSetting(2).asToggle().getChild(0).asColor().getRGBFloat();
                    RenderUtils.drawOutlineBox(e.getPos(), col[0], col[1], col[2], 1F);
                } else if (e instanceof DispenserBlockEntity && getSetting(3).asToggle().state) {
                    float[] col = getSetting(3).asToggle().getChild(0).asColor().getRGBFloat();
                    RenderUtils.drawOutlineBox(e.getPos(), col[0], col[1], col[2], 1F);
                } else if (e instanceof HopperBlockEntity && getSetting(4).asToggle().state) {
                    float[] col = getSetting(4).asToggle().getChild(0).asColor().getRGBFloat();
                    RenderUtils.drawOutlineBox(e.getPos(), col[0], col[1], col[2], 1F);
                } else if (e instanceof ShulkerBoxBlockEntity && getSetting(5).asToggle().state) {
                    float[] col = getSetting(5).asToggle().getChild(0).asColor().getRGBFloat();
                    RenderUtils.drawOutlineBox(e.getPos(), col[0], col[1], col[2], 1F);
                } else if (e instanceof BrewingStandBlockEntity && getSetting(6).asToggle().state) {
                    float[] col = getSetting(6).asToggle().getChild(0).asColor().getRGBFloat();
                    RenderUtils.drawOutlineBox(e.getPos(), col[0], col[1], col[2], 1F);
                }
            }

            for (Entity e : mc.world.getEntities()) {
                if (e instanceof ChestMinecartEntity && getSetting(7).asToggle().state) {
                    float[] col = getSetting(7).asToggle().getChild(0).asColor().getRGBFloat();
                    RenderUtils.drawOutlineBox(e.getBoundingBox(), col[0], col[1], col[2], 1F);
                } else if (e instanceof FurnaceMinecartEntity && getSetting(8).asToggle().state) {
                    float[] col = getSetting(8).asToggle().getChild(0).asColor().getRGBFloat();
                    RenderUtils.drawOutlineBox(e.getBoundingBox(), col[0], col[1], col[2], 1F);
                } else if (e instanceof HopperMinecartEntity && getSetting(9).asToggle().state) {
                    float[] col = getSetting(9).asToggle().getChild(0).asColor().getRGBFloat();
                    RenderUtils.drawOutlineBox(e.getBoundingBox(), col[0], col[1], col[2], 1F);
                } else if (e instanceof ItemFrameEntity && getSetting(10).asToggle().state) {
                    if (((ItemFrameEntity) e).getHeldItemStack().getItem() == Items.AIR) {
                        float[] col = getSetting(10).asToggle().getChild(0).asColor().getRGBFloat();
                        RenderUtils.drawOutlineBox(e.getBoundingBox(), col[0], col[1], col[2], 1F);
                    } else if (((ItemFrameEntity) e).getHeldItemStack().getItem() == Items.FILLED_MAP) {
                        int axis = e.getBoundingBox().maxX - e.getBoundingBox().minX < e.getBoundingBox().maxY - e.getBoundingBox().minY
                                ? 0 : e.getBoundingBox().maxY - e.getBoundingBox().minY < e.getBoundingBox().maxZ - e.getBoundingBox().minZ
                                ? 1 : 2;
                        float[] col = getSetting(10).asToggle().getChild(1).asColor().getRGBFloat();
                        RenderUtils.drawOutlineBox(e.getBoundingBox().expand(axis == 0 ? 0 : 0.12, axis == 1 ? 0 : 0.12, axis == 2 ? 0 : 0.12), col[0], col[1], col[2], 1F);
                    } else {
                        float[] col = getSetting(10).asToggle().getChild(2).asColor().getRGBFloat();
                        RenderUtils.drawOutlineBox(e.getBoundingBox(), col[0], col[1], col[2], 1F);
                    }
                }

                if (e instanceof ArmorStandEntity && getSetting(11).asToggle().state) {
                    float[] col = getSetting(11).asToggle().getChild(0).asColor().getRGBFloat();
                    RenderUtils.drawOutlineBox(e.getBoundingBox(), col[0], col[1], col[2], 1F);
                }
            }
        }

    }

    private BlockPos drawChest(BlockPos pos, boolean filled) {
        BlockState state = mc.world.getBlockState(pos);
        float[] col = getSetting(0).asToggle().getChild(0).asColor().getRGBFloat();
        if (!(state.getBlock() instanceof ChestBlock)) {
            RenderUtils.drawOutlineBox(pos,col[0], col[1], col[2], 1f);
            if(filled) {
                RenderUtils.drawFilledBox(pos, col[0], col[1], col[2], 0.7f);
            }
            return null;
        }

        if (state.get(ChestBlock.CHEST_TYPE) == ChestType.SINGLE) {
            RenderUtils.drawOutlineBox(new Box(
                    pos.getX() + 0.06, pos.getY(), pos.getZ() + 0.06,
                    pos.getX() + 0.94, pos.getY() + 0.875, pos.getZ() + 0.94), col[0], col[1], col[2], 1f);
            if(filled) {
                RenderUtils.drawFilledBox(new Box(
                        pos.getX() + 0.06, pos.getY(), pos.getZ() + 0.06,
                        pos.getX() + 0.94, pos.getY() + 0.875, pos.getZ() + 0.94), col[0], col[1], col[2], 0.7f);
            }
            return null;
        }

        boolean north = false, east = false, south = false, west = false;

        Direction dir = ChestBlock.getFacing(state);
        if (dir == Direction.NORTH) {
            north = true;
        } else if (dir == Direction.EAST) {
            east = true;
        } else if (dir == Direction.SOUTH) {
            south = true;
        } else if (dir == Direction.WEST) {
            west = true;
        }

        RenderUtils.drawOutlineBox(new Box(
                west ? pos.getX() - 0.94 : pos.getX() + 0.06,
                pos.getY(),
                north ? pos.getZ() - 0.94 : pos.getZ() + 0.06,
                east ? pos.getX() + 1.94 : pos.getX() + 0.94,
                pos.getY() + 0.875,
                south ? pos.getZ() + 1.94 : pos.getZ() + 0.94), col[0], col[1], col[2], 1f);
        if(filled) {
            RenderUtils.drawFilledBox(new Box(
                            west ? pos.getX() - 0.94 : pos.getX() + 0.06,
                            pos.getY(),
                            north ? pos.getZ() - 0.94 : pos.getZ() + 0.06,
                            east ? pos.getX() + 1.94 : pos.getX() + 0.94,
                            pos.getY() + 0.875,
                            south ? pos.getZ() + 1.94 : pos.getZ() + 0.94),
                    col[0], col[1], col[2], 0.7f);
        }

        return north ? pos.north() : east ? pos.east() : south ? pos.south() : west ? pos.west() : null;
    }

}
