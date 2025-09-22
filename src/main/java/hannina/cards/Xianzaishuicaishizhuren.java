package hannina.cards;

import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hannina.actions.LoseGoldAction;
import hannina.fantasyCard.AbstractHanninaCard;

public class Xianzaishuicaishizhuren extends AbstractHanninaCard {
    public Xianzaishuicaishizhuren() {
        super(Xianzaishuicaishizhuren.class.getSimpleName(), 0, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);
        this.magicNumber = this.baseMagicNumber = 50;
    }

    @Override
    public float getTitleFontSize() {
        return 20F;
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        return super.canUse(p, m) && p.gold > this.magicNumber;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new LoseGoldAction(this.magicNumber));

        addToBot(new ObtainPotionAction(AbstractDungeon.returnRandomPotion(true)));

        addToBot(new GainEnergyAction(1));
        addToBot(new DrawCardAction(1));
        addToBot(new HealAction(p, p, 10));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            this.returnToHand = true;
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new Xianzaishuicaishizhuren();
    }
}