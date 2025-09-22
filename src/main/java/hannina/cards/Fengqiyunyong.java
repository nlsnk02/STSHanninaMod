package hannina.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hannina.fantasyCard.AbstractHanninaCard;
import hannina.utils.ModHelper;
import hannina.utils.UnionManager;

import java.util.ArrayList;

public class Fengqiyunyong extends AbstractHanninaCard {
    public Fengqiyunyong() {
        super(Fengqiyunyong.class.getSimpleName(), 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        this.damage = this.baseDamage = 8;
        this.selfRetain = true;
    }

//    @Override
//    public void applyPowers() {
//        super.applyPowers();
//        int tmp = this.block;
//        int baseTmp = this.baseBlock;
//        this.baseBlock = this.baseMagicNumber;
//        super.applyPowers();
//        this.magicNumber = this.block;
//        this.block = tmp;
//        this.baseBlock = baseTmp;
//        this.isMagicNumberModified = this.isBlockModified;
//        this.isBlockModified = this.block != baseTmp;
//    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
//        applyPowers();

        addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn),
                AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));

        if (ModHelper.getPlayerColor() == CardColor.PURPLE) {
            this.addToBot(new ChangeStanceAction("Wrath"));
        }else {
            this.addToBot(new ChangeStanceAction("Calm"));
        }
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(3);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new Fengqiyunyong();
    }
}