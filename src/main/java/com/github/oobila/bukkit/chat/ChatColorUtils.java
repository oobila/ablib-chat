package com.github.oobila.bukkit.chat;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.bukkit.ChatColor;
import org.bukkit.Color;

import java.util.Arrays;

/**
 * Utility class to help with Minecraft chat colours
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ChatColorUtils {

    private static final String AMPERSAND = "&";
    private static final String EMPTY_STRING = "";

    /**
     * Function that replaces ChatColor codes with the ChatColor in a String. i.e. "&a" becomes ChatColor.GREEN
     * @param s
     * @return
     */
    public static String formatColors(String s){
        for(ChatColor color : ChatColor.values()){
            s = s.replaceAll(AMPERSAND + color.getChar(), color + EMPTY_STRING);
        }
        return s;
    }

    /**
     * Ensures a ChatColor is using it's light variant. i.e. DARK_GREEN becomes GREEN
     * @param chatColor
     * @return
     */
    public static ChatColor toLight(ChatColor chatColor){
        return switch (chatColor) {
            case BLACK, DARK_GRAY -> ChatColor.DARK_GRAY;
            case DARK_BLUE, BLUE -> ChatColor.BLUE;
            case DARK_GREEN, GREEN -> ChatColor.GREEN;
            case DARK_AQUA, AQUA -> ChatColor.AQUA;
            case DARK_RED, RED -> ChatColor.RED;
            case DARK_PURPLE, LIGHT_PURPLE -> ChatColor.LIGHT_PURPLE;
            case GOLD, YELLOW -> ChatColor.YELLOW;
            case GRAY, WHITE -> ChatColor.WHITE;
            default -> chatColor;
        };
    }

    /**
     * Ensures a ChatColor is using it's dark variant. i.e. GREEN becomes DARK_GREEN
     * @param chatColor
     * @return
     */
    public static ChatColor toDark(ChatColor chatColor){
        return switch (chatColor) {
            case BLACK, DARK_GRAY -> ChatColor.BLACK;
            case DARK_BLUE, BLUE -> ChatColor.DARK_BLUE;
            case DARK_GREEN, GREEN -> ChatColor.DARK_GREEN;
            case DARK_AQUA, AQUA -> ChatColor.DARK_AQUA;
            case DARK_RED, RED -> ChatColor.DARK_RED;
            case DARK_PURPLE, LIGHT_PURPLE -> ChatColor.DARK_PURPLE;
            case GOLD, YELLOW -> ChatColor.GOLD;
            case GRAY, WHITE -> ChatColor.GRAY;
            default -> chatColor;
        };
    }

    public static Color toColor(ChatColor chatColor) {
        return NamedColor.get(chatColor).getColor();
    }

    public static ChatColor fromColor(Color color) {
        return NamedColor.getClosest(color).getChatColor();
    }

    @AllArgsConstructor
    @Getter
    private enum NamedColor {
        DARK_RED(ChatColor.DARK_RED, Color.MAROON, Color.fromRGB(170, 0, 0)),
        RED(ChatColor.RED, Color.RED, Color.fromRGB(255, 85, 85)),
        GOLD(ChatColor.GOLD, Color.OLIVE, Color.fromRGB(255, 170, 0)),
        YELLOW(ChatColor.YELLOW, Color.YELLOW, Color.fromRGB(255, 255, 85)),
        DARK_GREEN(ChatColor.DARK_GREEN, Color.GREEN, Color.fromRGB(0, 170, 0)),
        GREEN(ChatColor.GREEN, Color.LIME, Color.fromRGB(85, 255, 85)),
        AQUA(ChatColor.AQUA, Color.AQUA, Color.fromRGB(85, 255, 255)),
        DARK_AQUA(ChatColor.DARK_AQUA, Color.TEAL, Color.fromRGB(0, 170, 170)),
        DARK_BLUE(ChatColor.DARK_BLUE, Color.NAVY, Color.fromRGB(0, 0, 170)),
        BLUE(ChatColor.BLUE, Color.BLUE, Color.fromRGB(85, 85, 255)),
        LIGHT_PURPLE(ChatColor.LIGHT_PURPLE, Color.FUCHSIA, Color.fromRGB(255, 85, 255)),
        DARK_PURPLE(ChatColor.DARK_PURPLE, Color.PURPLE, Color.fromRGB(170, 0, 170)),
        WHITE(ChatColor.WHITE, Color.WHITE, Color.fromRGB(255, 255, 255)),
        GRAY(ChatColor.GRAY, Color.SILVER, Color.fromRGB(170, 170, 170)),
        DARK_GRAY(ChatColor.DARK_GRAY, Color.GRAY, Color.fromRGB(85, 85, 85)),
        BLACK(ChatColor.BLACK, Color.BLACK, Color.fromRGB(0, 0, 0));

        private final ChatColor chatColor;
        private final Color color;
        private final Color literalChatColor;

        public static NamedColor get(ChatColor chatColor) {
            return Arrays.stream(NamedColor.values()).filter(c -> c.chatColor.equals(chatColor)).findFirst().orElse(null);
        }

        public static NamedColor get(Color color) {
            return Arrays.stream(NamedColor.values()).filter(c -> c.color.equals(color)).findFirst().orElse(null);
        }

        public static NamedColor getClosest(Color color) {
            NamedColor closest = BLACK;
            int diff = 255*3;
            for (NamedColor namedColor : NamedColor.values()) {
                int cDiff = Math.abs(color.getRed() - namedColor.literalChatColor.getRed()) +
                        Math.abs(color.getGreen() - namedColor.literalChatColor.getGreen()) +
                        Math.abs(color.getBlue() - namedColor.literalChatColor.getBlue());
                if (cDiff < diff) {
                    closest = namedColor;
                    diff = cDiff;
                }
            }
            return closest;
        }

        public static NamedColor getClosest(java.awt.Color color) {
            NamedColor closest = BLACK;
            int diff = 255*3;
            for (NamedColor namedColor : NamedColor.values()) {
                int cDiff = Math.abs(color.getRed() - namedColor.literalChatColor.getRed()) +
                        Math.abs(color.getGreen() - namedColor.literalChatColor.getGreen()) +
                        Math.abs(color.getBlue() - namedColor.literalChatColor.getBlue());
                if (cDiff < diff) {
                    closest = namedColor;
                    diff = cDiff;
                }
            }
            return closest;
        }

    }
}
