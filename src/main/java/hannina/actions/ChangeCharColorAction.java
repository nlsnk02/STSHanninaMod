package hannina.actions;


import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import hannina.misc.OnChangeColorSubscriber;
import hannina.modcore.Enums;
import hannina.powers.AntiUnionPower;
import hannina.powers.FusionPower;
import hannina.powers.UnionPower;
import hannina.utils.ModHelper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/*
所有改变角色形态全部通过这个action

action包括三个部分：
* 修改或给予玩家buff（每个形态特有的buff）
* 修改三堆里牌的形态
* 触发接口

 */
public class ChangeCharColorAction extends AbstractGameAction {
    public static final String[] actionTEXT;

    //在modcore中更新
    public static HashSet<AbstractCard.CardColor> cardColorsEnteredThisTurn = new HashSet<>();

    static {
        UIStrings actionUiStrings = CardCrawlGame.languagePack.getUIString("hannina:ChangeCharColorAction");
        actionTEXT = actionUiStrings.TEXT;
    }

    AbstractCard.CardColor color;

    public ChangeCharColorAction(AbstractCard.CardColor color) {
        this.color = color;
    }

    @Override
    public void update() {
        if(AbstractDungeon.player.hasPower(AntiUnionPower.POWER_ID)){
            AbstractDungeon.player.getPower(AntiUnionPower.POWER_ID).flash();
            this.isDone = true;
            return;
        }

        if(AbstractDungeon.player.hasPower(FusionPower.POWER_ID) && color != Enums.HanninaColor){
            AbstractDungeon.player.getPower(FusionPower.POWER_ID).flash();
            this.isDone = true;
            return;
        }

        //更改buff
        if(color == Enums.HanninaColor){
            addToTop(new RemoveSpecificPowerAction(AbstractDungeon.player, AbstractDungeon.player,
                    UnionPower.POWER_ID));
        }else {
            if (AbstractDungeon.player.hasPower(UnionPower.POWER_ID)) {
                UnionPower p = (UnionPower) AbstractDungeon.player.getPower(UnionPower.POWER_ID);

                p.leaveColor(p.color);
                p.enterColor(this.color);
            } else addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player,
                    new UnionPower(AbstractDungeon.player, color)));
        }

        //更改牌
        ModHelper.changeCardUnion(color, false);

        //触发接口
        ChangeCharColorAction.cardColorsEnteredThisTurn.add(color);

        AbstractDungeon.player.powers.forEach(p -> {
            if(p instanceof OnChangeColorSubscriber) {
                ((OnChangeColorSubscriber) p).onChangeColor(this.color);
            }
        });

        AbstractDungeon.player.relics.forEach(r -> {
            if(r instanceof OnChangeColorSubscriber) {
                ((OnChangeColorSubscriber) r).onChangeColor(this.color);
            }
        });

        this.isDone = true;
    }
}
