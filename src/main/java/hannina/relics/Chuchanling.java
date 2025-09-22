package hannina.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import hannina.actions.LoseGoldAction;
import hannina.utils.ModHelper;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class Chuchanling extends CustomRelic {
    public static final String ID = ModHelper.makeID(Chuchanling.class.getSimpleName());
    private static final String IMG = ModHelper.getImgPath("relics/" + Chuchanling.class.getSimpleName() + ".png");

    public Chuchanling() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(IMG), RelicTier.COMMON, LandingSound.MAGICAL);
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }


    @Override
    public void onEquip() {
        AbstractDungeon.effectsQueue.add(new AbstractGameEffect() {
            @Override
            public void update() {

                ArrayList<AbstractCard> strikes = AbstractDungeon.player.masterDeck.group.stream().filter(c -> c.hasTag(AbstractCard.CardTags.STARTER_STRIKE)).collect(Collectors.toCollection(ArrayList::new));
                ArrayList<AbstractCard> defences = AbstractDungeon.player.masterDeck.group.stream().filter(c -> c.hasTag(AbstractCard.CardTags.STARTER_DEFEND)).collect(Collectors.toCollection(ArrayList::new));

                if(!strikes.isEmpty()){
                    AbstractDungeon.player.masterDeck.removeCard(strikes.get(AbstractDungeon.miscRng.random(strikes.size()-1)));
                }
                if(!defences.isEmpty()){
                    AbstractDungeon.player.masterDeck.removeCard(defences.get(AbstractDungeon.miscRng.random(defences.size()-1)));
                }

                AbstractDungeon.player.increaseMaxHp(4, false);

                this.isDone = true;
            }

            @Override
            public void render(SpriteBatch spriteBatch) {}
            @Override
            public void dispose() {}
        });
    }

    @Override
    public AbstractRelic makeCopy() {
        return new Chuchanling();
    }
}

