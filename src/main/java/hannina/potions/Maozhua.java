package hannina.potions;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import hannina.actions.ChooseColor2EnterAction;
import hannina.modcore.Core;
import hannina.utils.ModHelper;

public class Maozhua extends AbstractHanninaPotion{
    public static String id = ModHelper.makeID(Maozhua.class.getSimpleName());
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(id);

    public Maozhua() {
        super(potionStrings.NAME, Maozhua.class.getSimpleName(), PotionRarity.COMMON);
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
        addToBot(new ChooseColor2EnterAction());
    }

    @Override
    public int getPotency(int i) {
        return 1;
    }

    @Override
    public AbstractPotion makeCopy() {
        return new Maozhua();
    }
}
