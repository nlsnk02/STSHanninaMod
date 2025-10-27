package hannina.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.GainGoldAction;
import com.megacrit.cardcrawl.actions.utility.ScryAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.watcher.MantraPower;
import hannina.actions.LoseGoldAction;
import hannina.actions.MyScryAction;
import hannina.fantasyCard.AbstractHanninaCard;
import hannina.utils.ModHelper;

public class Touqianwenlu extends AbstractHanninaCard {
    public Touqianwenlu() {
        super(Touqianwenlu.class.getSimpleName(), 0, CardType.SKILL, CardRarity.COMMON, CardTarget.NONE);
        this.damage = this.baseDamage = 2;
        this.block = this.baseBlock = 1;
        this.magicNumber = this.baseMagicNumber = 5;
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
        this.damage = this.baseDamage;
        this.isDamageModified = false;
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        super.calculateCardDamage(mo);
        this.damage = this.baseDamage;
        this.isDamageModified = false;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new LoseGoldAction(1));

        addToBot(new MyScryAction(this.magicNumber, (l1, l2) -> {
            if (!l2.isEmpty()) {
                addToTop(new GainBlockAction(p, this.block * l2.size()));
            }
        }));

        if (ModHelper.getPlayerColor() == CardColor.PURPLE)
            addToBot(new ApplyPowerAction(p, p, new MantraPower(p, this.baseDamage)));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(1);
            upgradeMagicNumber(2);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new Touqianwenlu();
    }
}