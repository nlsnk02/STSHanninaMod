package hannina.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;
import java.util.Optional;
import java.util.function.Consumer;

public class SelectFromRewardAction extends AbstractGameAction {
    private final ArrayList<AbstractCard> cards;

    private final Consumer<Optional<AbstractCard>> then;

    private final String uiString;

    private final boolean skippable;

    private boolean retrieveCard;

    public SelectFromRewardAction(ArrayList<AbstractCard> cards, Consumer<Optional<AbstractCard>> then, String uiString, boolean skippable, AbstractGameAction.ActionType actionType) {
        this.cards = cards;
        this.then = then;
        this.uiString = uiString;
        this.skippable = skippable;
        this.actionType = actionType;
        this.duration = Settings.ACTION_DUR_FAST;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            AbstractDungeon.cardRewardScreen.customCombatOpen(this.cards, this.uiString, this.skippable);
            tickDuration();
            return;
        }
        if (!this.retrieveCard) {
            this.then.accept(Optional.ofNullable(AbstractDungeon.cardRewardScreen.discoveryCard));
            AbstractDungeon.cardRewardScreen.discoveryCard = null;
            this.retrieveCard = true;
        }
        tickDuration();
    }
}


/* Location:              C:\Users\35727\Desktop\slay_mod\jd-gui-windows-1.6.6\timewalker-20240816.jar!\io\chaofan\sts\timewalker\actions\common\SelectFromRewardAction.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */