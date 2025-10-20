package hannina.fantasyCard;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PlatedArmorPower;
import com.megacrit.cardcrawl.vfx.ThoughtBubble;
import hannina.utils.ModHelper;

public class Zhenjianduijue extends AbstractHanninaCard {
    public static final String[] actionTEXT;

    public Zhenjianduijue() {
        super(Zhenjianduijue.class.getSimpleName(), 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        this.damage = this.baseDamage = 6;
        this.baseMagicNumber = this.magicNumber = 2;
        this.block = this.baseBlock = 4;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn),
                AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));

        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                if (m != null && m.getIntentBaseDmg() >= 0) {
                    addToTop(new GainBlockAction(p, p, block));
                } else {
                    AbstractDungeon.effectList.add(new ThoughtBubble(AbstractDungeon.player.dialogX, AbstractDungeon.player.dialogY, 3.0F, actionTEXT[0], true));
                }
                this.isDone = true;
            }

        });
        if(ModHelper.getPlayerColor() == CardColor.RED)
            addToBot(new ApplyPowerAction(p,p, new PlatedArmorPower(p, this.magicNumber)));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(2);
            upgradeBlock(2);
            upgradeMagicNumber(1);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new Zhenjianduijue();
    }

    static {
        UIStrings actionUiStrings = CardCrawlGame.languagePack.getUIString("OpeningAction");
        actionTEXT = actionUiStrings.TEXT;
    }
}