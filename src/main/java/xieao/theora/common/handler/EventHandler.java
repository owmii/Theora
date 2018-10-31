package xieao.theora.common.handler;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import xieao.theora.Theora;
import xieao.theora.api.TheoraAPI;
import xieao.theora.api.player.ability.Abilities;
import xieao.theora.api.player.ability.Ability;
import xieao.theora.api.player.data.PlayerData;
import xieao.theora.api.player.data.PlayerDataProvider;

import java.util.Map;

@Mod.EventBusSubscriber
public class EventHandler {

    @SubscribeEvent
    public static void attachCapability(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof EntityPlayer) {
            event.addCapability(Theora.location("player.data"), new PlayerDataProvider());
        }
    }

    @SubscribeEvent
    public static void clone(PlayerEvent.Clone event) {
        PlayerData oldData = TheoraAPI.getPlayerData(event.getOriginal());
        PlayerData newData = TheoraAPI.getPlayerData(event.getEntityPlayer());
        if (oldData != null && newData != null) {
            newData.deserializeNBT(oldData.serializeNBT());
        }
    }

    @SubscribeEvent
    public static void tickPlayer(TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            EntityPlayer player = event.player;
            PlayerData data = TheoraAPI.getPlayerData(player);
            if (data != null) {
                Abilities abilities = data.getAbilities();
                for (Map.Entry<Ability, NBTTagCompound> e : abilities.getAbilityMap().entrySet()) {
                    Ability ability = e.getKey();
                    NBTTagCompound nbt = e.getValue();
                    if (abilities.isActive(ability)) {
                        ability.tickAbility(player, player.world, abilities.getAbilityLevel(ability), abilities.getSubNbt(ability));
                    }
                }
            }
        }
    }

//    @SubscribeEvent
//    public static void rightClickEntity(PlayerInteractEvent.EntityInteract event) {
//        EntityPlayer player = event.getEntityPlayer();
//        Entity entity = event.getTarget();
//        World world = player.world;
//        if (entity instanceof EntityZombie) {
//            EntityZombie zombie = (EntityZombie) entity;
//            ItemStack held = player.getHeldItem(event.getHand());
//            if (zombie.isChild()) return;
//            if (!world.isRemote) {
//                if (held.getItem() == Items.GLASS_BOTTLE) {
//                    if (player.experienceLevel >= 10) {
//                        PlayerData data = TheoraAPI.getPlayerData(player);
//                        if (data != null) {
//                            if (!data.hasAcidVial()) {
//                                held.shrink(1);
//                                player.addExperienceLevel(-10);
//                                ItemHandlerHelper.giveItemToPlayer(player, new ItemStack(TheoraItems.ACID_VIAL));
//                                //TODO sounds ...
//                                //TODO particles ...
//                            }
//                        }
//                    } else {
//                        player.sendStatusMessage(new TextComponentTranslation("theora.status.no.enough.xp", player.experienceLevel), true);
//                    }
//                }
//            }
//        }
//    }
}
