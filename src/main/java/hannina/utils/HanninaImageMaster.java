package hannina.utils;

import com.badlogic.gdx.graphics.Texture;

import java.text.MessageFormat;
import java.util.HashMap;

public class HanninaImageMaster {
    public static Texture charR;
    public static Texture charG;
    public static Texture charB;
    public static Texture charP;
    public static Texture charIdle;
    public static Texture charFusion;
    public static Texture badge;

    static {
        charR = new Texture(ModHelper.getImgPath("char/zhan.png"));
        charG = new Texture(ModHelper.getImgPath("char/lie.png"));
        charB = new Texture(ModHelper.getImgPath("char/ji.png"));
        charP = new Texture(ModHelper.getImgPath("char/guan.png"));
        charIdle = new Texture(ModHelper.getImgPath("char/null.png"));
        charFusion = new Texture(ModHelper.getImgPath("char/fusion.png"));
        badge = new Texture(ModHelper.getImgPath("UI/badge.png"));
    }

    private static HashMap<String, Texture> skinTexture = new HashMap<>();

    public static Texture getSkinImg(String color) {
        if (skinTexture.get(color) == null) {
            skinTexture.put(color, new Texture(ModHelper.getImgPath(MessageFormat.format("char/skin/{0}/{1}.png",
                    SkinSelectScreen.getSkin().id, color))));
        }
        return skinTexture.get(color);
    }

    public static void refreshSkin() {
        skinTexture.clear();
    }
}
