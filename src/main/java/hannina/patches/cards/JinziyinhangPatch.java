package hannina.patches.cards;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import hannina.powers.JinziyinhangPower;
import hannina.utils.ModHelper;

@SpirePatch(clz = AbstractPlayer.class, method = "gainGold")
public class JinziyinhangPatch {
    @SpirePrefixPatch
    public static void patch(AbstractPlayer __instance, @ByRef int[] gold) {
        if(ModHelper.isInCombat() && __instance.hasPower(JinziyinhangPower.POWER_ID)){
            int amount = __instance.getPower(JinziyinhangPower.POWER_ID).amount;
            gold[0] *= amount+1;
        }
    }
}
