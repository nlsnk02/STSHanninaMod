package hannina.events;

import com.megacrit.cardcrawl.cards.AbstractCard;

import com.megacrit.cardcrawl.cards.curses.Pride;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;

import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import hannina.cards.HanninaDefence;
import hannina.cards.HanninaStrike;
import hannina.cards.MMRuizhan;
import hannina.relics.Gangqi;
import hannina.relics.Miaodan;



public class FlyingCat extends AbstractImageEvent {
    public static final String ID = "FlyingCat";
    private static final EventStrings eventStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    public static final String[] OPTIONS;
    private static final int MAX_GOLD = 150;
    private AbstractPotion potionOption;
    private AbstractCard cardOption;
    private int goldAmount;
    private AbstractRelic givenRelic;
    private static final String DIALOG_1;
    private FlyingCat.CUR_SCREEN screen;

    public FlyingCat() {
        super(NAME, DIALOG_1, "hanninaResources/img/event/epcg/Flyingcat.png");
        this.screen = FlyingCat.CUR_SCREEN.INTRO;
        this.imageEventText.updateDialogOption(0, OPTIONS[0] ,new MMRuizhan());
        this.imageEventText.setDialogOption(OPTIONS[1]);

    }



    private int getGoldAmount() {
        if (AbstractDungeon.player.gold < 50) {
            return 0;
        } else {
            return AbstractDungeon.player.gold > 150 ? AbstractDungeon.miscRng.random(50, 150) : AbstractDungeon.miscRng.random(50, AbstractDungeon.player.gold);
        }
    }

    protected void buttonEffect(int buttonPressed) {
        switch (this.screen) {

            case INTRO:
                this.screen = FlyingCat.CUR_SCREEN.COMPLETE;
                switch (buttonPressed) {
                    case 0:
                        this.imageEventText.updateBodyText(DESCRIPTIONS[1] );
                        int j=AbstractDungeon.player.masterDeck.group.size();
                        for (int i=0;i<j;i++){
                            AbstractDungeon.player.masterDeck.removeTopCard();
                        }
                        for (int i=0;i<4;i++){
                            AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new MMRuizhan(), (float)Settings.WIDTH / 2.0F - 350.0F * Settings.xScale, (float)Settings.HEIGHT / 2.0F));
                        } for (int i=0;i<4;i++){
                           AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new Pride(), (float)Settings.WIDTH / 2.0F - 350.0F * Settings.xScale, (float)Settings.HEIGHT / 2.0F));
                        }
                        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new HanninaStrike(), (float)Settings.WIDTH / 2.0F - 350.0F * Settings.xScale, (float)Settings.HEIGHT / 2.0F));
                        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new HanninaDefence(), (float)Settings.WIDTH / 2.0F - 350.0F * Settings.xScale, (float)Settings.HEIGHT / 2.0F));

                        AbstractDungeon.getCurrRoom().spawnRelicAndObtain((float)Settings.WIDTH * 0.28F, (float)Settings.HEIGHT / 2.0F, new Miaodan());
                   AbstractDungeon.getCurrRoom().spawnRelicAndObtain((float)Settings.WIDTH * 0.28F, (float)Settings.HEIGHT / 2.0F, new Gangqi());

                        break;
                    case 1:
                        this.imageEventText.updateBodyText(DESCRIPTIONS[2] );
                        AbstractDungeon.player.gainGold(50);
                        break;

                }

                this.imageEventText.updateDialogOption(0, OPTIONS[2]);
                this.imageEventText.clearRemainingOptions();
                break;
            case COMPLETE:
                this.openMap();
        }

    }

    private void relicReward() {
        this.givenRelic = AbstractDungeon.returnRandomScreenlessRelic(AbstractDungeon.returnRandomRelicTier());
        AbstractDungeon.getCurrRoom().spawnRelicAndObtain((float)Settings.WIDTH * 0.28F, (float)Settings.HEIGHT / 2.0F, this.givenRelic);
    }

    static {
        eventStrings = CardCrawlGame.languagePack.getEventString("FlyingCat");
        NAME = eventStrings.NAME;
        DESCRIPTIONS = eventStrings.DESCRIPTIONS;
        OPTIONS = eventStrings.OPTIONS;
        DIALOG_1 = DESCRIPTIONS[0];
    }

    private static enum CUR_SCREEN {
        INTRO,
        COMPLETE;
    }
}
