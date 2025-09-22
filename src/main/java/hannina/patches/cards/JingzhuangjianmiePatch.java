package hannina.patches.cards;

import com.evacipated.cardcrawl.modthespire.lib.SpireInstrumentPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hannina.powers.MaodearxingtaiPower;
import hannina.utils.ModHelper;
import hannina.utils.MonsterDieThisTurnManager;
import javassist.CannotCompileException;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;

@SpirePatch(clz = AbstractMonster.class, method = "die", paramtypes = {})
public class JingzhuangjianmiePatch {
    @SpirePostfixPatch
    public static void patch(AbstractMonster __instance) {
        if(!__instance.hasPower("Minion"))
            MonsterDieThisTurnManager.amount++;
    }
}
