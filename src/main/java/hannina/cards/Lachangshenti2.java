package hannina.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hannina.fantasyCard.AbstractHanninaCard;
import hannina.modcore.Enums;
import hannina.utils.ModHelper;
import hannina.utils.UnionManager;

import java.util.ArrayList;

public class Lachangshenti2 extends AbstractHanninaCard {
    public Lachangshenti2() {
        super(Lachangshenti2.class.getSimpleName(), 0, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        this.magicNumber = this.baseMagicNumber = 1;
        this.tags.add(Enums.UnionCard);
        this.tags.add(Enums.ChangeColorCard);
    }

    @Override
    public ArrayList<AbstractCard> setUnion() {
        ArrayList<AbstractCard> unions = new ArrayList<>();
        unions.add(UnionManager.getRamdomCard(c -> c.color == CardColor.RED));
        unions.add(UnionManager.getRamdomCard(c -> c.color == CardColor.GREEN));
        unions.add(UnionManager.getRamdomCard(c -> c.color == CardColor.BLUE));
        unions.add(UnionManager.getRamdomCard(c -> c.color == CardColor.PURPLE));
        return unions;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainEnergyAction(this.magicNumber));
        AbstractDungeon.actionManager.addToBottom(new AbstractGameAction() {
            @Override
            public void update() {
                ModHelper.enterRandomColor();
                this.isDone = true;
            }
        });
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(1);
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new Lachangshenti2();
    }
}