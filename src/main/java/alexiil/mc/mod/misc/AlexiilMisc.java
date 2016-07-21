package alexiil.mc.mod.misc;

import java.io.File;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

@Mod(modid = AlexiilMisc.MODID, version = AlexiilMisc.VERSION)
public class AlexiilMisc {
    public static final String MODID = "alexiilmisc";
    public static final String VERSION = "{$version}";

    @Mod.Instance
    public static AlexiilMisc instance;

    public static final Logger LOG = LogManager.getLogger("AlexiilMisc");
    public static final Configuration CONFIG;
    public static final Property PROP_POTIONS, PROP_CHAT, PROP_ROAMING_IP;

    static {
        CONFIG = new Configuration(new File("./config/alexiilmisc.cfg"));
        CONFIG.load();
        PROP_POTIONS = CONFIG.get("general", "betterPotions", false);
        PROP_CHAT = CONFIG.get("general", "textTime", false);
        PROP_ROAMING_IP = CONFIG.get("general", "roamingIP", false);
        CONFIG.save();
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {

    }
}
