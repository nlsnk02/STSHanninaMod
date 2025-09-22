package hannina.patches.cards;

import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.mod.stslib.StSLib;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.unique.FeedAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.purple.Wish;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.neow.NeowReward;
import com.megacrit.cardcrawl.saveAndContinue.SaveFile;
import com.megacrit.cardcrawl.shop.ShopScreen;
import com.megacrit.cardcrawl.vfx.FastCardObtainEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import hannina.utils.UnionManager;
import javassist.CannotCompileException;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@SuppressWarnings("unused")
public class UnionMechanicsPatch {
    private static final Logger logger = LogManager.getLogger(UnionMechanicsPatch.class.getName());

//	@SpirePatch(clz = AbstractCard.class, method = SpirePatch.CLASS)
//	public static class Fields {
//		@Deprecated
//		public static SpireField<Hue> hue = new SpireField<>(() -> null);
//	}

    @SpirePatch(clz = AbstractDungeon.class, method = "generateSeeds", paramtypez = {})
    public static class GenerateSeedsPatch {
        @SpirePostfixPatch
        public static void Postfix() {
            UnionManager.generateSeeds();
        }
    }

    @SpirePatch(clz = AbstractDungeon.class, method = "loadSeeds", paramtypez = {SaveFile.class})
    public static class LoadSeedsPatch {
        @SpirePostfixPatch
        public static void Postfix(SaveFile saveFile) {
            UnionManager.loadSeeds();
        }
    }


    @SpirePatch2(
            clz = AbstractCard.class,
            method = "render",
            paramtypez = {SpriteBatch.class, boolean.class}
    )
    @SpirePatch2(
            clz = AbstractCard.class,
            method = "renderInLibrary"
    )
    public static class CardModPreRenderPatch {
        @SpirePrefixPatch
        public static void PreRender(AbstractCard __instance, SpriteBatch sb) {
            //i didn't want to loop over player powers for every card rendered and otherwise caching it is also kinda gross
//            if (CopyAndPastePower.grossStaticListOfUUIDsToShowIcon.contains(__instance.uuid)) {
//                ArrayList<IconPayload> icons = ExtraIconsPatch.ExtraIconsField.extraIcons.get(__instance);
//                if (!icons.contains(CopyAndPastePower.icon)) {
//                    icons.add(CopyAndPastePower.icon);
//                }
//            }

            for (AbstractCardModifier mod : CardModifierManager.modifiers(__instance)) {
                if (mod instanceof PreCardRenderModifier)
                    ((PreCardRenderModifier) mod).preRender(__instance, sb);
            }
        }

        public interface PreCardRenderModifier {
            void preRender(AbstractCard card, SpriteBatch sb);
        }
    }


    @SpirePatch(clz = AbstractDungeon.class, method = "getRewardCards", paramtypez = {})
    public static class GetRewardCardsPatch {
        @SpireInstrumentPatch
        public static ExprEditor Instrument() {
            return new ExprEditor() {
                @Override
                public void edit(MethodCall m) throws CannotCompileException {
                    if (m.getMethodName().equals("makeCopy"))
                        m.replace("$_ = " + UnionMechanicsPatch.class.getName() +
                                ".configure($proceed($$));");
                }
            };
        }
    }

    @SpirePatch(clz = NeowReward.class, method = "getRewardCards", paramtypez = {boolean.class})
    public static class NeowRewardPatch {
        @SpireInstrumentPatch
        public static ExprEditor Instrument() {
            return new ExprEditor() {
                @Override
                public void edit(MethodCall m) throws CannotCompileException {
                    if (m.getMethodName().equals("makeCopy"))
                        m.replace("$_ = " + UnionMechanicsPatch.class.getName() +
                                ".configure($proceed($$));");
                }
            };
        }
    }

    @Deprecated
    public static AbstractCard configure(AbstractCard card) {
        UnionManager.configureOnSpawn(card);
        return card;
    }

    @SpirePatch(clz = AbstractPlayer.class, method = "initializeStarterDeck",
            paramtypez = {})
    public static class AbstractPlayerPatch {
        @SpirePostfixPatch
        public static void Postfix(AbstractPlayer _inst) {
            for (AbstractCard c : _inst.masterDeck.group) {
                UnionManager.configureOnSpawn(c);
            }
        }
    }

    @SpirePatch(clz = ShopScreen.class, method = "initCards",
            paramtypez = {})
    public static class ShopScreenPatch {
        @SpirePostfixPatch
        public static void Postfix(ShopScreen _inst) {
            for (AbstractCard c : _inst.coloredCards)
                UnionManager.configureOnSpawn(c);
            for (AbstractCard c : _inst.colorlessCards)
                UnionManager.configureOnSpawn(c);
        }
    }

    @SpirePatch(clz = ShowCardAndObtainEffect.class, method = SpirePatch.CONSTRUCTOR,
            paramtypez = {AbstractCard.class, float.class, float.class, boolean.class})
    public static class ShowCardAndObtainEffectPatch {
        @SpirePostfixPatch
        public static void Postfix(ShowCardAndObtainEffect _inst,
                                   AbstractCard card, float x, float y, boolean convergeCards) {
            UnionManager.configureOnSpawn(card);
        }
    }

    @SpirePatch(clz = FastCardObtainEffect.class, method = SpirePatch.CONSTRUCTOR,
            paramtypez = {AbstractCard.class, float.class, float.class})
    public static class FastCardObtainEffectPatch {
        @SpirePostfixPatch
        public static void Postfix(FastCardObtainEffect _inst, AbstractCard card, float x, float y) {
            UnionManager.configureOnSpawn(card);
        }
    }

//    @SpirePatch(clz = AbstractCard.class, method = "makeStatEquivalentCopy", paramtypez = {})
//    public static class MakeStatEquivalentCopyPatch {
//        @SpirePostfixPatch
//        public static AbstractCard Postfix(AbstractCard _ret, AbstractCard _inst) {
//            UnionManager.configureOnCopy(_ret, _inst);
//            return _ret;
//        }
//    }

    @SpirePatch(clz = StSLib.class, method = "onCreateCard", paramtypez = {AbstractCard.class})
    public static class SpawnInCombatPatch {
        @SpirePrefixPatch
        public static void Prefix(AbstractCard card) {
            UnionManager.configureOnSpawn(card);
        }
    }
}
