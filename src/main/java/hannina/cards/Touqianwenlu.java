package hannina.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainGoldAction;
import com.megacrit.cardcrawl.actions.utility.ScryAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.watcher.MantraPower;
import hannina.actions.LoseGoldAction;
import hannina.fantasyCard.AbstractHanninaCard;
import hannina.utils.ModHelper;

public class Touqianwenlu extends AbstractHanninaCard {
    public Touqianwenlu() {
        super(Touqianwenlu.class.getSimpleName(), 0, CardType.SKILL, CardRarity.COMMON, CardTarget.NONE);
        this.damage = this.baseDamage = 1;
        this.block = this.baseBlock = 4;
        this.magicNumber = this.baseMagicNumber = 2;
    }

    @Override
    public void applyPowers() {
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new LoseGoldAction(this.baseDamage));
        addToBot(new ScryAction(this.baseBlock));
        if(ModHelper.getPlayerColor() == CardColor.PURPLE)
            addToBot(new ApplyPowerAction(p,p, new MantraPower(p, this.magicNumber)));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(1);
            upgradeBlock(2);
            upgradeMagicNumber(1);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new Touqianwenlu();
    }
}