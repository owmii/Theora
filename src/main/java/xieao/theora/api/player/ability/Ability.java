package xieao.theora.api.player.ability;

import net.minecraft.util.ResourceLocation;
import xieao.theora.api.registry.RegistryEntry;

import java.util.HashMap;
import java.util.Map;

public class Ability extends RegistryEntry<Ability> {

    public static final Map<ResourceLocation, Ability> REGISTRY;
    public static final Ability EMPTY;

    static {
        REGISTRY = new HashMap<>();
        EMPTY = register(new Ability(), "theora:empty");
    }

    public static Ability register(Ability ability, String name) {
        ability.setRegistryName(name);
        REGISTRY.put(ability.getRegistryName(), ability);
        return ability;
    }

    public static Ability getAbility(String name) {
        Ability ability = REGISTRY.get(new ResourceLocation(name));
        return ability == null ? EMPTY : ability;
    }

    public boolean isEmpty() {
        return this == EMPTY;
    }
}
