package hannina.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class SelectFromHandAction extends AbstractGameAction {
    private final Predicate<AbstractCard> cardSelector;

    private final Consumer<List<AbstractCard>> then;

    private final String uiString;

    private final boolean skippable;

    private final ArrayList<AbstractCard> cannotBeSelected = new ArrayList<>();

    private final boolean anyNumber;

    private final boolean selectOrderMatters;

    public SelectFromHandAction(Predicate<AbstractCard> cardSelector, Consumer<List<AbstractCard>> then, String uiString, int amount, boolean anyNumber, boolean skippable, AbstractGameAction.ActionType actionType) {
        this(cardSelector, then, uiString, amount, anyNumber, skippable, actionType, false);
    }

    public SelectFromHandAction(Predicate<AbstractCard> cardSelector, Consumer<List<AbstractCard>> then, String uiString, int amount, boolean anyNumber, boolean skippable, AbstractGameAction.ActionType actionType, boolean selectOrderMatters) {
        this.actionType = actionType;
        this.cardSelector = cardSelector;
        this.then = then;
        this.uiString = uiString;
        this.amount = amount;
        this.anyNumber = anyNumber;
        this.skippable = skippable;
        this.duration = Settings.ACTION_DUR_FAST;
        this.selectOrderMatters = selectOrderMatters;
    }

    public void update() {
        AbstractPlayer player = AbstractDungeon.player;
        if (this.duration == Settings.ACTION_DUR_FAST) {
            this.cannotBeSelected.clear();
            this.cannotBeSelected.addAll((Collection<? extends AbstractCard>) player.hand.group
                    .stream()
                    .filter(c -> !this.cardSelector.test(c))
                    .collect(Collectors.toList()));
            if (this.cannotBeSelected.size() == player.hand.group.size()) {
                this.then.accept(Collections.emptyList());
                this.isDone = true;
                return;
            }
            if (!this.skippable && !this.anyNumber && player.hand.group.size() - this.cannotBeSelected.size() <= this.amount && (!this.selectOrderMatters || player.hand.group
                    .size() - this.cannotBeSelected.size() == 1)) {
                this.then.accept((List<AbstractCard>) player.hand.group.stream().filter(this.cardSelector).collect(Collectors.toList()));
                this.isDone = true;
                return;
            }
            player.hand.group.removeAll(this.cannotBeSelected);
            if (!player.hand.group.isEmpty()) {
                if (this.amount > player.hand.group.size())
                    this.amount = player.hand.group.size();
                AbstractDungeon.handCardSelectScreen.open(this.uiString, this.amount, this.anyNumber, this.skippable, false, false);
                tickDuration();
            } else {
                this.then.accept(Collections.emptyList());
                returnCards(player);
                this.isDone = true;
            }
            return;
        }
        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
            for (AbstractCard card : AbstractDungeon.handCardSelectScreen.selectedCards.group)
                player.hand.addToTop(card);
            this.then.accept(AbstractDungeon.handCardSelectScreen.selectedCards.group);
            returnCards(player);
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
            AbstractDungeon.handCardSelectScreen.selectedCards.group.clear();
            this.isDone = true;
        }
        tickDuration();
    }

    private void returnCards(AbstractPlayer player) {
        for (AbstractCard card : this.cannotBeSelected)
            player.hand.addToTop(card);
        player.hand.refreshHandLayout();
    }
}


/* Location:              C:\Users\35727\Desktop\slay_mod\jd-gui-windows-1.6.6\timewalker-20240816.jar!\io\chaofan\sts\timewalker\actions\common\SelectFromHandAction.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */