package hannina.cards;

import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import hannina.actions.PlayACardAction;
import hannina.fantasyCard.AbstractHanninaCard;
import hannina.misc.ReunionModifier;
import hannina.modcore.Enums;
import hannina.utils.UnionManager;

import java.util.ArrayList;

public class Jushouzailing extends AbstractHanninaCard {
    public Jushouzailing() {
        super(Jushouzailing.class.getSimpleName(), 4, CardType.ATTACK, CardRarity.RARE, CardTarget.ALL_ENEMY);
        this.damage = this.baseDamage = 24;
        this.isMultiDamage = true;
        this.magicNumber = this.baseMagicNumber = 2;
        this.tags.add(Enums.UnionCard);
        this.exhaust = true;
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
        addToBot(new DamageAllEnemiesAction(p, this.multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.BLUNT_HEAVY));

        ArrayList<AbstractCardModifier> list = CardModifierManager.getModifiers(this, "ReunionModifier");
        if (list.size() > 0) {
            ReunionModifier mod = (ReunionModifier) list.get(0);
            for (AbstractCard card : mod.union) {
                card.energyOnUse = EnergyPanel.totalCount;
                addToBot(new PlayACardAction(card, null, null, true));
            }
        }
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(4);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new Jushouzailing();
    }
}