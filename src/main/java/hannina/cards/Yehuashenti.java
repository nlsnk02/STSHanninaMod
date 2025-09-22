package hannina.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hannina.fantasyCard.AbstractHanninaCard;
import hannina.modcore.Enums;
import hannina.utils.ModHelper;

public class Yehuashenti extends AbstractHanninaCard {
    public Yehuashenti() {
        super(Yehuashenti.class.getSimpleName(), 1, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        this.block = this.baseBlock = 4;
    }

    @Override
    public void triggerOnGlowCheck() {
        this.glowColor = ModHelper.getPlayerColor() ==  Enums.HanninaColor ?
                AbstractCard.GOLD_BORDER_GLOW_COLOR : AbstractCard.BLUE_BORDER_GLOW_COLOR;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int times = 2;
        if (ModHelper.getPlayerColor() ==  Enums.HanninaColor) times++;

        for (int i = 0; i < times; ++i) {
            addToBot(new GainBlockAction(p, p, this.block));
        }
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBlock(1);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new Yehuashenti();
    }
}