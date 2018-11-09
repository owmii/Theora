package xieao.theora.common.item;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import xieao.theora.api.TheoraAPI;
import xieao.theora.api.player.ability.Abilities;
import xieao.theora.api.player.data.PlayerData;
import xieao.theora.api.trade.pigzombie.PigZombieTradeHandler;
import xieao.theora.common.ability.TheoraAbilities;
import xieao.theora.network.TheoraNetwork;
import xieao.theora.network.packets.PacketOpenPigZombieTradeGui;

import java.util.List;

public class ItemPigCoin extends ItemBase {

    @Override
    public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer playerIn, EntityLivingBase target, EnumHand hand) {
        if (target instanceof EntityPigZombie && playerIn instanceof EntityPlayerMP) {
            EntityPigZombie pigZombie = (EntityPigZombie) target;
            PlayerData data = TheoraAPI.getPlayerData(playerIn);
            if (data != null) {
                Abilities abilities = data.getAbilities();
                if (abilities.hasAbility(TheoraAbilities.ME_PIG)) {
                    List<ResourceLocation> sortedTrades = PigZombieTradeHandler.getSortedTrades(pigZombie, 2, 5, itemRand);
                    TheoraNetwork.sendToPlayer(new PacketOpenPigZombieTradeGui(pigZombie.getUniqueID(), sortedTrades), (EntityPlayerMP) playerIn);
                    return true;
                }
            }
        }
        return false;
    }
}
