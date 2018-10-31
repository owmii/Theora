package xieao.theora.common.ability;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.items.ItemHandlerHelper;
import xieao.theora.api.TheoraAPI;
import xieao.theora.api.player.ability.Abilities;
import xieao.theora.api.player.ability.Ability;
import xieao.theora.api.player.data.PlayerData;
import xieao.theora.common.item.TheoraItems;

import java.util.List;

@Mod.EventBusSubscriber
public class AbilityMePig extends Ability {

    @SubscribeEvent
    public static void entityDeath(LivingDeathEvent event) {
        EntityLivingBase livingBase = event.getEntityLiving();
        if (livingBase instanceof EntityPig) {
            EntityPig pig = (EntityPig) livingBase;
            Entity source = event.getSource().getTrueSource();
            if (source instanceof EntityPlayer) {
                EntityPlayer player = (EntityPlayer) source;
                PlayerData data = TheoraAPI.getPlayerData(player);
                if (data != null) {
                    Abilities abilities = data.getAbilities();
                    if (abilities.hasAbility(TheoraAbilities.ME_PIG)) {
                        abilities.lose(TheoraAbilities.ME_PIG);
                        //TODO sync ability
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void useItem(LivingEntityUseItemEvent.Finish event) {
        EntityLivingBase livingBase = event.getEntityLiving();
        if (livingBase instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) livingBase;
            PlayerData data = TheoraAPI.getPlayerData(player);
            if (!player.world.isRemote && data != null) {
                Abilities abilities = data.getAbilities();
                if (abilities.hasAbility(TheoraAbilities.ME_PIG)) {
                    Item[] items = {Items.PORKCHOP, Items.COOKED_PORKCHOP};
                    for (Item item : items) {
                        if (item == event.getResultStack().getItem()) {
                            player.addPotionEffect(new PotionEffect(MobEffects.POISON, 300));
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void updateEntity(LivingEvent.LivingUpdateEvent event) {
        EntityLivingBase livingBase = event.getEntityLiving();
        if (livingBase instanceof EntityPig) {
            EntityPig pig = (EntityPig) livingBase;
            if (pig.isBeingRidden()) {
                Entity ridingEntity = pig.getRidingEntity();
                if (ridingEntity instanceof EntityPlayer) {
                    EntityPlayer player = (EntityPlayer) ridingEntity;
                    PlayerData data = TheoraAPI.getPlayerData(player);
                    if (data != null) {
                        Abilities abilities = data.getAbilities();
                        if (abilities.hasAbility(TheoraAbilities.ME_PIG)) {
                            pig.moveRelative(pig.moveStrafing, 0F, pig.moveForward, 0.5F);
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void enityAttack(LivingAttackEvent event) {
        Entity source = event.getSource().getTrueSource();
        EntityLivingBase livingBase = event.getEntityLiving();
        if (livingBase instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) livingBase;
            PlayerData data = TheoraAPI.getPlayerData(player);
            if (!player.world.isRemote && data != null) {
                Abilities abilities = data.getAbilities();
                if (abilities.hasAbility(TheoraAbilities.ME_PIG)) {
                    World world = player.world;
                    BlockPos pos = new BlockPos(player.posX, player.posY, player.motionZ);
                    AxisAlignedBB boudingBox = new AxisAlignedBB(pos).grow(24.0D);
                    if (source instanceof EntityLivingBase) {
                        if (!(source instanceof EntityPigZombie)) {
                            List<EntityPigZombie> pigZombies = world.getEntitiesWithinAABB(EntityPigZombie.class, boudingBox);
                            for (EntityPigZombie pigZombie : pigZombies) {
                                pigZombie.setRevengeTarget((EntityLivingBase) source);
                                pigZombie.setAttackTarget((EntityLivingBase) source);
                                world.playSound(null, source.posX, source.posY, source.motionZ, SoundEvents.ENTITY_ZOMBIE_PIG_ANGRY, SoundCategory.HOSTILE, 0.8F, 1.0F);
                            }
                        } else {
                            List<EntityPigZombie> pigZombies = world.getEntitiesWithinAABB(EntityPigZombie.class, boudingBox);
                            for (EntityPigZombie pigZombie : pigZombies) {
                                if (pigZombie.isAngry()) {
                                    if (pigZombie.getAttackTarget() == player) {
                                        pigZombie.setRevengeTarget(null);
                                        pigZombie.setAttackTarget(null);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void babyBorn(BabyEntitySpawnEvent event) {
        EntityLivingBase parentA = event.getParentA();
        EntityLivingBase parentB = event.getParentB();
        if (parentA instanceof EntityPig && parentB instanceof EntityPig) {
            EntityPlayer player = event.getCausedByPlayer();
            if (player != null) {
                PlayerData data = TheoraAPI.getPlayerData(player);
                if (!player.world.isRemote && data != null) {
                    Abilities abilities = data.getAbilities();
                    if (abilities.hasAbility(TheoraAbilities.ME_PIG)) {
                        ItemHandlerHelper.giveItemToPlayer(player, new ItemStack(TheoraItems.PIG_COIN));
                        event.setCanceled(true);
                    }
                }
            }
        }
    }
}