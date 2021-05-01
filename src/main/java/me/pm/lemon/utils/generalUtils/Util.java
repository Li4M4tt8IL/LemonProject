package me.pm.lemon.utils.generalUtils;

import me.pm.lemon.gui.testScreen.settings.SettingRotate;
import me.pm.lemon.module.Module;
import me.pm.lemon.module.modules.combat.CrystalAura;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.DamageUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket;
import net.minecraft.network.packet.c2s.play.HandSwingC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.*;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.Difficulty;
import net.minecraft.world.explosion.Explosion;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

public class Util {
    public static final List<Block> NONSOLID_BLOCKS = Arrays.asList(
            Blocks.AIR, Blocks.LAVA, Blocks.WATER, Blocks.GRASS,
            Blocks.VINE, Blocks.SEAGRASS, Blocks.TALL_SEAGRASS,
            Blocks.SNOW, Blocks.TALL_GRASS, Blocks.FIRE, Blocks.VOID_AIR);

    public static final List<Block> RIGHTCLICKABLE_BLOCKS = Arrays.asList(
            Blocks.CHEST, Blocks.TRAPPED_CHEST, Blocks.ENDER_CHEST,
            Blocks.WHITE_SHULKER_BOX, Blocks.ORANGE_SHULKER_BOX, Blocks.MAGENTA_SHULKER_BOX,
            Blocks.LIGHT_BLUE_SHULKER_BOX, Blocks.YELLOW_SHULKER_BOX, Blocks.LIME_SHULKER_BOX,
            Blocks.PINK_SHULKER_BOX, Blocks.GRAY_SHULKER_BOX, Blocks.LIGHT_GRAY_SHULKER_BOX,
            Blocks.CYAN_SHULKER_BOX, Blocks.PURPLE_SHULKER_BOX, Blocks.BLUE_SHULKER_BOX,
            Blocks.BROWN_SHULKER_BOX, Blocks.GREEN_SHULKER_BOX, Blocks.RED_SHULKER_BOX,
            Blocks.BLACK_SHULKER_BOX, Blocks.ANVIL,
            Blocks.OAK_BUTTON, Blocks.ACACIA_BUTTON, Blocks.BIRCH_BUTTON, Blocks.DARK_OAK_BUTTON,
            Blocks.JUNGLE_BUTTON, Blocks.SPRUCE_BUTTON, Blocks.STONE_BUTTON, Blocks.COMPARATOR,
            Blocks.REPEATER, Blocks.OAK_FENCE_GATE, Blocks.SPRUCE_FENCE_GATE, Blocks.BIRCH_FENCE_GATE,
            Blocks.JUNGLE_FENCE_GATE, Blocks.DARK_OAK_FENCE_GATE, Blocks.ACACIA_FENCE_GATE,
            Blocks.BREWING_STAND, Blocks.DISPENSER, Blocks.DROPPER,
            Blocks.LEVER, Blocks.NOTE_BLOCK, Blocks.JUKEBOX,
            Blocks.BEACON, Blocks.BLACK_BED, Blocks.BLUE_BED, Blocks.BROWN_BED, Blocks.CYAN_BED, Blocks.GRAY_BED,
            Blocks.GREEN_BED, Blocks.LIGHT_BLUE_BED, Blocks.LIGHT_GRAY_BED, Blocks.LIME_BED, Blocks.MAGENTA_BED,
            Blocks.ORANGE_BED, Blocks.PINK_BED, Blocks.PURPLE_BED, Blocks.RED_BED, Blocks.WHITE_BED,
            Blocks.YELLOW_BED, Blocks.FURNACE, Blocks.OAK_DOOR, Blocks.SPRUCE_DOOR,
            Blocks.BIRCH_DOOR, Blocks.JUNGLE_DOOR, Blocks.ACACIA_DOOR,
            Blocks.DARK_OAK_DOOR, Blocks.CAKE, Blocks.ENCHANTING_TABLE,
            Blocks.DRAGON_EGG, Blocks.HOPPER, Blocks.REPEATING_COMMAND_BLOCK,
            Blocks.COMMAND_BLOCK, Blocks.CHAIN_COMMAND_BLOCK, Blocks.CRAFTING_TABLE,
            Blocks.ACACIA_TRAPDOOR, Blocks.BIRCH_TRAPDOOR, Blocks.DARK_OAK_TRAPDOOR, Blocks.JUNGLE_TRAPDOOR,
            Blocks.OAK_TRAPDOOR, Blocks.SPRUCE_TRAPDOOR, Blocks.CAKE, Blocks.ACACIA_SIGN, Blocks.ACACIA_WALL_SIGN,
            Blocks.BIRCH_SIGN, Blocks.BIRCH_WALL_SIGN, Blocks.DARK_OAK_SIGN, Blocks.DARK_OAK_WALL_SIGN,
            Blocks.JUNGLE_SIGN, Blocks.JUNGLE_WALL_SIGN, Blocks.OAK_SIGN, Blocks.OAK_WALL_SIGN,
            Blocks.SPRUCE_SIGN, Blocks.SPRUCE_WALL_SIGN);

    private static MinecraftClient mc = MinecraftClient.getInstance();

    public static synchronized void faceEntity(Entity entity) {
        final float[] rotations = getRotationsNeeded(entity);

        if (rotations != null) {
            assert mc.player != null;
            mc.player.yaw = rotations[0];
            mc.player.pitch = rotations[1];// 14
        }
    }

    public static float[] getRotationsNeeded(Entity entity) {
        if (entity == null) {
            return null;
        }

        final double diffX = entity.getX() - mc.player.getX();
        final double diffZ = entity.getZ() - mc.player.getZ();
        double diffY;

//        if (entity instanceof PlayerEntity) {
//            final PlayerEntity entityLivingBase = (PlayerEntity) entity;
//            diffY = entity.getY() + entity.getEyeHeight(mc.player.getPose()) - (mc.player.getX() + mc.player.getEyeHeight(mc.player.getPose()));
//        } else if(entity instanceof MobEntity){
//            final MobEntity entityLivingBase = (MobEntity) entity;
//            diffY = (entity.getBoundingBox().minY + entity.getBoundingBox().maxY) / 2.0D - (mc.player.getY() + mc.player.getEyeHeight(mc.player.getPose()));
//        }else{
//            final Entity entityLivingBase = (LivingEntity) entity;
//            diffY = (entity.getBoundingBox().minY + entity.getBoundingBox().maxY) / 2.0D - (mc.player.getY() + mc.player.getEyeHeight(mc.player.getPose()));
//        }

        diffY = (entity.getBoundingBox().minY + entity.getBoundingBox().maxY) / 2.0D - (mc.player.getY() + mc.player.getEyeHeight(mc.player.getPose()));

        final double dist = MathHelper.sqrt(diffX * diffX + diffZ * diffZ);
        final float yaw = (float) (Math.atan2(diffZ, diffX) * 180.0D / Math.PI) - 90.0F;
        final float pitch = (float) -(Math.atan2(diffY, dist) * 180.0D / Math.PI);
        return new float[] {
                mc.player.yaw + MathHelper.wrapDegrees(yaw - mc.player.yaw), mc.player.pitch + MathHelper.wrapDegrees(pitch - mc.player.pitch)
        };
    }

    public static String capitalizeFirstLetter(String string) {
        return string.substring(0, 1).toUpperCase() + string.substring(1, string.length());
    }

    public static int getRainbow(float sat, float bri, double speed, int offset) {
        double rainbowState = Math.ceil((System.currentTimeMillis() + offset) / speed);
        rainbowState %= 360.0;
        return Color.HSBtoRGB((float) (rainbowState / 360.0), sat, bri);
    }

    public static float[] intToFloat(int color) {
        float red = (float) (color >> 16 & 255) / 255.0F;
        float green = (float) (color >> 8 & 255) / 255.0F;
        float blue = (float) (color & 255) / 255.0F;
        return new float[] {red, green, blue};
    }

    public static boolean isBed(Block block) {
        return block == Blocks.BLACK_BED
                || block == Blocks.WHITE_BED
                || block == Blocks.BLUE_BED
                || block == Blocks.BROWN_BED
                || block == Blocks.CYAN_BED
                || block == Blocks.GRAY_BED
                || block == Blocks.GREEN_BED
                || block == Blocks.LIGHT_BLUE_BED
                || block == Blocks.LIGHT_GRAY_BED
                || block == Blocks.LIME_BED
                || block == Blocks.MAGENTA_BED
                || block == Blocks.ORANGE_BED
                || block == Blocks.PINK_BED
                || block == Blocks.PURPLE_BED
                || block == Blocks.RED_BED
                || block == Blocks.YELLOW_BED;
    }

    public static boolean isFluidOrAir(Block block) {
        return block == Blocks.AIR || block == Blocks.WATER || block == Blocks.LAVA || block == Blocks.CAVE_AIR || block == Blocks.VOID_AIR;
    }

    public static boolean isFluid(BlockPos blockPos) {
        Block block = mc.world.getBlockState(blockPos).getBlock();
        return block == Blocks.WATER || block == Blocks.LAVA;
    }

    public static boolean isAir(Block block) {
        return block == Blocks.AIR || block == Blocks.CAVE_AIR || block == Blocks.VOID_AIR;
    }

    public static boolean canPlace(BlockPos blockPos)
    {
        BlockPos up1 = blockPos.add(0, 1, 0);
        BlockPos up2 = blockPos.add(0, 2, 0);
        if (Module.getModule(CrystalAura.class).getSetting(8).asToggle().state) {
            return (mc.world.getBlockState(blockPos).getBlock() == Blocks.OBSIDIAN
                    || mc.world.getBlockState(blockPos).getBlock() == Blocks.BEDROCK)
                    && mc.world.getBlockState(up1).getBlock() == Blocks.AIR
                    && mc.world.getOtherEntities(null, new Box(up1).stretch(0, 1, 0)).isEmpty();

        } else {
            return (mc.world.getBlockState(blockPos).getBlock() == Blocks.OBSIDIAN
                    || mc.world.getBlockState(blockPos).getBlock() == Blocks.BEDROCK)
                    && mc.world.getBlockState(up1).getBlock() == Blocks.AIR
                    && mc.world.getBlockState(up2).getBlock() == Blocks.AIR
                    && mc.world.getOtherEntities(null, new Box(up1).stretch(0, 1, 0)).isEmpty()
                    && mc.world.getOtherEntities(null, new Box(up2).stretch(0, 1, 0)).isEmpty();
        }
    }

    public static double getCrystalDamage(LivingEntity entity, BlockPos blockPos) {
        if(mc.world.getDifficulty() == Difficulty.PEACEFUL || entity.isImmuneToExplosion())
            return 0;

        Vec3d vec3d = Vec3d.of(blockPos).add(0.5, 1, 0.5f);
        double distance = Math.sqrt(entity.squaredDistanceTo(vec3d));
        if(distance >= 13)
            return 0;

        double density = Explosion.getExposure(vec3d, entity);
        double impact = (1.d - (distance / 12.d)) * density;
        double damage = ((impact * impact + impact) / 2 * 7 * (6 * 2) + 1);

        Difficulty difficulty = mc.world.getDifficulty();
        if(difficulty == Difficulty.PEACEFUL)
            damage = 0.f;
        else if(difficulty == Difficulty.EASY)
            damage = Math.min(damage / 2.f + 1.f, damage);
        else if(difficulty == Difficulty.HARD)
            damage = damage * 3.f / 2.f;

        if(entity instanceof PlayerEntity)
        {
            PlayerEntity playerEntity = (PlayerEntity) entity;
            if(playerEntity.hasStatusEffect(StatusEffects.RESISTANCE))
            {
                damage *= (1 - ((playerEntity.getStatusEffect(StatusEffects.RESISTANCE).getAmplifier() + 1) * 0.2));
            }
            if(damage < 0)
                damage = 0;
        }

        damage = DamageUtil.getDamageLeft((float) damage, (float) entity.getArmor(), (float) entity.getAttributeInstance(EntityAttributes.GENERIC_ARMOR_TOUGHNESS).getValue());

        Explosion explosion = new Explosion(mc.world, null, vec3d.x, vec3d.y, vec3d.z, 6.f, false, Explosion.DestructionType.DESTROY);
        int protectionLevel = EnchantmentHelper.getProtectionAmount(entity.getArmorItems(), DamageSource.explosion(explosion));
        if(protectionLevel > 20)
            protectionLevel = 20;

        damage *= (1 - (protectionLevel / 25.d));
        if(damage < 0)
            damage = 0;

        return damage;
    }

    public static int changeHotbarSlotToItem(Item item) {
        int oldSlot = -1;
        int itemSlot = -1;
        for(int i = 0; i < 9; ++i)
        {
            if(mc.player.inventory.getStack(i).getItem() == item)
            {
                itemSlot = i;
                oldSlot = mc.player.inventory.selectedSlot;
            }
        }
        if(itemSlot != -1)
            mc.player.inventory.selectedSlot = itemSlot;
        return oldSlot;
    }

    public static void placeBlock(Vec3d vec, Hand hand, Direction direction) {
        mc.interactionManager.interactBlock(mc.player, mc.world, hand, new BlockHitResult(vec, direction, new BlockPos(vec), false));
        mc.player.swingHand(Hand.MAIN_HAND);
    }

    public static boolean placeBlock(BlockPos pos, int slot, boolean rotate, boolean rotateBack) {
        if (pos.getY() < 0 || pos.getY() > 255 || !isBlockEmpty(pos))
            return false;

        if (slot != mc.player.inventory.selectedSlot && slot >= 0 && slot <= 8)
            mc.player.inventory.selectedSlot = slot;

        for (Direction d : Direction.values()) {
            if ((d == Direction.DOWN && pos.getY() == 0) || (d == Direction.UP && pos.getY() == 255))
                continue;

            Block neighborBlock = mc.world.getBlockState(pos.offset(d)).getBlock();

            Vec3d vec = new Vec3d(pos.getX() + 0.5 + d.getOffsetX() * 0.5,
                    pos.getY() + 0.5 + d.getOffsetY() * 0.5,
                    pos.getZ() + 0.5 + d.getOffsetZ() * 0.5);

            if (NONSOLID_BLOCKS.contains(neighborBlock)
                    || mc.player.getPos().add(0, mc.player.getEyeHeight(mc.player.getPose()), 0).distanceTo(vec) > 4.55)
                continue;

            float[] rot = new float[] { mc.player.yaw, mc.player.pitch };

            if (rotate)
                facePosPacket(vec.x, vec.y, vec.z);
            if (RIGHTCLICKABLE_BLOCKS.contains(neighborBlock))
                mc.player.networkHandler.sendPacket(new ClientCommandC2SPacket(mc.player, ClientCommandC2SPacket.Mode.PRESS_SHIFT_KEY));

            mc.interactionManager.interactBlock(
                    mc.player, mc.world, Hand.MAIN_HAND, new BlockHitResult(Vec3d.of(pos), d.getOpposite(), pos.offset(d), false));

            if (RIGHTCLICKABLE_BLOCKS.contains(neighborBlock))
                mc.player.networkHandler.sendPacket(new ClientCommandC2SPacket(mc.player, ClientCommandC2SPacket.Mode.RELEASE_SHIFT_KEY));
            if (rotateBack)
                mc.player.networkHandler.sendPacket(new PlayerMoveC2SPacket.LookOnly(rot[0], rot[1], mc.player.isOnGround()));
            return true;
        }
        return false;
    }

    public static boolean isBlockEmpty(BlockPos pos) {
        if (!NONSOLID_BLOCKS.contains(mc.world.getBlockState(pos).getBlock()))
            return false;

        Box box = new Box(pos);
        for (Entity e : mc.world.getEntities()) {
            if (e instanceof LivingEntity && box.intersects(e.getBoundingBox()))
                return false;
        }

        return true;
    }

    public static boolean canPlaceBlock(BlockPos pos) {
        if (pos.getY() < 0 || pos.getY() > 255 || !isBlockEmpty(pos))
            return false;

        for (Direction d : Direction.values()) {
            if ((d == Direction.DOWN && pos.getY() == 0) || (d == Direction.UP && pos.getY() == 255)
                    || NONSOLID_BLOCKS.contains(mc.world.getBlockState(pos.offset(d)).getBlock())
                    || mc.player.getPos().add(0, mc.player.getEyeHeight(mc.player.getPose()), 0).distanceTo(
                    new Vec3d(pos.getX() + 0.5 + d.getOffsetX() * 0.5,
                            pos.getY() + 0.5 + d.getOffsetY() * 0.5,
                            pos.getZ() + 0.5 + d.getOffsetZ() * 0.5)) > 4.55)
                continue;

            return true;
        }
        return false;
    }


    public static void facePosAuto(double x, double y, double z, SettingRotate sr) {
        if (sr.getRotateMode() == 0)
            facePosPacket(x, y, z);
        else
            facePos(x, y, z);
    }

    public static void facePos(double x, double y, double z) {
        double diffX = x - mc.player.getX();
        double diffY = y - (mc.player.getY() + mc.player.getEyeHeight(mc.player.getPose()));
        double diffZ = z - mc.player.getZ();

        double diffXZ = Math.sqrt(diffX * diffX + diffZ * diffZ);

        float yaw = (float) Math.toDegrees(Math.atan2(diffZ, diffX)) - 90F;
        float pitch = (float) -Math.toDegrees(Math.atan2(diffY, diffXZ));

        mc.player.yaw += MathHelper.wrapDegrees(yaw - mc.player.yaw);
        mc.player.pitch += MathHelper.wrapDegrees(pitch - mc.player.pitch);
    }

    public static void facePosPacket(double x, double y, double z) {
        double diffX = x - mc.player.getX();
        double diffY = y - (mc.player.getY() + mc.player.getEyeHeight(mc.player.getPose()));
        double diffZ = z - mc.player.getZ();

        double diffXZ = Math.sqrt(diffX * diffX + diffZ * diffZ);

        float yaw = (float) Math.toDegrees(Math.atan2(diffZ, diffX)) - 90F;
        float pitch = (float) -Math.toDegrees(Math.atan2(diffY, diffXZ));

        //mc.player.headYaw = mc.player.yaw + MathHelper.wrapDegrees(yaw - mc.player.yaw);
        //mc.player.renderPitch = mc.player.pitch + MathHelper.wrapDegrees(pitch - mc.player.pitch);
        mc.player.networkHandler.sendPacket(
                new PlayerMoveC2SPacket.LookOnly(
                        mc.player.yaw + MathHelper.wrapDegrees(yaw - mc.player.yaw),
                        mc.player.pitch + MathHelper.wrapDegrees(pitch - mc.player.pitch), mc.player.isOnGround()));
    }

    public static Vec3d getEyesPos()
    {
        ClientPlayerEntity player = mc.player;

        return new Vec3d(player.getX(),
                player.getY() + player.getEyeHeight(player.getPose()),
                player.getZ());
    }

    public static void breakBlocksWithPacketSpam(Iterable<BlockPos> blocks)
    {
        Vec3d eyesPos = getEyesPos();

        for(BlockPos pos : blocks)
        {
            Vec3d posVec = Vec3d.ofCenter(pos);
            double distanceSqPosVec = eyesPos.squaredDistanceTo(posVec);

            for(Direction side : Direction.values())
            {
                Vec3d hitVec =
                        posVec.add(Vec3d.of(side.getVector()).multiply(0.5));

                // check if side is facing towards player
                if(eyesPos.squaredDistanceTo(hitVec) >= distanceSqPosVec)
                    continue;

                // break block
                mc.player.networkHandler.sendPacket(new PlayerActionC2SPacket(
                        PlayerActionC2SPacket.Action.START_DESTROY_BLOCK, pos, side));
                mc.player.networkHandler.sendPacket(new PlayerActionC2SPacket(
                        PlayerActionC2SPacket.Action.STOP_DESTROY_BLOCK, pos, side));

                break;
            }
        }
    }

    public static boolean breakOneBlock(BlockPos pos)
    {
        Direction side = null;
        Direction[] sides = Direction.values();

        BlockState state = mc.world.getBlockState(pos);
        VoxelShape shape = state.getOutlineShape(mc.world, pos);
        if(shape.isEmpty())
            return false;

        Vec3d eyesPos = getEyesPos();
        Vec3d relCenter = shape.getBoundingBox().getCenter();
        Vec3d center = Vec3d.of(pos).add(relCenter);

        Vec3d[] hitVecs = new Vec3d[sides.length];
        for(int i = 0; i < sides.length; i++)
        {
            Vec3i dirVec = sides[i].getVector();
            Vec3d relHitVec = new Vec3d(relCenter.x * dirVec.getX(),
                    relCenter.y * dirVec.getY(), relCenter.z * dirVec.getZ());
            hitVecs[i] = center.add(relHitVec);
        }

        for(int i = 0; i < sides.length; i++)
        {
            // check line of sight
            if(mc.world.raycastBlock(eyesPos, hitVecs[i], pos, shape,
                    state) != null)
                continue;

            side = sides[i];
            break;
        }

        if(side == null)
        {
            double distanceSqToCenter = eyesPos.squaredDistanceTo(center);
            for(int i = 0; i < sides.length; i++)
            {
                // check if side is facing towards player
                if(eyesPos.squaredDistanceTo(hitVecs[i]) >= distanceSqToCenter)
                    continue;

                side = sides[i];
                break;
            }
        }

        // player is inside of block, side doesn't matter
        if(side == null)
            side = sides[0];

        // face block
//        WURST.getRotationFaker().faceVectorPacket(hitVecs[side.ordinal()]);
//        facePos(pos.getX(), pos.getY(), pos.getZ());

        // damage block
        if(!mc.interactionManager.updateBlockBreakingProgress(pos, side))
            return false;

        // swing arm
        mc.player.networkHandler
                .sendPacket(new HandSwingC2SPacket(Hand.MAIN_HAND));

        return true;
    }

    public static Box voxelShapeToBox(VoxelShape in) {
        return in.getBoundingBox();
    }
}
