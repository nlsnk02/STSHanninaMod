package hannina.powers;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.powers.abstracts.TwoAmountPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.blue.SweepingBeam;
import com.megacrit.cardcrawl.cards.green.DeadlyPoison;
import com.megacrit.cardcrawl.cards.purple.PressurePoints;
import com.megacrit.cardcrawl.cards.red.IronWave;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.FocusPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import hannina.actions.PlayACardAction;
import hannina.misc.OnChangeColorSubscriber;
import hannina.modcore.Enums;
import hannina.utils.ModHelper;

public class MaodearxingtaiPower2 extends TwoAmountPower implements OnChangeColorSubscriber {
    public static final String POWER_ID = ModHelper.makeID(MaodearxingtaiPower2.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final String NAME = powerStrings.NAME;
    // 能力的描述
    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private static final String PATH128 = ModHelper.makeRelicAd(MaodearxingtaiPower2.class.getSimpleName(), true);
    private static final String PATH48 = ModHelper.makeRelicAd(MaodearxingtaiPower2.class.getSimpleName(), false);

    public MaodearxingtaiPower2(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.type = PowerType.BUFF;
        this.owner = owner;
        this.amount = amount;
        this.amount2 = amount;
        //        this.loadRegion("tools");
        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(PATH128), 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(PATH48), 0, 0, 32, 32);
        this.updateDescription();
    }

//效果直接特判在union power里了
    @Override
    public void onInitialApplication() {
        if(owner.hasPower(UnionPower.POWER_ID)) {
            ((UnionPower)owner.getPower(UnionPower.POWER_ID)).enhancedByForm = true;
        }
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
        if (this.amount <= 0) return;

        boolean flag = false;
        if (color == AbstractCard.CardColor.RED) {
            flag = true;
            addToBot(new PlayACardAction(new IronWave(), null, null, true));
        }
        if (color == AbstractCard.CardColor.GREEN) {
            flag = true;
            addToBot(new PlayACardAction(new DeadlyPoison(), null, null, true));
        }
        if (color == AbstractCard.CardColor.BLUE) {
            flag = true;
            addToBot(new PlayACardAction(new SweepingBeam(), null, null, true));
        }
        if (color == AbstractCard.CardColor.PURPLE) {
            flag = true;
            addToBot(new PlayACardAction(new PressurePoints(), null, null, true));
        }
        if (flag) {
            this.amount--;
            this.flash();
        }
    }
}