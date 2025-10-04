package hannina.patches.utils;

import basemod.helpers.CardModifierManager;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.TutorialStrings;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.screens.CardRewardScreen;
import com.megacrit.cardcrawl.ui.FtueTip;
import hannina.fantasyCard.AbstractHanninaCard;
import hannina.utils.ConfigHelper;

import java.util.ArrayList;

@SpirePatch(clz = CardRewardScreen.class, method = "open")
public class TutorialPatch {
    private static final TutorialStrings tutorialStrings = CardCrawlGame.languagePack.getTutorialString("HanninaTutorial");

    @SpireInsertPatch(rloc = 35)
    public static SpireReturn Insert(CardRewardScreen _inst, ArrayList<AbstractCard> cards, RewardItem rItem, String header) {
        if (ConfigHelper.activeTutorials)
            for (AbstractCard c : cards) {
                if (CardModifierManager.hasModifier(c, "ReunionModifier")) {
                    AbstractDungeon.ftue = new FtueTip(tutorialStrings.TEXT[0], tutorialStrings.LABEL[0], Settings.WIDTH / 2.0F - 500.0F * Settings.scale, Settings.HEIGHT / 2.0F, c);
                    AbstractDungeon.ftue.type = FtueTip.TipType.POWER;
                    ConfigHelper.saveActiveTutorials(false);
                }
            }
        return SpireReturn.Continue();
    }
}
