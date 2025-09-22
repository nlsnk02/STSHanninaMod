package hannina.relics;

import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainGoldAction;
import com.megacrit.cardcrawl.actions.defect.IncreaseMaxOrbAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import hannina.utils.ModHelper;

public class Maozhibaozang extends CustomRelic {
    public static final String ID = ModHelper.makeID(Maozhibaozang.class.getSimpleName());
    private static final String IMG = ModHelper.getImgPath("relics/" + Maozhibaozang.class.getSimpleName() + ".png");


    public Maozhibaozang() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(IMG), RelicTier.STARTER, LandingSound.MAGICAL);
    }

//    @Override
//    public void atBattleStart() {
//        addToBot(new IncreaseMaxOrbAction(2));
//    }

    @Override
    public void onVictory() {
        addToBot(new GainGoldAction(9));
    }

    @Override
    public int changeNumberOfCardsInReward(int numberOfCards) {
        return numberOfCards + 1;
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new Maozhibaozang();
    }
}

