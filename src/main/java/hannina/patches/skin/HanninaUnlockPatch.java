package hannina.patches.skin;

import basemod.ReflectionHacks;
import basemod.abstracts.CustomUnlock;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.MathHelper;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.neow.NeowUnlockScreen;
import com.megacrit.cardcrawl.screens.GameOverScreen;
import com.megacrit.cardcrawl.ui.buttons.UnlockConfirmButton;
import com.megacrit.cardcrawl.unlock.AbstractUnlock;
import hannina.modcore.Enums;
import hannina.utils.ModHelper;
import hannina.utils.SkinSelectScreen;
import hannina.utils.UnlockHelper;
import javassist.CannotCompileException;
import javassist.CtBehavior;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.WeakHashMap;

@SuppressWarnings("unused")
public class HanninaUnlockPatch {
	private static final Logger logger = LogManager.getLogger(HanninaUnlockPatch.class.getName());

	private static WeakHashMap<ArrayList<?>, UnlockHelper.UnlockItem> patched = new WeakHashMap<>();
	
	private static ArrayList<UnlockHelper.UnlockItem> unlockItems = new ArrayList<>();
	private static int currentIndex = 0;
	
	private static ArrayList<AbstractUnlock> convert(UnlockHelper.UnlockItem item) {
		ArrayList<AbstractUnlock> unlocks = new ArrayList<>();
		
		if (item.cards != null && !item.cards.isEmpty()) {
			for (AbstractCard card : item.cards) {
				AbstractUnlock unlock = new CustomUnlock(card.cardID);
				unlock.card = card;
				
				unlocks.add(unlock);
			}
		}
		else if (item.skinIndex >= 0) {
			AbstractUnlock unlock = new AbstractUnlock();
			unlock.type = AbstractUnlock.UnlockType.CHARACTER;
			unlock.player = CardCrawlGame.characterManager.getCharacter(Enums.HANNINA_CLASS);
			
			SkinSelectScreen.Inst.index = item.skinIndex;
			SkinSelectScreen.Inst.refresh();
			
			unlocks.add(unlock);
		}
		
		return unlocks;
	}
	
	private static ArrayList<AbstractUnlock> getNextUnlocks() {
		while (currentIndex < unlockItems.size()) {
			UnlockHelper.UnlockItem item = unlockItems.get(currentIndex++);
			
			ArrayList<AbstractUnlock> unlocks = convert(item);
			if (!unlocks.isEmpty())
				return unlocks;
		}
		
		return new ArrayList<>();
	}

	@SpirePatch(clz = GameOverScreen.class, method = "calculateUnlockProgress", paramtypez = {})
	public static class CalculateUnlockProgressPatch {
		@SpirePostfixPatch
		public static void Postfix(GameOverScreen _inst) {
			unlockItems = UnlockHelper.onGameOver(_inst);
			currentIndex = 0;

			if (ReflectionHacks.getPrivate(_inst, GameOverScreen.class, "unlockBundle") == null) {
				ArrayList<AbstractUnlock> unlocks = getNextUnlocks();

				if (!unlocks.isEmpty()) {
					ReflectionHacks.setPrivate(_inst, GameOverScreen.class, "unlockBundle",
							unlocks);
					
					patched.put(unlocks, unlockItems.get(currentIndex - 1));
				}
			}
		}
	}

	private static final UIStrings uiStrings =
			CardCrawlGame.languagePack.getUIString(ModHelper.makeID("GameOverScreen"));

	public static boolean handle(NeowUnlockScreen screen) {
		if (patched.containsKey(screen.unlockBundle)) {
			String title = patched.get(screen.unlockBundle).title;
			if (title == null || title.isEmpty())
				title = uiStrings.TEXT[0];

			AbstractDungeon.dynamicBanner.appear(title);

			return true;
		}

		return false;
	}

	@SpirePatch(clz = NeowUnlockScreen.class, method = "open", paramtypez = {ArrayList.class, boolean.class})
	public static class UnlockScreenOpenPatch {
		@SpireInstrumentPatch
		public static ExprEditor Instrument() {
			return new ExprEditor() {
				@Override
				public void edit(MethodCall m) throws CannotCompileException {
					if (m.getMethodName().equals("appearInstantly"))
						m.replace(" { if (" + HanninaUnlockPatch.class.getName() +
								".handle(this)) { } else { $_ = $proceed($$); } }");
				}
			};
		}
		
		@SpirePostfixPatch
		public static void Postfix(NeowUnlockScreen _inst, ArrayList<AbstractUnlock> unlocks, boolean isVictory) {
			UnlockScreenReOpenPatch.Postfix(_inst);
		}
	}

	@SpirePatch(clz = NeowUnlockScreen.class, method = "reOpen", paramtypez = {})
	public static class UnlockScreenReOpenPatch {
		@SpireInstrumentPatch
		public static ExprEditor Instrument() {
			return UnlockScreenOpenPatch.Instrument();
		}
		
		@SpirePostfixPatch
		public static void Postfix(NeowUnlockScreen _inst) {
			if (patched.containsKey(_inst.unlockBundle)) {
				if (_inst.unlockBundle.size() == 4 &&
						_inst.unlockBundle.get(0).type == AbstractUnlock.UnlockType.CARD) {
					for (int i = 0; i < _inst.unlockBundle.size(); i++) {
						AbstractCard c = _inst.unlockBundle.get(i).card;
						c.current_x = Settings.WIDTH * 0.2F * (i + 1);
						c.target_x = Settings.WIDTH * 0.2F * (i + 1);
					}
				}
			}
		}
	}

	@SpirePatch(clz = NeowUnlockScreen.class, method = "render", paramtypez = {SpriteBatch.class})
	public static class UnlockScreenRenderPatch {
		@SpireInsertPatch(rloc = 18)
		public static SpireReturn<Void> Insert(NeowUnlockScreen _inst, SpriteBatch sb) {
			if (patched.containsKey(_inst.unlockBundle)) {
				String tip = patched.get(_inst.unlockBundle).tip;
				
				if (tip != null && !tip.isEmpty()) {
					sb.draw(ImageMaster.UNLOCK_TEXT_BG,
							(float)Settings.WIDTH / 2.0F - 500.0F,
							(float)Settings.HEIGHT / 2.0F - 330.0F * Settings.scale - 130.0F,
							500.0F, 130.0F, 1000.0F, 260.0F,
							Settings.scale * 1.2F, Settings.scale * 0.8F,
							0.0F, 0, 0, 1000, 260,
							false, false);
					
					FontHelper.renderFontCentered(sb, FontHelper.buttonLabelFont,
							tip,
							(float) Settings.WIDTH / 2.0F,
							(float) Settings.HEIGHT / 2.0F - 330.0F * Settings.scale,
							Settings.CREAM_COLOR);
				}
				
				_inst.button.render(sb);
				
				return SpireReturn.Return();
			}
			
			return SpireReturn.Continue();
		}
	}
	
	@SpirePatch(clz = UnlockConfirmButton.class, method = "update", paramtypez = {})
	public static class UnlockConfirmButtonUpdatePatch {
		private static class Locator extends SpireInsertLocator {
			@Override
			public int[] Locate(CtBehavior ctBehavior) throws CannotCompileException, PatchingException {
				return LineFinder.findInOrder(ctBehavior,
						new Matcher.MethodCallMatcher(UnlockConfirmButton.class, "hide"));
			}
		}
		
		@SpireInsertPatch(locator = Locator.class)
		public static SpireReturn<Void> Insert(UnlockConfirmButton _inst) {
			patched.clear();
			ArrayList<AbstractUnlock> unlocks = getNextUnlocks();
			
			if (!unlocks.isEmpty()) {
				CardCrawlGame.sound.stop("UNLOCK_SCREEN", AbstractDungeon.gUnlockScreen.id);
				patched.put(unlocks, unlockItems.get(currentIndex - 1));
				AbstractDungeon.gUnlockScreen.open(unlocks, false); // isVictory is not used
				
				Color textColor = ReflectionHacks.getPrivate(_inst, UnlockConfirmButton.class, "textColor");
				float target_a = ReflectionHacks.getPrivate(_inst, UnlockConfirmButton.class, "target_a");
				Color btnColor = ReflectionHacks.getPrivate(_inst, UnlockConfirmButton.class, "btnColor");
				
				textColor.a = MathHelper.fadeLerpSnap(textColor.a, target_a);
				btnColor.a = textColor.a;
				
				return SpireReturn.Return();
			}
			else
				return SpireReturn.Continue();
		}
	}
}
