package hannina.cards;

import basemod.AutoAdd;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.utility.NewQueueCardAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.stances.NeutralStance;
import hannina.fantasyCard.AbstractHanninaCard;
import hannina.interfaces.card.OnScryAndDiscardedCard;

@AutoAdd.NotSeen
public class Wujingyichuan extends AbstractHanninaCard implements OnScryAndDiscardedCard {
    public Wujingyichuan() {
		super(Wujingyichuan.class.getSimpleName(), 0, CardType.ATTACK, CardRarity.RARE, CardTarget.NONE);
		this.damage = this.baseDamage = 30;
		this.isMultiDamage = true;
    }
	
	@Override
	public void onScryAndDiscarded() {
		AbstractCard copy = this.makeStatEquivalentCopy();
		copy.dontTriggerOnUseCard = true;
		copy.purgeOnUse = true;
		
		this.addToBot(new NewQueueCardAction(copy, true, false, true));
	}

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
		if (this.dontTriggerOnUseCard && this.purgeOnUse) {
			this.addToBot(new DamageAllEnemiesAction(p, this.multiDamage, this.damageTypeForTurn,
					AbstractGameAction.AttackEffect.FIRE));
			
			this.addToBot(new ChangeStanceAction(NeutralStance.STANCE_ID));
		}
	}

    @Override
    public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			this.upgradeDamage(10);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new Wujingyichuan();
    }
}
