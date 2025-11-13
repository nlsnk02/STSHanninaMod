package hannina.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.watcher.ChooseOneAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import hannina.fantasyCard.BlueColorCard;
import hannina.fantasyCard.GreenColorCard;
import hannina.fantasyCard.PurpleColorCard;
import hannina.fantasyCard.RedColorCard;
import hannina.powers.FusionPower;

import java.util.ArrayList;

public class ChooseColor2EnterAction extends AbstractGameAction {
    @Override
    public void update() {

        if(!AbstractDungeon.player.hasPower(FusionPower.POWER_ID)) {
            ArrayList<AbstractCard> stanceChoices = new ArrayList<>();
            stanceChoices.add(new RedColorCard());
            stanceChoices.add(new GreenColorCard());
            stanceChoices.add(new BlueColorCard());
            stanceChoices.add(new PurpleColorCard());
            addToTop(new ChooseOneAction(stanceChoices));
        }

        this.isDone = true;
    }
}
