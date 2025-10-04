package hannina.character;

import basemod.abstracts.CustomPlayer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.red.IronWave;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.cutscenes.CutscenePanel;
import com.megacrit.cardcrawl.events.city.Vampires;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.localization.CharacterStrings;
import com.megacrit.cardcrawl.relics.BloodVial;
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import hannina.cards.HanninaStrike;
import hannina.misc.SaveData;
import hannina.modcore.Enums;
import hannina.powers.FusionPower;
import hannina.powers.UnionPower;
import hannina.utils.ChangePlayerModel;
import hannina.utils.HanninaImageMaster;
import hannina.utils.ModHelper;
import hannina.utils.SkinSelectScreen;

import java.util.ArrayList;


public class Hannina extends CustomPlayer {
    private static final int ENERGY_PER_TURN = 3;
    private static final CharacterStrings characterStrings = CardCrawlGame.languagePack.getCharacterString("hannina:MyCharacter");
//    private static final String SHOULDER_2 = "hanninaResources/img/char/shoulder2.png";
//    private static final String SHOULDER_1 = "hanninaResources/img/char/shoulder1.png";
//    private static final String CORPSE = "hanninaResources/img/char/die.png";

    private static final String ORB_VFX = "hanninaResources/img/UI/orb/vfx.png";

    private static final String[] ORB_TEXTURES = new String[]{
            "hanninaResources/img/UI/orb/layer5.png",
            "hanninaResources/img/UI/orb/layer4.png",
            "hanninaResources/img/UI/orb/layer3.png",
            "hanninaResources/img/UI/orb/layer2.png",
            "hanninaResources/img/UI/orb/layer1.png",
            "hanninaResources/img/UI/orb/layer6.png",
            "hanninaResources/img/UI/orb/layer5d.png",
            "hanninaResources/img/UI/orb/layer4d.png",
            "hanninaResources/img/UI/orb/layer3d.png",
            "hanninaResources/img/UI/orb/layer2d.png",
            "hanninaResources/img/UI/orb/layer1d.png"
    };

    private static final float[] LAYER_SPEED = new float[]{-40.0F, -32.0F, 20.0F, -20.0F, 0.0F, -10.0F, -8.0F, 5.0F, -5.0F, 0.0F};


    private static final int STARTING_HP = 84;
    private static final int MAX_HP = 84;
    private static final int STARTING_GOLD = 99;
    private static final int DRAW_SIZE = 5;
    private static final int ASCENSION_MAX_HP_LOSS = 8;

    public static final Color My_COLOR = CardHelper.getColor(0, 0, 191);

    public Hannina(String name) {

        super(name, Enums.HANNINA_CLASS, ORB_TEXTURES, ORB_VFX, LAYER_SPEED, null, null);
        this.dialogX = this.drawX + 0.0F * Settings.scale;
        this.dialogY = this.drawY + 220.0F * Settings.scale;

        initializeClass("hanninaResources/img/char/null.png", null, null, null,
                getLoadout(),
                0F, 5.0F, 240.0F, 300.0F,
                new EnergyManager(ENERGY_PER_TURN));

        if (SkinSelectScreen.Inst != null)
            refreshSkin();
    }

    public void refreshSkin() {
        this.shoulderImg = HanninaImageMaster.getSkinImg("shoulder1");
        this.shoulder2Img = HanninaImageMaster.getSkinImg("shoulder2");
        this.corpseImg = HanninaImageMaster.getSkinImg("die");
    }

    @Override
    public ArrayList<String> getStartingDeck() {
        //添加初始卡组
        ArrayList<String> retVal = new ArrayList<>();

        retVal.add("hannina:HanninaStrike");
        retVal.add("hannina:HanninaStrike");
        retVal.add("hannina:HanninaStrike");
        retVal.add("hannina:HanninaStrike");

        retVal.add("hannina:HanninaDefence");
        retVal.add("hannina:HanninaDefence");
        retVal.add("hannina:HanninaDefence");
        retVal.add("hannina:HanninaDefence");

        retVal.add("hannina:Xingtaibianhua");
        retVal.add("hannina:Sisezhuayin");

        return retVal;
    }

    @Override
    public ArrayList<String> getStartingRelics() {
        //添加初始遗物
        ArrayList<String> retVal = new ArrayList<>();
        retVal.add("hannina:Maozhibaozang");
        UnlockTracker.markRelicAsSeen("hannina:Maozhibaozang");
        return retVal;
    }

    @Override
    public CharSelectInfo getLoadout() {
        String title = characterStrings.NAMES[0];
        String flavor = characterStrings.TEXT[0];

        return new CharSelectInfo(
                title, // 人物名字
                flavor, // 人物介绍
                STARTING_HP, // 当前血量
                MAX_HP, // 最大血量
                2, // 初始充能球栏位
                STARTING_GOLD, // 初始携带金币
                DRAW_SIZE, // 每回合抽牌数量
                this, // 别动
                this.getStartingRelics(), // 初始遗物
                this.getStartingDeck(), // 初始卡组
                false // 别动
        );
    }


    @Override
    public String getTitle(PlayerClass playerClass) {
        return characterStrings.NAMES[0];
    }

    @Override
    public AbstractCard.CardColor getCardColor() {
        //选择卡牌颜色
        return Enums.HanninaColor;
    }

    @Override
    public Color getCardRenderColor() {
        return My_COLOR;
    }

    @Override
    public AbstractCard getStartCardForEvent() {
        return new IronWave();
    }

    @Override
    public Color getCardTrailColor() {
        return My_COLOR;
    }

    @Override
    public int getAscensionMaxHPLoss() {
        return ASCENSION_MAX_HP_LOSS;
    }

    @Override
    public BitmapFont getEnergyNumFont() {
        return FontHelper.energyNumFontBlue;
    }

    @Override
    public void doCharSelectScreenSelectEffect() {
        //这里时选择人物时的特效
        CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.MED, ScreenShake.ShakeDur.SHORT, true);
    }

    @Override
    public String getCustomModeCharacterButtonSoundKey() {
        return "选择人物时的音效";
    }

    @Override
    public String getLocalizedCharacterName() {
        return characterStrings.NAMES[0];
    }

    @Override
    public AbstractPlayer newInstance() {
        return new Hannina(this.name);
    }

    @Override
    public String getSpireHeartText() {
        return characterStrings.TEXT[1];
    }

    @Override
    public Color getSlashAttackColor() {
        return My_COLOR;
    }


    public AbstractGameAction.AttackEffect[] getSpireHeartSlashEffect() {
        return new AbstractGameAction.AttackEffect[]{AbstractGameAction.AttackEffect.SLASH_HEAVY, AbstractGameAction.AttackEffect.FIRE, AbstractGameAction.AttackEffect.SLASH_DIAGONAL, AbstractGameAction.AttackEffect.SLASH_HEAVY, AbstractGameAction.AttackEffect.FIRE, AbstractGameAction.AttackEffect.SLASH_DIAGONAL};
    }

    @Override
    public String getVampireText() {
        return Vampires.DESCRIPTIONS[1];
    }

    @Override
    public ArrayList<CutscenePanel> getCutscenePanels() {
        ArrayList<CutscenePanel> panels = new ArrayList<>();
        // 有两个参数的，第二个参数表示出现图片时播放的音效
        panels.add(new CutscenePanel("hanninaResources/img/event/epcg/cut1.png"));
        panels.add(new CutscenePanel("hanninaResources/img/event/epcg/cut2.png"));
        panels.add(new CutscenePanel("hanninaResources/img/event/epcg/cut3.png"));

        return panels;
    }

    @Override
    public void update() {
        if (!this.isDead) {
            if (this.hasPower(FusionPower.POWER_ID)) {
                this.img = HanninaImageMaster.getSkinImg("fusion");
            } else if (this.hasPower(UnionPower.POWER_ID)) {
                UnionPower power = (UnionPower) this.getPower(UnionPower.POWER_ID);
                if (power.color == AbstractCard.CardColor.RED)
                    this.img = HanninaImageMaster.getSkinImg("red");
                if (power.color == AbstractCard.CardColor.GREEN)
                    this.img = HanninaImageMaster.getSkinImg("green");
                if (power.color == AbstractCard.CardColor.BLUE)
                    this.img = HanninaImageMaster.getSkinImg("blue");
                if (power.color == AbstractCard.CardColor.PURPLE)
                    this.img = HanninaImageMaster.getSkinImg("purple");
            } else this.img = HanninaImageMaster.getSkinImg("null");
        }
        super.update();
    }

//    @Override
//    public void render(SpriteBatch sb) {
//        if (this.shoulderImg == null || this.shoulder2Img == null || this.corpseImg == null || this.img == null) {
//            refreshSkin();
//            this.img = HanninaImageMaster.charIdle;
//        }
//        if (this.shoulderImg == null || this.shoulder2Img == null || this.corpseImg == null || this.img == null) {
//            ModHelper.logger.info("=================出现bug================");
//            ModHelper.logger.info("shoulderImg = {}， shoulder2Img = {}， corpseImg = {}， img = {}", this.shoulderImg, this.shoulder2Img, this.corpseImg, this.img);
//            SkinSelectScreen.printSkinNameAndId();
//            ModHelper.logger.info("=================出现bug================");
//        }
//        super.render(sb);
//    }

//    @Override
//    public void render(SpriteBatch spriteBatch) {
//        if(this.isDead) this.img = this.corpseImg;
//        super.render(spriteBatch);
//    }

    @Override
    public void dispose() {
        if (this.atlas != null) {
            this.atlas.dispose();
        }

//        这个不dispose了
//        if (this.img != null) {
//            this.img.dispose();
//        }
//
//        if (this.shoulderImg != null) {
//            this.shoulderImg.dispose();
//        }
//
//        if (this.shoulder2Img != null) {
//            this.shoulder2Img.dispose();
//        }
//
//        if (this.corpseImg != null) {
//            this.corpseImg.dispose();
//        }
    }
}

