package hannina.character.skin;

import com.esotericsoftware.spine.Skeleton;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import hannina.character.Hannina;
import hannina.utils.ModHelper;

import java.lang.reflect.Field;

public abstract class AbstractSkin {

    public String id;
    public String name;
    public String atlasPath;
    public String skeletonPath;
    public boolean isR18=false;
    public float scale=1;
    public AbstractSkin(String id) {
        this.id = id;
    }

    /**
     * 读取默认(null)情况下的
     */
    public void loadDefault(){
        atlasPath= ModHelper.getImgPath("char/Hannina_null/Hannina_null.atlas");
        skeletonPath=ModHelper.getImgPath("char/Hannina_null/hannina_null37.json");
    }
    //为玩家实例初始化
    public void init(Hannina player){
        loadDefault();
    }

    /**
     * 重设为默认(null)
     */
    public void reset(Hannina player){
        player.dispose();
        player.loadAnimation(atlasPath,skeletonPath,scale);
        player.state.setAnimation(0,"Idle",true);
    }

    /**
     * 切换形态
     */
    public void switchForm(Hannina player,AbstractCard.CardColor color){
        player.disposeModel();
        if (color!=null){
            String colorStr = color.name().toLowerCase();
            player.loadAnimation(
                    ModHelper.getImgPath(String.format("char/skin/NSFW/Hannina_%1$s/Hannina_%1$s.atlas", colorStr)),
                    ModHelper.getImgPath(String.format("char/skin/NSFW/Hannina_%1$s/hannina_%1$s37.json", colorStr)),
                    scale
            );
            String sfwPath = isR18 ? "nsfw" : "sfw";
            player.state.setAnimation(0, "Idle_" + sfwPath, true);
        }else {
            reset(player);
        }
    }

    /**
     * 进入融合模式
     */
    public void enterFusion(Hannina player){
        player.disposeModel();
        player.loadAnimation(
                ModHelper.getImgPath("char/skin/Hannina_fusion/Hannina_fusion.atlas"),
                ModHelper.getImgPath("char/skin/Hannina_fusion/hannina_fusion37.json"),
                scale
        );
        String sfwPath = isR18 ? "nsfw" : "sfw";
        player.state.setAnimation(0, "Idle_" + sfwPath, true);
    }

    /**
     * 死亡动画
     */
    public void dead(Hannina player){
        player.disposeModel();
        if (isR18){
            player.loadAnimation(
                    ModHelper.getImgPath("char/skin/Hannina_NSFW_die/Hannina_NSFW_die.atlas"),
                    ModHelper.getImgPath("char/skin/Hannina_NSFW_die/hannina_nsfw_die37.json"),
                    scale
            );
            player.state.setAnimation(0, "Idle", true);
        }else {
            player.loadAnimation(
                    ModHelper.getImgPath("char/skin/Hannina_SFW_die/Hannina_Die_Sfw.atlas"),
                    ModHelper.getImgPath("char/skin/Hannina_SFW_die/hannina_die_sfw37.json"),
                    scale
            );
            player.state.setAnimation(0, "Idle", true);
        }
    }

    /**
     * 有的皮肤切形态不会切换模型而是修改动画，需要调用这个来重置皮肤状态
     */
    protected void changeAnimMode(Hannina player){
        try {
            Field s = AbstractCreature.class.getDeclaredField("skeleton");
            s.setAccessible(true);
            ((Skeleton) s.get(player)).setToSetupPose();
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
