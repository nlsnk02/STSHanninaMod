package hannina.cards;

import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import hannina.actions.PlayACardAction;
import hannina.fantasyCard.AbstractHanninaCard;
import hannina.misc.ReunionModifier;
import hannina.modcore.Enums;
import hannina.utils.UnionManager;

import java.util.ArrayList;

public class MZJZhuying extends AbstractHanninaCard {
    public MZJZhuying() {
        super(MZJZhuying.class.getSimpleName(), 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.ENEMY);
        this.magicNumber = this.baseMagicNumber = 1;
        this.tags.add(Enums.UnionCard);
    }

    @Override
    public ArrayList<AbstractCard> setUnion() {
        ArrayList<AbstractCard> unions = new ArrayList<>();
        unions.add(UnionManager.getRamdomCard(c ->
                c.color == CardColor.RED && c.cost == 1 && c.type == CardType.SKILL && c.rarity == CardRarity.UNCOMMON));
        unions.add(UnionManager.getRamdomCard(c ->
                c.color == CardColor.GREEN && c.cost == 1 && c.type == CardType.SKILL && c.rarity == CardRarity.UNCOMMON));
        unions.add(UnionManager.getRamdomCard(c ->
                c.color == CardColor.BLUE && c.cost == 1 && c.type == CardType.SKILL && c.rarity == CardRarity.UNCOMMON));
        unions.add(UnionManager.getRamdomCard(c ->
                c.color == CardColor.PURPLE && c.cost == 1 && c.type == CardType.SKILL && c.rarity == CardRarity.UNCOMMON));
        return unions;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(m, p,
                new WeakPower(m, this.magicNumber, false)));
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                ReunionModifier r = (ReunionModifier) CardModifierManager.getModifiers(MZJZhuying.this, "ReunionModifier").get(0);
                ArrayList<AbstractCard> list = new ArrayList<>(r.union);
                list.remove(MZJZhuying.this);

                if(!list.isEmpty()){
                    int i = AbstractDungeon.cardRandomRng.random(list.size()-1);
                    addToTop(new PlayACardAction(list.get(i).makeSameInstanceOf(), null, m, true));
                }

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
        return new MZJZhuying();
    }
}