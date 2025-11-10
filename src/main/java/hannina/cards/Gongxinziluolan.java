package hannina.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.purple.Brilliance;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.watcher.MantraPower;
import com.megacrit.cardcrawl.stances.DivinityStance;
import hannina.actions.ChangeCharColorAction;
import hannina.fantasyCard.AbstractHanninaCard;
import hannina.modcore.Enums;
import hannina.powers.AntiUnionPower;

public class Gongxinziluolan extends AbstractHanninaCard {

    public Gongxinziluolan() {
        super(Gongxinziluolan.class.getSimpleName(), 1, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);
        this.tags.add(Enums.ChangeColorCard);
        this.cardsToPreview = new Brilliance();
        this.baseMagicNumber = this.magicNumber = 5;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ChangeCharColorAction(CardColor.PURPLE));
        addToBot(new ApplyPowerAction(p, p, new MantraPower(p, this.magicNumber)));
        addToBot(new ApplyPowerAction(p, p, new AntiUnionPower(p)));
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                if (p.stance instanceof DivinityStance) {
                    addToTop(new MakeTempCardInHandAction(cardsToPreview));
                    Gongxinziluolan.this.exhaust = true;
                }
                isDone = true;
            }
        });
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            this.initializeDescription();
            this.cardsToPreview.upgrade();
            this.upgradeMagicNumber(2);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new Gongxinziluolan();
    }
}