package hannina.cards;

import basemod.patches.com.megacrit.cardcrawl.cards.AbstractCard.MultiCardPreview;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hannina.actions.SelectFromRewardAction;
import hannina.fantasyCard.AbstractHanninaCard;
import hannina.powers.DoumaobangPower;
import hannina.powers.DoumaobangUPGPower;
import hannina.utils.UnionManager;

import java.util.ArrayList;

public class Doumaobang extends AbstractHanninaCard {
    public Doumaobang() {
        super(Doumaobang.class.getSimpleName(), 1, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);
        this.magicNumber = this.baseMagicNumber = 1;
        MultiCardPreview.add(this, new Maodepingwenluodi(), new Maodechongfengjinji());
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (!this.upgraded)
            addToBot(new ApplyPowerAction(p, p, new DoumaobangPower(p, this.magicNumber)));
        else addToBot(new ApplyPowerAction(p, p, new DoumaobangUPGPower(p, this.magicNumber)));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            this.rawDescription = this.cardStrings.UPGRADE_DESCRIPTION;
            this.initializeDescription();
            MultiCardPreview.clear(this);
            AbstractCard c1 = new Maodepingwenluodi();
            c1.upgrade();
            AbstractCard c2 = new Maodechongfengjinji();
            c2.upgrade();
            MultiCardPreview.add(this, c1, c2);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new Doumaobang();
    }
}