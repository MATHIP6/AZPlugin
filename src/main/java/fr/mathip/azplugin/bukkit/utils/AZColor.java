package fr.mathip.azplugin.bukkit.utils;

public class AZColor {

    public static Integer get0xAARRGGBB(String hexColor) {
        if (hexColor.startsWith("#")) {
            hexColor = hexColor.substring(1);
        }
        String hexValue = "0xFF" + hexColor;
        long longValue = Long.parseLong(hexValue.substring(2), 16);
        return (int) longValue;
    }

    public static Integer getRGB(Integer red, Integer green, Integer blue) {
        red = Math.min(Math.max(red, 0), 255);
        green = Math.min(Math.max(green, 0), 255);
        blue = Math.min(Math.max(blue, 0), 255);
        return (red << 16) | (green << 8) | blue;
    }

    /**
     * Personnaliser la couleur d'un texte
     * Par THE_BATTEUR (THEBATTEUR)
     */
    public static String customText(String text, int color) {
        StringBuilder sb = new StringBuilder("§#");
        String hex = Integer.toHexString(color);
        for (int i = 0; i < hex.length(); i += 2) {
            String str = hex.substring(i, i + 2);
            int charCode = Integer.parseInt(str, 16);
            sb.append((char) charCode);
        }
        return sb.append(text).toString();
    }
}
