package hannina.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.actions.defect.IncreaseMaxOrbAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.blue.Capacitor;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import hannina.actions.GainGoldAction;
import hannina.actions.SelectFromHandAction;
import hannina.fantasyCard.AbstractHanninaCard;
import hannina.utils.ModHelper;
import hannina.utils.UnionManager;

import java.util.ArrayList;

public class Wanqiushijiandao extends AbstractHanninaCard {
    public Wanqiushijiandao() {
        super(Wanqiushijiandao.class.getSimpleName(), 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        this.magicNumber = this.baseMagicNumber = 1;
    }

//    @Override
//    public ArrayList<AbstractCard> setUnion() {
//        ArrayList<AbstractCard> unions = new ArrayList<>();
//        unions.add(UnionManager.getRamdomCard(c -> c.color == CardColor.RED));
//        unions.add(UnionManager.getRamdomCard(c -> c.color == CardColor.GREEN));
//        unions.add(UnionManager.getRamdomCard(c -> c.color == CardColor.BLUE));
//        unions.add(UnionManager.getRamdomCard(c -> c.color == CardColor.PURPLE));
//
//        return unions;
//    }

    private void triggerOrb(AbstractOrb orb) {
        orb.onEndOfTurn();
        orb.onStartOfTurn();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractOrb orb = AbstractOrb.getRandomOrb(true);
        addToBot(new ChannelAction(orb));
        if (this.upgraded)
            addToBot(new IncreaseMaxOrbAction(1));

        for (int i = 0; i < this.baseMagicNumber; i++)
            triggerOrb(orb);
        if (ModHelper.getPlayerColor() == CardColor.BLUE) {
            triggerOrb(orb);
        }
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            upgradeMagicNumber(1);
            initializeDescription();
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new Wanqiushijiandao();
    }
}