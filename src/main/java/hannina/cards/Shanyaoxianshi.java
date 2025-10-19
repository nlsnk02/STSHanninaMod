package hannina.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hannina.fantasyCard.AbstractHanninaCard;
import hannina.modcore.Enums;
import hannina.utils.UnionManager;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public class Shanyaoxianshi extends AbstractHanninaCard {
    public Shanyaoxianshi() {
        super(Shanyaoxianshi.class.getSimpleName(), 1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        this.damage = this.baseDamage = 4;
        this.tags.add(Enums.UnionCard);
    }

    @Override
    public ArrayList<AbstractCard> setUnion() {
        ArrayList<AbstractCard> unions = new ArrayList<>();
        unions.add(UnionManager.getRamdomCard(c ->
                c.color == CardColor.RED && c.cost >= 2 && c.type == CardType.ATTACK));
        unions.add(UnionManager.getRamdomCard(c ->
                c.color == CardColor.GREEN && c.cost >= 2 && c.type == CardType.ATTACK));
        unions.add(UnionManager.getRamdomCard(c ->
                c.color == CardColor.BLUE && c.cost >= 2 && c.type == CardType.ATTACK));
        unions.add(UnionManager.getRamdomCard(c ->
                c.color == CardColor.PURPLE && c.cost >= 2 && c.type == CardType.ATTACK));
        return unions;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn),
                AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                AtomicBoolean found = new AtomicBoolean(false);

                new ArrayList<>(AbstractDungeon.player.hand.group).stream()
                        .filter(c -> c.type == CardType.STATUS || c.type == CardType.CURSE)
                        .forEach(card -> {
                    AbstractDungeon.player.hand.moveToExhaustPile(card);


                    found.set(true);
                });

                if(found.get()){
                    CardCrawlGame.dungeon.checkForPactAchievement();
                    AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
                }
                this.isDone = true;
            }
        });
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBaseCost(0);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new Shanyaoxianshi();
    }
}