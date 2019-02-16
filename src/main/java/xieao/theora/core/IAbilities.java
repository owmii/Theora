package xieao.theora.core;

import xieao.theora.ability.AbilityClouder;
import xieao.theora.ability.AbilityEnderMe;
import xieao.theora.ability.AbilityMePig;
import xieao.theora.api.player.Ability;
import xieao.theora.core.lib.annotation.AutoLoad;

@AutoLoad
public class IAbilities {
    public static final Ability ME_PIG = Ability.register("me_pig", new AbilityMePig(), 3);
    public static final Ability ENDER_ME = Ability.register("ender_me", new AbilityEnderMe(), 3);
    public static final Ability CLOUDER = Ability.register("clouder", new AbilityClouder(), 3);
}
