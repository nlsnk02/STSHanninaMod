package hannina.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.actions.watcher.ChooseOneAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.optionCards.ChooseCalm;
import com.megacrit.cardcrawl.cards.optionCards.ChooseWrath;
import com.megacrit.cardcrawl.cards.purple.Blasphemy;
import com.megacrit.cardcrawl.cards.purple.Brilliance;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.watcher.MantraPower;
import com.megacrit.cardcrawl.random.Random;
import hannina.actions.ChangeCharColorAction;
import hannina.fantasyCard.AbstractHanninaCard;
import hannina.modcore.Enums;
import hannina.powers.AntiUnionPower;

import java.util.ArrayList;
import java.util.Objects;

public class Gongxinziluolan extends AbstractHanninaCard {

    public int nextInt = 3;

    public Gongxinziluolan() {
        super(Gongxinziluolan.class.getSimpleName(), 1, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);

        this.magicNumber = this.baseMagicNumber = 2;
        this.block = this.baseBlock = 7;

        this.cardsToPreview = new Brilliance();

        this.tags.add(Enums.ChangeColorCard);
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
        this.block = this.baseBlock;
        this.isBlockModified = false;

        if (upgraded)
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
        else this.rawDescription = cardStrings.DESCRIPTION;

        this.rawDescription = String.format(this.rawDescription + cardStrings.EXTENDED_DESCRIPTION[0], nextInt);
        this.initializeDescription();
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        super.calculateCardDamage(mo);
        this.block = this.baseBlock;
        this.isBlockModified = false;
    }

    @Override
    public void update() {
        super.update();
        if (AbstractDungeon.cardRandomRng != null) {
            Random rng = AbstractDungeon.cardRandomRng.copy();
            nextInt = rng.random(this.baseMagicNumber, this.baseBlock);
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ChangeCharColorAction(CardColor.PURPLE));

        addToBot(new ApplyPowerAction(p, p, new MantraPower(p, nextInt)));
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                AbstractDungeon.cardRandomRng.random();
                this.isDone = true;
            }
        });

        int powerAmount = 0;
        if (p.hasPower(MantraPower.POWER_ID)) {
            powerAmount = p.getPower(MantraPower.POWER_ID).amount;
        }
        if (powerAmount + nextInt >= 10 && !Objects.equals(AbstractDungeon.player.stance.ID, "Divinity")) {
            this.exhaust = true;
            AbstractCard c = new Brilliance();
            if (upgraded)
                c.upgrade();
            addToBot(new MakeTempCardInHandAction(c));
        }

        addToBot(new ApplyPowerAction(p, p, new AntiUnionPower(p, 1)));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(1);
            upgradeBlock(1);

            AbstractCard c = new Brilliance();
            c.upgrade();
            this.cardsToPreview = c;

            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new Gongxinziluolan();
    }
}