package hannina.fantasyCard;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import hannina.effects.PlayGiFEffect;
import hannina.relics.Miaodan;

public class Chaojuehajimi extends AbstractHanninaCard {

    public Chaojuehajimi() {
        super(Chaojuehajimi.class.getSimpleName(), 3, CardType.ATTACK, CardRarity.SPECIAL, CardTarget.ALL_ENEMY);
        this.isMultiDamage = true;
        this.baseDamage = 1;
        this.magicNumber = this.baseMagicNumber = 3;
        this.exhaust = true;
    }
    public static int startGIF;

    @Override
    public void applyPowers() {
        int n = 0;
        if (AbstractDungeon.player.hasPower(VigorPower.POWER_ID)) {
            n = AbstractDungeon.player.getPower(VigorPower.POWER_ID).amount * (this.baseMagicNumber - 1);
        }
        this.baseDamage += n;
        super.applyPowers();
        this.baseDamage -= n;
        this.isDamageModified = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        applyPowers();
        for (int i = 0; i < 5; i++)
            this.addToBot(new DamageAllEnemiesAction(p, this.multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.BLUNT_HEAVY));
       /* if (AbstractDungeon.player.hasRelic(Miaodan.ID)){
            Chaojuehajimi.startGIF=4;
            AbstractDungeon.effectList.add(new PlayGiFEffect(4));

        }*/
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(1);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new Chaojuehajimi();
    }
}