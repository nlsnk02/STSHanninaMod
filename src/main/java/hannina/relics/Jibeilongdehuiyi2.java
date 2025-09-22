package hannina.relics;

import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.FocusPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import hannina.misc.OnChangeColorSubscriber;
import hannina.modcore.Enums;
import hannina.utils.ModHelper;

public class Jibeilongdehuiyi2 extends CustomRelic implements OnChangeColorSubscriber {
    public static final String ID = ModHelper.makeID(Jibeilongdehuiyi2.class.getSimpleName());
    private static final String IMG = ModHelper.getImgPath("relics/" + Jibeilongdehuiyi2.class.getSimpleName() + ".png");

    public Jibeilongdehuiyi2() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(IMG), RelicTier.BOSS, LandingSound.MAGICAL);
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }


    @Override
    public void atTurnStart() {
        this.counter = 0;
    }

    @Override
    public void onPlayerEndTurn() {
        if(counter>0) {
            addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player,
                    new StrengthPower(AbstractDungeon.player, -counter)));
            addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player,
                    new DexterityPower(AbstractDungeon.player, -counter)));
            addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player,
                    new FocusPower(AbstractDungeon.player, -counter)));
        }
    }

    @Override
    public AbstractRelic makeCopy() {
        return new Jibeilongdehuiyi2();
    }

    @Override
    public void onChangeColor(AbstractCard.CardColor color) {
        if (color != Enums.HanninaColor) {
            counter++;
            addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player,
                    new StrengthPower(AbstractDungeon.player, 1)));
            addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player,
                    new DexterityPower(AbstractDungeon.player, 1)));
            addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player,
                    new FocusPower(AbstractDungeon.player, 1)));
        }
    }
}

