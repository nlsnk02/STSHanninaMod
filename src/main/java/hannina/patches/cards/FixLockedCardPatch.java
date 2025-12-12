package hannina.patches.cards;

import basemod.ReflectionHacks;
import basemod.abstracts.CustomCard;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import hannina.fantasyCard.AbstractHanninaCard;

@SuppressWarnings("unused")
public class FixLockedCardPatch {
//	@SpirePatch(clz = AbstractCard.class, method = "renderPortrait", paramtypez = {SpriteBatch.class})
//	public static class RenderPortraitPatch {
//		@SpireInsertPatch(rloc = 45, localvars = {"drawX", "drawY"})
//		public static SpireReturn<Void> Insert(AbstractCard _inst, SpriteBatch sb, float drawX, float drawY) {
//			if (_inst instanceof AbstractHanninaCard && _inst.isLocked) {
//				if (_inst.portrait != null) {
//					sb.draw(_inst.portrait, drawX, drawY + 72.0F,
//							125.0F, 23.0F, 250.0F, 190.0F,
//							_inst.drawScale * Settings.scale, _inst.drawScale * Settings.scale,
//							_inst.angle);
//
//					return SpireReturn.Return();
//				}
//			}
//
//			return SpireReturn.Continue();
//		}
//	}
//
//	@SpirePatch(clz = AbstractCard.class, method = "renderJokePortrait", paramtypez = {SpriteBatch.class})
//	public static class RenderJokePortraitPatch {
//		@SpireInsertPatch(rloc = 45, localvars = {"drawX", "drawY"})
//		public static SpireReturn<Void> Insert(AbstractCard _inst, SpriteBatch sb, float drawX, float drawY) {
//			return RenderPortraitPatch.Insert(_inst, sb, drawX, drawY);
//		}
//	}
}
