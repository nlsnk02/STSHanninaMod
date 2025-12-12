package hannina.cards;

import basemod.AutoAdd;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.utility.NewQueueCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.StarBounceEffect;
import com.megacrit.cardcrawl.vfx.combat.ViolentAttackEffect;
import hannina.actions.AnonymousAction;
import hannina.actions.PlayACardAction;
import hannina.actions.PlaySpecificCardAction;
import hannina.fantasyCard.AbstractHanninaCard;

import java.util.ArrayList;

@AutoAdd.NotSeen
public class Kuaidaoluanma extends AbstractHanninaCard {
    public Kuaidaoluanma() {
		super(Kuaidaoluanma.class.getSimpleName(), 2, CardType.ATTACK, CardRarity.RARE, CardTarget.ENEMY);
		this.damage = this.baseDamage = 13;
		this.magicNumber = this.baseMagicNumber = 2;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
		if (Settings.FAST_MODE)
			this.addToBot(new VFXAction(new ViolentAttackEffect(m.hb.cX, m.hb.cY, Color.RED)));
		else
			this.addToBot(new VFXAction(new ViolentAttackEffect(m.hb.cX, m.hb.cY, Color.RED), 0.4F));
		
		for(int i = 0; i < 3; i++)
			this.addToBot(new VFXAction(new StarBounceEffect(m.hb.cX, m.hb.cY)));
		
		this.addToBot(new DamageAction(m,
				new DamageInfo(p, this.damage, this.damageTypeForTurn),
				AbstractGameAction.AttackEffect.BLUNT_LIGHT));
		
		for (int i = 0; i < this.magicNumber; i++) {
			this.addToBot(new AnonymousAction(() -> {
				int minCost = p.drawPile.group.stream()
						.filter(c -> c.costForTurn >= 0)
						.mapToInt(c -> c.costForTurn)
						.min()
						.orElse(-1);
				
				if (minCost != -1) {
					ArrayList<AbstractCard> candidates = p.drawPile.group.stream()
							.filter(c -> c.costForTurn == minCost)
							.collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
					
					if (candidates.isEmpty())
						return;
					
					AbstractCard card = candidates.remove(AbstractDungeon.cardRandomRng.random(candidates.size() - 1));
					this.addToTop(new PlaySpecificCardAction(card, p.drawPile));
				}
			}));
		}
	}

    @Override
    public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			this.upgradeDamage(3);
			this.upgradeMagicNumber(1);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new Kuaidaoluanma();
    }
}
