package hannina.utils;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.localization.UIStrings;
import hannina.character.Hannina;
import hannina.modcore.Core;
import hannina.modcore.Enums;

import java.util.ArrayList;
import java.util.Objects;

public class SkinSelectScreen {
    public static final float SKIN_SELECT_POSITION_X = Settings.WIDTH * 1.15F - 600.0F * Settings.scale;
    public static final float SKIN_SELECT_POSITION_Y = Settings.HEIGHT * 1.4F - 600.0F * Settings.scale;

    public static SkinSelectScreen Inst;

    public Hitbox leftHb;

    public Hitbox rightHb;

    public String curName = "";

    public String nextName = "";

    public int index;

    private static final ArrayList<Skin> skins;

    public static Skin getSkin() {
        if (Inst == null)
            return skins.get(0);
        return skins.get(Inst.index);
    }

    public SkinSelectScreen() {
        this.index = 0;

        //读取本地化内容
        if(ConfigHelper.skinId != null) {
            for (Skin skin : skins) {
                if (Objects.equals(skin.id, ConfigHelper.skinId)) {
                    this.index = skin.index;
                }
            }
        }

        refresh();

        this.leftHb = new Hitbox(70.0F * Settings.scale, 70.0F * Settings.scale);
        this.rightHb = new Hitbox(70.0F * Settings.scale, 70.0F * Settings.scale);
    }

    public void refresh() {
        Skin skin = skins.get(this.index);

        ConfigHelper.skinId = skin.id;
        ConfigHelper.saveId(ConfigHelper.skinId);

        this.curName = skin.name;
        this.nextName = skins.get(nextIndex()).name;

        HanninaImageMaster.refreshSkin();
        if (AbstractDungeon.player instanceof Hannina) {
            ((Hannina) AbstractDungeon.player).refreshSkin();
        }
    }

    public int prevIndex() {
        return (this.index - 1 < 0) ? (skins.size() - 1) : (this.index - 1);
    }

    public int nextIndex() {
        return (this.index + 1 > skins.size() - 1) ? 0 : (this.index + 1);
    }

    public void update() {
        float centerX = SKIN_SELECT_POSITION_X;
        float centerY = SKIN_SELECT_POSITION_Y;
        this.leftHb.move(centerX - 200.0F * Settings.scale, centerY);
        this.rightHb.move(centerX + 200.0F * Settings.scale, centerY);
        updateInput();
    }

    private void updateInput() {
        if (CardCrawlGame.chosenCharacter == Enums.HANNINA_CLASS) {
            this.leftHb.update();
            this.rightHb.update();
            if (this.leftHb.clicked) {
                this.leftHb.clicked = false;
                CardCrawlGame.sound.play("UI_CLICK_1");
                this.index = prevIndex();
                refresh();
            }
            if (this.rightHb.clicked) {
                this.rightHb.clicked = false;
                CardCrawlGame.sound.play("UI_CLICK_1");
                this.index = nextIndex();
                refresh();
            }
            if (InputHelper.justClickedLeft) {
                if (this.leftHb.hovered)
                    this.leftHb.clickStarted = true;
                if (this.rightHb.hovered)
                    this.rightHb.clickStarted = true;
            }
        }
    }

    public void render(SpriteBatch sb) {
        float centerX = SKIN_SELECT_POSITION_X;
        float centerY = SKIN_SELECT_POSITION_Y;
//        renderSkin(sb, centerX, centerY);
//        FontHelper.renderFontCentered(sb, FontHelper.cardTitleFont, uiStrings.TEXT[0], centerX, centerY + 250.0F * Settings.scale, Color.WHITE, 1.25F);
        Color color = Settings.GOLD_COLOR.cpy();
        color.a /= 2.0F;
        float dist = 100.0F * Settings.scale;
        FontHelper.renderFontCentered(sb, FontHelper.cardTitleFont, this.curName, centerX, centerY, Settings.GOLD_COLOR);
        FontHelper.renderFontCentered(sb, FontHelper.cardTitleFont, this.nextName, centerX + dist * 1.5F, centerY - dist, color);
        if (this.leftHb.hovered) {
            sb.setColor(Color.LIGHT_GRAY);
        } else {
            sb.setColor(Color.WHITE);
        }
        sb.draw(ImageMaster.CF_LEFT_ARROW, this.leftHb.cX - 24.0F, this.leftHb.cY - 24.0F, 24.0F, 24.0F, 48.0F, 48.0F, Settings.scale*1.5F, Settings.scale*1.5F, 0.0F, 0, 0, 48, 48, false, false);
        if (this.rightHb.hovered) {
            sb.setColor(Color.LIGHT_GRAY);
        } else {
            sb.setColor(Color.WHITE);
        }
        sb.draw(ImageMaster.CF_RIGHT_ARROW, this.rightHb.cX - 24.0F, this.rightHb.cY - 24.0F, 24.0F, 24.0F, 48.0F, 48.0F, Settings.scale*1.5F, Settings.scale*1.5F, 0.0F, 0, 0, 48, 48, false, false);
        this.rightHb.render(sb);
        this.leftHb.render(sb);
    }

    public void renderSkin(SpriteBatch sb, float x, float y) {
        Texture t = HanninaImageMaster.getSkinImg("red");

        sb.draw(t,
                x - t.getWidth() * Settings.scale / 2.0F,
                y,
                t.getWidth() * Settings.scale / 2.0F, t.getHeight() * Settings.scale / 2.0F,
                t.getWidth() * Settings.scale, t.getHeight() * Settings.scale,
                1, 1, 0,
                0, 0,
                t.getWidth(), t.getHeight(),
                false, false);
    }

    public void renderBackground(SpriteBatch sb) {
        Texture bgTexture = HanninaImageMaster.getSkinImg("portrait");
        sb.draw(bgTexture,
                Settings.WIDTH / 2F - bgTexture.getWidth() * Settings.scale / 2F,
                Settings.HEIGHT / 2F - bgTexture.getHeight() * Settings.scale / 2F,
                bgTexture.getWidth() * Settings.scale / 2F, bgTexture.getHeight() * Settings.scale / 2F,
                bgTexture.getWidth() * Settings.scale, bgTexture.getHeight() * Settings.scale,
                1, 1, 0,
                0, 0,
                bgTexture.getWidth(), bgTexture.getHeight(),
                false, false);
    }

    public static void init(){
        String currentGroup = null;
        int skinIndex = 0;

        for (int i = 1; i < uiStrings.TEXT.length; i++) {
            String item = uiStrings.TEXT[i];

            switch (item) {
                case "SFW SKIN":
                    currentGroup = "SFW SKIN";
                    break;
                case "NSFW SKIN":
                    currentGroup = "NSFW SKIN";
                    break;
                default:
                    if (currentGroup != null && i + 1 < uiStrings.TEXT.length) {

                        if(currentGroup.equals("NSFW SKIN") && !ConfigHelper.nsfw){
                            break;
                        }

                        String name = item;
                        String id = uiStrings.TEXT[i + 1];


                        skins.add(new Skin(skinIndex, name, id));

                        i++; // 跳过ID
                        skinIndex++;
                    }
                    break;
            }
        }

        Inst = new SkinSelectScreen();
    }

    public static class Skin {
        public int index;

        public String name;
        public String id;

        public Skin(int index, String name, String id) {
            this.index = index;
            this.name = name;
            this.id = id;
        }
    }

    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("hannina:SkinSelect");

    static {
        skins = new ArrayList<>();
    }
}