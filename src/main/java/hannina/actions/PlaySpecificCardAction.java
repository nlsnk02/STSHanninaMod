package hannina.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.NewQueueCardAction;
import com.megacrit.cardcrawl.actions.utility.UnlimboAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import hannina.modcore.Core;

public class PlaySpecificCardAction extends AbstractGameAction {
	private AbstractCard card;
	private CardGroup from;
	private boolean exhaustCard = false;

	public PlaySpecificCardAction(AbstractCard card, CardGroup from, AbstractCreature target, boolean exhaustCard) {
//		this.duration = this.startDuration = Settings.ACTION_DUR_FAST;
//		this.actionType = ActionType.WAIT;
		this.source = AbstractDungeon.player;

		this.card = card;
		this.from = from;
		this.target = target;
		this.exhaustCard = exhaustCard;
	}

	public PlaySpecificCardAction(AbstractCard card, CardGroup from, AbstractCreature target) {
		this(card, from, target, false);
	}

	public PlaySpecificCardAction(AbstractCard card, CardGroup from, boolean exhaustCard) {
		this(card, from, AbstractDungeon.getRandomMonster(), exhaustCard);
	}

	public PlaySpecificCardAction(AbstractCard card, CardGroup from) {
		this(card, from, AbstractDungeon.getRandomMonster(), false);
	}

	public void update() {
		if (!this.isDone) {
			if (!this.from.contains(this.card)) {
				Core.logger.warn("PlaySpecificCardAction: Card not found in the group.");
				this.isDone = true;
				return;
			}

			this.from.group.remove(this.card);
			AbstractDungeon.getCurrRoom().souls.remove(this.card);
			card.exhaustOnUseOnce = this.exhaustCard;

			AbstractDungeon.player.limbo.group.add(this.card);
			this.card.current_y = -200.0F * Settings.scale;
			this.card.target_x = (float) Settings.WIDTH / 2.0F + 200.0F * Settings.scale;
			this.card.target_y = (float) Settings.HEIGHT / 2.0F;
			this.card.targetAngle = 0.0F;
			this.card.lighten(false);
			this.card.drawScale = 0.12F;
			this.card.targetDrawScale = 0.75F;

			this.card.applyPowers();
			this.addToTop(new NewQueueCardAction(this.card, this.target, false, true));
			this.addToTop(new UnlimboAction(this.card));

			this.addToTop(new WaitAction(Settings.FAST_MODE ? Settings.ACTION_DUR_FASTER : Settings.ACTION_DUR_MED));
		}

		this.isDone = true;
	}
}
