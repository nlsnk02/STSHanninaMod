package hannina.misc;

import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.random.Random;
import hannina.cards.Zuiainile;
import hannina.patches.utils.CardSeedBeforeRollPatch;
import hannina.utils.ModHelper;
import hannina.utils.UnionManager;

public class SaveData {
    public static SaveData saveData = null;

    public int hueRngCounter;

    public String skin;

    public SaveData() {
        hueRngCounter = 0;
        skin = "Hannina";
    }

    public static SaveData save() {
        if (saveData == null)
            saveData = new SaveData();

        //如果是调用了卡牌奖励，那么回退saveData。否则更新UnionManager.rng
        if (CardSeedBeforeRollPatch.hue_count_before_roll != -1) {
            saveData.hueRngCounter = CardSeedBeforeRollPatch.hue_count_before_roll;
            CardSeedBeforeRollPatch.hue_count_before_roll = -1;
        } else {
            saveData.hueRngCounter = AbstractDungeon.floorNum;
            UnionManager.rng = new Random(Settings.seed, saveData.hueRngCounter);
        }

        return saveData;
    }

    public static void load(SaveData saveData) {
        if (saveData == null) SaveData.saveData = new SaveData();
        else SaveData.saveData = saveData;
    }
}
