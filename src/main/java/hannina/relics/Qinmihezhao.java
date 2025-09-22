package hannina.relics;

import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import hannina.actions.LoseGoldAction;
import hannina.misc.OnChangeColorSubscriber;
import hannina.modcore.Enums;
import hannina.utils.ModHelper;

public class Qinmihezhao extends CustomRelic implements OnChangeColorSubscriber {
    public static final String ID = ModHelper.makeID(Qinmihezhao.class.getSimpleName());
    private static final String IMG = ModHelper.getImgPath("relics/" + Qinmihezhao.class.getSimpleName() + ".png");

    private boolean avail = false;

    public Qinmihezhao() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(IMG), RelicTier.BOSS, LandingSound.MAGICAL);
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }


    @Override
    public void atTurnStart() {
        avail = true;
    }

    @Override
    public AbstractRelic makeCopy() {
        return new Qinmihezhao();
    }

    @Override
    public void onChangeColor(AbstractCard.CardColor color) {
        if (avail && color != Enums.HanninaColor) {
            addToBot(new GainEnergyAction(1));
            flash();
        }
        avail = false;
    }
}

