package hannina.utils;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import com.megacrit.cardcrawl.powers.BufferPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import hannina.actions.ChangeCharColorAction;
import hannina.modcore.Enums;
import hannina.powers.AntiUnionPower;
import hannina.powers.FusionPower;
import hannina.powers.UnionPower;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;


public class ModHelper {

    public static String makeID(String s) {
        return "hannina:" + s;
    }

    public static String getImgPath(String indirPath) {
        return "hanninaResources/img/" + indirPath;
    }

    public static String getShaderPath(String indirPath) {
        return "hanninaResources/shaders/" + indirPath;
    }

    public static String makeRelicAd(String name, boolean isPortrait) {
        String isP = "32";
        if (isPortrait) isP = "84";

        return "hanninaResources/img/powers/" + name + isP + ".png";
    }

    public static final Logger logger = LogManager.getLogger(ModHelper.class.getName());


    public static boolean isInCombat() {
        return AbstractDungeon.player != null &&
                AbstractDungeon.currMapNode != null &&
                AbstractDungeon.currMapNode.room != null &&
                AbstractDungeon.currMapNode.room.monsters != null &&
                (AbstractDungeon.getCurrRoom()).phase == AbstractRoom.RoomPhase.COMBAT;
    }

    public static AbstractCard.CardColor getPlayerColor() {
        if (isInCombat() && AbstractDungeon.player.hasPower(UnionPower.POWER_ID)) {
            return ((UnionPower) (AbstractDungeon.player.getPower(UnionPower.POWER_ID))).color;
        }
        return AbstractDungeon.player.getCardColor();
    }

    public static void enterRandomColor() {
        ArrayList<AbstractCard.CardColor> colors = new ArrayList<>();
        colors.add(AbstractCard.CardColor.RED);
        colors.add(AbstractCard.CardColor.BLUE);
        colors.add(AbstractCard.CardColor.GREEN);
        colors.add(AbstractCard.CardColor.PURPLE);
        colors.remove(ModHelper.getPlayerColor());
        AbstractCard.CardColor color = colors.get(AbstractDungeon.cardRandomRng.random(colors.size() - 1));
        AbstractDungeon.actionManager.addToBottom(new ChangeCharColorAction(color));

    }

    public static boolean checkHoverability(AbstractCard card) {
        if (AbstractDungeon.player == null) return false;

        switch (AbstractDungeon.screen) {
            case NONE:
                return AbstractDungeon.player.hoveredCard == card;
            case MASTER_DECK_VIEW:
                return AbstractDungeon.player.masterDeck.contains(card) && card.hb.hovered;
            case GAME_DECK_VIEW:
                return AbstractDungeon.player.drawPile.contains(card) && card.hb.hovered;
            case EXHAUST_VIEW:
                return AbstractDungeon.player.exhaustPile.contains(card) && card.hb.hovered;
            case DISCARD_VIEW:
                return AbstractDungeon.player.discardPile.contains(card) && card.hb.hovered;
        }
        return false;
    }

    //照抄的cardgroup的resetCardBeforeMoving
    public static void refreshCard(AbstractCard c) {
        if (AbstractDungeon.player.hoveredCard == c) {
            AbstractDungeon.player.releaseCard();
        }

        AbstractDungeon.actionManager.removeFromQueue(c);
        c.unhover();
        c.untip();
        c.stopGlowing();
        c.setAngle(0);
        c.flashVfx = null;
    }

    //变牌的逻辑
    public static void changeCardUnion(AbstractCard.CardColor color, boolean onlyInHand) {
        new ArrayList<>(AbstractDungeon.player.hand.group).forEach(c -> {
            c.update();
            UnionManager.changeUnion(c, color, AbstractDungeon.player.hand);
        });
        if (!onlyInHand) {
            new ArrayList<>(AbstractDungeon.player.discardPile.group).forEach(c -> {
                c.update();
                UnionManager.changeUnion(c, color, AbstractDungeon.player.discardPile);
            });
            new ArrayList<>(AbstractDungeon.player.drawPile.group).forEach(c -> {
                c.update();
                UnionManager.changeUnion(c, color, AbstractDungeon.player.drawPile);
            });
        }
    }

    //融合
    public static void fusion(){
        AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(AbstractDungeon.player, AbstractDungeon.player,
                AntiUnionPower.POWER_ID));

        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player,
                new ArtifactPower(AbstractDungeon.player, 1)));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player,
                new BufferPower(AbstractDungeon.player, 1)));

        //这个必须在fusion的前面
        AbstractDungeon.actionManager.addToBottom(new ChangeCharColorAction(Enums.HanninaColor));

        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player,
                new FusionPower(AbstractDungeon.player)));
    }
}
