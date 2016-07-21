package alexiil.mc.mod.misc.coremod;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.util.IChatComponent;

import net.minecraftforge.common.config.Property;

import alexiil.mc.mod.misc.AlexiilMisc;

public class ChatTextTime {
    private static final Property doSecs, doSecsWhileMin, doTimeChar, preTimeChar, postTimeChar;

    static {
        doSecs = AlexiilMisc.CONFIG.get("textTime", "doSecs.enabled", "true");
        doSecsWhileMin = AlexiilMisc.CONFIG.get("textTime", "doSecs.whileMin", "true");
        doTimeChar = AlexiilMisc.CONFIG.get("textTime", "timeChar.enabled", "true");
        preTimeChar = AlexiilMisc.CONFIG.get("textTime", "timeChar.pre", "<");
        postTimeChar = AlexiilMisc.CONFIG.get("textTime", "timeChar.post", ">");
        AlexiilMisc.CONFIG.save();
    }

    private static String getUnformatedText(IChatComponent chat) {
        String formatted = chat.getFormattedText();
        String unformatted = "";
        for (int i = 0; i < formatted.length(); i++) {
            if ('§' == formatted.codePointAt(i)) i++;
            else unformatted += formatted.charAt(i);
        }
        return unformatted;
    }

    private static String getTime(int ticks) {
        WorldClient wc = Minecraft.getMinecraft().theWorld;
        long age = wc.getTotalWorldTime() % 20;
        ticks -= age;
        if (ticks < 20) return "0s";
        int secs = (ticks / 20) % 60;
        int mins = (ticks / 1200) % 60;
        int hours = (ticks / 72000);
        String total = (hours == 0 ? "" : (hours + "h"));
        total += (mins == 0 ? "" : (mins + "m"));
        if (doSecs.getBoolean() && ((doSecsWhileMin.getBoolean()) || mins <= 0)) {
            total += (secs == 0 ? "" : (secs + "s"));
        }
        return total;
    }

    private static String getPrePostChars(String time) {
        if (!doTimeChar.getBoolean()) return time;
        String pre = preTimeChar.getString();
        String post = postTimeChar.getString();
        return pre + time + post;
    }

    private static boolean shouldDoPlayerStuff(String unformatted) {
        if (unformatted == null || unformatted.length() == 0) return false;
        return unformatted.charAt(0) == '<';
    }

    public static String getTimeText(IChatComponent chatLine, int ticks) {
        if (!AlexiilMisc.PROP_CHAT.getBoolean()) {
            return chatLine.getFormattedText();
        }
        String unformatted = getUnformatedText(chatLine);
        if (!shouldDoPlayerStuff(unformatted)) {
            return chatLine.getFormattedText();
        }
        String time = getTime(ticks);
        return getPrePostChars(time) + chatLine.getFormattedText();
    }
}
