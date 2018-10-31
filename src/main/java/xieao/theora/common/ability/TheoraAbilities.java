package xieao.theora.common.ability;

import xieao.theora.api.player.ability.Ability;

public class TheoraAbilities {

    public static final AbilityMePig ME_PIG = new AbilityMePig();

    public static void register() {
        Ability.register(ME_PIG, "mepig");
    }

}
