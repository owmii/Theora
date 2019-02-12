package xieao.theora.core;

import xieao.theora.ability.AbilityMePig;
import xieao.theora.api.player.Ability;
import xieao.theora.core.lib.annotation.AutoLoad;

@AutoLoad
public class IAbilities {
    public static final Ability ME_PIG = Ability.register("me_pig", new AbilityMePig(), 3);
}
