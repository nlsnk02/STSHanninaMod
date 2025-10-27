package hannina.effects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

import static hannina.fantasyCard.Chaojuehajimi.startGIF;
import static hannina.utils.MonsterDieThisTurnManager.amount;

public class PlayGiFEffect extends AbstractGameEffect {
    private  float elapsedTime=0;
    private int start;
    private int end;
    public PlayGiFEffect(int start,int end,float duration ){
        this.duration=duration;
        this.start=start;
        this.end=end;
    }
    @Override
    public void render(SpriteBatch spriteBatch) {

    } @Override
    public void update() {

        this.duration -= Gdx.graphics.getDeltaTime();
        elapsedTime += Gdx.graphics.getDeltaTime();

        // 每隔 interval 秒执行一次操作
        if (elapsedTime >=duration/(start-end)) {
            // 在这里执行你的操作
            if (startGIF>end){
                startGIF--;
            }
            elapsedTime = 0f; // 重置计时器
        }
        if (this.duration < 0.0F) {
            startGIF=0;
            this.isDone = true;
        }

    }

    @Override
    public void dispose() {

    }
}
