package hannina.fantasyCard;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hannina.actions.ChangeCharColorAction;

import javax.swing.*;

public class BlueColorCard extends AbstractHanninaCard {

    private AbstractGameAction action;

    public BlueColorCard() {
        super(BlueColorCard.class.getSimpleName(), -2, CardType.SKILL, CardRarity.SPECIAL, CardTarget.SELF);
        action = null;
    }

    public BlueColorCard(AbstractGameAction action) {
        this();
        this.action = action;
    }

    @Override
    public void onChoseThisOption() {
        if (action != null) {
            addToTop(action);
        } else
            addToTop(new ChangeCharColorAction(CardColor.BLUE));
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
    }

    @Override
    public void upgrade() {
    }

    @Override
    public AbstractCard makeCopy() {
        return new BlueColorCard();
    }
}