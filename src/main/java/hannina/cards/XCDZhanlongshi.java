package hannina.cards;

import basemod.abstracts.CustomSavable;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.curses.Necronomicurse;
import com.megacrit.cardcrawl.cards.curses.Parasite;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.GetAllInBattleInstances;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.NecronomicurseEffect;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;
import hannina.fantasyCard.AbstractHanninaCard;
import hannina.utils.ModHelper;
import hannina.utils.UnionManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class XCDZhanlongshi extends AbstractHanninaCard implements CustomSavable<Integer[]> {


    public XCDZhanlongshi() {
        super(XCDZhanlongshi.class.getSimpleName(), 0, CardType.ATTACK, CardRarity.RARE, CardTarget.ENEMY);
        this.damage = this.baseDamage = 8;
        this.magicNumber = this.baseMagicNumber = 1;
        this.cardsToPreview = new XingshengjianZhanlongshi();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        addToBot(new AbstractGameAction() {
            private DamageInfo info;
            private UUID uuid;

            {
                this.info = new DamageInfo(p, XCDZhanlongshi.this.damage, XCDZhanlongshi.this.damageTypeForTurn);
                this.setValues(m, info);
                this.actionType = ActionType.DAMAGE;
                this.duration = 0.1F;
                this.uuid = XCDZhanlongshi.this.uuid;
            }

            @Override
            public void update() {
                if (this.duration == 0.1F && this.target != null) {
                    AbstractDungeon.effectList.add(new FlashAtkImgEffect(this.target.hb.cX, this.target.hb.cY, AttackEffect.SLASH_HORIZONTAL));
                    this.target.damage(this.info);
                    if ((this.target.isDying || this.target.currentHealth <= 0) && !this.target.halfDead && !this.target.hasPower("Minion")) {
                        for (AbstractCard c : AbstractDungeon.player.masterDeck.group) {
                            if (c.uuid.equals(this.uuid)) {
                                c.baseDamage += c.baseMagicNumber;
                                c.upgradedDamage = true;
                            }
                        }

                        for (AbstractCard c : GetAllInBattleInstances.get(this.uuid)) {
                            c.baseDamage += c.baseMagicNumber;
                        }
                    }

                    if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
                        AbstractDungeon.actionManager.clearPostCombatActions();
                    }
                }

                this.tickDuration();
            }
        });
    }

    @Override
    public void onRemoveFromMasterDeck() {
        if (this.baseDamage >= 14)
            AbstractDungeon.effectsQueue.add(new NecronomicurseEffect(new XingshengjianZhanlongshi(), (float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));
    }

    @Override
    public boolean canUpgrade() {
        return true;
    }

    @Override
    public void upgrade() {
        upgradeDamage(1);
        upgradeMagicNumber(1);

        this.timesUpgraded++;
        this.upgraded = true;
        this.name = cardStrings.NAME + "+" + this.timesUpgraded;
        initializeTitle();
    }

    @Override
    public AbstractCard makeCopy() {
        return new XCDZhanlongshi();
    }

    @Override
    public Integer[] onSave() {
        return new Integer[]{this.baseDamage, this.magicNumber, this.timesUpgraded};
    }

    @Override
    public void onLoad(Integer[] integers) {
        if (integers != null) {
            this.baseDamage = integers[0];
            this.magicNumber = integers[1];
            this.timesUpgraded = integers[2];
        }
    }
}