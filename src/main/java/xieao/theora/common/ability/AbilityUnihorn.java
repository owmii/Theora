package xieao.theora.common.ability;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionUtils;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import xieao.theora.api.TheoraAPI;
import xieao.theora.api.player.ability.Abilities;
import xieao.theora.api.player.ability.Ability;
import xieao.theora.api.player.data.PlayerData;
import xieao.theora.network.TheoraNetwork;
import xieao.theora.network.packets.PacketSyncFlight;

@Mod.EventBusSubscriber
public class AbilityUnihorn extends Ability {

    @Override
    public void onUpdate(EntityPlayer player, World world, int abilityLevel, NBTTagCompound abilityNbt) {
        player.stepHeight = 1.1F;
    }

    @SubscribeEvent
    public static void tickPlayer(TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            EntityPlayer player = event.player;
            if (!player.world.isRemote) {
                PlayerData data = TheoraAPI.getPlayerData(player);
                if (data != null && player instanceof EntityPlayerMP) {
                    Abilities abilities = data.getAbilities();
                    boolean flag = false;
                    if (abilities.hasAbility(TheoraAbilities.UNIHORN)) {
                        if (abilities.isActive(TheoraAbilities.UNIHORN)) {
                            player.capabilities.allowFlying = true;
                            if (!data.allowFlying) {
                                data.allowFlying = true;
                                TheoraNetwork.NET.sendTo(new PacketSyncFlight(true), (EntityPlayerMP) player);
                            }
                            flag = true;
                        }
                    }
                    if (!flag) {
                        if (data.allowFlying) {
                            boolean isSpecial = player.isCreative() || player.isSpectator();
                            if (!isSpecial) {
                                player.capabilities.allowFlying = false;
                                player.capabilities.isFlying = false;
                                TheoraNetwork.NET.sendTo(new PacketSyncFlight(false), (EntityPlayerMP) player);
                            }
                            data.allowFlying = false;
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void attack(LivingHurtEvent event) {
        Entity source = event.getSource().getImmediateSource();
        if (source instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) source;
            PlayerData data = TheoraAPI.getPlayerData(player);
            if (data != null) {
                Abilities abilities = data.getAbilities();
                if (abilities.hasAbility(TheoraAbilities.UNIHORN)) {
                    if (abilities.isActive(TheoraAbilities.UNIHORN)) {
                        float amount = event.getAmount();
                        event.setAmount(amount * 1.3F);
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void useItem(LivingEntityUseItemEvent.Finish event) {
        ItemStack stack = event.getItem();
        if (stack.getItem() == Items.POTIONITEM && PotionUtils.getEffectsFromStack(stack).isEmpty()) {
            EntityLivingBase livingBase = event.getEntityLiving();
            if (livingBase instanceof EntityPlayer) {
                EntityPlayer player = (EntityPlayer) livingBase;
                PlayerData data = TheoraAPI.getPlayerData(player);
                if (data != null) {
                    Abilities abilities = data.getAbilities();
                    if (abilities.hasAbility(TheoraAbilities.UNIHORN)) {
                        if (abilities.isActive(TheoraAbilities.UNIHORN)) {
                            player.getActivePotionEffects().removeIf(potionEffect -> potionEffect.getPotion().isBadEffect());
                        }
                    }
                }
            }
        }
    }
}
