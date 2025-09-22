package hannina.potions;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.potions.FirePotion;
import hannina.actions.ChooseColor2EnterAction;
import hannina.powers.AiqingPower;
import hannina.utils.ModHelper;

public class Aiqing extends AbstractHanninaPotion{
    public static String id = ModHelper.makeID(Aiqing.class.getSimpleName());
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(id);

    public Aiqing() {
        super(potionStrings.NAME, Aiqing.class.getSimpleName(), PotionRarity.RARE);
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
        addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new AiqingPower(AbstractDungeon.player, 1), 1));
    }

    @Override
    public int getPotency(int i) {
        return 1;
    }

    @Override
    public AbstractPotion makeCopy() {
        return new Aiqing();
    }
}
