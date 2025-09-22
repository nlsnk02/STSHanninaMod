package hannina.fantasyCard;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hannina.actions.ChangeCharColorAction;

public class PurpleColorCard extends AbstractHanninaCard {

    private AbstractGameAction action;

    public PurpleColorCard() {
        super(PurpleColorCard.class.getSimpleName(), -2, CardType.SKILL, CardRarity.SPECIAL, CardTarget.SELF);
        action = null;
    }

    public PurpleColorCard(AbstractGameAction action) {
        this();
        this.action = action;
    }

    @Override
    public void onChoseThisOption() {
        if (action != null) {
            addToTop(action);
        } else
            addToTop(new ChangeCharColorAction(CardColor.PURPLE));
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
    }

    @Override
    public void upgrade() {
    }

    @Override
    public AbstractCard makeCopy() {
        return new PurpleColorCard();
    }
}