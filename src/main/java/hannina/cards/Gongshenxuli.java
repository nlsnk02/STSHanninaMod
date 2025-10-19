package hannina.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.red.Flex;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import hannina.fantasyCard.AbstractHanninaCard;
import hannina.utils.UnionManager;

import java.util.ArrayList;

public class Gongshenxuli extends AbstractHanninaCard {
    public Gongshenxuli() {
        super(Gongshenxuli.class.getSimpleName(), 1, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        this.magicNumber = this.baseMagicNumber = 2;
        this.block = this.baseBlock = 1;
    }

//    @Override
//    public ArrayList<AbstractCard> setUnion() {
//        ArrayList<AbstractCard> unions = new ArrayList<>();
//        unions.add(UnionManager.getRamdomCard(c ->
//                c.color == CardColor.RED && c.cost == 2 && c.type == CardType.ATTACK));
//        unions.add(UnionManager.getRamdomCard(c ->
//                c.color == CardColor.GREEN && c.cost == 2 && c.type == CardType.ATTACK));
//        unions.add(UnionManager.getRamdomCard(c ->
//                c.color == CardColor.BLUE && c.cost == 2 && c.type == CardType.ATTACK));
//        unions.add(UnionManager.getRamdomCard(c ->
//                c.color == CardColor.PURPLE && c.cost == 2 && c.type == CardType.ATTACK));
//        return unions;
//    }

    @Override
    public void applyPowers() {
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DrawCardAction(this.magicNumber));
        addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, this.baseBlock)));
        addToBot(new ApplyPowerAction(p, p, new LoseStrengthPower(p, this.baseBlock)));

    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(1);
            upgradeBlock(1);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new Gongshenxuli();
    }
}