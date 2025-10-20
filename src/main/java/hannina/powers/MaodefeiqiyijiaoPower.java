package hannina.powers;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.powers.abstracts.TwoAmountPower;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import hannina.cards.Maodechongfengjinji;
import hannina.cards.Maodepingwenluodi;
import hannina.misc.OnChangeColorSubscriber;
import hannina.utils.ModHelper;

public class MaodefeiqiyijiaoPower extends TwoAmountPower implements OnChangeColorSubscriber {
    public static final String POWER_ID = ModHelper.makeID(MaodefeiqiyijiaoPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final String NAME = powerStrings.NAME;
    // 能力的描述
    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private static final String PATH128 = ModHelper.makeRelicAd(MaodefeiqiyijiaoPower.class.getSimpleName(), true);
    private static final String PATH48 = ModHelper.makeRelicAd(MaodefeiqiyijiaoPower.class.getSimpleName(), false);

    //效果特判在union power里
    public MaodefeiqiyijiaoPower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.type = PowerType.BUFF;
        this.owner = owner;

        this.amount = this.amount2 = amount;

        //        this.loadRegion("tools");
        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(PATH128), 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(PATH48), 0, 0, 32, 32);
        this.updateDescription();
    }

    @Override
    public void atStartOfTurn() {
        this.amount = amount2;
    }

    @Override
    public void stackPower(int stackAmount) {
        super.stackPower(stackAmount);
        this.amount2 += stackAmount;
    }


    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }

    @Override
    public void onChangeColor(AbstractCard.CardColor color) {
        if (color == AbstractCard.CardColor.GREEN && this.amount >= 1) {
            addToBot(new DrawCardAction(3));
            amount -= 1;
            updateDescription();
        }
    }
}