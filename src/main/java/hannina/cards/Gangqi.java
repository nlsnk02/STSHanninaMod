package hannina.cards;

import basemod.AutoAdd;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.utility.ShowCardAndPoofAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hannina.actions.AnonymousAction;
import hannina.actions.MoveToHandAction;
import hannina.fantasyCard.AbstractHanninaCard;

import java.lang.reflect.Array;
import java.util.ArrayList;

@AutoAdd.NotSeen
public class Gangqi extends AbstractHanninaCard {
    public Gangqi() {
		super(Gangqi.class.getSimpleName(), 1, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
		this.block = this.baseBlock = 7;
		this.magicNumber = this.baseMagicNumber = 1;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
		this.addToBot(new GainBlockAction(p, p, this.block));
		
		this.addToBot(new AnonymousAction(() -> {
			ArrayList<AbstractCard> drawPile = p.drawPile.group.stream()
					.filter(c -> c.type == CardType.CURSE || c.type == CardType.STATUS)
					.collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
			ArrayList<AbstractCard> hand = p.hand.group.stream()
					.filter(c -> c.type == CardType.CURSE || c.type == CardType.STATUS)
					.collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
			
			for (int i = 0; i < this.magicNumber; i++) {
				AbstractCard card;
				boolean fromHand = false;
				if (!drawPile.isEmpty())
					card = drawPile.remove(AbstractDungeon.cardRandomRng.random(drawPile.size() - 1));
				else if (!hand.isEmpty()) {
					card = hand.remove(AbstractDungeon.cardRandomRng.random(hand.size() - 1));
					fromHand = true;
				}
				else
					break;
				
				this.addToTop(new ExhaustSpecificCardAction(card, fromHand ? p.hand : p.drawPile, true));
			}
		}));
	}

    @Override
    public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			this.upgradeBlock(2);
			this.upgradeMagicNumber(1);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new Gangqi();
    }
}
