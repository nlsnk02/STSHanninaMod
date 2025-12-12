package hannina.cards;

import basemod.AutoAdd;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hannina.actions.MoveToHandAction;
import hannina.fantasyCard.AbstractHanninaCard;

import java.util.ArrayList;

@AutoAdd.NotSeen
public class Zhuangdan extends AbstractHanninaCard {
    public Zhuangdan() {
		super(Zhuangdan.class.getSimpleName(), 0, CardType.SKILL, CardRarity.COMMON, CardTarget.NONE);
		this.magicNumber = this.baseMagicNumber = 1;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
		this.addToBot(new MoveToHandAction(p.discardPile, this.magicNumber,
				c -> c.type == CardType.ATTACK));
	}

    @Override
    public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			this.upgradeMagicNumber(1);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new Zhuangdan();
    }
}
