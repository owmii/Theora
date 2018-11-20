package xieao.theora.common.block.deathchamber;

import net.minecraft.entity.EntityLiving;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ITickable;
import net.minecraft.util.WeightedRandom;
import net.minecraft.world.biome.Biome;
import xieao.theora.api.item.ISummoningSlate;
import xieao.theora.common.block.TileInvBase;
import xieao.theora.common.item.TheoraItems;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class TileDeathChamber extends TileInvBase implements ITickable {

    public static final int[][] LAYER = new int[][]{{1, 0}, {0, 1}, {-1, 0}, {-1, 0}, {1, 1}, {1, -1}, {-1, 1}, {-1, -1}};
    public static final int[][] LEGS_LAYER = new int[][]{{2, 2}, {2, -2}, {-2, 2}, {-2, -2}};

    @Override
    public void update() {
        if (isServerWorld()) {
            if (world.getTotalWorldTime() % 40 == 0)
                System.out.println(getRandomEntity(0));
        }
    }

    @Nullable
    private EntityLiving getRandomEntity(int slot) {
        ItemStack stack = new ItemStack(TheoraItems.SUMMONING_SLATE);
        if (stack.getItem() instanceof ISummoningSlate) {
            ISummoningSlate slate = (ISummoningSlate) stack.getItem();
            List<Biome.SpawnListEntry> spawnListEntries = new ArrayList<>(slate.getSpawnListEntries(stack));
            Biome.SpawnListEntry spawnListEntry = WeightedRandom.getRandomItem(getWorld().rand, spawnListEntries);
            EntityLiving entity = null;
            try {
                entity = spawnListEntry.newInstance(getWorld());
            } catch (Exception e) {
                e.printStackTrace();
            }
            return entity;
        }
        return null;
    }


    @Override
    public int getSizeInventory() {
        return 12;
    }
}
