package hannina.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.actions.watcher.ChooseOneAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.optionCards.ChooseCalm;
import com.megacrit.cardcrawl.cards.optionCards.ChooseWrath;
import com.megacrit.cardcrawl.cards.purple.Blasphemy;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hannina.actions.ChangeCharColorAction;
import hannina.fantasyCard.AbstractHanninaCard;
import hannina.modcore.Enums;
import hannina.powers.AntiUnionPower;

import java.util.ArrayList;

public class Gongxinziluolan extends AbstractHanninaCard {
    public Gongxinziluolan() {
        super(Gongxinziluolan.class.getSimpleName(), 3, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);
        this.exhaust = true;
        this.tags.add(Enums.ChangeColorCard);
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ChangeCharColorAction(CardColor.PURPLE));
        addToBot(new ChangeStanceAction("Divinity"));
        addToBot(new ApplyPowerAction(p, p, new AntiUnionPower(p, 1)));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBaseCost(2);
//            this.isEthereal = false;
//            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
//            initializeDescription();
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new Gongxinziluolan();
    }
}