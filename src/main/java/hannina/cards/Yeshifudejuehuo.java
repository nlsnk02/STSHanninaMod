package hannina.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.watcher.ChooseOneAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hannina.actions.ChangeCharColorAction;
import hannina.fantasyCard.*;
import hannina.misc.SaveData;
import hannina.utils.ChangePlayerModel;
import hannina.utils.UnionManager;

import java.util.ArrayList;

public class Yeshifudejuehuo extends AbstractHanninaCard {
    public Yeshifudejuehuo() {
        super(Yeshifudejuehuo.class.getSimpleName(), 0, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
    }

    @Override
    public ArrayList<AbstractCard> setUnion() {
        ArrayList<AbstractCard> unions = new ArrayList<>();
        unions.add(UnionManager.getRamdomCard(c ->
                c.color == CardColor.RED && c.type == CardType.POWER));
        unions.add(UnionManager.getRamdomCard(c ->
                c.color == CardColor.GREEN && c.type == CardType.POWER));
        unions.add(UnionManager.getRamdomCard(c ->
                c.color == CardColor.BLUE && c.type == CardType.POWER));
        unions.add(UnionManager.getRamdomCard(c ->
                c.color == CardColor.PURPLE && c.type == CardType.POWER));
        return unions;
    }

    @Override
    public void triggerWhenDrawn() {
        addToBot(new GainEnergyAction(1));
    }

    private class TempAction extends AbstractGameAction {

        private String name = "Hannina";
        private AbstractCard card;

        public TempAction(String name, AbstractCard card) {
            this.name = name;
            this.card = card;
        }

        @Override
        public void update() {
            ChangePlayerModel.ChangeSkin(name);
            SaveData.saveData.skin = name;
            ArrayList<AbstractCard> list = new ArrayList<>(AbstractDungeon.player.masterDeck.group);
            for(AbstractCard c : list) {
                if(c.uuid.equals(card.uuid)) {
                    AbstractDungeon.player.masterDeck.removeCard(c);
                }
            }
            this.isDone = true;
        }
    };

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        ArrayList<AbstractCard> stanceChoices = new ArrayList<>();
        stanceChoices.add(new RedColorCard(new TempAction("RED", this)));
        stanceChoices.add(new GreenColorCard(new TempAction("GREEN", this)));
        stanceChoices.add(new BlueColorCard(new TempAction("BLUE", this)));
        stanceChoices.add(new PurpleColorCard(new TempAction("PURPLE", this)));
        addToBot(new ChooseOneAction(stanceChoices));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBaseCost(0);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new Yeshifudejuehuo();
    }
}