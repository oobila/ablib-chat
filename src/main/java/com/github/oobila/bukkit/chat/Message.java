package com.github.oobila.bukkit.chat;

import lombok.Setter;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.text.MessageFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@SuppressWarnings("unused")
public class Message {

    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public static final DateTimeFormatter DATE_AND_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd @ HH:mm:ss");
    public static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");
    public static final String LIST_PREFIX = "\n  ";

    @Setter
    private static ChatColor defaultColor = ChatColor.GOLD;
    @Setter
    private static ChatColor defaultArgColor = ChatColor.RED;
    private final String text;
    private List<String> args = new ArrayList<>();
    private ChatColor color = defaultColor;
    private ChatColor argColor = defaultArgColor;
    private final List<Message> appendix = new LinkedList<>();

    public Message(String text) {
        this.text = text;
    }

    public Message(String text, String... args) {
        this(text);
        this.args = Arrays.stream(args).toList();
    }

    public static Message builder(String message){
        return new Message(message);
    }

    public Message arg(String arg) {
        args.add(arg);
        return this;
    }

    public Message arg(ChatColor color, String arg) {
        args.add(color + arg);
        return this;
    }

    public Message arg(int arg) {
        return arg(Integer.toString(arg));
    }

    public Message arg(ChatColor color, int arg) {
        return arg(color, Integer.toString(arg));
    }

    public Message arg(long arg) {
        return arg(Long.toString(arg));
    }

    public Message arg(ChatColor color, long arg) {
        return arg(color, Long.toString(arg));
    }

    public Message arg(double arg) {
        return arg(Double.toString(arg));
    }

    public Message arg(ChatColor color, double arg) {
        return arg(color, Double.toString(arg));
    }

    public Message arg(boolean arg) {
        return arg(Boolean.toString(arg));
    }

    public Message arg(ChatColor color, boolean arg) {
        return arg(color, Boolean.toString(arg));
    }

    public Message arg(Location arg) {
        return arg(argColor, arg);
    }

    public Message arg(ChatColor color, Location arg) {
        args.add(MessageFormat.format("{0}[x={1},y={2},z={3}]",
                this.color,
                color + String.valueOf(arg.getBlockX()) + this.color,
                color + String.valueOf(arg.getBlockY()) + this.color,
                color + String.valueOf(arg.getBlockZ()) + this.color));
        return this;
    }

    public Message arg(OfflinePlayer arg) {
        return arg(arg.getName());
    }

    public Message arg(ChatColor color, OfflinePlayer arg) {
        return arg(color, arg.getName());
    }

    public Message arg(LocalDate arg) {
        return arg(DATE_FORMATTER.format(arg));
    }

    public Message arg(ChatColor color, LocalDate arg) {
        return arg(color, DATE_FORMATTER.format(arg));
    }

    public Message arg(ZonedDateTime arg) {
        return arg(DATE_AND_TIME_FORMATTER.format(arg));
    }

    public Message arg(ChatColor color, ZonedDateTime arg) {
        return arg(color, DATE_AND_TIME_FORMATTER.format(arg));
    }

    public Message arg(LocalTime arg) {
        return arg(TIME_FORMATTER.format(arg));
    }

    public Message arg(ChatColor color, LocalTime arg) {
        return arg(color, TIME_FORMATTER.format(arg));
    }

    public Message colors(ChatColor color, ChatColor argColor) {
        this.color = color;
        this.argColor = argColor;
        return this;
    }

    public Message append(ChatColor color, String text) {
        appendix.add(new Message(text).colors(color, argColor));
        return this;
    }

    public Message append(Message message) {
        appendix.add(message);
        return this;
    }

    public Message append(MessageListAppender messageListAppender) {
        appendix.addAll(messageListAppender.getAppendix());
        return this;
    }

    private String build() {
        StringBuilder sb = new StringBuilder();
        sb.append(color);
        sb.append(MessageFormat.format(text, args.stream().map(
                string -> argColor + string + color
        ).toArray()));
        appendix.forEach(message ->
                sb.append(LIST_PREFIX).append(message.build())
        );
        return sb.toString();
    }

    public void send(Player player) {
        player.sendMessage(build());
    }

    @Override
    public String toString() {
        return build();
    }
}