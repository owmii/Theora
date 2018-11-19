package xieao.theora.common.block.liquidurn;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;
import xieao.theora.api.liquid.Liquid;
import xieao.theora.api.liquid.LiquidContainer;
import xieao.theora.api.liquid.LiquidSlot;
import xieao.theora.common.block.BlockBase;
import xieao.theora.common.lib.helper.NBTHelper;

import javax.annotation.Nullable;
import java.util.List;

public class BlockLiquidUrn extends BlockBase implements ITileEntityProvider {


    public BlockLiquidUrn() {
        super(Material.WOOD);
        setSoundType(SoundType.WOOD);
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileLiquidUrn();
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World player, List<String> tooltip, ITooltipFlag advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        LiquidContainer liquidContainer = new LiquidContainer();
        liquidContainer.addLiquidSlots(LiquidContainer.EMPTY_SLOT);
        if (NBTHelper.hasNBT(stack) && NBTHelper.hasKey(stack, TAG_TILE_DATA, Constants.NBT.TAG_COMPOUND)) {
            liquidContainer.readNBT(NBTHelper.getCompoundTag(stack, TAG_TILE_DATA));
        }
        LiquidSlot liquidSlot = liquidContainer.getLiquidSlot(0);
        Liquid liquid = liquidSlot.getLiquid();
        if (!liquidSlot.isEmpty() && !liquid.isEmpty()) {
            tooltip.add("Liquid: " + liquid.getRegistryString());
            tooltip.add("Stored: " + liquidSlot.getStored() + "/" + liquidSlot.getCapacity());
        }//TODO localization
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }
}
