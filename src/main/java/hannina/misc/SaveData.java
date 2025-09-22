package hannina.misc;

import hannina.cards.Zuiainile;
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

        return saveData;
    }

    public static void load(SaveData saveData) {
        if (saveData == null) SaveData.saveData = new SaveData();
        else SaveData.saveData = saveData;
    }
}
