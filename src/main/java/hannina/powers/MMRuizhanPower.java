package hannina.powers;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import hannina.cards.MMRuizhan;
import hannina.effects.PlayGiFEffect;
import hannina.fantasyCard.Chaojuehajimi;
import hannina.relics.Gangqi;
import hannina.relics.Miaodan;
import hannina.utils.ModHelper;

public class MMRuizhanPower extends AbstractPower {
    public static final String POWER_ID = ModHelper.makeID(MMRuizhanPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final String NAME = powerStrings.NAME;
    // 能力的描述
    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private static final String PATH128 = ModHelper.makeRelicAd(MMRuizhanPower.class.getSimpleName(), true);
    private static final String PATH48 = ModHelper.makeRelicAd(MMRuizhanPower.class.getSimpleName(), false);

    //效果特判在union power里
    public MMRuizhanPower(AbstractCreature owner, int amount) {
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

    @Override
    public void atStartOfTurn() {
        addToBot(new ApplyPowerAction(this.owner, this.owner, new VigorPower(this.owner, this.amount), this.amount));
        if(MMRuizhan.count >= 15){
            MMRuizhan.count = 0;
            addToBot(new MakeTempCardInHandAction(new Chaojuehajimi()));
            if (AbstractDungeon.player.hasRelic(Gangqi.ID)){
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player,AbstractDungeon.player,new VigorPower(AbstractDungeon.player,10)));

            }
            if (AbstractDungeon.player.hasRelic(Miaodan.ID)){
                Chaojuehajimi.startGIF=4;
                AbstractDungeon.effectList.add(new PlayGiFEffect(4));

            }

        }
        addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, this));

    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount +DESCRIPTIONS[1];
    }
}