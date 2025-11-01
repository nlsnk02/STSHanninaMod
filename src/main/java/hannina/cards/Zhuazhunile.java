package hannina.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;
import hannina.fantasyCard.AbstractHanninaCard;
import hannina.powers.ZhuazhunilePower;

public class Zhuazhunile extends AbstractHanninaCard {
    public Zhuazhunile() {
        super(Zhuazhunile.class.getSimpleName(), 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        this.damage = this.baseDamage = 7;
        this.baseMagicNumber = this.magicNumber = 1;
        this.cardsToPreview = new Maodepingwenluodi();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn),
                AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));

        addToBot(new ApplyPowerAction(m, p, new StrengthPower(m, -this.magicNumber)));
        if (!m.hasPower(ArtifactPower.POWER_ID)) {
            this.addToBot(new ApplyPowerAction(m, p, new GainStrengthPower(m, this.magicNumber), this.magicNumber, true, AbstractGameAction.AttackEffect.NONE));
        }

        addToBot(new ApplyPowerAction(p, p, new ZhuazhunilePower(p, 1)));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(2);
            upgradeMagicNumber(1);
            AbstractCard c = new Maodepingwenluodi();
            c.upgrade();
            this.cardsToPreview = c;
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new Zhuazhunile();
    }
}