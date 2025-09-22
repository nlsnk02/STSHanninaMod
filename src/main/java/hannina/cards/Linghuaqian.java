package hannina.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.FlickCoinEffect;
import hannina.actions.GainGoldAction;
import hannina.actions.LoseGoldAction;
import hannina.actions.SelectFromHandAction;
import hannina.fantasyCard.AbstractHanninaCard;

public class Linghuaqian extends AbstractHanninaCard {
    private static final String[] UITEXT;

    static {
        UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("ExhaustAction");
        UITEXT = uiStrings.TEXT;
    }

    public Linghuaqian() {
        super(Linghuaqian.class.getSimpleName(), 1, CardType.SKILL, CardRarity.COMMON, CardTarget.NONE);
        this.magicNumber = this.baseMagicNumber = 1;
        this.isEthereal = true;
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new SelectFromHandAction(c -> true,
                list -> {
                    for (AbstractCard c : list) {
                        AbstractDungeon.player.hand.moveToExhaustPile(c);
                    }
                    addToTop(new GainGoldAction(list.size() * this.magicNumber));
                    addToTop(new DrawCardAction(list.size()));

                    CardCrawlGame.dungeon.checkForPactAchievement();
                    AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
                },
                UITEXT[0], 3, true, true, AbstractGameAction.ActionType.EXHAUST));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(1);
            this.isEthereal = false;
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new Linghuaqian();
    }
}