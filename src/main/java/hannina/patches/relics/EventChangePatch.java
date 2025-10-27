package hannina.patches.relics;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractEvent;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.rooms.EventRoom;
import hannina.events.FlyingCat;
import hannina.relics.Maozhibaozang;
import hannina.utils.ConfigHelper;
import hannina.utils.MonsterDieThisTurnManager;
import hannina.utils.SkinSelectScreen;

@SpirePatch(clz = EventRoom .class, method = "onPlayerEntry", paramtypes = {})
public class EventChangePatch {
    @SpireInsertPatch(
            rloc=3,
            localvars = {"event"}
    )
    public static void patch(EventRoom __instance, @ByRef AbstractEvent[] event) {
     if (AbstractDungeon.player.hasRelic(Maozhibaozang.ID)){
         Maozhibaozang maozhibaozang= (Maozhibaozang) AbstractDungeon.player.getRelic(Maozhibaozang.ID);
         if (!maozhibaozang.hasEnteredEvent&&SkinSelectScreen.getSkin().index==1){
event[0]=new FlyingCat();
maozhibaozang.hasEnteredEvent=true;

         }
     }
    }
}
