package hannina.patches.utils;

import basemod.ReflectionHacks;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.screens.charSelect.CharacterSelectScreen;
import hannina.modcore.Enums;
import hannina.utils.SkinSelectScreen;

public class SelectScreenPatch {
    public static boolean isHanninaSelected() {
        return (CardCrawlGame.chosenCharacter == Enums.HANNINA_CLASS && (Boolean) ReflectionHacks.getPrivate(CardCrawlGame.mainMenuScreen.charSelectScreen, CharacterSelectScreen.class, "anySelected"));
    }

    @SpirePatch(clz = CharacterSelectScreen.class, method = "render")
    public static class RenderButtonPatch {
        public static void Postfix(CharacterSelectScreen _inst, SpriteBatch sb) {
            if (SelectScreenPatch.isHanninaSelected())
                if (SkinSelectScreen.Inst != null)
                    SkinSelectScreen.Inst.render(sb);
        }

        @SpireInsertPatch(rloc = 78)
        public static void Insert(CharacterSelectScreen _inst, SpriteBatch sb) {
            if (SelectScreenPatch.isHanninaSelected())
                if (SkinSelectScreen.Inst != null) {
                    SkinSelectScreen.Inst.renderBackground(sb);
                }
        }
    }

    @SpirePatch(clz = CharacterSelectScreen.class, method = "update")
    public static class UpdateButtonPatch {
        public static void Prefix(CharacterSelectScreen _inst) {
            if (SelectScreenPatch.isHanninaSelected())
                SkinSelectScreen.Inst.update();
        }
    }
}
