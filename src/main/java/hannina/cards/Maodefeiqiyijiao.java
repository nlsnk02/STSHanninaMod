package hannina.cards;

import basemod.patches.com.megacrit.cardcrawl.cards.AbstractCard.MultiCardPreview;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hannina.fantasyCard.AbstractHanninaCard;
import hannina.powers.DoumaobangPower;
import hannina.powers.DoumaobangUPGPower;
import hannina.powers.MaodefeiqiyijiaoPower;

public class Maodefeiqiyijiao extends AbstractHanninaCard {
    public Maodefeiqiyijiao() {
        super(Maodefeiqiyijiao.class.getSimpleName(), 1, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);
        this.magicNumber = this.baseMagicNumber = 1;
        MultiCardPreview.add(this, new Maodepingwenluodi());
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new MaodefeiqiyijiaoPower(p, this.magicNumber)));
        addToBot(new MakeTempCardInHandAction(new Maodepingwenluodi(), 1));
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
        return new Maodefeiqiyijiao();
    }
}