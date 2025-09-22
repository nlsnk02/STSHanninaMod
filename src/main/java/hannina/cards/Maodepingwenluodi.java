package hannina.cards;

import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hannina.actions.ChooseColor2EnterAction;
import hannina.fantasyCard.AbstractHanninaCard;
import hannina.utils.UnionManager;

import java.util.ArrayList;

public class Maodepingwenluodi extends AbstractHanninaCard {
    public Maodepingwenluodi() {
        super(Maodepingwenluodi.class.getSimpleName(), 0, CardType.SKILL, CardRarity.SPECIAL, CardTarget.SELF, CardColor.COLORLESS);
        this.exhaust = true;
    }

    @Override
    public ArrayList<AbstractCard> setUnion() {
        ArrayList<AbstractCard> unions = new ArrayList<>();
        unions.add(UnionManager.getRamdomCard(c -> c.color == CardColor.RED));
        unions.add(UnionManager.getRamdomCard(c -> c.color == CardColor.GREEN));
        unions.add(UnionManager.getRamdomCard(c -> c.color == CardColor.BLUE));
        unions.add(UnionManager.getRamdomCard(c -> c.color == CardColor.PURPLE));
        return unions;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ChooseColor2EnterAction());
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            this.selfRetain = true;
            rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new Maodepingwenluodi();
    }
}