package hannina.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.powers.watcher.MarkPower;
import hannina.fantasyCard.AbstractHanninaCard;
import hannina.powers.ZhuazhunilePower;
import hannina.utils.ModHelper;

public class Zhuazhunile extends AbstractHanninaCard {
    public Zhuazhunile() {
        super(Zhuazhunile.class.getSimpleName(), 2, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        this.damage = this.baseDamage = 11;
        this.cardsToPreview = new Maodepingwenluodi();
    }

    private boolean hasModifiedCost = false;

    @Override
    public void applyPowers() {
        if (ModHelper.getPlayerColor() == CardColor.GREEN && this.costForTurn > 0 && !hasModifiedCost) {
            this.hasModifiedCost = true;
            setCostForTurn(this.costForTurn - 1);
        }
        if (ModHelper.getPlayerColor() != CardColor.GREEN && hasModifiedCost) {
            this.hasModifiedCost = false;
            if (this.costForTurn < this.cost)
                setCostForTurn(this.costForTurn + 1);
        }
        super.applyPowers();
    }

    @Override
    public void resetAttributes() {
        super.resetAttributes();
        this.hasModifiedCost = false;
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn),
                AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));

        addToBot(new ApplyPowerAction(m, p, new StrengthPower(m, -1)));

        addToBot(new ApplyPowerAction(p, p, new ZhuazhunilePower(p, 1)));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(3);
            AbstractCard c = new Maodepingwenluodi();
            c.upgrade();
            this.cardsToPreview = c;
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new Zhuazhunile();
    }
}