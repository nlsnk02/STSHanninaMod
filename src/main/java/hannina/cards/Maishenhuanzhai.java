package hannina.cards;

import basemod.ReflectionHacks;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.status.Slimed;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hannina.actions.GainGoldAction;
import hannina.fantasyCard.AbstractHanninaCard;

public class Maishenhuanzhai extends AbstractHanninaCard {

    public Maishenhuanzhai() {
        super(Maishenhuanzhai.class.getSimpleName(), 0, CardType.SKILL, CardRarity.COMMON, CardTarget.NONE);
        this.magicNumber = this.baseMagicNumber = 15;
        this.exhaust = true;
        this.cardsToPreview = new Baisenianye();
    }

    @Deprecated
    public static AbstractCard getUpgradeSlimed() {
        AbstractCard c = new Slimed();
        c.cost = c.costForTurn = 0;
        ReflectionHacks.privateMethod(AbstractCard.class, "upgradeName").invoke(c);
        return c;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        AbstractCard c = new Baisenianye();
        if(this.upgraded) c.upgrade();

        addToBot(new MakeTempCardInDrawPileAction(
                c, 2, true, true
        ));
        addToBot(new GainGoldAction(this.magicNumber));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(5);
            AbstractCard c = new Baisenianye();
            c.upgrade();
            this.cardsToPreview = c;
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new Maishenhuanzhai();
    }
}