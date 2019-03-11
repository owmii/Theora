package xieao.theora.inventory;

import net.minecraft.entity.player.EntityPlayer;
import xieao.theora.block.gate.TileGate;

public class ContainerGate extends ContainerBase<TileGate> {
    public ContainerGate(EntityPlayer player, TileGate inv) {
        super(player, inv);

        addPlayerInv(player.inventory, 8, 142, 84);
    }
}
