package hannina.potions;

import basemod.abstracts.CustomSavable;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.shrines.WeMeetAgain;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.potions.FruitJuice;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import hannina.actions.ChooseColor2EnterAction;
import hannina.actions.GainGoldAction;
import hannina.misc.OnLoseGoldSubscriber;
import hannina.utils.ModHelper;

public class Jinbi extends AbstractHanninaPotion implements CustomSavable<Integer>, OnLoseGoldSubscriber {
    public static String id = ModHelper.makeID(Jinbi.class.getSimpleName());
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(id);

    private int amount = 0;

    private void updateDescription() {
//        ModHelper.logger.info("Jinbi updateDescription, amount: " + amount);
        this.description = potionStrings.DESCRIPTIONS[0] + potionStrings.DESCRIPTIONS[1] + (amount * 3 / 10) + potionStrings.DESCRIPTIONS[2];
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
    }

    public Jinbi() {
        super(potionStrings.NAME, Jinbi.class.getSimpleName(), PotionRarity.UNCOMMON);
        this.labOutlineColor = Color.WHITE.cpy();
        this.isThrown = false;
    }

    @Override
    public void initializeData() {
        this.potency = getPotency();
        this.description = potionStrings.DESCRIPTIONS[0];
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
    }

    @Override
    public void use(AbstractCreature abstractCreature) {
        if (ModHelper.InCombat())
            addToBot(new GainGoldAction(amount * 3 / 10));
        else AbstractDungeon.player.gainGold(amount * 3 / 10);
    }

    @Override
    public boolean canUse() {
        if (AbstractDungeon.actionManager.turnHasEnded && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
            return false;
        } else {
            return AbstractDungeon.getCurrRoom().event == null || !(AbstractDungeon.getCurrRoom().event instanceof WeMeetAgain);
        }
    }

    @Override
    public int getPotency(int i) {
        return 1;
    }

    @Override
    public AbstractPotion makeCopy() {
        return new Jinbi();
    }

    @Override
    public Integer onSave() {
        return amount;
    }

    @Override
    public void onLoad(Integer integer) {
        if(integer == null) integer = 0;
        amount = integer;
        if(amount>0){
            updateDescription();
        }
    }

    @Override
    public void onLoseGold(int gold) {
        amount += gold;
        updateDescription();
    }
}
