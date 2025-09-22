package hannina.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import com.megacrit.cardcrawl.powers.EquilibriumPower;
import hannina.fantasyCard.AbstractHanninaCard;
import hannina.utils.UnionManager;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public class Shenlingpingyi extends AbstractHanninaCard {
    public Shenlingpingyi() {
        super(Shenlingpingyi.class.getSimpleName(), 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        this.magicNumber = this.baseMagicNumber = 1;
        this.block = this.baseBlock = 5;
    }

    @Override
    public ArrayList<AbstractCard> setUnion() {
        ArrayList<AbstractCard> unions = new ArrayList<>();
        unions.add(UnionManager.getRamdomCard(c ->
                c.color == CardColor.RED && c.cost >= 2 && c.type == CardType.SKILL));
        unions.add(UnionManager.getRamdomCard(c ->
                c.color == CardColor.GREEN && c.cost >= 2 && c.type == CardType.SKILL));
        unions.add(UnionManager.getRamdomCard(c ->
                c.color == CardColor.BLUE && c.cost >= 2 && c.type == CardType.SKILL));
        unions.add(UnionManager.getRamdomCard(c ->
                c.color == CardColor.PURPLE && c.cost >= 2 && c.type == CardType.SKILL));
        return unions;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p, p, this.block));
        addToBot(new ApplyPowerAction(p, p, new EquilibriumPower(p, this.magicNumber), this.magicNumber));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBlock(2);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new Shenlingpingyi();
    }
}