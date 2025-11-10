package hannina.cards;

import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hannina.actions.ChooseColor2EnterAction;
import hannina.fantasyCard.AbstractHanninaCard;
import hannina.misc.ReunionModifier;
import hannina.modcore.Enums;
import hannina.utils.ModHelper;
import hannina.utils.UnionManager;

import java.util.ArrayList;

public class YZYHSXTBJHLM extends AbstractHanninaCard {
    public static final String[] actionTEXT;

    static {
        UIStrings actionUiStrings = CardCrawlGame.languagePack.getUIString("hannina:YZYHSXTBJHLM");
        actionTEXT = actionUiStrings.TEXT;
    }

    @Override
    public float getTitleFontSize() {
        return 15F;
    }

    public YZYHSXTBJHLM() {
        super(YZYHSXTBJHLM.class.getSimpleName(), 0, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.NONE);
        this.magicNumber = this.baseMagicNumber = 3;
        this.tags.add(Enums.UnionCard);
    }

    @Override
    public ArrayList<AbstractCard> setUnion() {
        ArrayList<AbstractCard> unions = new ArrayList<>();
        unions.add(UnionManager.getRamdomCard(c -> c.color == CardColor.RED));
        unions.add(UnionManager.getRamdomCard(c -> c.color == CardColor.GREEN));
        unions.add(UnionManager.getRamdomCard(c -> c.color == CardColor.BLUE));
        unions.add(UnionManager.getRamdomCard(c -> c.color == CardColor.PURPLE));
        return unions;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                ArrayList<AbstractCardModifier> list = CardModifierManager.getModifiers(YZYHSXTBJHLM.this, "ReunionModifier");
                addToBot(new ChooseColor2EnterAction());
                addToBot(new AbstractGameAction() {
                    @Override
                    public void update() {
                        if (ModHelper.getPlayerColor() == CardColor.RED) {
                            if (list.size() > 0) {
                                ReunionModifier mod = (ReunionModifier) list.get(0);
                                for (AbstractCard card : mod.union) {
                                    if (card.color == CardColor.RED) {
                                        card.modifyCostForCombat(-1);
                                    }
                                }
                            }
                        }
                        this.isDone = true;
                    }
                });
                this.isDone = true;
            }
        });
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new YZYHSXTBJHLM();
    }
}