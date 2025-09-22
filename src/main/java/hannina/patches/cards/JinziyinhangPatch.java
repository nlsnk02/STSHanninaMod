package hannina.patches.cards;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hannina.powers.JinziyinhangPower;
import hannina.powers.MaodearxingtaiPower;
import hannina.utils.ModHelper;
import hannina.utils.MonsterDieThisTurnManager;
import javassist.CannotCompileException;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;

@SpirePatch(clz = AbstractPlayer.class, method = "gainGold")
public class JinziyinhangPatch {
    @SpirePrefixPatch
    public static void patch(AbstractPlayer __instance, @ByRef int[] gold) {
        if(ModHelper.InCombat() && __instance.hasPower(JinziyinhangPower.POWER_ID)){
            int amount = __instance.getPower(JinziyinhangPower.POWER_ID).amount;
            gold[0] *= amount+1;
        }
    }
}
