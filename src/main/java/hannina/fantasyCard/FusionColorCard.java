package hannina.fantasyCard;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ExhaustAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import com.megacrit.cardcrawl.powers.BufferPower;
import hannina.actions.ChangeCharColorAction;
import hannina.cards.XingshengjianZhanlongshi;
import hannina.modcore.Enums;
import hannina.powers.AntiUnionPower;
import hannina.powers.FusionPower;
import hannina.utils.ModHelper;

public class FusionColorCard extends AbstractHanninaCard {

    public AbstractCard tiggerCard;

    public FusionColorCard() {
        super(FusionColorCard.class.getSimpleName(), -2, CardType.SKILL, CardRarity.SPECIAL, CardTarget.SELF);
    }

    public FusionColorCard(AbstractCard card) {
        this();
        tiggerCard = card;
    }

    @Override
    public void onChoseThisOption() {
        //暂时懒得想更好的写法了
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                if (tiggerCard != null) {
                    if (AbstractDungeon.player.discardPile.contains(tiggerCard))
                        AbstractDungeon.player.discardPile.moveToExhaustPile(tiggerCard);
                    if (AbstractDungeon.player.hand.contains(tiggerCard))
                        AbstractDungeon.player.hand.moveToExhaustPile(tiggerCard);
                    if (AbstractDungeon.player.drawPile.contains(tiggerCard))
                        AbstractDungeon.player.drawPile.moveToExhaustPile(tiggerCard);
                }
                isDone = true;
            }
        });

        ModHelper.fusion();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
    }

    @Override
    public void upgrade() {
    }

    @Override
    public AbstractCard makeCopy() {
        return new FusionColorCard();
    }
}