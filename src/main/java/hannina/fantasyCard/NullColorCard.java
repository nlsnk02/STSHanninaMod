package hannina.fantasyCard;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import com.megacrit.cardcrawl.powers.BufferPower;
import hannina.actions.ChangeCharColorAction;
import hannina.modcore.Enums;
import hannina.powers.AntiUnionPower;

public class NullColorCard extends AbstractHanninaCard {
    public NullColorCard() {
        super(NullColorCard.class.getSimpleName(), -2, CardType.SKILL, CardRarity.SPECIAL, CardTarget.SELF);
    }

    @Override
    public void onChoseThisOption() {
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
    }

    @Override
    public void upgrade() {
    }

    @Override
    public AbstractCard makeCopy() {
        return new NullColorCard();
    }
}