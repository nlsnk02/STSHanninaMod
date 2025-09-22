package hannina.actions;

import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.EmptyDeckShuffleAction;
import com.megacrit.cardcrawl.actions.common.ShuffleAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import hannina.misc.ReunionModifier;

import java.util.ArrayList;

public class XuedingeAction extends AbstractGameAction {

    AbstractPlayer p = AbstractDungeon.player;

    public XuedingeAction(int amount) {
        this.amount = amount;
        this.actionType = ActionType.DRAW;
    }

    @Override
    public void update() {
        if (p.drawPile.isEmpty() && p.discardPile.isEmpty()) {
            this.isDone = true;
            return;
        }

        if (p.drawPile.isEmpty()) {
            addToTop(new XuedingeAction(amount));
            addToTop(new ShuffleAction(AbstractDungeon.player.drawPile, false));
            addToTop(new EmptyDeckShuffleAction());
            this.isDone = true;
            return;
        }

        AbstractCard c = p.drawPile.getTopCard();

        c.update();//刷新他的modifier

        if (!CardModifierManager.hasModifier(c, "ReunionModifier")) {
            addToTop(new PlayACardAction(c, p.drawPile, null, false));
        }else{
            ReunionModifier r = (ReunionModifier) CardModifierManager.getModifiers(c, "ReunionModifier").get(0);
            ArrayList<AbstractCard> list = new ArrayList<>(r.union);
            list.remove(c);

            int flag = this.amount;

            while(!list.isEmpty() && flag > 0) {
                int i = AbstractDungeon.cardRandomRng.random(list.size()-1);
                addToTop(new PlayACardAction(list.get(i).makeSameInstanceOf(), null, null, true));
                list.remove(i);
                --flag;
            }
        }
        this.isDone = true;
    }
}
