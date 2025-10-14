package hannina.modcore;

import basemod.AutoAdd;
import basemod.BaseMod;
import basemod.ModPanel;
import basemod.abstracts.CustomSavable;
import basemod.helpers.RelicType;
import basemod.interfaces.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import hannina.actions.ChangeCharColorAction;
import hannina.cards.MMRuizhan;
import hannina.cards.XCDDangmoshi;
import hannina.character.Hannina;
import hannina.events.FlyingCat;
import hannina.misc.SaveData;
import hannina.misc.ScrollBarForUnion;
import hannina.potions.Aiqing;
import hannina.potions.Jinbi;
import hannina.potions.Maozhua;
import hannina.relics.*;
import hannina.utils.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.charset.StandardCharsets;

import static com.megacrit.cardcrawl.core.Settings.language;
import static hannina.modcore.Enums.HanninaColor;

@SpireInitializer
public class Core implements
        PostPowerApplySubscriber,
        PostExhaustSubscriber,
//PostBattleSubscriber, 
        PostDungeonInitializeSubscriber,
        EditCharactersSubscriber,
        PostInitializeSubscriber,
        EditRelicsSubscriber,
        EditCardsSubscriber,
        EditStringsSubscriber,
        OnCardUseSubscriber,
        EditKeywordsSubscriber,
        OnPowersModifiedSubscriber,
        PostDrawSubscriber,
        PostEnergyRechargeSubscriber,
        OnPlayerDamagedSubscriber,
        OnPlayerTurnStartSubscriber,
        AddAudioSubscriber,
        RelicGetSubscriber,
        StartGameSubscriber,
        OnStartBattleSubscriber,
        PostBattleSubscriber,
        PostUpdateSubscriber,
        CustomSavable<SaveData> {
    public static final Logger logger = LogManager.getLogger(Core.class.getName());
    public static final String ATTACK = "hanninaResources/img/512/bg_attack_temp.png";
    public static final String SKILL = "hanninaResources/img/512/bg_skill_temp.png";
    public static final String POWER = "hanninaResources/img/512/bg_power_temp.png";

    public static final String ATTACK_PORTRAIT = "hanninaResources/img/1024/bg_attack_temp.png";
    public static final String SKILL_PORTRAIT = "hanninaResources/img/1024/bg_skill_temp.png";
    public static final String POWER_PORTRAIT = "hanninaResources/img/1024/bg_power_temp.png";

    public static final String ENERGY_ORB = "hanninaResources/img/512/CardOrb.png";
    public static final String ENERGY_ORB_PORTRAIT = "hanninaResources/img/1024/CardOrb.png";
    public static final String CARD_ENERGY_ORB = "hanninaResources/img/UI/EnergyOrb.png";

    private static final String BUTTON = "hanninaResources/img/charSelect/Button.png";
    private static final String PORTRAIT = "hanninaResources/img/charSelect/Portrait.png";
    public static final Color Purple = CardHelper.getColor(191, 64, 191);

    public static ScrollBarForUnion scrollBarForUnion = new ScrollBarForUnion();

    public Core() {
        BaseMod.subscribe(this);
        BaseMod.addSaveField("Hannina:SaveData", this);
        BaseMod.addColor(HanninaColor, Purple, Purple, Purple, Purple, Purple, Purple, Purple, ATTACK, SKILL, POWER, ENERGY_ORB, ATTACK_PORTRAIT, SKILL_PORTRAIT, POWER_PORTRAIT, ENERGY_ORB_PORTRAIT, CARD_ENERGY_ORB);
    }

    public static void initialize() {
        new Core();
        ConfigHelper.tryCreateConfig();
    }

    @Override
    public void receiveEditCharacters() {
        BaseMod.addCharacter(new Hannina("New Mod"), BUTTON, PORTRAIT, Enums.HANNINA_CLASS);
    }

    @Override
    public void receiveEditCards() {
        logger.info("================加入卡牌=============");
        (new AutoAdd("hannina"))
                .packageFilter("hannina.cards")
                .any(AbstractCard.class, (info, c) -> {
                    BaseMod.addCard(c);
                    UnlockTracker.unlockCard(c.cardID);
                });
        logger.info("================加入卡牌=============");
    }


    @Override
    public void receiveEditKeywords() {
        logger.info("===============加载关键字===============");
        Gson gson = new Gson();
        String lang = "zh";
        if (language == Settings.GameLanguage.ZHS) {
            lang = "zh";
        }

        String json = Gdx.files.internal("hanninaResources/localization/keywords_" + lang + ".json")
                .readString(String.valueOf(StandardCharsets.UTF_8));
        Keyword[] keywords = gson.fromJson(json, Keyword[].class);
        if (keywords != null) {
            for (Keyword keyword : keywords) {
                BaseMod.addKeyword("hannina", keyword.NAMES[0], keyword.NAMES, keyword.DESCRIPTION);
            }
        }
        logger.info("===============加载关键字===============");
    }

    @Override
    public void receiveAddAudio() {
    }

    @Override
    public void receivePostInitialize() {
        logger.info("===============加载事件与其他东西===============");
        ModPanel settingsPanel = ConfigHelper.initSettings();
        Texture badgeTexture = ImageMaster.loadImage(ModHelper.getImgPath("UI/badge.png"));
        BaseMod.registerModBadge(badgeTexture, "hannina mod", "332", "", settingsPanel);

        BaseMod.addPotion(Maozhua.class, null, null, null, Maozhua.id, Enums.HANNINA_CLASS);
        BaseMod.addPotion(Jinbi.class, null, null, null, Jinbi.id, Enums.HANNINA_CLASS);
        BaseMod.addPotion(Aiqing.class, null, null, null, Aiqing.id, Enums.HANNINA_CLASS);

        SkinSelectScreen.init();
        BaseMod.addEvent(FlyingCat.ID, FlyingCat.class);
        logger.info("===============加载事件与其他东西===============");

    }

    @Override
    public void receiveEditRelics() {
        logger.info("===============加载遗物===============");
        BaseMod.addRelicToCustomPool(new Maozhibaozang(), HanninaColor);
        BaseMod.addRelicToCustomPool(new Chuchanling(), HanninaColor);
//        BaseMod.addRelicToCustomPool(new Jibeilongdehuiyi(), HanninaColor);
        BaseMod.addRelicToCustomPool(new Jibeilongdehuiyi2(), HanninaColor);
        BaseMod.addRelicToCustomPool(new Maozhizhencang(), HanninaColor);
        BaseMod.addRelicToCustomPool(new PToy(), HanninaColor);
        BaseMod.addRelicToCustomPool(new Qinmihezhao(), HanninaColor);
        BaseMod.addRelicToCustomPool(new Xiantuanniudanji(), HanninaColor);
        BaseMod.addRelic(new Gangqi(), RelicType.SHARED);
        BaseMod.addRelic(new Miaodan(), RelicType.SHARED);
        logger.info("===============加载遗物===============");
    }

    @Override
    public void receiveEditStrings() {

        String relic = "", card = "", chr = "", power = "", potion = "", event = "", ui = "", tutorial = "";
        logger.info("===============加载文字信息===============");

        String lang;
        if (language == Settings.GameLanguage.ZHS || language == Settings.GameLanguage.ZHT) {
            lang = "zh";
        } else lang = "zh";

        //keywords在receiveEditKeywords里单独判断
        card = "hanninaResources/localization/cards_" + lang + ".json";
        chr = "hanninaResources/localization/characters_" + lang + ".json";
        relic = "hanninaResources/localization/relics_" + lang + ".json";
        power = "hanninaResources/localization/powers_" + lang + ".json";
        potion = "hanninaResources/localization/potion_" + lang + ".json";
        event = "hanninaResources/localization/event_" + lang + ".json";
        ui = "hanninaResources/localization/ui_" + lang + ".json";
        tutorial = "hanninaResources/localization/tutorial_" + lang + ".json";

        String cardStrings = Gdx.files.internal(card).readString(String.valueOf(StandardCharsets.UTF_8));
        BaseMod.loadCustomStrings(CardStrings.class, cardStrings);

        String chrStrings = Gdx.files.internal(chr).readString(String.valueOf(StandardCharsets.UTF_8));
        BaseMod.loadCustomStrings(CharacterStrings.class, chrStrings);

        String relicStrings = Gdx.files.internal(relic).readString(String.valueOf(StandardCharsets.UTF_8));
        BaseMod.loadCustomStrings(RelicStrings.class, relicStrings);

        String powerStrings = Gdx.files.internal(power).readString(String.valueOf(StandardCharsets.UTF_8));
        BaseMod.loadCustomStrings(PowerStrings.class, powerStrings);

        String potionStrings = Gdx.files.internal(potion).readString(String.valueOf(StandardCharsets.UTF_8));
        BaseMod.loadCustomStrings(PotionStrings.class, potionStrings);
      String eventStrings = Gdx.files.internal(event).readString(String.valueOf(StandardCharsets.UTF_8));
      BaseMod.loadCustomStrings(EventStrings.class, eventStrings);

        String uiStrings = Gdx.files.internal(ui).readString(String.valueOf(StandardCharsets.UTF_8));
        BaseMod.loadCustomStrings(UIStrings.class, uiStrings);

        String tutorialStrings = Gdx.files.internal(tutorial).readString(String.valueOf(StandardCharsets.UTF_8));
        BaseMod.loadCustomStrings(TutorialStrings.class, tutorialStrings);
        logger.info("===============加载文字信息===============");
    }

    @Override
    public void receivePostDungeonInitialize() {
        if (AbstractDungeon.floorNum < 1) {
            SaveData.saveData = new SaveData();
            ChangePlayerModel.ChangeSkin(SaveData.saveData.skin);
        }
    }

    @Override
    public void receivePostEnergyRecharge() {
    }

    @Override
    public void receiveRelicGet(AbstractRelic relic) {
    }

    @Override
    public void receiveCardUsed(AbstractCard c) {
    }

    @Override
    public void receiveOnBattleStart(AbstractRoom r) {
        GoldManager.initGoldLose();
        XCDDangmoshi.usedInThisCombat = 0;
        MMRuizhan.count = 0;
    }

    @Override
    public void receivePostBattle(AbstractRoom abstractRoom) {
    }

    @Override
    public void receivePostDraw(AbstractCard arg0) {
    }

    @Override
    public void receivePowersModified() {
    }

    @Override
    public int receiveOnPlayerDamaged(int var1, DamageInfo var2) {
        return var1;
    }

    @Override
    public void receivePostExhaust(AbstractCard c) {
    }

    @Override
    public void receivePostPowerApplySubscriber(AbstractPower pow, AbstractCreature target, AbstractCreature owner) {
    }

    @Override
    public void receiveOnPlayerTurnStart() {
        MonsterDieThisTurnManager.amount = 0;
        ChangeCharColorAction.cardColorsEnteredThisTurn.clear();
    }

    @Override
    public void receiveStartGame() {
        if (SaveData.saveData != null) {
            ChangePlayerModel.ChangeSkin(SaveData.saveData.skin);
        }
//        ModHelper.logger.info("++++++++++++=======start game============+++++++++");
    }

    @Override
    public SaveData onSave() {
        return SaveData.save();
    }

    @Override
    public void onLoad(SaveData saveData) {
        SaveData.load(saveData);
    }

    @Override
    public void receivePostUpdate() {
        scrollBarForUnion.update();
        GoldManager.updateGold();
    }
}

