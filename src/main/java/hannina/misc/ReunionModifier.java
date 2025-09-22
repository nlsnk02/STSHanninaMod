package hannina.misc;

import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.colorless.Madness;
import com.megacrit.cardcrawl.cards.red.Exhume;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import hannina.modcore.Core;
import hannina.patches.cards.UnionMechanicsPatch;
import hannina.utils.ModHelper;

import java.util.*;
import java.util.stream.Collectors;

public class ReunionModifier extends AbstractCardModifier implements
        UnionMechanicsPatch.CardModPreRenderPatch.PreCardRenderModifier {
    // 视觉相关常量
    private static final int THREAD_SIZE = 7;
    private static final float DRAW_SIZE = THREAD_SIZE, DRAW_OFFSET = DRAW_SIZE / 2f;
    private static final Color lineColor = Color.WHITE.cpy();
    private static final float HB_W = 300.0F * Settings.scale, HB_H = 420.0F * Settings.scale;
    private static final float
            STACK_SCALING = 0.9f,
            START_SCALING = 0.7f,
            START_OFFSET = 60f * Settings.scale,
            STACK_OFFSET = 65f * Settings.scale,
            STACK_OFFSET_ACC = 350f * Settings.scale;

    // 位置跟踪
    private transient int index = 0;

    public transient ArrayList<AbstractCard> union;

    private String[] saveFile;

    private boolean upgraded = false;

    public ReunionModifier() {
        union = new ArrayList<>();
        saveFile = new String[0];
    }

    public ReunionModifier(ArrayList<AbstractCard> union) {
        this.union = new ArrayList<>(union);
        onSave();
    }


    @Override
    public boolean shouldApply(AbstractCard card) {
        if (union.isEmpty())
            return false;

        for (AbstractCardModifier modifier : CardModifierManager.modifiers(card))
            if (modifier instanceof ReunionModifier) {
                return false;
            }
        return true;
    }

    @Override
    public void onUpdate(AbstractCard card) {

        //更新union和union里的牌的modifier
        ArrayList<AbstractCard> unionCopy = new ArrayList<>(union);
        for (int i = 0; i < unionCopy.size(); i++) {
            AbstractCard c = unionCopy.get(i);
            if (c.cardID.equals(card.cardID)) {
                union.set(i, card);
            }
        }

        for (AbstractCard c : union) {
            if (!c.cardID.equals(card.cardID)) {
                CardModifierManager.removeModifiersById(c, "ReunionModifier", true);
                c.lighten(true);
                c.update();
            }
        }

        if (card.upgraded && !upgraded) {
            upgraded = true;
            for (AbstractCard c : unionCopy) {
                if (!c.upgraded && c.canUpgrade())
                    c.upgrade();
            }
        }
    }

    @Override
    public AbstractCardModifier makeCopy() {
        //如果使用的是无参构造体，说明是进入游戏时调用的方法，需要主动调用on load
        if (union.isEmpty()) {
//            ModHelper.logger.info("===========saveFile = {}===========", (Object) saveFile);
            if (saveFile.length > 0) {
                onLoad(saveFile, upgraded);
            } else union.add(new Madness());
        }

        return new ReunionModifier(union.stream().map(AbstractCard::makeCopy).collect(Collectors.toCollection(ArrayList::new)));
    }

    @Override
    public String identifier(AbstractCard card) {
        return "ReunionModifier";
    }

    public void onSave() {
        if (union != null && !union.isEmpty())
            saveFile = union.stream().map(c -> c.cardID).toArray(String[]::new);
        else saveFile = new String[0];
    }

    public void onLoad(String[] abstractCards, boolean upgraded) {
        if (abstractCards != null) {
            union = Arrays.stream(abstractCards).map(CardLibrary::getCard).collect(Collectors.toCollection(ArrayList::new));
            if (upgraded)
                union.forEach(c -> {
                    if (!c.upgraded && c.canUpgrade())
                        c.upgrade();
                });
        }
    }

    //---渲染逻辑---
    private void updatePositions(AbstractCard mainCard) {
        boolean hover = mainCard == Core.scrollBarForUnion.lastCard;

        //更新index
        if (hover) {
            index = Core.scrollBarForUnion.index;
            if (index >= union.size()) {
                index = union.size() - 1;
                Core.scrollBarForUnion.index = union.size() - 1;
            }
        } else index = 0;

        // 计算相对位置
        int flag = 1;
        ArrayList<Float> offsets = new ArrayList<>();
        ArrayList<Float> scales = new ArrayList<>();
        for (AbstractCard c : union) {
            if (c.cardID.equals(mainCard.cardID)) continue;

            float tmp = (float) Math.pow(STACK_SCALING, flag - 1) * START_SCALING;

            if (flag == index) offsets.add(STACK_OFFSET_ACC * tmp);
            else offsets.add(STACK_OFFSET * tmp);

            scales.add(tmp);

            flag++;
        }
        for (int i = 1; i < offsets.size(); i++) {
            offsets.set(i, offsets.get(i) + offsets.get(i - 1));
        }
        Collections.reverse(offsets);
        Collections.reverse(scales);

        flag = 0;
        for (AbstractCard uCard : union) {
            if (uCard.cardID.equals(mainCard.cardID)) continue;

            // 缩放效果
            uCard.drawScale = scales.get(flag) * mainCard.drawScale;
            uCard.angle = mainCard.angle;

            // 悬停检测
            float offset = offsets.get(flag);
            if (hover) {
                offset *= 1.2f; // 悬停时增加偏移
            }

            // 计算目标位置
            uCard.target_x = mainCard.current_x +
                    MathUtils.sinDeg(-uCard.angle) * mainCard.drawScale * (offset + START_OFFSET);
            uCard.target_y = mainCard.current_y +
                    MathUtils.cosDeg(uCard.angle) * mainCard.drawScale * (offset + START_OFFSET);

            updateCardMovement(uCard);

            flag++;
        }
    }


    private void updateCardMovement(AbstractCard card) {
//        if (Settings.FAST_MODE) {
//            card.current_x = MathHelper.fadeLerpSnap(card.current_x, card.target_x);
//            card.current_y = MathHelper.fadeLerpSnap(card.current_y, card.target_y);
//        } else {
//            card.current_x = MathHelper.cardLerpSnap(card.current_x, card.target_x);
//            card.current_y = MathHelper.cardLerpSnap(card.current_y, card.target_y);
//        }
        card.current_x = card.target_x;
        card.current_y = card.target_y;

        card.hb.move(card.current_x, card.current_y);
        card.hb.resize(HB_W * card.drawScale, HB_H * card.drawScale);
    }


    @Override
    public void preRender(AbstractCard card, SpriteBatch sb) {
        // 更新联合卡牌位置
        updatePositions(card);

        for (AbstractCard uCard : union) {
            if (uCard.cardID.equals(card.cardID)) continue;

            lineColor.a = uCard.transparency;
            sb.setColor(lineColor);

            uCard.render(sb, false);
        }
    }
}
