package hannina.cards;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.ClawEffect;
import hannina.fantasyCard.AbstractHanninaCard;
import hannina.utils.UnionManager;

import java.util.ArrayList;

public class Sisezhuayin extends AbstractHanninaCard {
    public Sisezhuayin() {
        super(Sisezhuayin.class.getSimpleName(), 2, CardType.ATTACK, CardRarity.BASIC, CardTarget.ENEMY);
        this.magicNumber = this.baseMagicNumber = 2;
        this.damage = this.baseDamage = 4;
        this.block = this.baseBlock = 4;
    }

    @Override
    public ArrayList<AbstractCard> setUnion() {
        ArrayList<AbstractCard> unions = new ArrayList<>();
        unions.add(UnionManager.getRamdomCard(c -> c.color == CardColor.RED &&
                c.rarity == CardRarity.BASIC &&
                !c.hasTag(AbstractCard.CardTags.STARTER_STRIKE) &&
                !c.hasTag(CardTags.STARTER_DEFEND)));
        unions.add(UnionManager.getRamdomCard(c -> c.color == CardColor.GREEN &&
                c.rarity == CardRarity.BASIC &&
                !c.hasTag(AbstractCard.CardTags.STARTER_STRIKE) &&
                !c.hasTag(CardTags.STARTER_DEFEND)));
        unions.add(UnionManager.getRamdomCard(c -> c.color == CardColor.BLUE &&
                c.rarity == CardRarity.BASIC &&
                !c.hasTag(AbstractCard.CardTags.STARTER_STRIKE) &&
                !c.hasTag(CardTags.STARTER_DEFEND)));
        unions.add(UnionManager.getRamdomCard(c -> c.color == CardColor.PURPLE &&
                c.rarity == CardRarity.BASIC &&
                !c.hasTag(AbstractCard.CardTags.STARTER_STRIKE) &&
                !c.hasTag(CardTags.STARTER_DEFEND)));
        return unions;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        ArrayList<Color> colors = new ArrayList<>();
        colors.add(Color.RED);
        colors.add(Color.GREEN);
        colors.add(Color.BLUE);
        colors.add(Color.PURPLE);


        if (m != null) {
            Color cl = colors.get(MathUtils.random(colors.size() - 1));
            addToBot(new VFXAction(new ClawEffect(m.hb.cX, m.hb.cY, cl, Color.WHITE), 0.1F));
        }
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn),
                AbstractGameAction.AttackEffect.NONE));

        if (m != null) {
            Color cl = colors.get(MathUtils.random(colors.size() - 1));
            addToBot(new VFXAction(new ClawEffect(m.hb.cX, m.hb.cY, cl, Color.WHITE), 0.1F));
        }
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn),
                AbstractGameAction.AttackEffect.NONE));

        addToBot(new GainBlockAction(p, p, block));
        addToBot(new GainBlockAction(p, p, block));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            this.selfRetain = true;
            upgradeDamage(1);
            upgradeBlock(1);
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new Sisezhuayin();
    }
}