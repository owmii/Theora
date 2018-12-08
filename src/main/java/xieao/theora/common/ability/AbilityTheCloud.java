package xieao.theora.common.ability;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import xieao.theora.api.player.ability.Ability;

public class AbilityTheCloud extends Ability {

    //TODO trowing snow ball on entity will hit them with lightning

    @Override
    public void onUpdate(EntityPlayer player, World world, int abilityLevel, NBTTagCompound abilityNbt) {
        if (world.isRainingAt(player.getPosition())) {
            if (world.canBlockSeeSky(player.getPosition())) {
                if (player.ticksExisted % 77 == 0) {
                    player.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 80));
                }
            }
        }
        BlockPos pos = player.getPosition();
        BlockPos pos1 = pos.add(0.0D, -2.0D, 0.0D);
        if (!world.isAirBlock(pos1) && player.motionY < -0.55D) {
            player.motionY = -0.2D;
            if (!world.isRemote) {
                int[][] offsets = {{0, 0, 0}, {0, 0, 1}, {1, 0, 0}, {-1, 0, 0}, {0, 0, -1}};
                for (int[] offset : offsets) {
                    BlockPos pos2 = pos1.add(offset[0], offset[1], offset[2]);
                    if (world.getBlockState(pos2).getBlock() == Blocks.LAVA) {
                        world.setBlockState(pos2, Blocks.OBSIDIAN.getDefaultState(), 2);
                    }
                }
            }
            if (world.isRemote) {
                world.spawnParticle(EnumParticleTypes.CLOUD, player.posX, player.posY + 0.5D, player.posZ, 0, 0, 0);
            }
        }
        player.fallDistance = 0.0F;
    }
}
