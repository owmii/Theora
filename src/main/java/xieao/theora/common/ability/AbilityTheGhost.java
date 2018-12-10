package xieao.theora.common.ability;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import xieao.theora.api.TheoraAPI;
import xieao.theora.api.player.ability.Abilities;
import xieao.theora.api.player.ability.Ability;
import xieao.theora.api.player.data.PlayerData;

@Mod.EventBusSubscriber
public class AbilityTheGhost extends Ability {

    // # when invisible means invisible
    // TODO you can see invisible entities
    // TODO
    // TODO
    // TODO

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public static void fullyInvisible(RenderPlayerEvent.Pre event) {
        EntityPlayer player = event.getEntityPlayer();
        PlayerData data = TheoraAPI.getPlayerData(player);
        if (data != null) {
            Abilities abilities = data.getAbilities();
            if (abilities.hasAbility(TheoraAbilities.THE_GHOST)) {
                if (abilities.isActive(TheoraAbilities.THE_GHOST)) {
                    if (player.getActivePotionEffect(MobEffects.INVISIBILITY) != null) {
                        event.setCanceled(true);
                    }
                }
            }
        }
    }
}
