package hannina.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.watcher.ChooseOneAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hannina.fantasyCard.*;

import java.util.ArrayList;

public class XingshengjianZhanlongshi extends AbstractHanninaCard {
    public XingshengjianZhanlongshi() {
        super(XingshengjianZhanlongshi.class.getSimpleName(), 0, CardType.ATTACK, CardRarity.SPECIAL, CardTarget.ENEMY, CardColor.COLORLESS);
        this.damage = this.baseDamage = 14;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int tmp = 1;
        if (m.type == AbstractMonster.EnemyType.BOSS) tmp += 1;

        addToBot(new DamageAction(m, new DamageInfo(p, this.damage * tmp, this.damageTypeForTurn),
                AbstractGameAction.AttackEffect.NONE));

        ArrayList<AbstractCard> stanceChoices = new ArrayList<>();
        stanceChoices.add(new FusionColorCard(this));
        stanceChoices.add(new NullColorCard());
        addToBot(new ChooseOneAction(stanceChoices));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            this.isInnate = true;
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new XingshengjianZhanlongshi();
    }
}