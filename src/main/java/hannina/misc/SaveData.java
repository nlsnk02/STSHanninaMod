package hannina.misc;

import hannina.cards.Zuiainile;
import hannina.patches.utils.CardSeedBeforeRollPatch;
import hannina.utils.ModHelper;

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

        if(CardSeedBeforeRollPatch.hue_count_before_roll != -1) {
            saveData.hueRngCounter = CardSeedBeforeRollPatch.hue_count_before_roll;
            CardSeedBeforeRollPatch.hue_count_before_roll = -1;
        }

        return saveData;
    }

    public static void load(SaveData saveData) {
        if (saveData == null) SaveData.saveData = new SaveData();
        else SaveData.saveData = saveData;
    }
}
