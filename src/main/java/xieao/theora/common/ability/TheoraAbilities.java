package xieao.theora.common.ability;

import xieao.theora.api.player.ability.Ability;

public class TheoraAbilities {

    public static final AbilityMePig ME_PIG = new AbilityMePig();
    public static final AbilityTheCloud THE_CLOUD = new AbilityTheCloud();
    public static final AbilityUnihorn UNIHORN = new AbilityUnihorn();
    public static final AbilityTheGhost THE_GHOST = new AbilityTheGhost();
    public static final AbilityTheWizard THE_WIZARD = new AbilityTheWizard();
    public static final AbilityEnderMe ENDER_ME = new AbilityEnderMe();

    public static void register() {
        Ability.register(ME_PIG, "mepig");
        Ability.register(THE_CLOUD, "thecloud");
        Ability.register(UNIHORN, "unihorn");
        Ability.register(THE_GHOST, "theghost");
        Ability.register(THE_WIZARD, "thewizard");
        Ability.register(ENDER_ME, "enderme");
    }

}
