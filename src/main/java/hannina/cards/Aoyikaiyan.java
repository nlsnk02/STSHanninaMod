package hannina.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hannina.actions.AoyikaiyanAction;
import hannina.fantasyCard.AbstractHanninaCard;
import hannina.utils.ModHelper;

public class Aoyikaiyan extends AbstractHanninaCard {
    public static final String[] actionTEXT;

    public Aoyikaiyan() {
        super(Aoyikaiyan.class.getSimpleName(), 1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ALL_ENEMY);
        this.baseDamage = 2;
        this.magicNumber = this.baseMagicNumber = 4;
        this.isMultiDamage = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new AoyikaiyanAction(this.magicNumber, this.multiDamage, ModHelper.getPlayerColor() == CardColor.PURPLE));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(2);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new Aoyikaiyan();
    }

    static {
        UIStrings actionUiStrings = CardCrawlGame.languagePack.getUIString("hannina:Xuedinge");
        actionTEXT = actionUiStrings.TEXT;
    }
}