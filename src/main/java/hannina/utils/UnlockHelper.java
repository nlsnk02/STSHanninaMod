package hannina.utils;

import basemod.devcommands.unlock.Unlock;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.dungeons.TheEnding;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.screens.GameOverScreen;
import com.megacrit.cardcrawl.screens.VictoryScreen;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import hannina.cards.*;
import hannina.character.Hannina;
import hannina.modcore.Core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public abstract class UnlockHelper {
	public static final String[] unlockBundle1 = {
			ModHelper.makeID(Huiyaoqiangya.class.getSimpleName()),
			ModHelper.makeID(Biduancanxiang.class.getSimpleName()),
			ModHelper.makeID(Quanzhuangfensui.class.getSimpleName()),
			ModHelper.makeID(Wujingyichuan.class.getSimpleName())
	};
	public static final String[] unlockBundle2 = {
			ModHelper.makeID(Zhuangdan.class.getSimpleName()),
			ModHelper.makeID(Gangqi.class.getSimpleName()),
			ModHelper.makeID(Tuijinzhan.class.getSimpleName()),
			ModHelper.makeID(Kuaidaoluanma.class.getSimpleName())
	};
	
	public static ArrayList<UnlockItem> onGameOver(GameOverScreen screen) {
		ArrayList<UnlockItem> items = new ArrayList<>();
		
		if (AbstractDungeon.player instanceof Hannina /* &&
				screen instanceof VictoryScreen &&
				CardCrawlGame.dungeon instanceof TheEnding*/) {
			if (screen instanceof VictoryScreen &&
					CardCrawlGame.dungeon instanceof TheEnding) {
				if (!ConfigHelper.hasDefeatedTheHeart ||
						Arrays.stream(unlockBundle1).anyMatch(UnlockTracker::isCardLocked)) {
					items.add(new UnlockItem().IDs(unlockBundle1).tip("提示：异热同心猫皮肤已解锁！"));
					ConfigHelper.saveHasDefeatedTheHeart(true);
				}
				
				Core.logger.info("skinId = {}", ConfigHelper.skinId);
				
				if (ConfigHelper.skinId.equalsIgnoreCase("SFW-2")) {
					if (!ConfigHelper.hasDefeatedTheHeartSFW2 ||
							Arrays.stream(unlockBundle2).anyMatch(UnlockTracker::isCardLocked)) {
						items.add(new UnlockItem().IDs(unlockBundle2));
						ConfigHelper.saveHasDefeatedTheHeartSFW2(true);
					}
				}
			}
		}
		
		return items;
	}
	
	public static class UnlockItem {
		public ArrayList<AbstractCard> cards;
		public int skinIndex = -1;
		public String title;
		public String tip;
		
		public UnlockItem() {
		
		}
		
		public UnlockItem cards(ArrayList<AbstractCard> cards) {
			this.cards = cards;
			return this;
		}
		
		public UnlockItem cards(AbstractCard... cards) {
			this.cards = new ArrayList<>();
			this.cards.addAll(Arrays.asList(cards));
			return this;
		}
		
		public UnlockItem IDs(String... IDs) {
			if (IDs == null)
				this.cards = null;
			else {
				this.cards = new ArrayList<>();
				for (String id : IDs) {
					AbstractCard card = CardLibrary.getCopy(id);
//					UnlockTracker.unlockCard(id);
					this.cards.add(card);
				}
			}
			
			return this;
		}
		
		public UnlockItem IDs(ArrayList<String> IDs) {
			if (IDs != null)
				return this.IDs(IDs.toArray(new String[0]));
			else {
				this.cards = null;
				return this;
			}
		}
		
		public UnlockItem skinIndex(int skinIndex) {
			this.skinIndex = skinIndex;
			return this;
		}
		
		public UnlockItem title(String title) {
			this.title = title;
			return this;
		}
		
		public UnlockItem tip(String tip) {
			this.tip = tip;
			return this;
		}
	}
}
