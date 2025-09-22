package hannina.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.EmptyDeckShuffleAction;
import com.megacrit.cardcrawl.actions.utility.NewQueueCardAction;
import com.megacrit.cardcrawl.actions.utility.UnlimboAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class PlayACardAction extends AbstractGameAction {
    private boolean exhaustCards;
    private CardGroup group;
    private AbstractCard card;

    public PlayACardAction(AbstractCard c, CardGroup group, AbstractCreature target, boolean exhausts) {
        this.duration = Settings.ACTION_DUR_FAST;
        this.actionType = ActionType.WAIT;
        this.source = AbstractDungeon.player;
        this.target = target;
        this.exhaustCards = exhausts;
        this.group = group;
        this.card = c;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
//            if (AbstractDungeon.player.drawPile.size() + AbstractDungeon.player.discardPile.size() == 0) {
//                this.isDone = true;
//                return;
//            }

            if (group == null || group.contains(card)) {
                if (group != null) {
                    group.group.remove(card);
                    if(group.type == CardGroup.CardGroupType.EXHAUST_PILE)
                        card.unfadeOut();
                }

                AbstractDungeon.getCurrRoom().souls.remove(card);
                card.exhaustOnUseOnce = this.exhaustCards;
                AbstractDungeon.player.limbo.group.add(card);
                card.current_y = -200.0F * Settings.scale;
                card.target_x = (float) Settings.WIDTH / 2.0F + 200.0F * Settings.xScale;
                card.target_y = (float) Settings.HEIGHT / 2.0F;
                card.targetAngle = 0.0F;
                card.lighten(false);
                card.drawScale = 0.12F;
                card.targetDrawScale = 0.75F;
                card.applyPowers();

                if (this.target == null && card.target == AbstractCard.CardTarget.ENEMY)
                    this.target = AbstractDungeon.getRandomMonster();

                this.addToTop(new NewQueueCardAction(card, this.target, this.group == null, true));
                this.addToTop(new UnlimboAction(card));
                if (!Settings.FAST_MODE) {
                    this.addToTop(new WaitAction(Settings.ACTION_DUR_MED));
                } else {
                    this.addToTop(new WaitAction(Settings.ACTION_DUR_FASTER));
                }
            }

            this.isDone = true;
        }

    }
}
