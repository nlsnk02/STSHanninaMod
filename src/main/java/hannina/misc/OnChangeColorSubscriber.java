package hannina.misc;

import com.megacrit.cardcrawl.cards.AbstractCard;

public interface OnChangeColorSubscriber {
    //暂时只能用于能力和遗物
    void onChangeColor(AbstractCard.CardColor color);
}
