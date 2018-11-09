package xieao.theora.common.item;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import xieao.theora.api.TheoraAPI;
import xieao.theora.api.player.ability.Abilities;
import xieao.theora.api.player.data.PlayerData;
import xieao.theora.common.ability.TheoraAbilities;

public class ItemPigCoin extends ItemBase {

    @Override
    public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer playerIn, EntityLivingBase target, EnumHand hand) {
        PlayerData data = TheoraAPI.getPlayerData(playerIn);
        if (data != null) {
            Abilities abilities = data.getAbilities();
            if (abilities.hasAbility(TheoraAbilities.ME_PIG)) {

                return true;
            }
        }
        return false;
    }
}
