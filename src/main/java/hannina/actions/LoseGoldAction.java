package hannina.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class LoseGoldAction extends AbstractGameAction {

    private int amt;

    public LoseGoldAction(int amt) {
        this.amt = amt;
    }

    @Override
    public void update() {
        AbstractDungeon.player.loseGold(amt);
        this.isDone = true;
    }
}
