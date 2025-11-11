package hannina.cards;

import basemod.patches.com.megacrit.cardcrawl.cards.AbstractCard.MultiCardPreview;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hannina.fantasyCard.AbstractHanninaCard;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.UUID;
import java.util.stream.Collectors;

public class Zuiainile extends AbstractHanninaCard {

    //效果特判在初始牌里
    public Zuiainile() {
        super(Zuiainile.class.getSimpleName(), 1, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);
        this.baseMagicNumber = 1;
        this.misc = 1;
        MultiCardPreview.add(this, new HanninaStrike(), new HanninaDefence());
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new AbstractGameAction() {

            void enhanceCard(AbstractCard card) {
                card.baseMagicNumber += Zuiainile.this.magicNumber;
                if (card instanceof HanninaStrike) {
                    card.baseDamage += Zuiainile.this.magicNumber;
                } else card.baseBlock += Zuiainile.this.magicNumber;
            }

            void foo(String cardId) {
                ArrayList<AbstractCard> list = AbstractDungeon.player.masterDeck.group.stream()
                        .filter(c -> c.cardID.equals(cardId))
                        .sorted(Comparator.comparingInt(c -> c.baseMagicNumber))
                        .collect(Collectors.toCollection(ArrayList::new));
                for (AbstractCard c : list) {
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
                }
            }

            @Override
            public void update() {
                foo("hannina:HanninaDefence");
                foo("hannina:HanninaStrike");
                this.isDone = true;
            }
        });
    }

    @Override
    public boolean canUpgrade() {
        return true;
    }

    @Override
    public void upgradeName() {
        ++this.timesUpgraded;
        this.upgraded = true;
        this.name = cardStrings.NAME + "+" + this.timesUpgraded;
        this.initializeTitle();
    }

    @Override
    public void upgrade() {
        upgradeName();
        upgradeMagicNumber(this.misc);
        this.misc++;
    }

    @Override
    public AbstractCard makeCopy() {
        return new Zuiainile();
    }
}