package hannina.cards;

import basemod.abstracts.CustomSavable;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.watcher.ChooseOneAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.GetAllInBattleInstances;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;
import hannina.fantasyCard.AbstractHanninaCard;
import hannina.fantasyCard.FusionColorCard;
import hannina.fantasyCard.NullColorCard;
import hannina.utils.ModHelper;

import java.util.ArrayList;
import java.util.UUID;

public class XCDDangmoshi extends AbstractHanninaCard implements CustomSavable<Integer[]> {

    public static int usedInThisCombat = 0;

    public XCDDangmoshi() {
        super(XCDDangmoshi.class.getSimpleName(), 2, CardType.ATTACK, CardRarity.RARE, CardTarget.ENEMY);
        this.damage = this.baseDamage = 6;
        this.baseMagicNumber = this.magicNumber = 3;
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
        if(this.returnToHand)
            this.returnToHand = false;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                XCDDangmoshi.usedInThisCombat++;
                isDone = true;
            }
        });

        for (int i=0;i<3;i++) {
            addToBot(new AbstractGameAction() {
                private DamageInfo info;

                {
                    this.info = new DamageInfo(p, XCDDangmoshi.this.damage, XCDDangmoshi.this.damageTypeForTurn);
                    this.setValues(m, info);
                    this.actionType = ActionType.DAMAGE;
                    this.duration = 0.1F;
                }

                @Override
                public void update() {
                    if (this.duration == 0.1F && this.target != null) {
                        AbstractDungeon.effectList.add(new FlashAtkImgEffect(this.target.hb.cX, this.target.hb.cY, AttackEffect.SLASH_HORIZONTAL));
                        this.target.damage(this.info);
                        if ((this.target.isDying || this.target.currentHealth <= 0) && !this.target.halfDead) {
                            XCDDangmoshi.this.returnToHand = true;
                            XCDDangmoshi.this.modifyCostForCombat(-1);
                            isDone = true;
                            return;
                        }

                        if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
                            AbstractDungeon.actionManager.clearPostCombatActions();
                        }
                    }

                    this.tickDuration();
                }
            });
        }

        if(usedInThisCombat >= this.magicNumber - 1) {
            addToBot(new GainEnergyAction(2));
            ArrayList<AbstractCard> stanceChoices = new ArrayList<>();
            stanceChoices.add(new FusionColorCard(this));
            stanceChoices.add(new NullColorCard());
            addToBot(new ChooseOneAction(stanceChoices));
        }
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(2);
            upgradeMagicNumber(-1);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new XCDDangmoshi();
    }

    @Override
    public Integer[] onSave() {
        return new Integer[]{this.baseDamage, this.magicNumber, this.timesUpgraded};
    }

    @Override
    public void onLoad(Integer[] integers) {
        if(integers != null) {
            this.baseDamage = integers[0];
            this.magicNumber = integers[1];
            this.timesUpgraded = integers[2];
        }
    }
}