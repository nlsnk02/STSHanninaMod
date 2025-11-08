package hannina.cards;

import basemod.abstracts.CustomSavable;
import com.evacipated.cardcrawl.mod.stslib.actions.common.AllEnemyApplyPowerAction;
import com.evacipated.cardcrawl.mod.stslib.powers.StunMonsterPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hannina.fantasyCard.AbstractHanninaCard;
import hannina.misc.SaveData;
import hannina.utils.ChangePlayerModel;
import hannina.utils.ModHelper;

public class HanninaStrike extends AbstractHanninaCard implements CustomSavable<Integer> {
    public HanninaStrike() {
        super(HanninaStrike.class.getSimpleName(), 1, AbstractCard.CardType.ATTACK, CardRarity.BASIC, AbstractCard.CardTarget.ENEMY);
        this.damage = this.baseDamage = 6;
        this.magicNumber = this.baseMagicNumber = 0;
        this.tags.add(AbstractCard.CardTags.STRIKE);
        this.tags.add(AbstractCard.CardTags.STARTER_STRIKE);
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
            upgradeDamage(3);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new HanninaStrike();
    }

    @Override
    public Integer onSave() {
        return this.baseMagicNumber;
    }

    @Override
    public void onLoad(Integer integer) {
        if (integer == null) integer = 0;

        this.baseMagicNumber = integer;
        this.baseDamage += integer;
    }
}