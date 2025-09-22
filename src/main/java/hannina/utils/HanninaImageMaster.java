package hannina.utils;

import com.badlogic.gdx.graphics.Texture;

public class HanninaImageMaster {
    public static Texture charR;
    public static Texture charG;
    public static Texture charB;
    public static Texture charP;
    public static Texture charIdle;
    public static Texture charFusion;

    static {
        charR = new Texture(ModHelper.getImgPath("char/zhan.png"));
        charG = new Texture(ModHelper.getImgPath("char/lie.png"));
        charB = new Texture(ModHelper.getImgPath("char/ji.png"));
        charP = new Texture(ModHelper.getImgPath("char/guan.png"));
        charIdle = new Texture(ModHelper.getImgPath("char/null.png"));
        charFusion = new Texture(ModHelper.getImgPath("char/fusion.png"));
    }
}
