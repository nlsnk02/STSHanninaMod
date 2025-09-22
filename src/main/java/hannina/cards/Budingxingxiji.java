package hannina.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.purple.Brilliance;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hannina.fantasyCard.AbstractHanninaCard;
import hannina.modcore.Enums;
import hannina.utils.ModHelper;

public class Budingxingxiji extends AbstractHanninaCard {
    public Budingxingxiji() {
        super(Budingxingxiji.class.getSimpleName(), 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        this.damage = this.baseDamage = 3;
        this.magicNumber = this.baseMagicNumber = 2;
        this.cardsToPreview = new Maodechongfengjinji();
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        calculateCardDamage(m);
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn),
                AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn),
                AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        addToBot(new MakeTempCardInHandAction(new Maodechongfengjinji()));
    }

    @Override
    public void triggerOnGlowCheck() {
        this.glowColor = ModHelper.getPlayerColor() != Enums.HanninaColor ?
                AbstractCard.GOLD_BORDER_GLOW_COLOR : AbstractCard.BLUE_BORDER_GLOW_COLOR;
    }

    @Override
    public void applyPowers() {
        if (ModHelper.getPlayerColor() != Enums.HanninaColor) this.baseDamage += this.baseMagicNumber;
        super.applyPowers();
        if (ModHelper.getPlayerColor() != Enums.HanninaColor) this.baseDamage -= this.baseMagicNumber;
        this.isDamageModified = this.damage != this.baseDamage;
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        if (ModHelper.getPlayerColor() != Enums.HanninaColor) this.baseDamage += this.baseMagicNumber;
        super.calculateCardDamage(mo);
        if (ModHelper.getPlayerColor() != Enums.HanninaColor) this.baseDamage -= this.baseMagicNumber;
        this.isDamageModified = this.damage != this.baseDamage;
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(1);
            upgradeMagicNumber(1);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new Budingxingxiji();
    }
}