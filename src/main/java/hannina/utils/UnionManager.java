package hannina.utils;

import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.random.Random;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import hannina.fantasyCard.AbstractHanninaCard;
import hannina.misc.ReunionModifier;
import hannina.misc.SaveData;
import hannina.modcore.Enums;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class UnionManager {
    public static Random rng = new Random();

    public static int getRamdom(int index) {
        if (index < 0) return 0;

        Random rng;
        boolean flag = false;

        if (AbstractDungeon.player != null &&
                AbstractDungeon.currMapNode != null &&
                AbstractDungeon.currMapNode.room != null &&
                AbstractDungeon.currMapNode.room.monsters != null) {
            if ((AbstractDungeon.getCurrRoom()).phase == AbstractRoom.RoomPhase.COMBAT && !(AbstractDungeon.getCurrRoom()).isBattleOver) {
                rng = AbstractDungeon.cardRandomRng;
            } else {
                rng = UnionManager.rng;
                flag = true;
            }
        } else
            rng = new Random();

        int n = rng.random(index - 1);

        if (flag) {
            SaveData.saveData.hueRngCounter = rng.counter;
        }
        return n;
    }

    public static AbstractCard getRamdomCard(Predicate<AbstractCard> filter) {
        return getRamdomCard(filter, 0);
    }

    public static AbstractCard getRamdomCard(Predicate<AbstractCard> filter, int index) {
        List<AbstractCard> l = CardLibrary.getAllCards().stream().filter(filter).collect(Collectors.toList());
        AbstractCard c = l.get(getRamdom(l.size())).makeCopy();

        if (ModHelper.isInCombat()
                && !(AbstractDungeon.getCurrRoom()).isBattleOver
                && c.hasTag(AbstractCard.CardTags.HEALING)
                && index < 3) {
            return getRamdomCard(filter, index + 1);
        }
        return c;
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
            if(tmp == null && color == Enums.HanninaColor)
                tmp = mod.union.stream().filter(u -> u.color == AbstractCard.CardColor.COLORLESS).findFirst().orElse(null);


            AbstractCard rtCard = tmp;
            if (rtCard != null) {
                c.stopGlowing();

                CardModifierManager.removeModifiersById(c, "ReunionModifier", true);
                CardModifierManager.addModifier(rtCard, mod);

                group.group = group.group.stream().map(gc -> {
                    if (gc == c) return rtCard;
                    return gc;
                }).collect(Collectors.toCollection(ArrayList::new));
            }
        }
    }
}
