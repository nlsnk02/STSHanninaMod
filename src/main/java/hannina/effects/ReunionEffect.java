package hannina.effects;

import basemod.ReflectionHacks;
import basemod.patches.com.megacrit.cardcrawl.core.CardCrawlGame.ApplyScreenPostProcessor;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.helpers.ShaderHelper;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import hannina.utils.ModHelper;

public class ReunionEffect extends AbstractGameEffect {
    private AbstractCard source;
    private AbstractCard target;
    private static ShaderProgram colorShader;
    boolean shaderError = false;

    public ReunionEffect(AbstractCard source, AbstractCard target) {
        this.startingDuration = this.duration = 1.0F;
        this.source = source;
        this.target = target;
    }

    public void update() {
        this.duration -= Gdx.graphics.getDeltaTime();
        if (!this.source.fadingOut && this.duration < 0.7F)
            this.source.fadingOut = true;
        if (this.duration < 0.0F) {
            this.isDone = true;
            this.source.resetAttributes();
        }
        this.source.update();
    }

    public void render(SpriteBatch sb) {
        if (this.shaderError)
            return;
        if (colorShader == null || !colorShader.isCompiled()) {
            colorShader = new ShaderProgram(Gdx.files.internal(ModHelper.getShaderPath("default.vs")).readString(), Gdx.files.internal(ModHelper.getShaderPath("hallucinating.fs")).readString());
            if (!colorShader.isCompiled()) {
                this.isDone = true;
                this.shaderError = true;
                return;
            }
        }
        sb.end();
        TextureRegion region = getCardAsTexture(sb);
        sb.setShader(colorShader);
        sb.setColor(Color.WHITE);
        sb.begin();
        colorShader.setUniformf("u_time", this.startingDuration - this.duration);
        sb.draw(region, 0.0F, 0.0F);
        ShaderHelper.setShader(sb, ShaderHelper.Shader.DEFAULT);
    }

    public void dispose() {}

    private TextureRegion getCardAsTexture(SpriteBatch sb) {
        FrameBuffer buffer = ReflectionHacks.getPrivate(null, ApplyScreenPostProcessor.class, "secondaryFrameBuffer");
        buffer.begin();
        sb.begin();
        Gdx.gl.glClearColor(0.0F, 0.0F, 0.0F, 0.0F);
        Gdx.gl.glClear(16384);
        sb.setColor(Color.WHITE);
        sb.setBlendFunction(1, 0);
        this.source.render(sb);
        sb.setBlendFunction(770, 771);
        sb.end();
        buffer.end();
        return ReflectionHacks.getPrivate(null, ApplyScreenPostProcessor.class, "secondaryFboRegion");
    }
}