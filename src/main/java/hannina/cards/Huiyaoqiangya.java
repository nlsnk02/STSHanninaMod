package hannina.cards;

import basemod.AutoAdd;
import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.FastDrawCardAction;
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

import java.util.ArrayList;

@AutoAdd.NotSeen
public class Huiyaoqiangya extends AbstractHanninaCard {
    public Huiyaoqiangya() {
		super(Huiyaoqiangya.class.getSimpleName(), 1, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);
		this.block = this.baseBlock = 4;
		this.magicNumber = this.baseMagicNumber = 1;
		this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
		for (AbstractCard c : p.drawPile.group) // 抽牌堆
			if (c.type == CardType.STATUS || c.type == CardType.CURSE) {
				this.addToBot(new AnonymousAction(() -> {
					this.addToTop(new GainBlockAction(p, this.block));
					this.addToTop(new ApplyPowerAction(p, p, new StrengthPower(p, this.magicNumber)));
					
					boolean canDraw = true;
					if (p.hasPower(NoDrawPower.POWER_ID)) {
						p.getPower(NoDrawPower.POWER_ID).flash();
						canDraw = false;
					}
					else if (p.hand.size() >= BaseMod.MAX_HAND_SIZE) {
						p.createHandIsFullDialog();
						canDraw = false;
					}
					
					if (!canDraw)
						this.addToTop(new ExhaustSpecificCardAction(c, p.drawPile, true));
					else {
						c.current_x = CardGroup.DRAW_PILE_X;
						c.current_y = CardGroup.DRAW_PILE_Y;
						c.setAngle(0.0F, true);
						c.lighten(false);
						c.drawScale = 0.12F;
						c.targetDrawScale = 0.75F;
						c.triggerWhenDrawn();
						p.hand.addToHand(c);
						p.drawPile.removeCard(c);
						
						for(AbstractPower power : p.powers)
							power.onCardDraw(c);
						
						BaseMod.publishPostDraw(c); // Don't know if necessary
						
						for(AbstractRelic r : p.relics)
							r.onCardDraw(c);
						
						if (p.hand.contains(c))
							this.addToTop(new ExhaustSpecificCardAction(c, p.hand, true));
					}
				}));
			}
		
		for (AbstractCard c : p.hand.group) // 手牌
			if (c.type == CardType.STATUS || c.type == CardType.CURSE) {
				this.addToBot(new AnonymousAction(() -> {
					this.addToTop(new GainBlockAction(p, this.block));
					this.addToTop(new ApplyPowerAction(p, p, new StrengthPower(p, this.magicNumber)));
					this.addToTop(new ExhaustSpecificCardAction(c, p.hand, true));
				}));
			}
		
		for (AbstractCard c : p.discardPile.group) // 弃牌堆
			if (c.type == CardType.STATUS || c.type == CardType.CURSE) {
				this.addToBot(new AnonymousAction(() -> { // 从弃牌堆抽牌
					this.addToTop(new GainBlockAction(p, this.block));
					this.addToTop(new ApplyPowerAction(p, p, new StrengthPower(p, this.magicNumber)));
					
					boolean canDraw = true;
					if (p.hasPower(NoDrawPower.POWER_ID)) {
						p.getPower(NoDrawPower.POWER_ID).flash();
						canDraw = false;
					}
					else if (p.hand.size() >= BaseMod.MAX_HAND_SIZE) {
						p.createHandIsFullDialog();
						canDraw = false;
					}
					
					if (!canDraw)
						this.addToTop(new ExhaustSpecificCardAction(c, p.discardPile, true));
					else {
						c.current_x = CardGroup.DISCARD_PILE_X;
						c.current_y = CardGroup.DISCARD_PILE_Y;
						c.setAngle(0.0F, true);
						c.lighten(false);
						c.drawScale = 0.12F;
						c.targetDrawScale = 0.75F;
						c.triggerWhenDrawn();
						p.hand.addToHand(c);
						p.discardPile.removeCard(c);
						
						for(AbstractPower power : p.powers)
							power.onCardDraw(c);
						
						BaseMod.publishPostDraw(c); // Don't know if necessary
						
						for(AbstractRelic r : p.relics)
							r.onCardDraw(c);
						
						if (p.hand.contains(c))
							this.addToTop(new ExhaustSpecificCardAction(c, p.hand, true));
					}
				}));
			}
	}

    @Override
    public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			this.exhaust = false;
			this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
			this.initializeDescription();
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new Huiyaoqiangya();
    }
}
