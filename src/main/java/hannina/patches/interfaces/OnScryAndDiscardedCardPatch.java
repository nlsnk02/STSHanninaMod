package hannina.patches.interfaces;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.actions.utility.ScryAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import hannina.interfaces.card.OnScryAndDiscardedCard;
import javassist.CannotCompileException;
import javassist.CtBehavior;

import java.util.ArrayList;

@SuppressWarnings("unused")
public class OnScryAndDiscardedCardPatch {
	@SpirePatch(clz = ScryAction.class, method = "update", paramtypez = {})
	public static class ScryUpdatePatch {
		private static class Locator extends SpireInsertLocator {
			@Override
			public int[] Locate(CtBehavior ctBehavior) throws CannotCompileException, PatchingException {
				return LineFinder.findInOrder(ctBehavior,
						new Matcher.MethodCallMatcher(ArrayList.class, "clear"));
			}
		}
		
		@SpireInsertPatch(locator = Locator.class)
		public static void Insert(ScryAction _inst) {
			for (AbstractCard card : AbstractDungeon.gridSelectScreen.selectedCards)
				if (card instanceof OnScryAndDiscardedCard)
					((OnScryAndDiscardedCard) card).onScryAndDiscarded();
		}
	}
}
