package hannina.cards;

import basemod.AutoAdd;
import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.NoDrawPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import hannina.actions.AnonymousAction;
import hannina.actions.MoveToHandAction;
import hannina.fantasyCard.AbstractHanninaCard;
import hannina.interfaces.card.OnEnterDiscardPileCard;
import hannina.modcore.Core;

@AutoAdd.NotSeen
public class Biduancanxiang extends AbstractHanninaCard implements OnEnterDiscardPileCard {
    public Biduancanxiang() {
		super(Biduancanxiang.class.getSimpleName(), 1, CardType.SKILL, CardRarity.RARE, CardTarget.NONE);
		this.block = this.baseBlock = 3;
    }
	
	@Override
	public void onEnterDiscardPile() {
		this.addToBot(new AnonymousAction(() -> {
			if (AbstractDungeon.player.discardPile.contains(this) &&
					AbstractDungeon.player.hand.size() < BaseMod.MAX_HAND_SIZE) {
				AbstractDungeon.player.hand.addToHand(this);
				AbstractDungeon.player.discardPile.removeCard(this);
				
				this.lighten(false);
				this.applyPowers();
				this.flash();
				
				this.addToTop(new GainBlockAction(AbstractDungeon.player, this.block));
			}
		}));
	}
	
	@Override
	public void update() {
		if (this.cost != 1 || this.costForTurn != 1 || this.freeToPlayOnce) {
			Core.logger.warn("Biduancanxiang cost changed, resetting to 1");
			this.cost = this.costForTurn = 1;
			this.freeToPlayOnce = false;
			this.isCostModified = false;
		}
		
		super.update();
	}

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
		
	}
	
	@Override
	public void updateCost(int amt) {}
	
	@Override
	public void setCostForTurn(int amt) {}
	
	@Override
	public void modifyCostForCombat(int amt) {}
	
	@Override
	public boolean freeToPlay() {
		return false;
	}

    @Override
    public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			this.upgradeBlock(1);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new Biduancanxiang();
    }
}
