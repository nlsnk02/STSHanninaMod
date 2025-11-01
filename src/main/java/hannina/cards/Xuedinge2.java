package hannina.cards;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hannina.actions.XuedingeAction;
import hannina.fantasyCard.AbstractHanninaCard;

public class Xuedinge2 extends AbstractHanninaCard {
    public static final String[] actionTEXT;

    public Xuedinge2() {
        super(Xuedinge2.class.getSimpleName(), 1, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        this.magicNumber = this.baseMagicNumber = 2;
        this.baseBlock = 4;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p, p, this.block));
        addToBot(new XuedingeAction(this.magicNumber));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(1);
            upgradeBlock(2);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new Xuedinge2();
    }

    static {
        UIStrings actionUiStrings = CardCrawlGame.languagePack.getUIString("hannina:Xuedinge");
        actionTEXT = actionUiStrings.TEXT;
    }
}