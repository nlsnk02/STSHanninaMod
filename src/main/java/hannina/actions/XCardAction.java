package hannina.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class XCardAction extends AbstractGameAction {
    private final AbstractPlayer player;

    private final boolean freeToPlayOnce;

    private final int energyOnUse;

    private final Consumer<Integer> onUpdate;

    private final boolean addToTop;

    public XCardAction(AbstractGameAction.ActionType actionType, AbstractPlayer player, boolean freeToPlayOnce, int energyOnUse, Consumer<Integer> onUpdate) {
        this(actionType, player, freeToPlayOnce, energyOnUse, onUpdate, false);
    }

    public XCardAction(AbstractGameAction.ActionType actionType, AbstractPlayer player, boolean freeToPlayOnce, int energyOnUse, Consumer<Integer> onUpdate, boolean addToTop) {
        this.player = player;
        this.freeToPlayOnce = freeToPlayOnce;
        this.energyOnUse = energyOnUse;
        this.onUpdate = onUpdate;
        this.actionType = actionType;
        this.addToTop = addToTop;
    }

    public void update() {
        int amount = EnergyPanel.totalCount;
        if (this.energyOnUse != -1)
            amount = this.energyOnUse;
        AbstractPlayer p = this.player;
        AbstractRelic relic = p.getRelic("Chemical X");
        if (relic != null) {
            relic.flash();
            amount += 2;
        }
        if (this.addToTop) {
            List<AbstractGameAction> actions = new ArrayList<>(AbstractDungeon.actionManager.actions);
            AbstractDungeon.actionManager.actions.clear();
            this.onUpdate.accept(Integer.valueOf(amount));
            AbstractDungeon.actionManager.actions.addAll(actions);
        } else {
            this.onUpdate.accept(Integer.valueOf(amount));
        }
        if (!this.freeToPlayOnce)
            p.energy.use(EnergyPanel.totalCount);
        this.isDone = true;
    }
}


/* Location:              C:\Users\35727\Desktop\slay_mod\jd-gui-windows-1.6.6\timewalker-20240816.jar!\io\chaofan\sts\timewalker\actions\common\XCardAction.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */