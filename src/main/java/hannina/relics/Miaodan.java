package hannina.relics;

import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.GainGoldAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PlatedArmorPower;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import hannina.utils.ModHelper;



public class Miaodan extends CustomRelic {
    public static final String ID = ModHelper.makeID(Miaodan.class.getSimpleName());
    private static final String IMG = ModHelper.getImgPath("relics/" + Miaodan.class.getSimpleName() + ".png");


    public Miaodan() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(IMG), RelicTier.SPECIAL, LandingSound.MAGICAL);
    }

//    @Override
//    public void atBattleStart() {
//        addToBot(new IncreaseMaxOrbAction(2));
//    }


    public void onPlayCard(AbstractCard c, AbstractMonster m) {
        if (c.type== AbstractCard.CardType.ATTACK){
            if(AbstractDungeon.player.hasPower(VigorPower.POWER_ID)){
                int amount=AbstractDungeon.player.getPower(VigorPower.POWER_ID).amount;
               this.addToBot(new GainBlockAction(AbstractDungeon.player,amount/2));
            }
        }
    }
    public void onExhaust(AbstractCard card) {
        if (card.type== AbstractCard.CardType.STATUS||card.type==AbstractCard.CardType.CURSE){
            this.addToBot(new ApplyPowerAction(AbstractDungeon.player,AbstractDungeon.player,new PlatedArmorPower(AbstractDungeon.player,1)));
        }
    }
    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new Miaodan();
    }
}

