package xieao.theora.common.item;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import xieao.theora.Theora;
import xieao.theora.api.item.wand.IWand;
import xieao.theora.api.item.wand.IWandable;
import xieao.theora.api.liquid.IliquidContainer;
import xieao.theora.api.liquid.LiquidUtil;
import xieao.theora.client.helper.ColorHelper;
import xieao.theora.client.helper.RendererHelper;
import xieao.theora.common.block.orb.TileOrb;
import xieao.theora.common.lib.helper.NBTHelper;

import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Mod.EventBusSubscriber(Side.CLIENT)
public class ItemWand extends ItemBase implements IWand {

    @Override
    public EnumActionResult onItemUseFirst(EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand) {
        IBlockState state = world.getBlockState(pos);
        ItemStack held = player.getHeldItem(hand);
        TileEntity tileEntity = world.getTileEntity(pos);
        int mode = NBTHelper.getInteger(held, "mode");
        if (mode == 0) {
            if (state.getBlock() instanceof IWandable) {
                IWandable wandable = (IWandable) state.getBlock();
                wandable.performWand(world, pos, player, hand, (IWand) held.getItem(), side);
                return EnumActionResult.SUCCESS;
            }
        } else {
            if (tileEntity instanceof TileOrb) {
                TileOrb orb = (TileOrb) tileEntity;
                NBTHelper.setTag(held, "orbPos", NBTUtil.createPosTag(pos));
                return EnumActionResult.SUCCESS;
            } else {
                BlockPos orbPos = NBTUtil.getPosFromTag(NBTHelper.getCompoundTag(held, "orbPos"));
                if (orbPos != BlockPos.ORIGIN && world.isAreaLoaded(orbPos, 4)) {
                    TileEntity tileEntity1 = world.getTileEntity(orbPos);
                    if (tileEntity1 instanceof TileOrb) {
                        TileOrb orb = (TileOrb) tileEntity1;
                        if (orb.link(pos)) {
                            return EnumActionResult.SUCCESS;
                        }
                    }
                }
            }
        }
        return super.onItemUseFirst(player, world, pos, side, hitX, hitY, hitZ, hand);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        ItemStack stack = playerIn.getHeldItem(handIn);
        if (playerIn.isSneaking()) {
            int mode = NBTHelper.getInteger(stack, "mode");
            NBTHelper.setInteger(stack, "mode", mode == 0 ? 1 : 0);
            return new ActionResult<>(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
        }
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        int mode = NBTHelper.getInteger(stack, "mode");
        tooltip.add("Mode: " + (mode == 0 ? "Normal" : "Link"));
        BlockPos orbPos = NBTUtil.getPosFromTag(NBTHelper.getCompoundTag(stack, "orbPos"));
        if (mode == 1 && orbPos != BlockPos.ORIGIN && !worldIn.isBlockLoaded(orbPos)) {
            tooltip.add(TextFormatting.RED + "Linked Orb are in unloaded chunk!");
        }
        //TODO localisation
    }

    @Override
    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        if (entityIn instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) entityIn;
            int mode = NBTHelper.getInteger(stack, "mode");
            if (mode == 1) {
                if (!isSelected && player.ticksExisted % 200 != 0) return;
                BlockPos orbPos = NBTUtil.getPosFromTag(NBTHelper.getCompoundTag(stack, "orbPos"));
                if (orbPos != BlockPos.ORIGIN && worldIn.isBlockLoaded(orbPos)) {
                    TileEntity tileEntity = worldIn.getTileEntity(orbPos);
                    if (tileEntity instanceof TileOrb) {
                        TileOrb orb = (TileOrb) tileEntity;
                        if (player.ticksExisted % 4 == 0) {
                            int range = 16;
                            Set<BlockPos> linkedList = new HashSet<>();
                            Set<BlockPos> unlinkedList = new HashSet<>();
                            for (int x = -range; x < range; x++) {
                                for (int y = -range; y < range; y++) {
                                    for (int z = -range; z < range; z++) {
                                        BlockPos pos = player.getPosition().add(x, y, z);
                                        IliquidContainer liquidContainer = LiquidUtil.getContainer(worldIn, pos, null);
                                        if (liquidContainer != null) {
                                            if (orb.linkedPositions.contains(pos)) {
                                                linkedList.add(pos);
                                            } else {
                                                unlinkedList.add(pos);
                                            }
                                        }
                                    }
                                }
                            }
                            NBTTagList linkedTagList = new NBTTagList();
                            for (BlockPos pos : linkedList) {
                                NBTTagCompound nbt = new NBTTagCompound();
                                nbt.setTag("pos", NBTUtil.createPosTag(pos));
                                linkedTagList.appendTag(nbt);
                            }
                            NBTHelper.setTag(stack, "linkedPosList", linkedTagList);

                            NBTTagList unlinkedTagList = new NBTTagList();
                            for (BlockPos pos : unlinkedList) {
                                NBTTagCompound nbt = new NBTTagCompound();
                                nbt.setTag("pos", NBTUtil.createPosTag(pos));
                                unlinkedTagList.appendTag(nbt);
                            }
                            NBTHelper.setTag(stack, "unlinkedPosList", unlinkedTagList);

                        }
                    } else {
                        NBTHelper.setTag(stack, "orbPos", NBTUtil.createPosTag(BlockPos.ORIGIN));
                        NBTHelper.setTag(stack, "linkedPosList", new NBTTagList());
                        NBTHelper.setTag(stack, "unlinkedPosList", new NBTTagList());
                    }
                }
            }
        }
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public static void renderLinkable(RenderWorldLastEvent event) {
        Minecraft mc = Minecraft.getMinecraft();
        EntityPlayerSP player = mc.player;
        for (EnumHand hand : EnumHand.values()) {
            ItemStack stack = player.getHeldItem(hand);
            if (stack.getItem() == TheoraItems.WAND) {
                int mode = NBTHelper.getInteger(stack, "mode");
                if (mode == 1) {
                    BlockPos orbPos = NBTUtil.getPosFromTag(NBTHelper.getCompoundTag(stack, "orbPos"));
                    if (orbPos != BlockPos.ORIGIN && player.world.isBlockLoaded(orbPos)) {
                        double x = player.lastTickPosX + (player.posX - player.lastTickPosX) * event.getPartialTicks();
                        double y = player.lastTickPosY + (player.posY - player.lastTickPosY) * event.getPartialTicks();
                        double z = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * event.getPartialTicks();
                        GlStateManager.pushMatrix();
                        GlStateManager.translate(-x, -y, -z);
                        GlStateManager.enableBlend();
                        ColorHelper.glColor(0xc402ef, 0.7F);
                        GlStateManager.blendFunc(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE);
                        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 61680.0F, 0.0F);
                        GlStateManager.disableDepth();
                        GlStateManager.pushMatrix();
                        GlStateManager.translate(orbPos.getX() + 0.5D, orbPos.getY() + 0.3D, orbPos.getZ() + 0.5D);
                        RendererHelper.renderFacingQuad(Theora.location("textures/misc/wand_hilight.png"), .5D);
                        GlStateManager.popMatrix();
                        if (NBTHelper.hasKey(stack, "linkedPosList", Constants.NBT.TAG_LIST)) {
                            NBTTagList tagList = (NBTTagList) NBTHelper.getTag(stack, "linkedPosList");
                            for (int i = 0; i < tagList.tagCount(); i++) {
                                NBTTagCompound nbt = tagList.getCompoundTagAt(i);
                                BlockPos pos = NBTUtil.getPosFromTag(nbt.getCompoundTag("pos"));
                                GlStateManager.pushMatrix();
                                ColorHelper.glColor(0x00bf3f, 0.7F);
                                GlStateManager.translate(pos.getX() + 0.5D, pos.getY() + 0.3D, pos.getZ() + 0.5D);
                                RendererHelper.renderFacingQuad(Theora.location("textures/misc/wand_hilight.png"), .5D);
                                GlStateManager.popMatrix();
                            }
                        }
                        if (NBTHelper.hasKey(stack, "unlinkedPosList", Constants.NBT.TAG_LIST)) {
                            NBTTagList tagList = (NBTTagList) NBTHelper.getTag(stack, "unlinkedPosList");
                            for (int i = 0; i < tagList.tagCount(); i++) {
                                NBTTagCompound nbt = tagList.getCompoundTagAt(i);
                                BlockPos pos = NBTUtil.getPosFromTag(nbt.getCompoundTag("pos"));
                                GlStateManager.pushMatrix();
                                ColorHelper.glColor(0xff5b16, 0.7F);
                                GlStateManager.translate(pos.getX() + 0.5D, pos.getY() + 0.3D, pos.getZ() + 0.5D);
                                RendererHelper.renderFacingQuad(Theora.location("textures/misc/wand_hilight.png"), .5D);
                                GlStateManager.popMatrix();
                            }
                        }
                        GlStateManager.enableDepth();
                        GlStateManager.disableBlend();
                        GlStateManager.popMatrix();
                    }
                    break;
                }
            }
        }
    }
}

