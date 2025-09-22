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

    public static Properties SSAModDefaultSettings = new Properties();
    public static SpireConfig config = null;

    public static String skinId = "";
    public static boolean nsfw = false;

    public static void tryCreateConfig() {
        try {
            config = new SpireConfig("hannina", "hanninaConfig");
        } catch (IOException e) {
            ModHelper.logger.warn("+++++++++++++++++++++" + e + "++++++++++++++++++++++");
            config = null;
        }

        if (config != null) {
            if(config.has("skinId")){
                skinId = config.getString("skinId");
            }else skinId = "";

            nsfw = config.has("nsfw") && config.getBool("nsfw");
        }
    }

    public static void trySaveConfig(SpireConfig config) {
        try {
            config.save();
        } catch (IOException e) {
            ModHelper.logger.warn(e);
        }
    }

    public static void saveId(String id) {
        if(config == null) return;
        skinId = id;
        config.setString("skinId", skinId);
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

            modPanel.addUIElement(nsfwButton);

        ModHelper.logger.info("===============设置加载完毕===============");
        return modPanel;
    }
}
