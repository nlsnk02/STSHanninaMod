package hannina.utils;

import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.colorless.Madness;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.random.Random;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import hannina.fantasyCard.AbstractHanninaCard;
import hannina.misc.ReunionModifier;
import hannina.misc.SaveData;
import hannina.modcore.Enums;
import hannina.patches.utils.CardSeedBeforeRollPatch;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/*
复合牌的核心逻辑，包括生成牌，切换复合牌，以及自己的随机数
调用这些代码主要是在 patches.UnionMechanicsPatch 里
 */
public class UnionManager {
    public static Random rng = new Random();

    public static int getRamdom(int index) {
        if (index < 0) return 0;

        Random rng = null;
        boolean flag = false;

        if (AbstractDungeon.player != null &&
                AbstractDungeon.currMapNode != null &&
                AbstractDungeon.currMapNode.room != null) {
            if ((AbstractDungeon.getCurrRoom()).phase == AbstractRoom.RoomPhase.COMBAT) {
                rng = AbstractDungeon.cardRandomRng;
            }
//            else if ((AbstractDungeon.getCurrRoom()).phase == AbstractRoom.RoomPhase.COMPLETE) {
//                rng = UnionManager.rng;
//            }
            else {
                rng = UnionManager.rng;
                flag = true;
            }
        }
        if (rng == null)
            rng = new Random();

        int n = rng.random(index - 1);

        if (flag) {
            SaveData.saveData.hueRngCounter = rng.counter;
        }
        return n;
    }

    /*
    获取一个随机数，战斗内使用card random rng，战斗外用自己的随机数。
    */
    public static AbstractCard getRamdomCard(Predicate<AbstractCard> filter) {
        List<AbstractCard> l = CardLibrary.getAllCards().stream()
                .filter(filter)
                .filter(c -> !(ModHelper.isInCombat()
                        && !(AbstractDungeon.getCurrRoom()).isBattleOver
                        && c.hasTag(AbstractCard.CardTags.HEALING)))
                .collect(Collectors.toList());

        if (l.isEmpty())
            return new Madness();

        return l.get(getRamdom(l.size())).makeCopy();
    }

    public static void generateSeeds() {
        rng = new Random(Settings.seed);
    }

    public static void loadSeeds() {
        if (SaveData.saveData == null) {
            SaveData.saveData = new SaveData();
        }
        rng = new Random(Settings.seed, SaveData.saveData.hueRngCounter);
    }

    public static void onSaveSeeds() {
        //如果是调用了卡牌奖励，那么回退saveData。否则更新UnionManager.rng
        //这段代码主要是解决sl之后复合卡牌变化的问题
        if (CardSeedBeforeRollPatch.hue_count_before_roll != -1) {
            SaveData.saveData.hueRngCounter = CardSeedBeforeRollPatch.hue_count_before_roll;
            CardSeedBeforeRollPatch.hue_count_before_roll = -1;
        } else {
            SaveData.saveData.hueRngCounter = AbstractDungeon.floorNum;
            UnionManager.rng = new Random(Settings.seed, SaveData.saveData.hueRngCounter);
        }
    }

    public static void configureOnSpawn(AbstractCard c) {
        if (c instanceof AbstractHanninaCard && !CardModifierManager.hasModifier(c, "ReunionModifier")) {
            AbstractHanninaCard h = (AbstractHanninaCard) c;
            ArrayList<AbstractCard> unions = h.setUnion();
            if (unions != null) {
                unions.add(h);
                CardModifierManager.addModifier(h, new ReunionModifier(unions));
            }
        }
    }

    public static void configureOnCopy(AbstractCard copy, AbstractCard original) {
        //不需要代码
    }

    public static void changeUnion(AbstractCard c, AbstractCard.CardColor color, CardGroup group) {
        if (!CardModifierManager.getModifiers(c, "ReunionModifier").isEmpty()) {
            ReunionModifier mod = (ReunionModifier)
                    (CardModifierManager.getModifiers(c, "ReunionModifier").get(0));

            //切回本体有时候也要切回无色牌
            AbstractCard tmp = mod.union.stream().filter(u -> u.color == color).findFirst().orElse(null);
            if (tmp == null && color == Enums.HanninaColor)
                tmp = mod.union.stream().filter(u -> u.color == AbstractCard.CardColor.COLORLESS).findFirst().orElse(null);


            AbstractCard rtCard = tmp;
            if (rtCard != null) {
                c.stopGlowing();

                CardModifierManager.removeModifiersById(c, "ReunionModifier", true);
                CardModifierManager.addModifier(rtCard, mod);

                group.group = group.group.stream().map(gc -> {
                    if (gc == c) {
                        //特判腐化
                        if (AbstractDungeon.player.hasPower("Corruption") && rtCard.type == AbstractCard.CardType.SKILL)
                            rtCard.setCostForTurn(-9);

                        return rtCard;
                    }
                    return gc;
                }).collect(Collectors.toCollection(ArrayList::new));
            }
        }
    }
}
