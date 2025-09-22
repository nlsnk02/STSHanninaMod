package hannina.utils;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import hannina.misc.OnGainGoldSubscriber;
import hannina.misc.OnLoseGoldSubscriber;

import java.util.stream.Stream;

public class GoldManager {

    public static int goldLoseInCombat = 0;
    public static int goldLoseLastTime = 0;

    private static int gold = -1000;

    public static void initGoldLose() {
        goldLoseInCombat = 0;
        goldLoseLastTime = 0;
        gold = -1000;
    }

    public static void updateGold() {
        if (ModHelper.InCombat()) {
            if (AbstractDungeon.player.gold != gold) {
                if (AbstractDungeon.player.gold > gold) {
                    if (gold != -1000) {
                        AbstractDungeon.player.powers.stream()
                                .filter(p -> p instanceof OnGainGoldSubscriber)
                                .forEach(p -> ((OnGainGoldSubscriber) p).onGainGold());
                    }
                } else {
                    goldLoseLastTime = gold - AbstractDungeon.player.gold;
                    goldLoseInCombat += goldLoseLastTime;

                    Stream.of(AbstractDungeon.player.hand.group,
                            AbstractDungeon.player.drawPile.group,
                            AbstractDungeon.player.discardPile.group).forEach(
                            l -> l.stream()
                                    .filter(c -> c instanceof OnLoseGoldSubscriber)
                                    .forEach(c -> ((OnLoseGoldSubscriber) c).onLoseGold(goldLoseLastTime)));

                    AbstractDungeon.player.powers.stream()
                            .filter(p -> p instanceof OnLoseGoldSubscriber)
                            .forEach(p -> {
                                ((OnLoseGoldSubscriber) p).onLoseGold(goldLoseLastTime);
                            });

                    AbstractDungeon.player.relics.stream()
                            .filter(r -> r instanceof OnLoseGoldSubscriber)
                            .forEach(r -> {
                                ((OnLoseGoldSubscriber) r).onLoseGold(goldLoseLastTime);
                            });

                    AbstractDungeon.player.potions.stream()
                            .filter(p -> p instanceof OnLoseGoldSubscriber)
                            .forEach(p -> {
                                ((OnLoseGoldSubscriber) p).onLoseGold(goldLoseLastTime);
                            });

                }
            }
            gold = AbstractDungeon.player.gold;
        }
    }
}
