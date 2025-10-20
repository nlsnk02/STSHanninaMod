package hannina.fantasyCard;

import basemod.abstracts.CustomCard;
import basemod.helpers.CardModifierManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hannina.misc.ReunionModifier;
import hannina.modcore.Enums;
import hannina.utils.ConfigHelper;
import hannina.utils.ModHelper;

import java.util.ArrayList;

public abstract class AbstractHanninaCard extends CustomCard {
    private static final TextureAtlas cardAtlas = new TextureAtlas(Gdx.files.internal("cards/cards.atlas"));

    public CardStrings cardStrings;

    public AbstractHanninaCard(
            String NAME,
            int COST,
            CardType TYPE,
            CardRarity RARITY,
            CardTarget TARGET
    ) {
        this(NAME, COST, TYPE, RARITY, TARGET, Enums.HanninaColor);
    }

    public AbstractHanninaCard(
            String NAME,
            int COST,
            CardType TYPE,
            CardRarity RARITY,
            CardTarget TARGET,
            CardColor color
    ) {
        super("hannina:" + NAME, getCardStrings(NAME).NAME, getPicPath(NAME, TYPE),
                COST, getCardStrings(NAME).DESCRIPTION, TYPE, color, RARITY, TARGET);
        cardStrings = getCardStrings(NAME);
    }

    /*
    自动加载卡牌的卡图
     */
    public static String getPicPath(String name, CardType type) {
        if (Gdx.files.internal(ModHelper.getImgPath("cards/" + ConfigHelper.skinId + "/" + name + ".png")).exists())
            return ModHelper.getImgPath("cards/" + ConfigHelper.skinId + "/" + name + ".png");

        if (Gdx.files.internal(ModHelper.getImgPath("cards/" + name + ".png")).exists())
            return ModHelper.getImgPath("cards/" + name + ".png");

        if (type == CardType.ATTACK)
            return ModHelper.getImgPath("cards/attack.png");
        else if (type == CardType.SKILL)
            return ModHelper.getImgPath("cards/skill.png");
        else if (type == CardType.POWER)
            return ModHelper.getImgPath("cards/power.png");
        else
            return ModHelper.getImgPath("cards/temp.png");
    }

    public static CardStrings getCardStrings(String name) {
        return CardCrawlGame.languagePack.
                getCardStrings("hannina:" + name);
    }

    public void steal(AbstractCard c) {
        String img = c.assetUrl;
        this.portrait = AbstractHanninaCard.cardAtlas.findRegion(img);
        this.assetUrl = img;
    }

    @Override
    public abstract void use(AbstractPlayer p, AbstractMonster m);

    @Override
    public abstract void upgrade();

    /*
    为卡牌设置他的复合牌，方法在一张牌加入战斗或进入卡牌奖励时自动执行。
    不过想要让一张牌被识别为复合牌，依旧需要主动设置tag。
     */
    public ArrayList<AbstractCard> setUnion() {
        return null;
    }
}
