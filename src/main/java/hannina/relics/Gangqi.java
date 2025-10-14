package hannina.relics;

import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainGoldAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.curses.Pride;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import hannina.utils.ModHelper;

public class Gangqi extends CustomRelic {
    public static final String ID = ModHelper.makeID(Gangqi.class.getSimpleName());
    private static final String IMG = ModHelper.getImgPath("relics/" + Gangqi.class.getSimpleName() + ".png");


    public Gangqi() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(IMG), RelicTier.SPECIAL, LandingSound.MAGICAL);
        this.counter=0;
    }

//    @Override
//    public void atBattleStart() {
//        addToBot(new IncreaseMaxOrbAction(2));
//    }


@Override
public void onVictory() {
    this.counter=0;
}
    @Override
    public void atTurnStartPostDraw() {
if (this.counter<=4){
    this.counter++;
    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player,AbstractDungeon.player,new VigorPower(AbstractDungeon.player,5)));
AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(new Pride()));
}
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new Gangqi();
    }
}

