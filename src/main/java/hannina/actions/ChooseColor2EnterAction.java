package hannina.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.watcher.ChooseOneAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import hannina.fantasyCard.BlueColorCard;
import hannina.fantasyCard.GreenColorCard;
import hannina.fantasyCard.PurpleColorCard;
import hannina.fantasyCard.RedColorCard;

import java.util.ArrayList;

public class ChooseColor2EnterAction extends AbstractGameAction {
    @Override
    public void update() {
        ArrayList<AbstractCard> stanceChoices = new ArrayList<>();
        stanceChoices.add(new RedColorCard());
        stanceChoices.add(new GreenColorCard());
        stanceChoices.add(new BlueColorCard());
        stanceChoices.add(new PurpleColorCard());
        addToTop(new ChooseOneAction(stanceChoices));
        this.isDone = true;
    }
}
