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
                    //TODO 目前只实装了sfw皮肤，后面需要判断当前使用的哪个皮肤
                    loadAnimationMethod = AbstractCreature.class.getDeclaredMethod(
                            "loadAnimation",
                            String.class,
                            String.class,
                            float.class);
                    loadAnimationMethod.setAccessible(true);
                    loadAnimationMethod.invoke(
                            AbstractDungeon.player,
                            ModHelper.getImgPath("char/skin/animations/hannina/idle/sfw/Hannina_Kimono_null.atlas"),
                            ModHelper.getImgPath("char/skin/animations/hannina/idle/sfw/hannina_kimono_null37.json"),
                            2.0F
                    );
                    AbstractDungeon.player.state.setAnimation(0, "Idle", true);
                    break;
            }
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
