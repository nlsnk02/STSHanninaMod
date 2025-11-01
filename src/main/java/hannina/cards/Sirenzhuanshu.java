package hannina.cards;

import basemod.abstracts.CustomSavable;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.BarricadePower;
import com.megacrit.cardcrawl.powers.MetallicizePower;
import hannina.actions.LoseGoldAction;
import hannina.fantasyCard.AbstractHanninaCard;
import hannina.powers.JinziyinhangPower;
import hannina.powers.SirenzhuanshuPower;

public class Sirenzhuanshu extends AbstractHanninaCard {

    public Sirenzhuanshu() {
        super(Sirenzhuanshu.class.getSimpleName(), 1, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);
        this.magicNumber = this.baseMagicNumber = 1;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new MetallicizePower(p, this.magicNumber)));
        addToBot(new ApplyPowerAction(p, p, new SirenzhuanshuPower(p, 1)));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(1);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new Sirenzhuanshu();
    }
}