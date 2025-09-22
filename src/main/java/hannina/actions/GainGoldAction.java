package hannina.actions;


import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.RainingGoldEffect;
import com.megacrit.cardcrawl.vfx.SpotlightPlayerEffect;

public class GainGoldAction extends AbstractGameAction {
    public GainGoldAction(int amount) {
        this.amount = amount;
        this.actionType = ActionType.DAMAGE;
    }

    public void update() {
        AbstractDungeon.effectList.add(new RainingGoldEffect(this.amount * 2, true));
        AbstractDungeon.player.gainGold(this.amount);
        this.isDone = true;
    }
}