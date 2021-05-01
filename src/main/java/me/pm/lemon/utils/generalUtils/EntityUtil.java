package me.pm.lemon.utils.generalUtils;

import net.minecraft.block.FluidBlock;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.EndermanEntity;
import net.minecraft.entity.mob.WaterCreatureEntity;
import net.minecraft.entity.mob.ZombifiedPiglinEntity;
import net.minecraft.entity.passive.GolemEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.scoreboard.Team;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class EntityUtil {
    private static Object AmbientEntity;

    public static boolean isLiving(Entity e) {
        return e instanceof LivingEntity;
    }

    public static boolean isFakeLocalPlayer(Entity entity) {
        return entity != null && entity.getEntityId() == -100 && mc.player != entity;
    }

    /**
     * Find the entities interpolated amount
     */
    public static Vec3d getInterpolatedAmount(Entity entity, double x, double y, double z) {
        return entity.getPos().subtract(entity.prevX, entity.prevY, entity.prevZ).multiply(x, y, z);
    }
    public static Vec3d getInterpolatedAmount(Entity entity, Vec3d vec) {
        return getInterpolatedAmount(entity, vec.x, vec.y, vec.z);
    }
    public static Vec3d getInterpolatedAmount(Entity entity, double ticks) {
        return getInterpolatedAmount(entity, ticks, ticks, ticks);
    }

    /**
     * If the mob by default wont attack the player, but will if the player attacks it
     */
    public static boolean isNeutralMob(Entity entity) {
        return entity instanceof ZombifiedPiglinEntity ||
                entity instanceof WolfEntity ||
                entity instanceof EndermanEntity;
    }

    /**
     * Find the entities interpolated position
     */
    public static Vec3d getInterpolatedPos(Entity entity, float ticks) {
        return new Vec3d(entity.prevX, entity.prevY, entity.prevZ).add(getInterpolatedAmount(entity, ticks));
    }

//    public static Vec3d getInterpolatedRenderPos(Entity entity, float ticks) {
//        Vec3d renderPos = Wrapper.getRenderPosition();
//        return getInterpolatedPos(entity, ticks).subtract(renderPos);
//    }

    public static boolean isInWater(Entity entity) {
        if(entity == null) return false;

        double y = entity.getY() + 0.01;

        for(int x = MathHelper.floor(entity.getZ()); x < MathHelper.ceil(entity.getX()); x++)
            for (int z = MathHelper.floor(entity.getZ()); z < MathHelper.ceil(entity.getZ()); z++) {
                BlockPos pos = new BlockPos(x, (int) y, z);

                if (mc.world.getBlockState(pos).getBlock() instanceof FluidBlock) return true;
            }

        return false;
    }

    public static boolean isDrivenByPlayer(Entity entityIn) {
        return mc.player != null && entityIn != null && entityIn.equals(mc.player.getVehicle());
    }

    public static boolean isAboveWater(Entity entity) { return isAboveWater(entity, false); }
    public static boolean isAboveWater(Entity entity, boolean packet){
        if (entity == null) return false;

        double y = entity.getY() - (packet ? 0.03 : (EntityUtil.isPlayer(entity) ? 0.2 : 0.5)); // increasing this seems to flag more in NCP but needs to be increased so the player lands on solid water

        for(int x = MathHelper.floor(entity.getX()); x < MathHelper.ceil(entity.getX()); x++)
            for (int z = MathHelper.floor(entity.getZ()); z < MathHelper.ceil(entity.getZ()); z++) {
                BlockPos pos = new BlockPos(x, MathHelper.floor(y), z);

                if (mc.world.getBlockState(pos).getBlock() instanceof FluidBlock) return true;
            }

        return false;
    }

    public static double[] calculateLookAt(double px, double py, double pz, ClientPlayerEntity me) {
        double dirx = me.getX() - px;
        double diry = me.getY() - py;
        double dirz = me.getZ() - pz;

        double len = Math.sqrt(dirx*dirx + diry*diry + dirz*dirz);

        dirx /= len;
        diry /= len;
        dirz /= len;

        double pitch = Math.asin(diry);
        double yaw = Math.atan2(dirz, dirx);

        //to degree
        pitch = pitch * 180.0d / Math.PI;
        yaw = yaw * 180.0d / Math.PI;

        yaw += 90f;

        return new double[]{yaw,pitch};
    }

    public static void updateVelocityX(Entity entity, double x) {
        Vec3d velocity = entity.getVelocity();
        entity.setVelocity(x, velocity.y, velocity.z);
    }

    public static void updateVelocityY(Entity entity, double y) {
        Vec3d velocity = entity.getVelocity();
        entity.setVelocity(velocity.x, y, velocity.z);
    }

    public static void updateVelocityZ(Entity entity, double z) {
        Vec3d velocity = entity.getVelocity();
        entity.setVelocity(velocity.x, velocity.y, z);
    }

    public static boolean isPlayer(Entity entity) {
        return entity instanceof ClientPlayerEntity;
    }

    public static double getRelativeX(float yaw){
        return MathHelper.sin(-yaw * 0.017453292F);
    }

    public static double getRelativeZ(float yaw){
        return MathHelper.cos(yaw * 0.017453292F);
    }

    private static final MinecraftClient mc = MinecraftClient.getInstance();

    public static boolean isAnimal(Entity e) {
        return e instanceof PassiveEntity || e instanceof net.minecraft.entity.mob.AmbientEntity || e instanceof WaterCreatureEntity || e instanceof GolemEntity;
    }

    public static void setGlowing(Entity entity, Formatting color, String teamName) {
        Team team = (mc.world.getScoreboard().getTeamNames().contains(teamName) ?
                mc.world.getScoreboard().getTeam(teamName) :
                mc.world.getScoreboard().addTeam(teamName));

        mc.world.getScoreboard().addPlayerToTeam(
                entity instanceof PlayerEntity ? entity.getEntityName() : entity.getUuidAsString(), team);
        mc.world.getScoreboard().getTeam(teamName).setColor(color);

        entity.setGlowing(true);
    }
    public static double[] calculateLookAt(double px, double py, double pz, PlayerEntity me)
    {
        double dirx = me.getX() - px;
        double diry = me.getY() - py;
        double dirz = me.getZ() - pz;

        double len = Math.sqrt(dirx * dirx + diry * diry + dirz * dirz);

        dirx /= len;
        diry /= len;
        dirz /= len;

        double pitch = Math.asin(diry);
        double yaw = Math.atan2(dirz, dirx);

        // to degree
        pitch = pitch * 180.0d / Math.PI;
        yaw = yaw * 180.0d / Math.PI;

        yaw += 90f;

        return new double[]
                { yaw, pitch };
    }

    public static float[] getLegitRotations(Vec3d vec)
    {
        Vec3d eyesPos = getEyesPos();

        double diffX = vec.x - eyesPos.x;
        double diffY = vec.y - eyesPos.y;
        double diffZ = vec.z - eyesPos.z;

        double diffXZ = Math.sqrt(diffX * diffX + diffZ * diffZ);

        float yaw = (float) Math.toDegrees(Math.atan2(diffZ, diffX)) - 90F;
        float pitch = (float) -Math.toDegrees(Math.atan2(diffY, diffXZ));

        return new float[]
                { MinecraftClient.getInstance().player.yaw + MathHelper.wrapDegrees(yaw - MinecraftClient.getInstance().player.yaw),
                        MinecraftClient.getInstance().player.pitch + MathHelper.wrapDegrees(pitch - MinecraftClient.getInstance().player.pitch) };
    }
    private static Vec3d getEyesPos()
    {
        return new Vec3d(MinecraftClient.getInstance().player.getX(), MinecraftClient.getInstance().player.getY() + MinecraftClient.getInstance().player.getEyeHeight(mc.player.getPose()), MinecraftClient.getInstance().player.getZ());
    }
    public enum FacingDirection
    {
        North,
        South,
        East,
        West,
    }

    public static FacingDirection GetFacing()
    {
        switch (MathHelper.floor((double) (mc.player.yaw * 8.0F / 360.0F) + 0.5D) & 7)
        {
            case 0:
            case 1:
                return FacingDirection.South;
            case 2:
            case 3:
                return FacingDirection.West;
            case 4:
            case 5:
                return FacingDirection.North;
            case 6:
            case 7:
                return FacingDirection.East;
            case 8:
        }
        return FacingDirection.North;
    }
}
