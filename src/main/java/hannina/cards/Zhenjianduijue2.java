package hannina.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.green.Finisher;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PlatedArmorPower;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import com.megacrit.cardcrawl.vfx.ThoughtBubble;
import hannina.fantasyCard.AbstractHanninaCard;
import hannina.utils.ModHelper;

public class Zhenjianduijue2 extends AbstractHanninaCard {
    private int numSkill = 0;
    private int numAttack = 0;

    public Zhenjianduijue2() {
        super(Zhenjianduijue2.class.getSimpleName(), 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        this.damage = this.baseDamage = 4;
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
        numSkill = 0;
        numAttack = 0;
        for (AbstractCard c : AbstractDungeon.actionManager.cardsPlayedThisTurn) {
            if (c.type == CardType.ATTACK) {
                ++numAttack;
            }
            if (c.type == CardType.SKILL) {
                ++numSkill;
            }
        }
        this.rawDescription = String.format(cardStrings.DESCRIPTION + cardStrings.EXTENDED_DESCRIPTION[0],
                numSkill, numAttack);
        this.initializeDescription();
    }

    @Override
    public void onMoveToDiscard() {
        this.rawDescription = cardStrings.DESCRIPTION;
        this.initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (numSkill > 0)
            addToBot(new DamageAction(m, new DamageInfo(p, damage * numSkill, damageTypeForTurn),
                    AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));

        if (ModHelper.getPlayerColor() == CardColor.RED && numAttack > 0)
            addToBot(new ApplyPowerAction(p, p, new VigorPower(p, numAttack)));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(2);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new Zhenjianduijue2();
    }
}