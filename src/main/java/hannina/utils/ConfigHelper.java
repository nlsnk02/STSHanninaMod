package hannina.utils;

import basemod.ModLabeledToggleButton;
import basemod.ModPanel;
import com.badlogic.gdx.Gdx;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Properties;

public class ConfigHelper {
    public static SpireConfig config = null;

    public static String skinId = "";
    public static boolean nsfw = false;
    public static boolean activeTutorials = true;
	
	public static boolean hasDefeatedTheHeart = false;
	public static boolean hasDefeatedTheHeartSFW2 = false;
	public static String checkedLatestVersion = "";

    public static void tryCreateConfig() {
		Properties defaults = new Properties();
		defaults.setProperty("skinId", "");
		defaults.setProperty("nsfw", "false");
		defaults.setProperty("activeTutorials", "true");
		defaults.setProperty("hasDefeatedTheHeart", "false");
		defaults.setProperty("hasDefeatedTheHeartSFW2", "false");
		defaults.setProperty("checkedLatestVersion", "");
		
        try {
            config = new SpireConfig("hannina", "hanninaConfig", defaults);
        } catch (IOException e) {
            ModHelper.logger.warn("+++++++++++++++++++++" + e + "++++++++++++++++++++++");
            config = null;
        }

        if (config != null) {
			skinId = config.getString("skinId");
			nsfw = config.getBool("nsfw");
			activeTutorials = config.getBool("activeTutorials");
			hasDefeatedTheHeart = config.getBool("hasDefeatedTheHeart");
			hasDefeatedTheHeartSFW2 = config.getBool("hasDefeatedTheHeartSFW2");
			checkedLatestVersion = config.getString("checkedLatestVersion");
        }
    }

    public static void trySaveConfig(SpireConfig config) {
        try {
            config.save();
        } catch (IOException e) {
            ModHelper.logger.warn(e);
        }
    }
	
	public static void saveHasDefeatedTheHeart(boolean defeated) {
		if (config == null) return;
		hasDefeatedTheHeart = defeated;
		config.setBool("hasDefeatedTheHeart", hasDefeatedTheHeart);
		trySaveConfig(config);
	}
	
	public static void saveHasDefeatedTheHeartSFW2(boolean defeated) {
		if (config == null) return;
		hasDefeatedTheHeartSFW2 = defeated;
		config.setBool("hasDefeatedTheHeartSFW2", hasDefeatedTheHeartSFW2);
		trySaveConfig(config);
	}
	
	public static void saveCheckedLatestVersion(String version) {
		if (config == null) return;
		checkedLatestVersion = version;
		config.setString("checkedLatestVersion", checkedLatestVersion);
		trySaveConfig(config);
	}

    public static void saveId(String id) {
        if (config == null) return;
        skinId = id;
        config.setString("skinId", skinId);
        trySaveConfig(config);
    }

    public static void saveActiveTutorials(boolean active) {
        if (config == null) return;
        activeTutorials = active;
        config.setBool("activeTutorials", activeTutorials);
        trySaveConfig(config);
    }

    //初始化设置
    public static ModPanel initSettings() {
        if (config == null)
            tryCreateConfig();

        ModHelper.logger.info("===============加载设置===============");
        String configPath = "hanninaResources/localization/config_zh.json";

        Gson gson = new Gson();
        String json = Gdx.files.internal(configPath).readString(String.valueOf(StandardCharsets.UTF_8));
        Type configType = (new TypeToken<Map<String, String>>() {
        }).getType();

        Map<String, String> configStrings = gson.fromJson(json, configType);


        ModPanel modPanel = new ModPanel();

        float yPos = 750.0F;
        ModLabeledToggleButton nsfwButton =
                new ModLabeledToggleButton(configStrings.get("nsfw"), 350.0F, yPos, Settings.CREAM_COLOR, FontHelper.charDescFont, nsfw, modPanel, label -> {
                }, button -> {
                    if (config != null) {
                        nsfw = button.enabled;
                        config.setBool("nsfw", nsfw);
                        trySaveConfig(config);
                    }
                });

        yPos -= 50.0F;
        ModLabeledToggleButton activeTutorialsButton =
                new ModLabeledToggleButton(configStrings.get("activeTutorials"), 350.0F, yPos, Settings.CREAM_COLOR, FontHelper.charDescFont, activeTutorials, modPanel, label -> {
                }, button -> {
                    if (config != null) {
                        activeTutorials = button.enabled;
                        config.setBool("activeTutorials", activeTutorials);
                        trySaveConfig(config);
                    }
                });

        modPanel.addUIElement(nsfwButton);
        modPanel.addUIElement(activeTutorialsButton);

        ModHelper.logger.info("===============设置加载完毕===============");
        return modPanel;
    }
}
