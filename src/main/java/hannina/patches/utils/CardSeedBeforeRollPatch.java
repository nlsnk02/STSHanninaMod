package hannina.patches.utils;

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import hannina.utils.UnionManager;

public class CardSeedBeforeRollPatch {
    public static int hue_count_before_roll = 0;

    @SpirePatch(clz = AbstractRoom.class, method = "update")
    public static class AbstractRoomPatch {
        @SpireInsertPatch(rloc = 428-252)
        public static SpireReturn Insert(AbstractRoom _inst) {
            hue_count_before_roll = UnionManager.rng.counter;
            return SpireReturn.Continue();
        }
    }
}
