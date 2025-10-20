package hannina.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hannina.fantasyCard.AbstractHanninaCard;
import hannina.modcore.Enums;
import hannina.utils.UnionManager;

import java.util.ArrayList;

public class MZLFushu extends AbstractHanninaCard {
    public MZLFushu() {
        super(MZLFushu.class.getSimpleName(), 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        this.damage = this.baseDamage = 8;
        this.tags.add(Enums.UnionCard);
    }

    @Override
    public ArrayList<AbstractCard> setUnion() {
        ArrayList<AbstractCard> unions = new ArrayList<>();
        unions.add(UnionManager.getRamdomCard(c ->
                c.color == CardColor.RED && c.cost == 1 && c.type == CardType.ATTACK && c.rarity == CardRarity.COMMON));
        unions.add(UnionManager.getRamdomCard(c ->
                c.color == CardColor.GREEN && c.cost == 1 && c.type == CardType.ATTACK && c.rarity == CardRarity.COMMON));
        unions.add(UnionManager.getRamdomCard(c ->
                c.color == CardColor.BLUE && c.cost == 1 && c.type == CardType.ATTACK && c.rarity == CardRarity.COMMON));
        unions.add(UnionManager.getRamdomCard(c ->
                c.color == CardColor.PURPLE && c.cost == 1 && c.type == CardType.ATTACK && c.rarity == CardRarity.COMMON));
        return unions;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn),
                AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(2);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new MZLFushu();
    }
}