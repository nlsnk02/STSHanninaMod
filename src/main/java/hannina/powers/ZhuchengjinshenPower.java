package hannina.powers;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import hannina.misc.OnGainGoldSubscriber;
import hannina.misc.OnLoseGoldSubscriber;
import hannina.utils.ModHelper;

public class ZhuchengjinshenPower extends AbstractPower implements OnLoseGoldSubscriber, OnGainGoldSubscriber {
    public static final String POWER_ID = ModHelper.makeID(ZhuchengjinshenPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final String NAME = powerStrings.NAME;
    // 能力的描述
    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private static final String PATH128 = ModHelper.makeRelicAd(ZhuchengjinshenPower.class.getSimpleName(), true);
    private static final String PATH48 = ModHelper.makeRelicAd(ZhuchengjinshenPower.class.getSimpleName(), false);

    //效果特判在union power里
    public ZhuchengjinshenPower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.type = PowerType.BUFF;
        this.owner = owner;
        this.amount = amount;
        //        this.loadRegion("tools");
        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(PATH128), 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(PATH48), 0, 0, 32, 32);
        this.updateDescription();
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount +DESCRIPTIONS[1];
    }

    @Override
    public void onLoseGold(int gold) {
        addToBot(new GainBlockAction(this.owner, this.amount));
    }

    @Override
    public void onGainGold() {
        addToBot(new GainBlockAction(this.owner, this.amount));
    }
}