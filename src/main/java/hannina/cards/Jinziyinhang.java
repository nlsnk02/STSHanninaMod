package hannina.cards;

import basemod.abstracts.CustomSavable;
import basemod.patches.com.megacrit.cardcrawl.cards.AbstractCard.MultiCardPreview;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.BarricadePower;
import hannina.actions.LoseGoldAction;
import hannina.fantasyCard.AbstractHanninaCard;
import hannina.powers.JinziyinhangPower;

import java.util.stream.Stream;

public class Jinziyinhang extends AbstractHanninaCard implements CustomSavable<Integer> {

    public Jinziyinhang() {
        super(Jinziyinhang.class.getSimpleName(), 3, CardType.POWER, CardRarity.RARE, CardTarget.SELF);
        this.block = this.baseBlock = 10;
        this.magicNumber = this.baseMagicNumber = 30;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new BarricadePower(p)));
        int gold = p.gold * this.magicNumber / 100;
        addToBot(new LoseGoldAction(gold));
        addToBot(new GainBlockAction(p, gold + (upgraded ? this.block : 0)));
        addToBot(new ApplyPowerAction(p, p, new JinziyinhangPower(p, 1)));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
//            upgradeMagicNumber(1);
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new Jinziyinhang();
    }

    @Override
    public Integer onSave() {
        return this.baseMagicNumber;
    }

    @Override
    public void onLoad(Integer integer) {
        this.baseMagicNumber = integer;
    }
}