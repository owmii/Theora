package xieao.theora.common.entity;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;

import javax.annotation.Nullable;

public abstract class LivingBase extends EntityLiving {

    private BlockPos firsSpawnPos = BlockPos.ORIGIN;

    private int deathTime;
    public int deathTicks;

    public LivingBase(World world) {
        super(world);
    }

    @Nullable
    @Override
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingData) {
        IEntityLivingData data = super.onInitialSpawn(difficulty, livingData);
        setFirsSpawnPos(getPosition());
        return data;
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {
        onAttackHit(source, amount);
        return super.attackEntityFrom(source, amount);
    }

    protected void onAttackHit(DamageSource source, float amount) {
    }

    @Override
    protected void onDeathUpdate() {
        if (this.deathTime > 0) {
            this.deathTicks++;
            onSpecificDeathTicks(this.deathTicks);
        } else {
            super.onDeathUpdate();
        }
    }

    protected void onSpecificDeathTicks(int deathTicks) {
        if (this.deathTicks >= this.deathTime) {
            dropExperience(getExperiencePoints(this.attackingPlayer));
            setDead();
        }
    }

    protected void dropExperience(int expValue) {
        if (!this.world.isRemote && (this.isPlayer() || this.recentlyHit > 0 && this.canDropLoot() && this.world.getGameRules().getBoolean("doMobLoot"))) {
            expValue = ForgeEventFactory.getExperienceDrop(this, this.attackingPlayer, expValue);
            while (expValue > 0) {
                int i = EntityXPOrb.getXPSplit(expValue);
                expValue -= i;
                this.world.spawnEntity(new EntityXPOrb(this.world, this.posX, this.posY, this.posZ, i));
            }
        }
    }

    @Override
    public boolean isNonBoss() {
        return !(this instanceof IBoss);
    }

    public BlockPos getFirsSpawnPos() {
        return firsSpawnPos;
    }

    public void setFirsSpawnPos(BlockPos firsSpawnPos) {
        this.firsSpawnPos = firsSpawnPos;
    }

    public int getDeathTime() {
        return deathTime;
    }

    public void setDeathTime(int deathTime) {
        this.deathTime = deathTime;
    }
}
