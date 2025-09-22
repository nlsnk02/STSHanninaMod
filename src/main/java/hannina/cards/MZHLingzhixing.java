package hannina.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hannina.fantasyCard.AbstractHanninaCard;
import hannina.utils.ModHelper;
import hannina.utils.UnionManager;

import java.util.ArrayList;

public class MZHLingzhixing extends AbstractHanninaCard {
    public MZHLingzhixing() {
        super(MZHLingzhixing.class.getSimpleName(), 1, CardType.SKILL, CardRarity.COMMON, CardTarget.NONE);
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractCard card1 = UnionManager.getRamdomCard(c ->
                c.color == ModHelper.getPlayerColor() && c.rarity == CardRarity.COMMON);
        AbstractCard card2 = UnionManager.getRamdomCard(c ->
                c.color == ModHelper.getPlayerColor() && c.rarity == CardRarity.COMMON);
        card1.setCostForTurn(0);
        card2.setCostForTurn(0);
        addToBot(new MakeTempCardInHandAction(card1));
        addToBot(new MakeTempCardInHandAction(card2));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBaseCost(0);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new MZHLingzhixing();
    }
}