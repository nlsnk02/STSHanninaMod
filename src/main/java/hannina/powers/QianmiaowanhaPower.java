package hannina.powers;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.powers.abstracts.TwoAmountPower;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import hannina.actions.ChangeCharColorAction;
import hannina.utils.ModHelper;

import java.util.ArrayList;

public class QianmiaowanhaPower extends TwoAmountPower {
    public static final String POWER_ID = ModHelper.makeID(QianmiaowanhaPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final String NAME = powerStrings.NAME;
    // 能力的描述
    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private static final String PATH128 = ModHelper.makeRelicAd(QianmiaowanhaPower.class.getSimpleName(), true);
    private static final String PATH48 = ModHelper.makeRelicAd(QianmiaowanhaPower.class.getSimpleName(), false);

    //效果特判在union power里
    public QianmiaowanhaPower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.type = PowerType.BUFF;
        this.owner = owner;
        this.amount = amount;
        this.amount2 = 0;
        //        this.loadRegion("tools");
        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(PATH128), 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(PATH48), 0, 0, 32, 32);
        this.updateDescription();
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if(card.color != AbstractDungeon.player.getCardColor()) {
            ArrayList<AbstractCard.CardColor> cardColors = new ArrayList<>();
            if(!ChangeCharColorAction.cardColorsEnteredThisTurn.contains(AbstractCard.CardColor.RED)){
                cardColors.add(AbstractCard.CardColor.RED);
            }
            if(!ChangeCharColorAction.cardColorsEnteredThisTurn.contains(AbstractCard.CardColor.GREEN)){
                cardColors.add(AbstractCard.CardColor.GREEN);
            }
            if(!ChangeCharColorAction.cardColorsEnteredThisTurn.contains(AbstractCard.CardColor.BLUE)){
                cardColors.add(AbstractCard.CardColor.BLUE);
            }
            if(!ChangeCharColorAction.cardColorsEnteredThisTurn.contains(AbstractCard.CardColor.PURPLE)){
                cardColors.add(AbstractCard.CardColor.PURPLE);
            }
            if(cardColors.size() > 0) {
                addToBot(new GainEnergyAction(this.amount));
                int n = AbstractDungeon.cardRandomRng.random(cardColors.size()-1);
                addToBot(new ChangeCharColorAction(cardColors.get(n)));
            }
        }
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        if (isPlayer)
            addToBot(new RemoveSpecificPowerAction(owner, owner, this));
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + (4 - this.amount2) + DESCRIPTIONS[1] + this.amount + DESCRIPTIONS[2];
    }
}