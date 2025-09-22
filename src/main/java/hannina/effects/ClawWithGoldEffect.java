package hannina.effects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.combat.AnimatedSlashEffect;

public class ClawWithGoldEffect extends AbstractGameEffect {
    private float x;
    private float y;
    private Color color2;
    private int goldAmt;

    public ClawWithGoldEffect(float x, float y, Color color1, Color color2, int goldAmt) {
        this.x = x;
        this.y = y;
        this.color = color1;
        this.color2 = color2;
        this.goldAmt = goldAmt;
        this.startingDuration = 0.1F;
        this.duration = this.startingDuration;
    }

    public void update() {
        if (MathUtils.randomBoolean()) {
            CardCrawlGame.sound.playA("ATTACK_DAGGER_5", MathUtils.random(0.0F, -0.3F));
        } else {
            CardCrawlGame.sound.playA("ATTACK_DAGGER_6", MathUtils.random(0.0F, -0.3F));
        }

        AbstractDungeon.effectsQueue.add(new AnimatedSlashEffect(this.x + 35.0F, this.y + 35.0F, 150.0F, -150.0F, -135.0F, this.color, this.color2));
        AbstractDungeon.effectsQueue.add(new AnimatedSlashEffect(this.x, this.y, 150.0F, -150.0F, -135.0F, this.color, this.color2));
        AbstractDungeon.effectsQueue.add(new AnimatedSlashEffect(this.x - 35.0F, this.y - 35.0F, 150.0F, -150.0F, -135.0F, this.color, this.color2));

        for(int i=0;i<this.goldAmt;i++) {
            AbstractDungeon.effectsQueue.add(new TouchPickupGoldEffect(this.x, this.y));
        }

        this.isDone = true;
    }

    public void render(SpriteBatch sb) {
    }

    public void dispose() {
    }
}
