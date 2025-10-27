package hannina.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

//暂时懒得想更好的写法了..
public class RemoveSpecificCardAction extends AbstractGameAction {

    private final AbstractCard card;

    public RemoveSpecificCardAction(AbstractCard card) {
        this.card = card;
    }

    @Override
    public void update() {
        if (card != null) {
            if (AbstractDungeon.player.discardPile.contains(card))
                AbstractDungeon.player.discardPile.moveToExhaustPile(card);
            if (AbstractDungeon.player.hand.contains(card))
                AbstractDungeon.player.hand.moveToExhaustPile(card);
            if (AbstractDungeon.player.drawPile.contains(card))
                AbstractDungeon.player.drawPile.moveToExhaustPile(card);
        }
        isDone = true;
    }
}
