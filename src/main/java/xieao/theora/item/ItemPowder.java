package xieao.theora.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import xieao.theora.Theora;
import xieao.theora.api.TheoraAPI;
import xieao.theora.api.player.HorData;
import xieao.theora.entity.EntityWorker;
import xieao.theora.lib.util.PlayerUtil;
import xieao.theora.network.packet.gui.OpenPlayerGui;

public class ItemPowder extends ItemBase {
    public ItemPowder(Properties properties) {
        super(properties);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
        if (!world.isRemote && player instanceof EntityPlayerMP) {
            TheoraAPI.getPlayerData(player).ifPresent(playerData -> {
                HorData horData = playerData.hor;
                horData.setGuiOpen(true);
                horData.setLoaded(PlayerUtil.getHor(player) != null);
                Theora.NET.toClient(new OpenPlayerGui(horData.serialize()), player);
            });
        }
        return super.onItemRightClick(world, player, hand);
    }

    public static final int[][] OFFSETS_0 = new int[][]{{0, 0}, {0, 1}, {1, 0}, {1, 1}};
    public static final int[][] OFFSETS_1 = new int[][]{{0, 0}, {0, -1}, {1, 0}, {1, -1}};
    public static final int[][] OFFSETS_2 = new int[][]{{0, 0}, {0, -1}, {-1, 0}, {-1, -1}};
    public static final int[][] OFFSETS_3 = new int[][]{{0, 0}, {0, 1}, {-1, 0}, {-1, 1}};
    public static final int[][][] ALL = new int[][][]{OFFSETS_0, OFFSETS_1, OFFSETS_2, OFFSETS_3};

    @Override
    public EnumActionResult onItemUse(ItemUseContext context) {
        World world = context.getWorld();
        EntityPlayer player = context.getPlayer();
        if (player != null && !PlayerUtil.isFake(player)) {
            BlockPos pos = context.getPos();
            if (isObsidian(world, pos)) {
                for (int i = 0; i < 4; i++) {
                    BlockPos pos1 = pos.add(0, i, 0);
                    if (!isObsidian(world, pos1)) {
                        return EnumActionResult.FAIL;
                    } else {
                        if (world.isAirBlock(pos1.up())) {
                            for (int[][] offs : ALL) {
                                BlockPos pos2 = null;
                                int count = 0;
                                for (int[] off : offs) {
                                    BlockPos pos3 = pos1.add(off[0], 0, off[1]);
                                    if (isObsidian(world, pos3)
                                            && isObsidian(world, pos3.add(1, 0, 0))
                                            && isObsidian(world, pos3.add(0, 0, 1))
                                            && isObsidian(world, pos3.add(1, 0, 1))) {
                                        pos2 = pos3;
                                        break;
                                    }
                                }
                                if (pos2 != null) {
                                    for (int[] off : OFFSETS_0) {
                                        for (int j = 0; j >= -3; j--) {
                                            BlockPos pos3 = pos2.add(off[0], j, off[1]);
                                            if (isObsidian(world, pos3)) {
                                                count++;
                                            }
                                        }
                                    }
                                    if (count == 16) {
                                        if (!world.isRemote) {
                                            EntityWorker worker = new EntityWorker(EntityWorker.Job.BUILD_HOR, player, world);
                                            worker.setPosition(pos2.getX(), pos2.getY(), pos2.getZ());
                                            world.spawnEntity(worker);
                                            if (!player.isCreative()) {
                                                context.getItem().shrink(1);
                                            }
                                        }
                                        return EnumActionResult.SUCCESS;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return super.onItemUse(context);
    }

    private boolean isObsidian(World world, BlockPos pos) {
        return world.getBlockState(pos).getBlock() == Blocks.OBSIDIAN;
    }
}
