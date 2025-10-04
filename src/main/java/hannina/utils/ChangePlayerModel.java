package hannina.utils;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import hannina.character.Hannina;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ChangePlayerModel {
    public static void ChangeSkin(String Player) {
        if(!(AbstractDungeon.player instanceof Hannina))
            return;

//        if(AbstractDungeon.player == null) return;

        try {
            Method loadAnimationMethod;
            switch (Player) {
                case "RED":
                    loadAnimationMethod = AbstractCreature.class.getDeclaredMethod(
                            "loadAnimation",
                            String.class,
                            String.class,
                            float.class
                    );
                    loadAnimationMethod.setAccessible(true);
                    loadAnimationMethod.invoke(
                            AbstractDungeon.player,
                            "images/characters/ironclad/idle/skeleton.atlas",
                            "images/characters/ironclad/idle/skeleton.json",
                            1.0F
                    );
                    AbstractDungeon.player.state.setAnimation(0, "Idle", true);
//                if (AbstractDungeon.overlayMenu != null)
//                    Invoker.setField(AbstractDungeon.overlayMenu.energyPanel, "gainEnergyImg", ImageMaster.RED_ORB_FLASH_VFX);
                    break;
                case "GREEN":
                    loadAnimationMethod = AbstractCreature.class.getDeclaredMethod(
                            "loadAnimation",
                            String.class,
                            String.class,
                            float.class
                    );
                    loadAnimationMethod.setAccessible(true);
                    loadAnimationMethod.invoke(
                            AbstractDungeon.player,
                            "images/characters/theSilent/idle/skeleton.atlas",
                            "images/characters/theSilent/idle/skeleton.json",
                            1.0F
                    );
                    AbstractDungeon.player.state.setAnimation(0, "Idle", true);
//                if (AbstractDungeon.overlayMenu != null)
//                    Invoker.setField(AbstractDungeon.overlayMenu.energyPanel, "gainEnergyImg", ImageMaster.GREEN_ORB_FLASH_VFX);
                    break;
                case "BLUE":
                    loadAnimationMethod = AbstractCreature.class.getDeclaredMethod(
                            "loadAnimation",
                            String.class,
                            String.class,
                            float.class);
                    loadAnimationMethod.setAccessible(true);
                    loadAnimationMethod.invoke(
                            AbstractDungeon.player,
                            "images/characters/defect/idle/skeleton.atlas",
                            "images/characters/defect/idle/skeleton.json",
                            1.0F
                    );
                    AbstractDungeon.player.state.setAnimation(0, "Idle", true);
//                if (AbstractDungeon.overlayMenu != null)
//                    Invoker.setField(AbstractDungeon.overlayMenu.energyPanel, "gainEnergyImg", ImageMaster.BLUE_ORB_FLASH_VFX);
                    break;
                case "PURPLE":
                    loadAnimationMethod = AbstractCreature.class.getDeclaredMethod(
                            "loadAnimation",
                            String.class,
                            String.class,
                            float.class);
                    loadAnimationMethod.setAccessible(true);
                    loadAnimationMethod.invoke(
                            AbstractDungeon.player,
                            "images/characters/watcher/idle/skeleton.atlas",
                            "images/characters/watcher/idle/skeleton.json",
                            1.0F
                    );
                    AbstractDungeon.player.state.setAnimation(0, "Idle", true);
//                if (AbstractDungeon.overlayMenu != null)
//                    Invoker.setField(AbstractDungeon.overlayMenu.energyPanel, "gainEnergyImg", ImageMaster.PURPLE_ORB_FLASH_VFX);
                    break;
                case "Hannina":
                    Field atlas = AbstractCreature.class.getDeclaredField("atlas");
                    atlas.setAccessible(true);
                    atlas.set(AbstractDungeon.player, null);
                    break;
            }
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException | NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }
}
