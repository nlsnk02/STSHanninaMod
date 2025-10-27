package hannina.powers;

import basemod.helpers.CardModifierManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import hannina.actions.PlayACardAction;
import hannina.actions.SelectFromGridAction;
import hannina.actions.SelectFromRewardAction;
import hannina.fantasyCard.AbstractHanninaCard;
import hannina.misc.ReunionModifier;
import hannina.modcore.Enums;
import hannina.utils.ModHelper;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class FusionPower extends AbstractPower {
    public static final String POWER_ID = ModHelper.makeID(FusionPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final String NAME = powerStrings.NAME;
    // 能力的描述
    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private static final String PATH128 = ModHelper.makeRelicAd(FusionPower.class.getSimpleName(), true);
    private static final String PATH48 = ModHelper.makeRelicAd(FusionPower.class.getSimpleName(), false);

    public static AbstractCreature unionCardtarget;

    public FusionPower(AbstractCreature owner) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.type = PowerType.BUFF;
        this.owner = owner;
        //        this.loadRegion("tools");
        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(PATH128), 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(PATH48), 0, 0, 32, 32);
        this.updateDescription();
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {

        unionCardtarget = null;

        if (card instanceof AbstractHanninaCard && CardModifierManager.hasModifier(card, "ReunionModifier")) {
            ArrayList<AbstractCard> list = new ArrayList<>(
                    ((ReunionModifier) (CardModifierManager.getModifiers(card, "ReunionModifier").get(0)))
                            .union
            );
            list.remove(card);
            list = list.stream().map(AbstractCard::makeStatEquivalentCopy).collect(Collectors.toCollection(ArrayList::new));
            unionCardtarget = action.target;

            addToTop(new SelectFromRewardAction(list,
                    c -> c.ifPresent(
                            abstractCard -> addToTop(new PlayACardAction(abstractCard, null, unionCardtarget, true))
                    ), "", true, AbstractGameAction.ActionType.DRAW));
        }

        if (card instanceof AbstractHanninaCard && card.hasTag(Enums.ChangeColorCard)) {
            addToBot(new GainEnergyAction(1));
        }
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        if (isPlayer) {
            addToBot(new RemoveSpecificPowerAction(owner, owner, this));
        }
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }
}