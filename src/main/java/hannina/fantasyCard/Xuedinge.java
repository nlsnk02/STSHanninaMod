package hannina.fantasyCard;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hannina.actions.SelectFromRewardAction;
import hannina.utils.UnionManager;

import java.util.ArrayList;

public class Xuedinge extends AbstractHanninaCard {
    public static final String[] actionTEXT;

    public Xuedinge() {
        super(Xuedinge.class.getSimpleName(), 0, CardType.SKILL, CardRarity.COMMON, CardTarget.NONE);
        this.magicNumber = this.baseMagicNumber = 2;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        ArrayList<AbstractCard> cards = new ArrayList<>();
        while (cards.size() < this.magicNumber) {
            AbstractCard card = UnionManager.getRamdomCard(c -> true);
            if (cards.stream().noneMatch(c -> card.cardID.equals(c.cardID))) {
                cards.add(card);
            }
        }

        addToBot(new SelectFromRewardAction(cards,
                c -> c.ifPresent(abstractCard -> addToBot(new MakeTempCardInDrawPileAction(abstractCard, 1, true, true))),
                actionTEXT[0], true, AbstractGameAction.ActionType.DRAW));
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
        return new Xuedinge();
    }

    static {
        UIStrings actionUiStrings = CardCrawlGame.languagePack.getUIString("hannina:Xuedinge");
        actionTEXT = actionUiStrings.TEXT;
    }
}