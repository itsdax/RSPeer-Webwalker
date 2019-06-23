package com.dax.api.game;

import com.dax.api.time.Timer;
import org.rspeer.runetek.adapter.scene.Player;
import org.rspeer.runetek.api.commons.Time;
import org.rspeer.runetek.api.commons.math.Random;
import org.rspeer.runetek.api.input.Keyboard;
import org.rspeer.runetek.api.scene.Players;
import org.rspeer.ui.Log;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.regex.Pattern;

public class AutoReplier {

    private static final AutoReplier AUTO_REPLIER = new AutoReplier();

    public static AutoReplier getInstance() {
        return AUTO_REPLIER;
    }

    private enum MessageCategory {
        BOT_ACCUSATION(
                3,
                new String[]{"beebop", "nah", "nope", "yikes", "no u", "?", "??", "no", "lol", "lmao", "o", "oh", "..."},
                new Pattern[]{Pattern.compile("(.+ |)(bot|botting|botter).?"),}
        ),

        GREETING(
                1,
                new String[]{"hello", "hey", "heya", "hi", "yo", "sup"},
                new Pattern[]{Pattern.compile("(yo|hey|hi|hello|hola|good .+).?")}
        ),

        QUESTION(
                1,
                new String[]{"ionno", "iderno", "no clue", "?", "??", "magic", "stuff"},
                new Pattern[]{Pattern.compile(".+\\?"), Pattern.compile("(what|w.t).?(are|r)?.?(that|you|u|ya).*")}
        ),

        COMPLIMENT (
                1,
                new String[]{":)", "ty", "thx", "tyty"},
                new Pattern[]{Pattern.compile("(.+ |)(nice|dope|cool).?")}
        ),

        UNCLASSIFIED(
                0,
                new String[]{"wat", "wut", "huh", "uh huh"},
                new Pattern[]{Pattern.compile(".*")}
        );

        private int priority;
        private String[] responses;
        private Pattern[] regex;
        private Map<String, Long> lastResponseMap;
        private Lock lock;

        MessageCategory(int priority, String[] responses, Pattern[] matchers) {
            this.priority = priority;
            this.responses = responses;
            this.regex = matchers;
            lastResponseMap = new HashMap<>();
            lock = new ReentrantLock();
        }

        private void respond(String name) {
            Keyboard.sendText(getRandom(responses));
            Keyboard.pressEnter();
            lock.lock();
            lastResponseMap.put(name, System.currentTimeMillis());
            lock.unlock();
        }

        private boolean shouldRespond(String name, String text) {
            lock.lock();
            Long lastResponded = lastResponseMap.get(name);
            lock.unlock();

            if (lastResponded != null && Timer.since(lastResponded) < 180000) return false;

            String input = text.toLowerCase();
            return Arrays.stream(regex).anyMatch(pattern -> pattern.matcher(input).matches());
        }

        private int getPriority() {
            return priority;
        }

    }

    private Executor executor;

    private AutoReplier() {
        executor = Executors.newFixedThreadPool(2);
    }

    public void handle(String name, String text) {
        Log.log(Level.FINE, "AutoReplier", name + ": " + text);
        if (text == null) return;
        Player player = Players.getLocal();
        if (player == null) return;
        if (player.getName().equals(name)) return;
        executor.execute(() -> {
            Time.sleep(1500, 2250);
            Arrays.stream(MessageCategory.values())
                    .filter(messageCategory -> messageCategory.shouldRespond(name, text))
                    .max(Comparator.comparingInt(MessageCategory::getPriority))
                    .ifPresent(category -> category.respond(name));
        });
    }

    private static String getRandom(String[] responses) {
        return responses[Random.nextInt(0, responses.length)];
    }

}
