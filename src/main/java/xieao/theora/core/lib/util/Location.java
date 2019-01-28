package xieao.theora.core.lib.util;

import net.minecraft.util.ResourceLocation;
import xieao.theora.Theora;

public class Location extends ResourceLocation {
    public static final Location ROOT = new Location(Theora.ID);
    public static final Location TEXTURES = ROOT.get("textures/");
    public static final Location MODELS = ROOT.get("models/");
    public static final Location SOUNDS = ROOT.get("sounds/");

    public static final Location TEXTURES_BLOCKS = TEXTURES.get("blocks/");
    public static final Location TEXTURES_ITEMS = TEXTURES.get("items/");
    public static final Location TEXTURES_GUI = TEXTURES.get("gui/");
    public static final Location TEXTURES_PARTICLES = TEXTURES.get("particles/");
    public static final Location TEXTURES_ENTITY = TEXTURES.get("entity/");
    public static final Location TEXTURES_TER = TEXTURES.get("ter/");
    public static final Location TEXTURES_MISC = TEXTURES.get("misc/");
    public static final Location MODELS_BLOCKS = MODELS.get("blocks/");
    public static final Location MODELS_ITEMS = MODELS.get("items/");

    private String target = "";

    public Location(String id) {
        super(new String[]{id, "location.empty"});
    }

    @Override
    public String getPath() {
        return super.getPath().replace("location.empty", "") + this.target;
    }

    public Location get(String target) {
        this.target += target;
        return this;
    }

    @Override
    public int compareTo(ResourceLocation location) {
        int i = getPath().compareTo(location.getPath());
        if (i == 0) {
            i = getNamespace().compareTo(location.getNamespace());
        }
        return i;
    }

    @Override
    public int hashCode() {
        return 31 * getNamespace().hashCode() + getPath().hashCode();
    }

    @Override
    public boolean equals(Object location) {
        if (this == location) {
            return true;
        } else if (!(location instanceof ResourceLocation)) {
            return false;
        } else {
            ResourceLocation resourcelocation = (ResourceLocation) location;
            return getNamespace().equals(resourcelocation.getNamespace())
                    && getPath().equals(resourcelocation.getPath());
        }
    }

    @Override
    public String toString() {
        return getNamespace() + ':' + getPath();
    }
}
