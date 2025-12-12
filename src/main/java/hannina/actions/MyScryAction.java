package hannina.actions;

import com.megacrit.cardcrawl.actions.utility.ScryAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

import java.util.ArrayList;
import java.util.function.BiConsumer;

public class MyScryAction extends ScryAction {
    public ArrayList<AbstractCard> scriedCards;
    public ArrayList<AbstractCard> selectedCards;
    public BiConsumer<ArrayList<AbstractCard>, ArrayList<AbstractCard>> then;

    public MyScryAction(int numCards, BiConsumer<ArrayList<AbstractCard>, ArrayList<AbstractCard>> then) {
		super(numCards);

        this.then = then;
    }

//    public void update() {
//        if (AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
//            this.isDone = true;
//        } else {
//            if (this.duration == this.startingDuration) {
//                for(AbstractPower p : AbstractDungeon.player.powers) {
//                    p.onScry();
//                }
//
//                if (AbstractDungeon.player.drawPile.isEmpty()) {
//                    this.isDone = true;
//                    return;
//                }
//
//                CardGroup tmpGroup = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
//                if (this.amount != -1) {
//                    for(int i = 0; i < Math.min(this.amount, AbstractDungeon.player.drawPile.size()); ++i) {
//                        tmpGroup.addToTop(AbstractDungeon.player.drawPile.group.get(AbstractDungeon.player.drawPile.size() - i - 1));
//                    }
//                } else {
//                    for(AbstractCard c : AbstractDungeon.player.drawPile.group) {
//                        tmpGroup.addToBottom(c);
//                    }
//                }
//
//                scriedCards = new ArrayList<>(tmpGroup.group);
//
//                AbstractDungeon.gridSelectScreen.open(tmpGroup, this.amount, true, TEXT[0]);
//            } else if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
//                for(AbstractCard c : AbstractDungeon.gridSelectScreen.selectedCards) {
//                    AbstractDungeon.player.drawPile.moveToDiscardPile(c);
//                }
//
//                selectedCards = new ArrayList<>(AbstractDungeon.gridSelectScreen.selectedCards);
//
//                AbstractDungeon.gridSelectScreen.selectedCards.clear();
//
//                this.then.accept(scriedCards, selectedCards);
//            }
//
//            for(AbstractCard c : AbstractDungeon.player.discardPile.group) {
//                c.triggerOnScry();
//            }
//
//            this.tickDuration();
//        }
//    }
}
