package hannina.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.watcher.ChooseOneAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hannina.fantasyCard.AbstractHanninaCard;
import hannina.fantasyCard.BlueColorCard;
import hannina.fantasyCard.GreenColorCard;
import hannina.fantasyCard.RedColorCard;
import hannina.modcore.Enums;
import hannina.powers.AntiUnionPower;

import java.util.ArrayList;

public class Jiaorongsansejin extends AbstractHanninaCard {
    public Jiaorongsansejin() {
        super(Jiaorongsansejin.class.getSimpleName(), 1, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);
        this.magicNumber = this.baseMagicNumber = 1;
        this.exhaust = true;
        this.tags.add(Enums.ChangeColorCard);
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        ArrayList<AbstractCard> stanceChoices = new ArrayList<>();
        stanceChoices.add(new RedColorCard());
        stanceChoices.add(new GreenColorCard());
        stanceChoices.add(new BlueColorCard());
        addToBot(new ChooseOneAction(stanceChoices));
        addToBot(new ApplyPowerAction(p, p, new AntiUnionPower(p, 4)));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(1);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new Jiaorongsansejin();
    }
}