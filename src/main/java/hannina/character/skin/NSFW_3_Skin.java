package hannina.character.skin;


import com.esotericsoftware.spine.Skeleton;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import hannina.character.Hannina;
import hannina.utils.ModHelper;

import java.lang.reflect.Field;

public class NSFW_3_Skin extends AbstractSkin{
    public static final String ID="NSFW-3";
    public NSFW_3_Skin() {
        super(ID);
        isR18=true;
    }

    @Override
    public void loadDefault() {
        atlasPath= ModHelper.getImgPath("char/skin/NSFW-3/Hannina_NSFW-3/Hannina_NSFW-3.atlas");
        skeletonPath= ModHelper.getImgPath("char/skin/NSFW-3/Hannina_NSFW-3/hannina_nsfw-337.json");
    }

    @Override
    public void reset(Hannina player) {
        player.dispose();
        player.loadAnimation(atlasPath,skeletonPath,scale);
        player.state.setAnimation(0,"Idle_null",true);
    }

    @Override
    public void enterFusion(Hannina player) {
        changeAnimMode(player);
        player.state.setAnimation(0, "Idle_fusion", true);
    }

    @Override
    public void switchForm(Hannina player, AbstractCard.CardColor color) {
        if (color!=null){
            changeAnimMode(player);
            player.state.setAnimation(0, "Idle_"+color.name().toLowerCase(),true);
        }else {
            reset(player);
        }
    }
}
