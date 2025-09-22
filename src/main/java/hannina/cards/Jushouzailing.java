package hannina.cards;

import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hannina.fantasyCard.AbstractHanninaCard;
import hannina.misc.ReunionModifier;
import hannina.utils.UnionManager;

import java.util.ArrayList;

public class Jushouzailing extends AbstractHanninaCard {
    public Jushouzailing() {
        super(Jushouzailing.class.getSimpleName(), 3, CardType.ATTACK, CardRarity.RARE, CardTarget.ENEMY);
        this.damage = this.baseDamage = 18;
        this.magicNumber = this.baseMagicNumber = 2;
    }

    @Override
    public ArrayList<AbstractCard> setUnion() {
        ArrayList<AbstractCard> unions = new ArrayList<>();
        unions.add(UnionManager.getRamdomCard(c ->
                c.color == CardColor.RED && c.rarity == CardRarity.RARE));
        unions.add(UnionManager.getRamdomCard(c ->
                c.color == CardColor.GREEN && c.rarity == CardRarity.RARE));
        unions.add(UnionManager.getRamdomCard(c ->
                c.color == CardColor.BLUE && c.rarity == CardRarity.RARE));
        unions.add(UnionManager.getRamdomCard(c ->
                c.color == CardColor.PURPLE && c.rarity == CardRarity.RARE));
        return unions;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn),
                AbstractGameAction.AttackEffect.BLUNT_HEAVY));

        addToBot(new DrawCardAction(this.magicNumber, new AbstractGameAction() {

            void dcost(AbstractCard c) {
                ArrayList<AbstractCardModifier> list = CardModifierManager.getModifiers(c, "ReunionModifier");
                if (list.size() > 0) {
                    ReunionModifier mod = (ReunionModifier) list.get(0);
                    for (AbstractCard card : mod.union) {
                        if (!card.cardID.equals(c.cardID)) {
                            card.modifyCostForCombat(-1);
                        }
                    }
                }
            }

            @Override
            public void update() {
                dcost(Jushouzailing.this);
                DrawCardAction.drawnCards.forEach(this::dcost);
                this.isDone = true;
            }
        }));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(6);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new Jushouzailing();
    }
}