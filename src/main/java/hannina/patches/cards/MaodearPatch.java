package hannina.patches.cards;

import com.evacipated.cardcrawl.modthespire.lib.SpireInstrumentPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import hannina.powers.MaodearxingtaiPower;
import hannina.utils.ModHelper;
import javassist.CannotCompileException;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;

@SpirePatch(clz = UseCardAction.class, method = "update")
public class MaodearPatch {

    public static boolean onUsedCard(AbstractCard c) {
        if (ModHelper.InCombat() && AbstractDungeon.player.hasPower(MaodearxingtaiPower.POWER_ID)) {
            int amount = AbstractDungeon.player.getPower(MaodearxingtaiPower.POWER_ID).amount;
            if (amount > 0) {
                AbstractDungeon.player.getPower(MaodearxingtaiPower.POWER_ID).amount--;

                ModHelper.refreshCard(c);

                c.freeToPlayOnce = true;
                AbstractDungeon.player.drawPile.moveToBottomOfDeck(c);

                return false;
            }
        }

        return true;
    }

    //猫deer形态
    @SpireInstrumentPatch
    public static ExprEditor Instrument() {
        return new ExprEditor() {
            public void edit(MethodCall m) throws CannotCompileException {
                if (m.getMethodName().equals("moveToDiscardPile"))
                    m.replace(String.format("{ if (%s.onUsedCard($1)) { $_ = $proceed($$); } }", new Object[]{MaodearPatch.class.getName()}));
            }
        };
    }
}
