package zeroneye.theora.api.registry;

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
        if (!getString().equals("theora:null")) {
            throw new IllegalStateException("Attempted to set registry name with existing registry name! New: " + name + " Old: " + getRegistryName());
        } else if (name.isEmpty()) {
            throw new IllegalStateException("Empty name!!");
        }
        this.registryName = GameData.checkPrefix(name);
        return (T) this;
    }

    public String getNamespace() {
        return getRegistryName().getNamespace();
    }

    public String getPath() {
        return getRegistryName().getPath();
    }

    public final ResourceLocation getRegistryName() {
        return registryName;
    }

    public final String getString() {
        return getRegistryName().toString();
    }
}
