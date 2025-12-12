package hannina.utils;

import basemod.ReflectionHacks;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.monsters.ending.CorruptHeart;
import com.megacrit.cardcrawl.screens.runHistory.RunHistoryPath;
import com.megacrit.cardcrawl.screens.runHistory.RunPathElement;
import com.megacrit.cardcrawl.screens.stats.BattleStats;
import com.megacrit.cardcrawl.screens.stats.RunData;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import hannina.cards.Biduancanxiang;
import hannina.cards.Huiyaoqiangya;
import hannina.cards.Quanzhuangfensui;
import hannina.cards.Wujingyichuan;
import hannina.modcore.Enums;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.Arrays;
import java.util.Objects;

public class HistoryHelper {
	private static final Logger logger = LogManager.getLogger(HistoryHelper.class.getName());

	public static boolean checkHasDefeatedTheHeart(RunHistoryPath path) {
		if (path != null && !path.pathElements.isEmpty()) {
			boolean hasDefeatedTheHeart = path.pathElements.stream()
					.filter(e -> e.nodeType == RunPathElement.PathNodeType.BOSS)
					.map(e -> (BattleStats) ReflectionHacks
							.getPrivate(e, RunPathElement.class, "battleStats"))
					.filter(Objects::nonNull)
					.anyMatch(s -> s.enemies.equals(CorruptHeart.ID));

			RunPathElement last = path.pathElements.get(path.pathElements.size() - 1);

			return hasDefeatedTheHeart && last.nodeType != RunPathElement.PathNodeType.BOSS;
		}

		return false;
	}

	public static RunHistoryPath getPath(RunData data) {
		RunHistoryPath path = new RunHistoryPath();
		path.setRunData(data);

		return path;
	}

	private static Stats checkHistories() {
		Stats res = new Stats();

		FileHandle[] subfolders = Gdx.files.local("runs" + File.separator).list();

		for (FileHandle subfolder : subfolders) {
			if (CardCrawlGame.saveSlot == 0) {
				if (subfolder.name().contains("0_") || subfolder.name().contains("1_") ||
						subfolder.name().contains("2_"))
					continue;
			}
			else {
				if (!subfolder.name().contains(CardCrawlGame.saveSlot + "_"))
					continue;
			}

			if (!subfolder.name().contains(Enums.HANNINA_CLASS.name()) &&
					Arrays.stream(AbstractPlayer.PlayerClass.values())
							.noneMatch(c -> subfolder.name().contains(c.name())))
				continue;

			Gson gson = new Gson();

			for (FileHandle file : subfolder.list()) {
				RunData data;

				try {
					data = gson.fromJson(file.readString(), RunData.class);
					if (data != null) {
						try {
							AbstractPlayer.PlayerClass.valueOf(data.character_chosen);
						}
						catch (NullPointerException | IllegalArgumentException e) {
							continue;
						}
					}
				}
				catch (JsonSyntaxException e) {
					continue;
				}

				if (data == null)
					continue;

				boolean isA20 = data.is_ascension_mode && data.ascension_level >= 20;
				boolean isHannina = data.character_chosen.equals(Enums.HANNINA_CLASS.name());
				boolean isOriginalChar = Arrays.stream(AbstractPlayer.PlayerClass.values())
						.anyMatch(c -> c.name().equals(data.character_chosen));

				boolean defeatedTheHeart = checkHasDefeatedTheHeart(getPath(data));

				if (defeatedTheHeart && isHannina) {
					res.hasDefeatedTheHeart = true;
					
					if (isA20)
						res.hasDefeatedTheHeartA20 = true;
				}
			}
		}

		return res;
	}

	public static class Stats {
		public boolean hasDefeatedTheHeart = false;
		public boolean hasDefeatedTheHeartA20 = false;
	}

	public static void runCheck() {
		Stats stats = HistoryHelper.checkHistories();

		if (stats.hasDefeatedTheHeart) {
			logger.info("Has defeated the heart before. Setting config accordingly.");
			
			ConfigHelper.saveHasDefeatedTheHeart(true);
			
			UnlockTracker.unlockCard(ModHelper.makeID(Huiyaoqiangya.class.getSimpleName()));
			UnlockTracker.unlockCard(ModHelper.makeID(Biduancanxiang.class.getSimpleName()));
			UnlockTracker.unlockCard(ModHelper.makeID(Quanzhuangfensui.class.getSimpleName()));
			UnlockTracker.unlockCard(ModHelper.makeID(Wujingyichuan.class.getSimpleName()));
			
			// 历史记录里没有记录皮肤，所以只能打完一局后解锁
		}
		else
			logger.info("Has not defeated the heart before.");
	}
}
