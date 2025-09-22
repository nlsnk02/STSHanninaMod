package hannina.relics;

import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainGoldAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import hannina.misc.OnLoseGoldSubscriber;
import hannina.utils.ModHelper;

public class PToy extends CustomRelic implements OnLoseGoldSubscriber {
    public static final String ID = ModHelper.makeID(PToy.class.getSimpleName());
    private static final String IMG = ModHelper.getImgPath("relics/" + PToy.class.getSimpleName() + ".png");

    public PToy() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(IMG), RelicTier.UNCOMMON, LandingSound.MAGICAL);
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new PToy();
    }

    @Override
    public void onLoseGold(int gold) {
        addToBot(new GainGoldAction(1));
        addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player,
                new VigorPower(AbstractDungeon.player, 3)));
    }
}

