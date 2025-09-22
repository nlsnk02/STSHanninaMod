package hannina.potions;

import basemod.ReflectionHacks;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.MathHelper;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.vfx.FlashPotionEffect;
import hannina.relics.Maozhizhencang;
import hannina.utils.ModHelper;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;

public abstract class AbstractHanninaPotion extends AbstractPotion {
    protected Texture potionImg;

    protected Texture potionOutlineImg;

    public Color potionImageColor;

    public AbstractHanninaPotion(String name, String className, AbstractPotion.PotionRarity rarity) {
        super(name, ModHelper.makeID(className), rarity, AbstractPotion.PotionSize.S, AbstractPotion.PotionColor.STRENGTH);
        this.potionImageColor = Color.WHITE.cpy();
        this.potionImg = new Texture(ModHelper.getImgPath("potions/" +className + ".png"));
        this.potionOutlineImg = new Texture(ModHelper.getImgPath("potions/" +className + ".png"));
    }

    @Override
    public void render(SpriteBatch sb) {
        if (this.potionImg != null) {
            float angle = ((Float) ReflectionHacks.getPrivate(this, AbstractPotion.class, "angle")).floatValue();
            sb.setColor(this.potionImageColor);
            sb.draw(this.potionImg, this.posX - 32.0F, this.posY - 32.0F, 32.0F, 32.0F, 64.0F, 64.0F, this.scale, this.scale, angle, 0, 0, 64, 64, false, false);
        }
        ArrayList<FlashPotionEffect> effects = ReflectionHacks.getPrivate(this, AbstractPotion.class, "effect");
        Iterator<FlashPotionEffect> var3 = effects.iterator();
        while (var3.hasNext()) {
            FlashPotionEffect e = var3.next();
            e.render(sb, this.posX, this.posY);
        }
        if (this.hb != null)
            this.hb.render(sb);
    }

    @Override
    public void renderLightOutline(SpriteBatch sb) {
        if (this.potionOutlineImg != null) {
            float angle = ((Float) ReflectionHacks.getPrivate(this, AbstractPotion.class, "angle")).floatValue();
            sb.setColor(Settings.QUARTER_TRANSPARENT_BLACK_COLOR);
            sb.draw(this.potionOutlineImg, this.posX - 32.0F, this.posY - 32.0F, 32.0F, 32.0F, 64.0F, 64.0F, this.scale, this.scale, angle, 0, 0, 64, 64, false, false);
        }
    }

    @Override
    public void renderOutline(SpriteBatch sb) {
        if (this.potionOutlineImg != null) {
            float angle = ((Float) ReflectionHacks.getPrivate(this, AbstractPotion.class, "angle")).floatValue();
            sb.setColor(Settings.HALF_TRANSPARENT_BLACK_COLOR);
            sb.draw(this.potionOutlineImg, this.posX - 32.0F, this.posY - 32.0F, 32.0F, 32.0F, 64.0F, 64.0F, this.scale, this.scale, angle, 0, 0, 64, 64, false, false);
        }
    }

    @Override
    public void renderOutline(SpriteBatch sb, Color c) {
        if (this.potionOutlineImg != null) {
            float angle = ((Float) ReflectionHacks.getPrivate(this, AbstractPotion.class, "angle")).floatValue();
            sb.setColor(c);
            sb.draw(this.potionOutlineImg, this.posX - 32.0F, this.posY - 32.0F, 32.0F, 32.0F, 64.0F, 64.0F, this.scale, this.scale, angle, 0, 0, 64, 64, false, false);
        }
    }

    @Override
    public void labRender(SpriteBatch sb) {
        try {
            Method updateFlash = AbstractPotion.class.getDeclaredMethod("updateFlash");
            updateFlash.setAccessible(true);
            Method updateEffect = AbstractPotion.class.getDeclaredMethod("updateEffect");
            updateEffect.setAccessible(true);
            updateFlash.invoke(this);
            updateEffect.invoke(this);
        } catch (IllegalAccessException | java.lang.reflect.InvocationTargetException | NoSuchMethodException var4) {
            ReflectiveOperationException e = var4;
            e.printStackTrace();
        }
        if (this.hb.hovered) {
            TipHelper.queuePowerTips(150.0F * Settings.scale, 800.0F * Settings.scale, this.tips);
            this.scale = 1.5F * Settings.scale;
        } else {
            this.scale = MathHelper.scaleLerpSnap(this.scale, 1.2F * Settings.scale);
        }
        renderOutline(sb, this.labOutlineColor);
        render(sb);
    }

    @Override
    public void shopRender(SpriteBatch sb) {
        try {
            Method updateFlash = AbstractPotion.class.getDeclaredMethod("updateFlash");
            updateFlash.setAccessible(true);
            Method updateEffect = AbstractPotion.class.getDeclaredMethod("updateEffect");
            updateEffect.setAccessible(true);
            updateFlash.invoke(this);
            updateEffect.invoke(this);
        } catch (IllegalAccessException | java.lang.reflect.InvocationTargetException | NoSuchMethodException var4) {
            ReflectiveOperationException e = var4;
            e.printStackTrace();
        }
        if (this.hb.hovered) {
            TipHelper.queuePowerTips(InputHelper.mX + 50.0F * Settings.scale, InputHelper.mY + 50.0F * Settings.scale, this.tips);
            this.scale = 1.5F * Settings.scale;
        } else {
            this.scale = MathHelper.scaleLerpSnap(this.scale, 1.2F * Settings.scale);
        }
        renderOutline(sb);
        render(sb);
    }
}