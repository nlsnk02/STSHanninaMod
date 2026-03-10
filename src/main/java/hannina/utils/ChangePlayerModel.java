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
//                    loadAnimationMethod = AbstractCreature.class.getDeclaredMethod(
//                            "loadAnimation",
//                            String.class,
//                            String.class,
//                            float.class);
//                    loadAnimationMethod.setAccessible(true);
//
//                    if (ConfigHelper.skinId.equals("SFW") ||
//                            ConfigHelper.skinId.equals("NSFW") ||
//                            ConfigHelper.skinId.equals("NSFW-2") ||
//                            ConfigHelper.skinId.isEmpty()) {
//                        loadAnimationMethod.invoke(
//                                AbstractDungeon.player,
//                                ModHelper.getImgPath("char/Hannina_null/Hannina_null.atlas"),
//                                ModHelper.getImgPath("char/Hannina_null/hannina_null37.json"),
//                                2.0F
//                        );
//                        AbstractDungeon.player.state.setAnimation(0, "Idle", true);
//                    }
//                    else if (ConfigHelper.skinId.equals("SFW-2")) {
//                        loadAnimationMethod.invoke(
//                                AbstractDungeon.player,
//                                ModHelper.getImgPath("char/skin/SFW-2/Hannina_SFW-2_null/Hannina_SFW-2_null.atlas"),
//                                ModHelper.getImgPath("char/skin/SFW-2/Hannina_SFW-2_null/hannina_sfw-2_null37.json"),
//                                2.0F
//                        );
//                        AbstractDungeon.player.state.setAnimation(0, "Idle", true);
//                    }
//                    else { // SFW-3 / NSFW-3
//                        loadAnimationMethod.invoke(
//                                AbstractDungeon.player,
//                                ModHelper.getImgPath(String.format("char/skin/%1$s/Hannina_%1$s/Hannina_%1$s.atlas", ConfigHelper.skinId)),
//                                ModHelper.getImgPath(String.format("char/skin/%1$s/Hannina_%1$s/hannina_%2$s37.json", ConfigHelper.skinId, ConfigHelper.skinId.toLowerCase())),
//                                2.0F
//                        );
//                        AbstractDungeon.player.state.setAnimation(0, "Idle_null", true);
//                    }

                    break;
            }
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
