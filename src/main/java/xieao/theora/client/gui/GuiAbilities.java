package xieao.theora.client.gui;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.LazyOptional;
import xieao.theora.api.TheoraAPI;
import xieao.theora.api.player.Ability;
import xieao.theora.api.player.PlayerData;

import java.util.Map;

public class GuiAbilities extends GuiBase {
    private String[] regNames = new String[0];
    private ResourceLocation[] textures = new ResourceLocation[0];
    private boolean[] status = new boolean[0];
    private int[] levels = new int[0];

    public GuiAbilities() {
        LazyOptional<PlayerData> holder = TheoraAPI.getPlayerData(this.mc.player);
        holder.map(playerData -> {
            Ability.Data data = playerData.getAbilityData();
            Map<Ability, NBTTagCompound> map = data.getAbilityMap();
            this.regNames = new String[map.size()];
            this.textures = new ResourceLocation[map.size()];
            this.status = new boolean[map.size()];
            this.levels = new int[map.size()];
            final int[] index = {0};
            map.forEach((ability, compound) -> {
                this.regNames[index[0]] = ability.getRegistryString();
                this.textures[index[0]] = ability.getIcon();
                this.status[index[0]] = data.enabled(ability);
                this.levels[index[0]] = data.level(ability);
                index[0]++;
            });
            return playerData;
        });
    }
}
