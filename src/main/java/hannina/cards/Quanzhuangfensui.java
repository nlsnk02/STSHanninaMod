package hannina.cards;

import basemod.AutoAdd;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.actions.defect.EvokeAllOrbsAction;
import com.megacrit.cardcrawl.actions.utility.NewQueueCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.EmptyOrbSlot;
import com.megacrit.cardcrawl.vfx.StarBounceEffect;
import com.megacrit.cardcrawl.vfx.combat.ViolentAttackEffect;
import hannina.actions.AnonymousAction;
import hannina.fantasyCard.AbstractHanninaCard;

import java.util.ArrayList;

@AutoAdd.NotSeen
public class Quanzhuangfensui extends AbstractHanninaCard {
    public Quanzhuangfensui() {
		super(Quanzhuangfensui.class.getSimpleName(), 3, CardType.ATTACK, CardRarity.RARE, CardTarget.ENEMY);
		this.damage = this.baseDamage = 20;
		this.magicNumber = this.baseMagicNumber = 4;
    }
	
	@Override
	public void applyPowers() {
		int baseDamage = this.baseDamage;
		
		this.baseDamage += (int) AbstractDungeon.player.orbs.stream()
				.filter(o -> !(o instanceof EmptyOrbSlot))
				.count() * this.magicNumber;
		
		super.applyPowers();
		
		this.baseDamage = baseDamage;
		this.isDamageModified = this.damage != this.baseDamage;
	}
	
	@Override
	public void calculateCardDamage(AbstractMonster mo) {
		int baseDamage = this.baseDamage;
		
		this.baseDamage += (int) AbstractDungeon.player.orbs.stream()
				.filter(o -> !(o instanceof EmptyOrbSlot))
				.count() * this.magicNumber;
		
		super.calculateCardDamage(mo);
		
		this.baseDamage = baseDamage;
		this.isDamageModified = this.damage != this.baseDamage;
	}

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
		this.addToBot(new DamageAction(m,
				new DamageInfo(p, this.damage, this.damageTypeForTurn),
				AbstractGameAction.AttackEffect.BLUNT_HEAVY));
		
		this.addToBot(new EvokeAllOrbsAction());
		
		int count = (int) p.orbs.stream()
				.filter(o -> !(o instanceof EmptyOrbSlot))
				.count();
		for (int i = 0; i < count; i++)
			this.addToBot(new ChannelAction(AbstractOrb.getRandomOrb(true)));
	}

    @Override
    public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			this.upgradeDamage(6);
			this.upgradeMagicNumber(2);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new Quanzhuangfensui();
    }
}
