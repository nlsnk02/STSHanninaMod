package hannina.powers;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.actions.utility.ScryAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.orbs.Lightning;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.FocusPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import hannina.actions.ChangeCharColorAction;
import hannina.cards.Jiaorongsansejin;
import hannina.modcore.Enums;
import hannina.utils.ModHelper;

public class UnionPower extends AbstractPower {
    public static final String POWER_ID = ModHelper.makeID(UnionPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final String NAME = powerStrings.NAME;
    // 能力的描述
    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private static final int AMOUNT = 2;
    private int multiplier = 1;


    private TextureAtlas.AtlasRegion region128R = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(ModHelper.makeRelicAd("UnionPowerR", true)), 0, 0, 84, 84);
    private TextureAtlas.AtlasRegion region48R = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(ModHelper.makeRelicAd("UnionPowerR", false)), 0, 0, 32, 32);

    private TextureAtlas.AtlasRegion region128G = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(ModHelper.makeRelicAd("UnionPowerG", true)), 0, 0, 84, 84);
    private TextureAtlas.AtlasRegion region48G = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(ModHelper.makeRelicAd("UnionPowerG", false)), 0, 0, 32, 32);

    private TextureAtlas.AtlasRegion region128B = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(ModHelper.makeRelicAd("UnionPowerB", true)), 0, 0, 84, 84);
    private TextureAtlas.AtlasRegion region48B = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(ModHelper.makeRelicAd("UnionPowerB", false)), 0, 0, 32, 32);

    private TextureAtlas.AtlasRegion region128P = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(ModHelper.makeRelicAd("UnionPowerP", true)), 0, 0, 84, 84);
    private TextureAtlas.AtlasRegion region48P = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(ModHelper.makeRelicAd("UnionPowerP", false)), 0, 0, 32, 32);

    public AbstractCard.CardColor color;

    public boolean enhancedByForm = false;

    public UnionPower(AbstractCreature owner, AbstractCard.CardColor color) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.type = PowerType.BUFF;
        this.owner = owner;
        this.color = color;
//        this.loadRegion("tools");
//        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(PATH128), 0, 0, 84, 84);
//        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(PATH48), 0, 0, 32, 32);
        this.updateDescription();
    }

    @Override
    public void onInitialApplication() {
        if (owner.hasPower(MaodearxingtaiPower2.POWER_ID))
            this.enhancedByForm = true;

        enterColor(color);

        //置顶自己
        if (this.owner.powers.contains(this)) {
            AbstractDungeon.player.powers.remove(this);
            AbstractDungeon.player.powers.add(0, this);
        }
    }

    @Override
    public void onRemove() {
        leaveColor(this.color);
    }

//    @Override
//    public void onVictory() {
//        onRemove();
//    }

    public void leaveColor(AbstractCard.CardColor color) {
        if (color == AbstractCard.CardColor.RED || (owner.hasPower(MaodearxingtaiPower2.POWER_ID) && enhancedByForm)) {
            addToTop(new ReducePowerAction(this.owner, this.owner,
                    StrengthPower.POWER_ID, AMOUNT * multiplier));
        }
        if (color == AbstractCard.CardColor.GREEN || (owner.hasPower(MaodearxingtaiPower2.POWER_ID) && enhancedByForm)) {
            addToTop(new ReducePowerAction(this.owner, this.owner,
                    DexterityPower.POWER_ID, AMOUNT * multiplier));
        }
        if (color == AbstractCard.CardColor.BLUE || (owner.hasPower(MaodearxingtaiPower2.POWER_ID) && enhancedByForm)) {
            addToTop(new ReducePowerAction(this.owner, this.owner,
                    FocusPower.POWER_ID, multiplier));
        }
    }

    public void enterColor(AbstractCard.CardColor color) {
        this.color = color;
        AbstractCard lastCard = AbstractDungeon.actionManager.cardsPlayedThisTurn.stream().filter(c -> c.cardID.equals("hannina:" + Jiaorongsansejin.class.getSimpleName())).findFirst().orElse(null);
        multiplier = lastCard != null ? (lastCard.upgraded ? 4 : 3) : 1;
        if (color == AbstractCard.CardColor.RED || owner.hasPower(MaodearxingtaiPower2.POWER_ID)) {
            addToBot(new ApplyPowerAction(this.owner, this.owner,
                    new StrengthPower(this.owner, AMOUNT * multiplier)));
            this.region128 = region128R;
            this.region48 = region48R;

        }
        if (color == AbstractCard.CardColor.GREEN || owner.hasPower(MaodearxingtaiPower2.POWER_ID)) {
            addToBot(new ApplyPowerAction(this.owner, this.owner,
                    new DexterityPower(this.owner, AMOUNT * multiplier)));
            this.region128 = region128G;
            this.region48 = region48G;
        }
        if (color == AbstractCard.CardColor.BLUE || owner.hasPower(MaodearxingtaiPower2.POWER_ID)) {
            addToBot(new ChannelAction(new Lightning()));
            addToBot(new ApplyPowerAction(this.owner, this.owner,
                    new FocusPower(this.owner, multiplier)));
            this.region128 = region128B;
            this.region48 = region48B;
        }
        if (color == AbstractCard.CardColor.PURPLE || owner.hasPower(MaodearxingtaiPower2.POWER_ID)) {
            addToBot(new ScryAction(AMOUNT * multiplier));
            this.region128 = region128P;
            this.region48 = region48P;
        }
        updateDescription(color);
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        if (isPlayer && !AbstractDungeon.player.hasPower(XingtaibianhuaPower.POWER_ID)) {
            addToTop(new ChangeCharColorAction(Enums.HanninaColor));
        }
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + DESCRIPTIONS[4];
    }

    public void updateDescription(AbstractCard.CardColor color) {
        if (color == AbstractCard.CardColor.RED) this.description = DESCRIPTIONS[0];
        else if (color == AbstractCard.CardColor.GREEN) this.description = DESCRIPTIONS[1];
        else if (color == AbstractCard.CardColor.BLUE) this.description = DESCRIPTIONS[2];
        else if (color == AbstractCard.CardColor.PURPLE) this.description = DESCRIPTIONS[3];

        this.description += DESCRIPTIONS[4];
    }
}