package hannina.patches.interfaces;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import hannina.interfaces.card.OnEnterDiscardPileCard;

@SuppressWarnings("unused")
public class OnEnterDiscardPileCardPatch {
	@SpirePatch(clz = CardGroup.class, method = "moveToDiscardPile", paramtypez = {AbstractCard.class})
	public static class MoveToDiscardPilePatch {
		@SpirePostfixPatch
		public static void Postfix(CardGroup _inst, AbstractCard c) {
			if (c instanceof OnEnterDiscardPileCard)
				((OnEnterDiscardPileCard) c).onEnterDiscardPile();
		}
	}
}
