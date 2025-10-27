package hannina.cards;

import com.megacrit.cardcrawl.actions.watcher.ChooseOneAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.optionCards.ChooseCalm;
import com.megacrit.cardcrawl.cards.optionCards.ChooseWrath;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hannina.actions.ChangeCharColorAction;
import hannina.fantasyCard.AbstractHanninaCard;
import hannina.modcore.Enums;

import java.util.ArrayList;

public class Daoyingziluolan extends AbstractHanninaCard {
    public Daoyingziluolan() {
        super(Daoyingziluolan.class.getSimpleName(), 1, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        this.magicNumber = this.baseMagicNumber = 1;
        this.tags.add(Enums.ChangeColorCard);
    }

//    @Override
//    public void triggerWhenDrawn(){
//        addToBot(new DrawCardAction(1));
//    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ChangeCharColorAction(CardColor.PURPLE));
        ArrayList<AbstractCard> stanceChoices = new ArrayList<>();
        stanceChoices.add(new ChooseWrath());
        stanceChoices.add(new ChooseCalm());
        addToBot(new ChooseOneAction(stanceChoices));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            this.selfRetain = true;
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new Daoyingziluolan();
    }
}