package hannina.relics;

import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.FocusPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import hannina.misc.OnChangeColorSubscriber;
import hannina.utils.ModHelper;

public class Jibeilongdehuiyi extends CustomRelic implements OnChangeColorSubscriber {
    public static final String ID = ModHelper.makeID(Jibeilongdehuiyi.class.getSimpleName());
    private static final String IMG = ModHelper.getImgPath("relics/" + Jibeilongdehuiyi.class.getSimpleName() + ".png");

    private int strength = 0;
    private int dexterity = 0;
    private int focus = 0;

    public Jibeilongdehuiyi() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(IMG), RelicTier.BOSS, LandingSound.MAGICAL);
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }


    @Override
    public void atTurnStart() {
        strength = 0;
        dexterity = 0;
        focus = 0;
    }

    @Override
    public void onPlayerEndTurn() {
        addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player,
                new StrengthPower(AbstractDungeon.player, -strength)));
        addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player,
                new DexterityPower(AbstractDungeon.player, -dexterity)));
        addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player,
                new FocusPower(AbstractDungeon.player, -focus)));
    }

    @Override
    public AbstractRelic makeCopy() {
        return new Jibeilongdehuiyi();
    }

    @Override
    public void onChangeColor(AbstractCard.CardColor color) {
        if (color == AbstractCard.CardColor.RED) {
            strength++;
            addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player,
                    new StrengthPower(AbstractDungeon.player, 1)));
        }
        if (color == AbstractCard.CardColor.GREEN) {
            dexterity++;
            addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player,
                    new DexterityPower(AbstractDungeon.player, 1)));
        }
        if (color == AbstractCard.CardColor.BLUE) {
            focus++;
            addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player,
                    new FocusPower(AbstractDungeon.player, 1)));
        }
    }
}

