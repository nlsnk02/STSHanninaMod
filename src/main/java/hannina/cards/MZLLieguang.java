package hannina.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import hannina.fantasyCard.AbstractHanninaCard;
import hannina.modcore.Enums;
import hannina.utils.UnionManager;

import java.util.ArrayList;

public class MZLLieguang extends AbstractHanninaCard {
    public MZLLieguang() {
        super(MZLLieguang.class.getSimpleName(), 1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        this.damage = this.baseDamage = 6;
        this.magicNumber = this.baseMagicNumber = 1;
        this.tags.add(Enums.UnionCard);
    }

    @Override
    public void applyPowers() {
        int tmp = 0;
        for (AbstractCard c : AbstractDungeon.actionManager.cardsPlayedThisCombat) {
            if (c.color != Enums.HanninaColor)
                tmp += this.baseMagicNumber;
        }
        this.baseDamage += tmp;
        super.applyPowers();
        this.baseDamage -= tmp;
        this.isDamageModified = this.isDamageModified || tmp > 0;
    }

    @Override
    public ArrayList<AbstractCard> setUnion() {
        ArrayList<AbstractCard> unions = new ArrayList<>();
        unions.add(UnionManager.getRamdomCard(c ->
                c.color == CardColor.RED && c.cost == 1 && c.type == CardType.ATTACK && c.rarity == CardRarity.UNCOMMON));
        unions.add(UnionManager.getRamdomCard(c ->
                c.color == CardColor.GREEN && c.cost == 1 && c.type == CardType.ATTACK && c.rarity == CardRarity.UNCOMMON));
        unions.add(UnionManager.getRamdomCard(c ->
                c.color == CardColor.BLUE && c.cost == 1 && c.type == CardType.ATTACK && c.rarity == CardRarity.UNCOMMON));
        unions.add(UnionManager.getRamdomCard(c ->
                c.color == CardColor.PURPLE && c.cost == 1 && c.type == CardType.ATTACK && c.rarity == CardRarity.UNCOMMON));
        return unions;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn),
                AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
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
        return new MZLLieguang();
    }
}