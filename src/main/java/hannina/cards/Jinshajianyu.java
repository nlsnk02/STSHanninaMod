package hannina.cards;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hannina.effects.ClawWithGoldEffect;
import hannina.fantasyCard.AbstractHanninaCard;
import hannina.misc.OnLoseGoldSubscriber;
import hannina.utils.ModHelper;

public class Jinshajianyu extends AbstractHanninaCard implements OnLoseGoldSubscriber {
    public Jinshajianyu() {
        super(Jinshajianyu.class.getSimpleName(), 4, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        this.magicNumber = this.baseMagicNumber = 4;
        this.damage = this.baseDamage = 5;
    }

    @Override
    public void onLoseGold(int gold) {
        modifyCostForCombat(-gold);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int i = 0; i < magicNumber; i++) {
            if (m != null)
                addToBot(new VFXAction(new ClawWithGoldEffect(m.hb.cX, m.hb.cY, Color.PURPLE, Color.WHITE, this.damage / 2), 0.1F));
            addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn),
                    AbstractGameAction.AttackEffect.NONE));
        }

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
        return new Jinshajianyu();
    }
}