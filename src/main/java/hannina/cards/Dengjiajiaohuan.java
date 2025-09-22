package hannina.cards;

import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ExhaustAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hannina.actions.GainGoldAction;
import hannina.actions.SelectFromHandAction;
import hannina.fantasyCard.AbstractHanninaCard;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Dengjiajiaohuan extends AbstractHanninaCard {
    public Dengjiajiaohuan() {
        super(Dengjiajiaohuan.class.getSimpleName(), 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        this.magicNumber = this.baseMagicNumber = 2;
        this.block = this.baseBlock = 0;
    }

    @Override
    public List<TooltipInfo> getCustomTooltips() {
        return Collections.singletonList(new TooltipInfo(
                this.cardStrings.EXTENDED_DESCRIPTION[0],
                (this.upgraded ? this.cardStrings.EXTENDED_DESCRIPTION[2] : this.cardStrings.EXTENDED_DESCRIPTION[1])
        ));
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int amount = this.block;

        addToBot(new SelectFromHandAction(c -> true, l -> {
            for (AbstractCard c : l) {
                AbstractDungeon.player.hand.moveToExhaustPile(c);
                int amt = amount;
                if (Dengjiajiaohuan.this.upgraded) amt += 2;
                if (c.type == CardType.STATUS || c.type == CardType.CURSE || c.rarity == CardRarity.BASIC) amt += 4;
                else if (c.rarity == CardRarity.COMMON || c.rarity == CardRarity.UNCOMMON) amt += 8;
                else if (c.rarity == CardRarity.RARE) amt += 12;
                addToTop(new GainBlockAction(AbstractDungeon.player, amt));
            }

            if (!l.isEmpty())
                addToTop(new GainGoldAction(this.magicNumber));

            CardCrawlGame.dungeon.checkForPactAchievement();
        }, this.cardStrings.EXTENDED_DESCRIPTION[3],
                1, false, false, AbstractGameAction.ActionType.DRAW));
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
        return new Dengjiajiaohuan();
    }
}