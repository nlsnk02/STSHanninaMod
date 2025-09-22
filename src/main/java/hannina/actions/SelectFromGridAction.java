package hannina.actions;

import basemod.ReflectionHacks;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.screens.select.GridCardSelectScreen;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SelectFromGridAction extends AbstractGameAction {
    private final BiPredicate<Source, AbstractCard> cardSelector;

    private final BiConsumer<Source[], AbstractCard[]> then;

    private final String uiString;

    private final int numCards;

    private final boolean anyNumber;

    private final BiConsumer<Source, AbstractCard> beforeShow;

    private final BiConsumer<Source, AbstractCard> afterShow;

    private boolean complete = false;

    private List<AbstractCard> selectedCards;

    private List<AbstractCard> preselectedCards;

    public SelectFromGridAction(BiPredicate<Source, AbstractCard> cardSelector, BiConsumer<Source[], AbstractCard[]> then, String uiString, AbstractGameAction.ActionType actionType, int numCards, boolean anyNumber) {
        this(cardSelector, then, uiString, actionType, numCards, anyNumber, (BiConsumer<Source, AbstractCard>) null, (BiConsumer<Source, AbstractCard>) null, (List<AbstractCard>) null);
    }

    public SelectFromGridAction(BiPredicate<Source, AbstractCard> cardSelector, BiConsumer<Source[], AbstractCard[]> then, String uiString, AbstractGameAction.ActionType actionType, int numCards, boolean anyNumber, List<AbstractCard> preselectedCards) {
        this(cardSelector, then, uiString, actionType, numCards, anyNumber, (BiConsumer<Source, AbstractCard>) null, (BiConsumer<Source, AbstractCard>) null, preselectedCards);
    }

    public SelectFromGridAction(List<AbstractCard> selectedCards, BiConsumer<Source[], AbstractCard[]> then, String uiString, AbstractGameAction.ActionType actionType, int numCards, boolean anyNumber) {
        this((BiPredicate<Source, AbstractCard>) null, then, uiString, actionType, numCards, anyNumber, (BiConsumer<Source, AbstractCard>) null, (BiConsumer<Source, AbstractCard>) null, (List<AbstractCard>) null);
        this.selectedCards = selectedCards;
    }

    public SelectFromGridAction(BiPredicate<Source, AbstractCard> cardSelector, BiConsumer<Source[], AbstractCard[]> then, String uiString, AbstractGameAction.ActionType actionType, int numCards, boolean anyNumber, BiConsumer<Source, AbstractCard> beforeShow, BiConsumer<Source, AbstractCard> afterShow, List<AbstractCard> preselectedCards) {
        this.anyNumber = anyNumber;
        this.beforeShow = beforeShow;
        this.afterShow = afterShow;
        this.actionType = actionType;
        this.cardSelector = cardSelector;
        this.then = then;
        this.uiString = uiString;
        this.numCards = numCards;
        this.duration = Settings.ACTION_DUR_FAST;
        this.preselectedCards = preselectedCards;
    }

    public void update() {
        AbstractPlayer player = AbstractDungeon.player;
        if (this.duration == Settings.ACTION_DUR_FAST) {
            if (this.selectedCards == null)
                this.selectedCards = (List<AbstractCard>) Stream.of(
                        new Stream[]{filterCards(player.hand, Source.HAND), filterCards(player.drawPile, Source.DRAW_PILE), filterCards(player.discardPile, Source.DISCARD_PILE)})
                        .flatMap(g -> g).collect(Collectors.toList());
            if (this.selectedCards.isEmpty()) {
                this.then.accept(new Source[0], new AbstractCard[0]);
                this.isDone = true;
                return;
            }
            CardGroup group = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
            boolean shouldSort = false;
            for (AbstractCard card : this.selectedCards) {
                Source cardSource = getSource(player, card);
                group.addToTop(card);
                if (this.beforeShow != null)
                    this.beforeShow.accept(cardSource, card);
                card.stopGlowing();
                if (cardSource == Source.DRAW_PILE)
                    shouldSort = true;
            }
            if (shouldSort) {
                group.sortAlphabetically(true);
                group.sortByRarityPlusStatusCardType(false);
            }
            int size = Math.min(this.numCards, this.selectedCards.size());
            if (!this.anyNumber) {
                AbstractDungeon.gridSelectScreen.open(group, size, this.uiString, false);
            } else {
                AbstractDungeon.gridSelectScreen.open(group, size, this.anyNumber, this.uiString);
            }
            if (this.preselectedCards != null) {
                for (AbstractCard card : this.preselectedCards) {
                    AbstractDungeon.gridSelectScreen.selectedCards.add(card);
                    card.beginGlowing();
                }
                ReflectionHacks.setPrivate(AbstractDungeon.gridSelectScreen, GridCardSelectScreen.class, "cardSelectAmount", Integer.valueOf(this.preselectedCards.size()));
            }
        } else if (!this.complete && AbstractDungeon.screen == AbstractDungeon.CurrentScreen.NONE) {
            List<AbstractCard> cards = new ArrayList<>(AbstractDungeon.gridSelectScreen.selectedCards);
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
            cards.forEach(AbstractCard::stopGlowing);
            this.then.accept((Source[]) cards.stream().map(card -> getSource(player, card)).toArray(x$0 -> new Source[x$0]), cards.toArray(new AbstractCard[0]));
            this.complete = true;
            for (AbstractCard selectedCard : this.selectedCards) {
                if (this.afterShow != null)
                    this.afterShow.accept(getSource(player, selectedCard), selectedCard);
            }
        }
        tickDuration();
    }

    public static BiConsumer<Source[], AbstractCard[]> acceptMultipleCards(BiConsumer<Source, AbstractCard> singleThen) {
        return (sources, cards) -> {
            for (int i = 0; i < cards.length; i++) {
                Source source = sources[i];
                AbstractCard card = cards[i];
                singleThen.accept(source, card);
            }
        };
    }

    private Source getSource(AbstractPlayer player, AbstractCard card) {
        return player.hand.contains(card) ? Source.HAND : (
                player.drawPile.contains(card) ? Source.DRAW_PILE : (
                        player.discardPile.contains(card) ? Source.DISCARD_PILE : Source.NONE));
    }

    private Stream<AbstractCard> filterCards(CardGroup cardGroup, Source source) {
        return cardGroup.group.stream().filter(c -> this.cardSelector.test(source, c));
    }

    public enum Source {
        NONE, DRAW_PILE, DISCARD_PILE, HAND, FREEZE_PILE;
    }
}