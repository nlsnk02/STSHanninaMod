package hannina.cards;

import basemod.AutoAdd;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.utility.ShowCardAndPoofAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hannina.actions.AnonymousAction;
import hannina.fantasyCard.AbstractHanninaCard;

import java.util.ArrayList;

@AutoAdd.NotSeen
public class Tuijinzhan extends AbstractHanninaCard {
    public Tuijinzhan() {
		super(Tuijinzhan.class.getSimpleName(), 1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
		this.damage = this.baseDamage = 9;
		this.magicNumber = this.baseMagicNumber = 1;
		this.isEthereal = true;
		
		this.cardsToPreview = new MMRuizhan();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
		this.addToBot(new DamageAction(m,
				new DamageInfo(p, this.damage, this.damageTypeForTurn),
				AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
		
		this.addToBot(new AnonymousAction(() -> {
			int minCost = p.drawPile.group.stream()
					.filter(c -> c.costForTurn >= 0)
					.mapToInt(c -> c.costForTurn)
					.min()
					.orElse(-1);
			
			if (minCost != -1) {
				AbstractCard[] candidates = p.drawPile.group.stream()
						.filter(c -> c.costForTurn == minCost)
						.toArray(AbstractCard[]::new);
				
				AbstractCard card = candidates[AbstractDungeon.cardRandomRng.random(candidates.length - 1)];
				this.addToTop(new ExhaustSpecificCardAction(card, p.drawPile));
			}
		}));
		
		AbstractCard derivative = new MMRuizhan();
		if (this.upgraded)
			derivative.upgrade();
		this.addToBot(new MakeTempCardInHandAction(derivative));
	}

    @Override
    public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			this.upgradeDamage(4);
			this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
			this.initializeDescription();
			
			this.cardsToPreview.upgrade();
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new Tuijinzhan();
    }
}
