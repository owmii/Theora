package xieao.theora.core.lib.util;

import net.minecraft.util.ResourceLocation;
import xieao.theora.Theora;

public class Location extends ResourceLocation {
    public static final Location ROOT = create();
    public static final Location TEXTURES = create().parent("textures/");
    public static final Location MODELS = create().parent("models/");
    public static final Location SOUNDS = create().parent("sounds/");

    public static final Location TEXTURES_BLOCKS = create().parent("textures/blocks/");
    public static final Location TEXTURES_ITEMS = create().parent("textures/items/");
    public static final Location TEXTURES_GUI = create().parent("textures/gui/");
    public static final Location TEXTURES_PARTICLES = create().parent("textures/particles/");
    public static final Location TEXTURES_ENTITY = create().parent("textures/entity/");
    public static final Location TEXTURES_TER = create().parent("textures/ter/");
    public static final Location TEXTURES_MISC = create().parent("textures/misc/");
    public static final Location MODELS_BLOCKS = create().parent("models/blocks/");
    public static final Location MODELS_ITEMS = create().parent("models/items/");

    private String parent = "";
    private String target = "";

    public Location() {
        super(new String[]{Theora.ID, "location.empty"});
    }

    @Override
    public String getPath() {
        if (this.target.isEmpty()) {
            this.target = "empty";
        }
        String path = super.getPath().replace("location.empty", "") + this.parent + this.target;
        this.target = "";
        return path;
    }

    public Location get(String target) {
        this.target = target;
        return this;
    }

    private Location parent(String parent) {
        this.parent = parent;
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

    static Location create() {
        return new Location();
    }
}
