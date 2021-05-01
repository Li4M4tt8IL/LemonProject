package me.pm.lemon.utils.generalUtils;

import net.minecraft.block.Block;

import java.util.ArrayList;

public class XRayUtil {
    public static ArrayList<Block> xrayBlocks = new ArrayList<Block>();

    public static boolean isXrayBlock(Block b) {
        if (xrayBlocks.contains(b)) {
            return true;
        } else {
            return false;
        }
    }
}
