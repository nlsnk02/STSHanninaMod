package hannina.modcore;
import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.helpers.CardLibrary;


public class Enums {

    @SpireEnum
    public static AbstractPlayer.PlayerClass HANNINA_CLASS;
    @SpireEnum(name = "hannina")
    public static CardLibrary.LibraryType HanninaLibraryType;
    @SpireEnum(name = "hannina")
    public static AbstractCard.CardColor HanninaColor;
    @SpireEnum
    public static AbstractCard.CardTags UnionCard;
    @SpireEnum
    public static AbstractCard.CardTags ChangeColorCard;
}