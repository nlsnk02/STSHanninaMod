package hannina.cards;

import basemod.abstracts.CustomSavable;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.random.Random;
import hannina.fantasyCard.AbstractHanninaCard;
import hannina.misc.SaveData;
import hannina.utils.ModHelper;

public class HanninaDefence extends AbstractHanninaCard implements CustomSavable<Integer> {

    public HanninaDefence() {
        super(HanninaDefence.class.getSimpleName(), 1, CardType.SKILL, CardRarity.BASIC, CardTarget.SELF);
        this.block = this.baseBlock = 5;
        this.magicNumber = this.baseMagicNumber = 0;
        this.tags.add(CardTags.STARTER_DEFEND);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p, p, this.block));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBlock(3);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new HanninaDefence();
    }

    @Override
    public Integer onSave() {
        return this.baseMagicNumber;
    }

    @Override
    public void onLoad(Integer integer) {
        if(integer == null) integer = 0;
        
        this.baseMagicNumber = integer;
        this.baseBlock += integer;
    }
}