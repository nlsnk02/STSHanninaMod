package hannina.misc;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.screens.mainMenu.ScrollBar;
import com.megacrit.cardcrawl.screens.mainMenu.ScrollBarListener;
import com.megacrit.cardcrawl.screens.select.GridCardSelectScreen;
import hannina.utils.ModHelper;

import java.lang.reflect.Field;

public class ScrollBarForUnion implements ScrollBarListener {

    public int index;

//    public final ScrollBar scrollBar;

    public AbstractCard lastCard;

    public ScrollBarForUnion() {
        this.index = 0;
//        this.scrollBar = new ScrollBar(this);
//        this.scrollBar.changeHeight(Settings.HEIGHT - 384.0F * Settings.scale);
    }


    public void update() {
        if (AbstractDungeon.player != null && AbstractDungeon.screen != null) {
            try {
                AbstractCard hoverdCard;

                switch (AbstractDungeon.screen) {
                    case NONE:
                        hoverdCard = AbstractDungeon.player.hoveredCard;
                        break;
                    case MASTER_DECK_VIEW:
                        hoverdCard = AbstractDungeon.player.masterDeck.getHoveredCard();
                        break;
                    case GAME_DECK_VIEW:
                        hoverdCard = AbstractDungeon.player.drawPile.getHoveredCard();
                        break;
                    case EXHAUST_VIEW:
                        hoverdCard = AbstractDungeon.player.exhaustPile.getHoveredCard();
                        break;
                    case DISCARD_VIEW:
                        hoverdCard = AbstractDungeon.player.discardPile.getHoveredCard();
                        break;
                    case GRID:
                        Field f = GridCardSelectScreen.class.getDeclaredField("hoveredCard");
                        f.setAccessible(true);
                        hoverdCard = (AbstractCard) f.get(AbstractDungeon.gridSelectScreen);
                        break;
                    case CARD_REWARD:
                        hoverdCard = AbstractDungeon.cardRewardScreen.rewardGroup.stream()
                                .filter(c -> c.hb.hovered).findFirst().orElse(null);
                        break;
                    case SHOP:
                        hoverdCard = AbstractDungeon.shopScreen.coloredCards.stream()
                                .filter(c -> c.hb.hovered).findFirst().orElse(null);
                        break;
                    default:
                        hoverdCard = null;
                }

//                ModHelper.logger.info("===========hoverdCard: {}==========", hoverdCard);


                if (hoverdCard == null || hoverdCard != lastCard) {
                    lastCard = hoverdCard;
                    index = 0;
                }
            } catch (NoSuchFieldException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }

        updateScrolling();
//        this.scrollBar.update();
    }

    public void updateScrolling() {
        if (InputHelper.scrolledDown) {
            this.index--;
            if (index < 0) {
                this.index = 0;
            }
        } else if (InputHelper.scrolledUp) {
            this.index++;
        }

        updateBarPosition();
    }

    private void updateBarPosition() {
//        this.scrollBar.positionWithinOnRight(AbstractCard.IMG_WIDTH * 0.65F,
//                PredictorRenderHelper.STARTY + PredictorRenderHelper.IntervalDistance,
//                PredictorRenderHelper.STARTY - PredictorRenderHelper.IntervalDistance * PredictorRenderHelper.cardsToPreview.size());
//        this.scrollBar.parentScrolledToPercent(index / (float) (PredictorRenderHelper.cardsToPreview.size() - 1));
    }


    @Override
    public void scrolledUsingBar(float newPercent) {
    }
}
