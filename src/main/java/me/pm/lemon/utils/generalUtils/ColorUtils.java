package me.pm.lemon.utils.generalUtils;

import me.pm.lemon.module.Module;
import me.pm.lemon.module.modules.gui.Colours;

public class ColorUtils {
    public static int guiColor() {
        if (Module.getModule(Colours.class).getSetting(0).asToggle().state) {
            float sat = (float) Module.getModule(Colours.class).getSetting(0).asToggle().getChild(0).asSlider().getValue();
            float bri = (float) Module.getModule(Colours.class).getSetting(0).asToggle().getChild(1).asSlider().getValue();
            double speed = Module.getModule(Colours.class).getSetting(0).asToggle().getChild(2).asSlider().getValue();
            return Util.getRainbow(sat, bri, speed, 0);
        }
        return Module.getModule(Colours.class).getSetting(1).asColor().getRGB();
    }
    public static int guiBackgroundColor() {
        if (Module.getModule(Colours.class).getSetting(0).asToggle().state) {
            float sat = (float) Module.getModule(Colours.class).getSetting(0).asToggle().getChild(0).asSlider().getValue();
            float bri = (float) Module.getModule(Colours.class).getSetting(0).asToggle().getChild(1).asSlider().getValue();
            double speed = Module.getModule(Colours.class).getSetting(0).asToggle().getChild(2).asSlider().getValue();
            return Util.getRainbow(sat, bri, speed, 0);
        }
        return Module.getModule(Colours.class).getSetting(4).asColor().getRGB();
    }
    public static int textColor() {
        return Module.getModule(Colours.class).getSetting(2).asColor().getRGB();
    }
    public static int guiTextColor() {
        return Module.getModule(Colours.class).getSetting(3).asColor().getRGB();
    }
}
