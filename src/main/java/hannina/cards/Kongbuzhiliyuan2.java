package hannina.cards;

import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.colorless.Madness;
import com.megacrit.cardcrawl.cards.purple.Collect;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.LockOnPower;
import com.megacrit.cardcrawl.powers.PoisonPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.powers.watcher.MarkPower;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import hannina.fantasyCard.AbstractHanninaCard;
import hannina.misc.ReunionModifier;
import hannina.modcore.Enums;
import hannina.utils.UnionManager;

public class Kongbuzhiliyuan2 extends AbstractHanninaCard {
    public Kongbuzhiliyuan2() {
        super(Kongbuzhiliyuan2.class.getSimpleName(), -1, CardType.SKILL, CardRarity.RARE, CardTarget.NONE);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        boolean tmpFreeToPlayOnce = this.freeToPlayOnce;
        int tmpEnergyOnUse = this.energyOnUse;
        boolean tmpUpgraded = this.upgraded;

        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                int effect = EnergyPanel.totalCount;
                if (tmpEnergyOnUse != -1) {
                    effect = tmpEnergyOnUse;
                }

                if (AbstractDungeon.player.hasRelic("Chemical X")) {
                    effect += 2;
                    AbstractDungeon.player.getRelic("Chemical X").flash();
                }

                for (int i = 0; i < effect; ++i) {
                    AbstractCard card = UnionManager.getRamdomCard(c ->
                            c.hasTag(Enums.UnionCard)
                    );

                    UnionManager.configureOnSpawn(card);
                    if (CardModifierManager.hasModifier(card, "ReunionModifier")) {
                        ReunionModifier r = (ReunionModifier) (CardModifierManager.getModifiers(card, "ReunionModifier").get(0));

                        for (AbstractCard cc : r.union) {
                            cc.modifyCostForCombat(-100);
                            if (tmpUpgraded) cc.upgrade();
                            cc.update();
                        }
                    }

                    addToTop(new MakeTempCardInHandAction(card));


                    if (!tmpFreeToPlayOnce) {
                        AbstractDungeon.player.energy.use(EnergyPanel.totalCount);
                    }
                }

                this.isDone = true;
            }
        });
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new Kongbuzhiliyuan2();
    }
}