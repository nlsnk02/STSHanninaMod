package hannina.character.skin;


import com.megacrit.cardcrawl.cards.AbstractCard;
import hannina.character.Hannina;
import hannina.utils.ModHelper;

public class NSFW_2_Skin extends AbstractSkin{
    public static final String ID="NSFW-2";
    public NSFW_2_Skin() {
        super(ID);
        isR18=true;
    }

    @Override
    public void switchForm(Hannina player, AbstractCard.CardColor color) {
        player.disposeModel();
        if (color!=null){
            String colorStr = color.name().toLowerCase();
            player.loadAnimation(
                    ModHelper.getImgPath(String.format("char/skin/NSFW-2/Hannina_NSFW-2_%1$s/Hannina_NSFW-2_%1$s.atlas", colorStr)),
                    ModHelper.getImgPath(String.format("char/skin/NSFW-2/Hannina_NSFW-2_%1$s/hannina_nsfw-2_%1$s37.json", colorStr)),
                    scale
            );
            player.state.setAnimation(0, "Idle", true);
        }else {
            reset(player);
        }
    }
}
