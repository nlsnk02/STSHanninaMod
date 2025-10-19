package hannina.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.watcher.ChooseOneAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hannina.actions.ChangeCharColorAction;
import hannina.actions.ChooseColor2EnterAction;
import hannina.fantasyCard.*;
import hannina.modcore.Enums;
import hannina.powers.XingtaibianhuaPower;
import hannina.utils.ModHelper;

import java.util.ArrayList;

public class Xingtaibianhua extends AbstractHanninaCard {
    public Xingtaibianhua() {
        super(Xingtaibianhua.class.getSimpleName(), 0, CardType.SKILL, CardRarity.BASIC, CardTarget.SELF);
        this.magicNumber = this.baseMagicNumber = 1;
        this.tags.add(Enums.ChangeColorCard);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ChooseColor2EnterAction());
        addToBot(new ApplyPowerAction(p, p, new XingtaibianhuaPower(p, this.magicNumber), this.magicNumber));
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
        return new Xingtaibianhua();
    }
}