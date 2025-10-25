package hannina.cards;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;
import com.megacrit.cardcrawl.vfx.combat.LightningEffect;
import hannina.actions.GainGoldAction;
import hannina.fantasyCard.AbstractHanninaCard;
import hannina.utils.GoldManager;
import hannina.utils.ModHelper;
import hannina.utils.MonsterDieThisTurnManager;

public class Jingzhuangjianmie2 extends AbstractHanninaCard {

    public Jingzhuangjianmie2() {
        super(Jingzhuangjianmie2.class.getSimpleName(), 1, CardType.ATTACK, CardRarity.RARE, CardTarget.ALL_ENEMY);
        this.isMultiDamage = true;
        this.baseDamage = 7;
        this.magicNumber = this.baseMagicNumber = 0;
        this.exhaust = true;
    }

    @Override
    public void triggerOnGlowCheck() {
        this.glowColor = GoldManager.goldLoseInCombat >= 100 ? AbstractCard.GOLD_BORDER_GLOW_COLOR : AbstractCard.BLUE_BORDER_GLOW_COLOR;
    }

    @Override
    public void applyPowers() {
        this.magicNumber = this.baseMagicNumber =  GoldManager.goldLoseInCombat;
        super.applyPowers();
        this.rawDescription = cardStrings.DESCRIPTION + cardStrings.EXTENDED_DESCRIPTION[0];
        initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new SFXAction("THUNDERCLAP", 0.05F));

        for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
            if (!mo.isDeadOrEscaped()) {
                this.addToBot(new VFXAction(new LightningEffect(mo.drawX, mo.drawY), 0.05F));
            }
        }
        addToBot(new AbstractGameAction() {

            public int[] damage;
            private boolean firstFrame;

            {
                this.firstFrame = true;
                this.source = AbstractDungeon.player;
                this.damage = Jingzhuangjianmie2.this.multiDamage;
                this.actionType = ActionType.DAMAGE;
                this.damageType = Jingzhuangjianmie2.this.damageTypeForTurn;
                this.attackEffect = AbstractGameAction.AttackEffect.NONE;
                this.duration = Settings.ACTION_DUR_XFAST;

            }

            @Override
            public void update() {
                if (this.firstFrame) {
                    boolean playedMusic = false;
                    int temp = AbstractDungeon.getCurrRoom().monsters.monsters.size();

                    for (int i = 0; i < temp; ++i) {
                        if (!AbstractDungeon.getCurrRoom().monsters.monsters.get(i).isDying && AbstractDungeon.getCurrRoom().monsters.monsters.get(i).currentHealth > 0 && !AbstractDungeon.getCurrRoom().monsters.monsters.get(i).isEscaping) {
                            if (playedMusic) {
                                AbstractDungeon.effectList.add(new FlashAtkImgEffect(AbstractDungeon.getCurrRoom().monsters.monsters.get(i).hb.cX, AbstractDungeon.getCurrRoom().monsters.monsters.get(i).hb.cY, this.attackEffect, true));
                            } else {
                                playedMusic = true;
                                AbstractDungeon.effectList.add(new FlashAtkImgEffect(AbstractDungeon.getCurrRoom().monsters.monsters.get(i).hb.cX, AbstractDungeon.getCurrRoom().monsters.monsters.get(i).hb.cY, this.attackEffect));
                            }
                        }
                    }

                    this.firstFrame = false;
                }

                this.tickDuration();
                if (this.isDone) {
                    for (AbstractPower p : AbstractDungeon.player.powers) {
                        p.onDamageAllEnemies(this.damage);
                    }

                    int temp = AbstractDungeon.getCurrRoom().monsters.monsters.size();

                    for (int i = 0; i < temp; ++i) {
                        if (!AbstractDungeon.getCurrRoom().monsters.monsters.get(i).isDeadOrEscaped()) {
                            if (this.attackEffect == AttackEffect.POISON) {
                                AbstractDungeon.getCurrRoom().monsters.monsters.get(i).tint.color.set(Color.CHARTREUSE);
                                AbstractDungeon.getCurrRoom().monsters.monsters.get(i).tint.changeColor(Color.WHITE.cpy());
                            } else if (this.attackEffect == AttackEffect.FIRE) {
                                AbstractDungeon.getCurrRoom().monsters.monsters.get(i).tint.color.set(Color.RED);
                                AbstractDungeon.getCurrRoom().monsters.monsters.get(i).tint.changeColor(Color.WHITE.cpy());
                            }

                            AbstractDungeon.getCurrRoom().monsters.monsters.get(i).damage(new DamageInfo(this.source, this.damage[i], this.damageType));
                            addToTop(new GainGoldAction(this.damage[i]));
                        }
                    }

                    if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
                        AbstractDungeon.actionManager.clearPostCombatActions();
                    }

                    if (!Settings.FAST_MODE) {
                        this.addToTop(new WaitAction(0.1F));
                    }
                }

            }
        });

        if(GoldManager.goldLoseInCombat + GoldManager.goldGainInCombat >= 100){
            ModHelper.fusion();
        }
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(3);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new Jingzhuangjianmie2();
    }
}