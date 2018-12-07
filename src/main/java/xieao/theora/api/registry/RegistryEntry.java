package xieao.theora.api.registry;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.GameData;

public class RegistryEntry<T> {

    private ResourceLocation registryName = new ResourceLocation("theora:null");

    public final T setRegistryName(String modID, String name) {
        return setRegistryName(modID + ":" + name);
    }

    public final T setRegistryName(ResourceLocation name) {
        return setRegistryName(name.toString());
    }

    @SuppressWarnings("unchecked")
    public final T setRegistryName(String name) {
        if (!getRegistryString().equals("theora:null")) {
            throw new IllegalStateException("Attempted to set registry name with existing registry name! New: " + name + " Old: " + getRegistryName());
        } else if (name.isEmpty()) {
            throw new IllegalStateException("Empty name!!");
        }
        this.registryName = GameData.checkPrefix(name);
        return (T) this;
    }

    public String getResourceDomain() {
        return getRegistryName().getResourceDomain();
    }

    public String getResourcePath() {
        return getRegistryName().getResourcePath();
    }

    public final ResourceLocation getRegistryName() {
        return registryName;
    }

    public final String getRegistryString() {
        return getRegistryName().toString();
    }
}
