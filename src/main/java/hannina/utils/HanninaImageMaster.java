package hannina.utils;

import com.badlogic.gdx.graphics.Texture;

import java.text.MessageFormat;
import java.util.HashMap;

public class HanninaImageMaster {
    public static Texture charIdle;
    public static Texture badge;
    public static Texture lockedPic;

    static {
        charIdle = new Texture(ModHelper.getImgPath("char/Hannina_null/Hannina_null.png"));
        badge = new Texture(ModHelper.getImgPath("UI/badge.png"));
        lockedPic = new Texture(ModHelper.getImgPath("cards/locked_skill.png"));
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
        if (skinTexture != null)
            skinTexture.clear();
        else skinTexture = new HashMap<>();
    }
}
