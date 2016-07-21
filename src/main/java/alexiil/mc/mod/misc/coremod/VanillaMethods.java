package alexiil.mc.mod.misc.coremod;

import net.minecraft.potion.PotionEffect;

import alexiil.mc.mod.misc.AlexiilMisc;

import gnu.trove.map.hash.TIntObjectHashMap;

public class VanillaMethods {
    private static final TIntObjectHashMap<String> CACHED_NUMERALS = new TIntObjectHashMap<>();
    private static int[] romanImportance = { 1, 5, 10, 50, 100, 500, 1000 };
    private static char[] romanChars = { 'I', 'V', 'X', 'L', 'C', 'D', 'M' };

    static {
        // Manually place 4 and 9 into the cache
        CACHED_NUMERALS.put(4, "IV");
        CACHED_NUMERALS.put(9, "IX");
        // Cache a lot of lower ones
        for (int i = 0; i < 145; i++) {
            getRomanNumeral(i);
        }
    }

    public static String getEnchantmentLevel(String preText, PotionEffect potionEffect) {
        if (potionEffect.getAmplifier() <= 4) return preText;
        if (AlexiilMisc.PROP_POTIONS.getBoolean()) {
            return preText + " " + getRomanNumeral(potionEffect.getAmplifier() + 1);
        }
        return preText;
    }

    public static String getRomanNumeral(final int num) {
        if (num == 0) {
            return "";
        } else if (num < 0) {
            return "-" + getRomanNumeral(-num);
        } else if (CACHED_NUMERALS.contains(num)) {
            return CACHED_NUMERALS.get(num);
        } else {
            int tempVal = num;
            String total = "";
            for (int i = romanImportance.length - 1; i >= 0; i--) {
                char c = romanChars[i];
                int mag = romanImportance[i];
                while (tempVal >= mag) {
                    tempVal -= mag;
                    total += c;
                }
            }
            CACHED_NUMERALS.put(num, total);
            return total;
        }
    }
}
