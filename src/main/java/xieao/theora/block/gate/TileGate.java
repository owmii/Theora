package xieao.theora.block.gate;

import org.apache.commons.lang3.tuple.Pair;
import xieao.theora.block.TileBase;
import xieao.theora.core.ITiles;

import javax.annotation.Nullable;
import java.util.UUID;

public class TileGate extends TileBase.Tickable {
    @Nullable
    protected Pair<UUID, String> owner;

    public TileGate() {
        super(ITiles.GATE);
    }

    @Override
    public void tick() {
        super.tick();
    }

    @Nullable
    public Pair<UUID, String> getOwner() {
        return owner;
    }

    public void setOwner(UUID id, String name) {
        this.owner = Pair.of(id, name);
    }
}
