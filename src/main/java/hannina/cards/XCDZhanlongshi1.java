//package hannina.cards;
//
//import basemod.abstracts.CustomSavable;
//import com.megacrit.cardcrawl.actions.AbstractGameAction;
//import com.megacrit.cardcrawl.cards.AbstractCard;
//import com.megacrit.cardcrawl.cards.DamageInfo;
//import com.megacrit.cardcrawl.characters.AbstractPlayer;
//import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
//import com.megacrit.cardcrawl.helpers.GetAllInBattleInstances;
//import com.megacrit.cardcrawl.monsters.AbstractMonster;
//import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;
//import hannina.fantasyCard.AbstractHanninaCard;
//import hannina.utils.ModHelper;
//
//import java.util.ArrayList;
//import java.util.UUID;
//
//public class XCDZhanlongshi1 extends AbstractHanninaCard implements CustomSavable<ArrayList<Integer>> {
//
//    private ArrayList<Integer> savedata;
//
//    public XCDZhanlongshi1(int timesUpgraded, ArrayList<Integer> savedata) {
//        super(XCDZhanlongshi1.class.getSimpleName(), 2, CardType.ATTACK, CardRarity.RARE, CardTarget.ENEMY);
//        this.damage = this.baseDamage = 14;
//        this.magicNumber = this.baseMagicNumber = 2;
//        this.timesUpgraded = timesUpgraded;
//
//        this.savedata = new ArrayList<>();
//        this.savedata.add(0);
//        if (savedata != null)
//            this.savedata = new ArrayList<>(savedata);
//    }
//
//    public XCDZhanlongshi1() {
//        this(0, null);
//    }
//
//    @Override
//    public void use(AbstractPlayer p, AbstractMonster m) {
//
//        addToBot(new AbstractGameAction() {
//            private DamageInfo info;
//            private UUID uuid;
//
//            {
//                this.info = new DamageInfo(p, XCDZhanlongshi1.this.damage, XCDZhanlongshi1.this.damageTypeForTurn);
//                this.setValues(m, info);
//                this.actionType = ActionType.DAMAGE;
//                this.duration = 0.1F;
//                this.uuid = XCDZhanlongshi1.this.uuid;
//            }
//
//            @Override
//            public void update() {
//                if (this.duration == 0.1F && this.target != null) {
//                    AbstractDungeon.effectList.add(new FlashAtkImgEffect(this.target.hb.cX, this.target.hb.cY, AttackEffect.SLASH_HORIZONTAL));
//                    this.target.damage(this.info);
//                    if ((this.target.isDying || this.target.currentHealth <= 0) && !this.target.halfDead && !this.target.hasPower("Minion")) {
//                        for(AbstractCard c : AbstractDungeon.player.masterDeck.group) {
//                            if (c.uuid.equals(this.uuid)) {
//                                XCDZhanlongshi1 x = (XCDZhanlongshi1)c;
//                                x.savedata.set(x.timesUpgraded, x.savedata.get(x.timesUpgraded) + 1);
//                                x.baseDamage += x.baseMagicNumber;
//                                x.isDamageModified = false;
//                            }
//                        }
//
//                        for(AbstractCard c : GetAllInBattleInstances.get(this.uuid)) {
//                            XCDZhanlongshi1 x = (XCDZhanlongshi1)c;
//                            x.savedata.set(x.timesUpgraded, x.savedata.get(x.timesUpgraded) + 1);
//                            x.baseDamage += x.baseMagicNumber;
//                        }
//                    }
//
//                    if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
//                        AbstractDungeon.actionManager.clearPostCombatActions();
//                    }
//                }
//
//                this.tickDuration();
//            }
//        });
//    }
//
//    @Override
//    public void upgrade() {
//        ModHelper.logger.info("===============XCDZhanlongshi upgrade==============");
//        ModHelper.logger.info("==============={}==============", savedata);
//
//        upgradeDamage(4);
//        upgradeMagicNumber(1);
//
//        while (this.savedata.size() <= this.timesUpgraded) {
//            this.savedata.add(0);
//        }
//
//
//        for (int i = 0; i <= savedata.get(timesUpgraded); i++) {
//            upgradeDamage(timesUpgraded+2);//当前升级层数时,每次斩杀会提升的伤害
//        }
//
//        this.timesUpgraded++;
//        this.upgraded = true;
//        this.name = cardStrings.NAME + "+" + this.timesUpgraded;
//        initializeTitle();
//    }
//
//    @Override
//    public AbstractCard makeCopy() {
//        return new XCDZhanlongshi1(0, savedata);
//    }
//
//    @Override
//    public ArrayList<Integer> onSave() {
//        return savedata;
//    }
//
//    @Override
//    public void onLoad(ArrayList<Integer> integers) {
//        if (integers != null) {
//            savedata = integers;
//        } else savedata = new ArrayList<>();
//    }
//}