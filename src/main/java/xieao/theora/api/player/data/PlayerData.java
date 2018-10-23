package xieao.theora.api.player.data;

import net.minecraft.nbt.NBTTagCompound;

public final class PlayerData implements IPlayerData {

    private static final String TAG_ACID_STORED = "acid.stored";
    private static final String TAG_ACID_VIAL = "acid.vial.acquired";

    private float storedAcid;
    private boolean hasAcidVial;

    @Override
    public NBTTagCompound serializeNBT(NBTTagCompound nbt) {
        nbt.setFloat(TAG_ACID_STORED, this.storedAcid);
        nbt.setBoolean(TAG_ACID_VIAL, this.hasAcidVial);
        return nbt;
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {
        this.storedAcid = nbt.getFloat(TAG_ACID_STORED);
        this.hasAcidVial = nbt.getBoolean(TAG_ACID_VIAL);
    }

    public float getMaxAcid() {
        return 200.0F;
    }

    public float getStoredAcid() {
        return storedAcid;
    }

    public void setStoredAcid(float storedAcid) {
        this.storedAcid = storedAcid;
    }

    public boolean hasAcidVial() {
        return hasAcidVial;
    }

    public void setHasAcidVial(boolean hasAcidVial) {
        this.hasAcidVial = hasAcidVial;
    }
}
