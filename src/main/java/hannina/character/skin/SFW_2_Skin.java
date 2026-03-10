package hannina.character.skin;


import com.megacrit.cardcrawl.cards.AbstractCard;
import hannina.character.Hannina;
import hannina.utils.ModHelper;

public class SFW_2_Skin extends AbstractSkin{
    public static final String ID="SFW-2";
    public SFW_2_Skin() {
        super(ID);
    }

    @Override
    public void loadDefault() {
        atlasPath= ModHelper.getImgPath("char/skin/SFW-2/Hannina_SFW-2_null/Hannina_SFW-2_null.atlas");
        skeletonPath= ModHelper.getImgPath("char/skin/SFW-2/Hannina_SFW-2_null/hannina_sfw-2_null37.json");
    }

    @Override
    public void dead(Hannina player) {
        player.disposeModel();
        player.loadAnimation(
                ModHelper.getImgPath("char/skin/SFW-2/Hannina_SFW-2_die/Hannina_SFW-2_die.atlas"),
                ModHelper.getImgPath("char/skin/SFW-2/Hannina_SFW-2_die/hannina_sfw-2_die37.json"),
                scale
        );
        player.state.setAnimation(0, "Idle", false);
    }

    //TODO暂时没有切形态的动画
    @Override
    public void enterFusion(Hannina player) {
//        player.state.setAnimation(0,"Idle_fusion",true);
    }

    @Override
    public void switchForm(Hannina player, AbstractCard.CardColor color) {
//        if (color!=null){
//            player.state.setAnimation(0, "Idle_"+color.name().toLowerCase(),true);
//        }else {
//            reset(player);
//        }
    }
}
