package hannina.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hannina.fantasyCard.AbstractHanninaCard;
import hannina.powers.MMRuizhanPower;
import hannina.utils.UnionManager;

import java.util.ArrayList;

public class MMRuizhan extends AbstractHanninaCard {

    public static int count = 0;

    public MMRuizhan() {
        super(MMRuizhan.class.getSimpleName(), 0, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        this.damage = this.baseDamage = 5;
        this.magicNumber = this.baseMagicNumber = 2;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn),
                AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {

                MMRuizhan.count++;

                new ArrayList<>(AbstractDungeon.player.drawPile.group).stream()
                        .filter(card -> card instanceof MMRuizhan)
                        .forEach(c -> {
                            if (AbstractDungeon.player.hand.size() >= 10) {
                                AbstractDungeon.player.drawPile.moveToDiscardPile(c);
                                AbstractDungeon.player.createHandIsFullDialog();
                            } else {
                                c.unhover();
                                c.lighten(true);
                                c.setAngle(0.0F);
                                c.drawScale = 0.12F;
                                c.targetDrawScale = 0.75F;
                                c.current_x = CardGroup.DRAW_PILE_X;
                                c.current_y = CardGroup.DRAW_PILE_Y;
                                c.flash();
                                AbstractDungeon.player.drawPile.removeCard(c);
                                AbstractDungeon.player.hand.addToTop(c);
                                AbstractDungeon.player.hand.refreshHandLayout();
                                AbstractDungeon.player.hand.applyPowers();
                            }
                        });
                isDone = true;
            }
        });
        addToBot(new ApplyPowerAction(p,p,new MMRuizhanPower(p, this.magicNumber), this.magicNumber));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(2);
            upgradeMagicNumber(1);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new MMRuizhan();
    }
}