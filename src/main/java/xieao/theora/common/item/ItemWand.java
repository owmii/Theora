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
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import xieao.lib.item.ItemBase;
import xieao.lib.util.ColorUtil;
import xieao.lib.util.NBTUtil;
import xieao.lib.util.RenderUtil;
import xieao.theora.Theora;
import xieao.theora.api.item.wand.IWand;
import xieao.theora.api.item.wand.IWandable;
import xieao.theora.api.liquid.IliquidContainer;
import xieao.theora.api.liquid.LiquidUtil;
import xieao.theora.common.block.orb.TileOrb;

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
        int mode = NBTUtil.getInteger(held, "mode");
        if (mode == 0) {
            if (state.getBlock() instanceof IWandable) {
                IWandable wandable = (IWandable) state.getBlock();
                wandable.performWand(world, pos, player, hand, (IWand) held.getItem(), side);
                return EnumActionResult.SUCCESS;
            }
        } else {
            if (tileEntity instanceof TileOrb) {
                TileOrb orb = (TileOrb) tileEntity;
                NBTUtil.setTag(held, "orbPos", net.minecraft.nbt.NBTUtil.createPosTag(pos));
                return EnumActionResult.SUCCESS;
            } else {
                BlockPos orbPos = net.minecraft.nbt.NBTUtil.getPosFromTag(NBTUtil.getCompoundTag(held, "orbPos"));
                if (orbPos != BlockPos.ORIGIN && world.isAreaLoaded(orbPos, 4)) {
                    TileEntity tileEntity1 = world.getTileEntity(orbPos);
                    if (tileEntity1 instanceof TileOrb) {
                        TileOrb orb = (TileOrb) tileEntity1;
                        if (orb.link(pos)) {
                            return EnumActionResult.SUCCESS;
                        } else if (orb.unlink(pos)) {
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
            int mode = NBTUtil.getInteger(stack, "mode");
            NBTUtil.setInteger(stack, "mode", mode == 0 ? 1 : 0);
            return new ActionResult<>(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
        }
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        int mode = NBTUtil.getInteger(stack, "mode");
        tooltip.add("Mode: " + (mode == 0 ? "Normal" : "Link"));
        BlockPos orbPos = net.minecraft.nbt.NBTUtil.getPosFromTag(NBTUtil.getCompoundTag(stack, "orbPos"));
        if (mode == 1 && orbPos != BlockPos.ORIGIN && !worldIn.isBlockLoaded(orbPos)) {
            tooltip.add(TextFormatting.RED + "Linked Orb are in unloaded chunk!");
        }
        //TODO localisation
    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        return false;
    }

    @Override
    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        if (entityIn instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) entityIn;
            int mode = NBTUtil.getInteger(stack, "mode");
            if (mode == 1) {
                if (!isSelected && player.ticksExisted % 200 != 0) return;
                BlockPos orbPos = net.minecraft.nbt.NBTUtil.getPosFromTag(NBTUtil.getCompoundTag(stack, "orbPos"));
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
                                nbt.setTag("pos", net.minecraft.nbt.NBTUtil.createPosTag(pos));
                                linkedTagList.appendTag(nbt);
                            }
                            NBTUtil.setTag(stack, "linkedPosList", linkedTagList);

                            NBTTagList unlinkedTagList = new NBTTagList();
                            for (BlockPos pos : unlinkedList) {
                                NBTTagCompound nbt = new NBTTagCompound();
                                nbt.setTag("pos", net.minecraft.nbt.NBTUtil.createPosTag(pos));
                                unlinkedTagList.appendTag(nbt);
                            }
                            NBTUtil.setTag(stack, "unlinkedPosList", unlinkedTagList);

                        }
                    } else {
                        NBTUtil.setTag(stack, "orbPos", net.minecraft.nbt.NBTUtil.createPosTag(BlockPos.ORIGIN));
                        NBTUtil.setTag(stack, "linkedPosList", new NBTTagList());
                        NBTUtil.setTag(stack, "unlinkedPosList", new NBTTagList());
                    }
                }
            }
        }
    }

    public static final ResourceLocation HILIGHT_TEXTURE = Theora.location("textures/misc/wand_hilight.png");

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public static void renderLinkable(RenderWorldLastEvent event) {
        Minecraft mc = Minecraft.getMinecraft();
        EntityPlayerSP player = mc.player;
        if (player == null) return;
        for (EnumHand hand : EnumHand.values()) {
            ItemStack stack = player.getHeldItem(hand);
            if (stack.getItem() == TheoraItems.WAND) {
                int mode = NBTUtil.getInteger(stack, "mode");
                if (mode == 1) {
                    BlockPos orbPos = net.minecraft.nbt.NBTUtil.getPosFromTag(NBTUtil.getCompoundTag(stack, "orbPos"));
                    if (orbPos != BlockPos.ORIGIN && player.world.isBlockLoaded(orbPos)) {
                        double x = player.lastTickPosX + (player.posX - player.lastTickPosX) * event.getPartialTicks();
                        double y = player.lastTickPosY + (player.posY - player.lastTickPosY) * event.getPartialTicks();
                        double z = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * event.getPartialTicks();
                        GlStateManager.pushMatrix();
                        GlStateManager.translate(-x, -y, -z);
                        GlStateManager.enableBlend();
                        GlStateManager.blendFunc(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE);
                        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 61680.0F, 0.0F);
                        GlStateManager.disableDepth();
                        GlStateManager.pushMatrix();
                        ColorUtil.glColor(0x6b3396, 0.9F);
                        GlStateManager.translate(orbPos.getX() + 0.5D, orbPos.getY() + 0.3D, orbPos.getZ() + 0.5D);
                        RenderUtil.renderFacingQuad(HILIGHT_TEXTURE, 0.7D);
                        GlStateManager.popMatrix();
                        if (NBTUtil.hasKey(stack, "linkedPosList", Constants.NBT.TAG_LIST)) {
                            NBTTagList tagList = (NBTTagList) NBTUtil.getTag(stack, "linkedPosList");
                            for (int i = 0; i < tagList.tagCount(); i++) {
                                NBTTagCompound nbt = tagList.getCompoundTagAt(i);
                                BlockPos pos = net.minecraft.nbt.NBTUtil.getPosFromTag(nbt.getCompoundTag("pos"));
                                GlStateManager.pushMatrix();
                                ColorUtil.glColor(0x4e8448, 0.7F);
                                GlStateManager.translate(pos.getX() + 0.5D, pos.getY() + 0.3D, pos.getZ() + 0.5D);
                                RenderUtil.renderFacingQuad(HILIGHT_TEXTURE, 0.4D);
                                GlStateManager.popMatrix();
                            }
                        }
                        if (NBTUtil.hasKey(stack, "unlinkedPosList", Constants.NBT.TAG_LIST)) {
                            NBTTagList tagList = (NBTTagList) NBTUtil.getTag(stack, "unlinkedPosList");
                            for (int i = 0; i < tagList.tagCount(); i++) {
                                NBTTagCompound nbt = tagList.getCompoundTagAt(i);
                                BlockPos pos = net.minecraft.nbt.NBTUtil.getPosFromTag(nbt.getCompoundTag("pos"));
                                GlStateManager.pushMatrix();
                                ColorUtil.glColor(0x686853, 0.3F);
                                GlStateManager.translate(pos.getX() + 0.5D, pos.getY() + 0.3D, pos.getZ() + 0.5D);
                                RenderUtil.renderFacingQuad(HILIGHT_TEXTURE, 0.2D);
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

