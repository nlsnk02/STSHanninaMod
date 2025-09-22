package hannina.cards;

import basemod.patches.com.megacrit.cardcrawl.cards.AbstractCard.MultiCardPreview;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hannina.fantasyCard.AbstractHanninaCard;

import java.util.ArrayList;
import java.util.UUID;
import java.util.stream.Collectors;

public class Zuiainile extends AbstractHanninaCard {

    //效果特判在初始牌里
    public Zuiainile() {
        super(Zuiainile.class.getSimpleName(), 1, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);
        this.baseMagicNumber = 1;
        MultiCardPreview.add(this, new HanninaStrike(), new HanninaDefence());
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new AbstractGameAction() {

            void enhanceCard(AbstractCard card) {
                card.baseMagicNumber += 1;
                if (card instanceof HanninaStrike) {
                    card.baseDamage += 1;
                } else card.baseBlock += 1;
            }

            void foo(String cardId, int num){
                ArrayList<AbstractCard> list = AbstractDungeon.player.masterDeck.group.stream()
                        .filter(c -> c.cardID.equals(cardId))
                        .sorted((c1, c2) -> c1.baseMagicNumber - c2.baseMagicNumber)
                        .collect(Collectors.toCollection(ArrayList::new));

                int i = 0;

                for (AbstractCard c : list) {
                    enhanceCard(c);
                    UUID uuid = c.uuid;
                    AbstractDungeon.player.hand.group.forEach(cc -> {
                        if (cc.uuid.equals(uuid)) {
                            enhanceCard(cc);
                            cc.flash();
                        }
                    });
                    AbstractDungeon.player.discardPile.group.forEach(cc -> {
                        if (cc.uuid.equals(uuid)) enhanceCard(cc);
                    });
                    AbstractDungeon.player.drawPile.group.forEach(cc -> {
                        if (cc.uuid.equals(uuid)) enhanceCard(cc);
                    });

                    i++;
                    if(i>=num) break;
                }
            }

            @Override
            public void update() {
                foo("hannina:HanninaDefence", Zuiainile.this.baseMagicNumber);
                foo("hannina:HanninaStrike", Zuiainile.this.baseMagicNumber);
                this.isDone = true;
            }
        });
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(1);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new Zuiainile();
    }
}