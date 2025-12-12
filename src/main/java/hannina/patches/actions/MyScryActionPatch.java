package hannina.patches.actions;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.actions.utility.ScryAction;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.screens.select.GridCardSelectScreen;
import hannina.actions.MyScryAction;
import javassist.CannotCompileException;
import javassist.CtBehavior;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;

import java.util.ArrayList;

@SuppressWarnings("unused")
public class MyScryActionPatch {
	@SpirePatch(clz = ScryAction.class, method = "update")
	public static class UpdatePatch {
		private static class BeforeOpenLocator extends SpireInsertLocator {
			@Override
			public int[] Locate(CtBehavior ctBehavior) throws CannotCompileException, PatchingException {
				return LineFinder.findInOrder(ctBehavior,
						new Matcher.MethodCallMatcher(GridCardSelectScreen.class, "open"));
			}
		}
		
		@SpireInsertPatch(locator = BeforeOpenLocator.class, localvars = {"tmpGroup"})
		public static void Insert(ScryAction _inst, CardGroup tmpGroup) {
			if (_inst instanceof MyScryAction)
				((MyScryAction) _inst).scriedCards = new ArrayList<>(tmpGroup.group);
		}
		
		public static void before(ScryAction _inst) {
			if (_inst instanceof MyScryAction)
				((MyScryAction) _inst).selectedCards = new ArrayList<>(AbstractDungeon.gridSelectScreen.selectedCards);
		}
		
		public static void after(ScryAction _inst) {
			if (_inst instanceof MyScryAction)
				((MyScryAction) _inst).then.accept(((MyScryAction) _inst).scriedCards,
						((MyScryAction) _inst).selectedCards);
		}
		
		@SpireInstrumentPatch
		public static ExprEditor Instrument() {
			return new ExprEditor() {
				@Override
				public void edit(MethodCall m) throws CannotCompileException {
					if (m.getMethodName().equals("clear"))
						m.replace(" { " +
								MyScryActionPatch.UpdatePatch.class.getName() + ".before(this); $proceed($$); " +
								MyScryActionPatch.UpdatePatch.class.getName() + ".after(this); } ");
				}
			};
		}
	}
}
