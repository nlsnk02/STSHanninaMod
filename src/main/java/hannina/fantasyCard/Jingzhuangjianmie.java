package hannina.fantasyCard;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.LightningEffect;
import hannina.actions.GainGoldAction;
import hannina.utils.MonsterDieThisTurnManager;

public class Jingzhuangjianmie extends AbstractHanninaCard {

    //效果特判在初始牌里
    public Jingzhuangjianmie() {
        super(Jingzhuangjianmie.class.getSimpleName(), 1, CardType.ATTACK, CardRarity.RARE, CardTarget.ALL_ENEMY);
        this.isMultiDamage = true;
        this.baseDamage = 7;
        this.baseMagicNumber = 15;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new SFXAction("THUNDERCLAP", 0.05F));

        for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
            if (!mo.isDeadOrEscaped()) {
                this.addToBot(new VFXAction(new LightningEffect(mo.drawX, mo.drawY), 0.05F));
            }
        }
        this.addToBot(new DamageAllEnemiesAction(p, this.multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.NONE));

//        for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
//            this.addToBot(new ApplyPowerAction(mo, p, new VulnerablePower(mo, 1, false), 1, true, AbstractGameAction.AttackEffect.NONE));
//        }

        addToBot(new AbstractGameAction() {
            {
                actionType = AbstractGameAction.ActionType.DAMAGE;
            }
            @Override
            public void update() {
                addToBot(new AbstractGameAction() {

                    {
                        actionType = AbstractGameAction.ActionType.DAMAGE;
                    }

                    @Override
                    public void update() {
                        int amt = MonsterDieThisTurnManager.amount;
                        if (amt > 0) {
//                            addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player,
//                                    new BufferPower(AbstractDungeon.player, amt)));
                            addToTop(new GainGoldAction(amt * Jingzhuangjianmie.this.magicNumber));
                        }
                        this.isDone = true;
                    }
                });
                this.isDone = true;
            }
        });
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(3);
            upgradeMagicNumber(5);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new Jingzhuangjianmie();
    }
}