package hannina.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainGoldAction;
import com.megacrit.cardcrawl.actions.defect.IncreaseMaxOrbAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.MonsterRoomBoss;
import hannina.utils.ModHelper;

public class Maozhizhencang extends CustomRelic {
    public static final String ID = ModHelper.makeID(Maozhizhencang.class.getSimpleName());
    private static final String IMG = ModHelper.getImgPath("relics/" + Maozhizhencang.class.getSimpleName() + ".png");


    public Maozhizhencang() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(IMG), RelicTier.BOSS, LandingSound.MAGICAL);
    }

    @Override
    public void obtain() {
        AbstractPlayer player = AbstractDungeon.player;
        player.relics.stream()
                .filter(r -> r instanceof Maozhibaozang)
                .findFirst()
                .map(r -> player.relics.indexOf(r))
                .ifPresent(index
                        -> instantObtain(player, index, true));

        AbstractDungeon.player.gainGold(400);

        this.flash();
    }

//    @Override
//    public boolean canSpawn() {
//        return AbstractDungeon.player.hasRelic(Maozhibaozang.ID) && AbstractDungeon.actNum <= 1;
//    }

    @Override
    public void onVictory() {
        addToBot(new GainGoldAction(15));
//        AbstractRoom r = AbstractDungeon.getCurrRoom();
//        if(r instanceof MonsterRoomBoss){
//            int tmp = 100 + AbstractDungeon.miscRng.random(-5, 5);
//            if (AbstractDungeon.ascensionLevel >= 13) {
//                r.addGoldToRewards(MathUtils.round((float)tmp * 0.75F));
//            } else {
//                r.addGoldToRewards(tmp);
//            }
//        }
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
        return new Maozhizhencang();
    }
}

