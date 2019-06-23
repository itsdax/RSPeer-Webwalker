package com.dax.walker.engine.definitions;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class StrongHoldAnswers {
    private static StrongHoldAnswers ourInstance = new StrongHoldAnswers();

    public static StrongHoldAnswers getInstance() {
        return ourInstance;
    }

    private Set<String> set;

    private StrongHoldAnswers() {
        this.set = new HashSet<>();
        set.addAll(Arrays.asList(
                "Use the Account Recovery System.",
                "Nobody.",
                "Don't tell them anything and click the 'Report Abuse' button.",
                "Me.",
                "Only on the RuneScape website.",
                "Report the incident and do not click any links.",
                "Authenticator and two-step login on my registered email.",
                "No way! You'll just take my gold for your own! Reported!",
                "No.",
                "Don't give them the information and send an 'Abuse Report'.",
                "Don't give them my password.",
                "The birthday of a famous person or event.",
                "Through account settings on runescape.com.",
                "Secure my device and reset my RuneScape password.",
                "Report the player for phishing.",
                "Don't click any links, forward the email to reportphishing@jagex.com.",
                "Inform Jagex by emailing reportphishing@jagex.com.",
                "Don't give out your password to anyone. Not even close friends.",
                "Politely tell them no and then use the 'Report Abuse' button.",
                "Set up 2 step authentication with my email provider.",
                "No, you should never buy a RuneScape account.",
                "Do not visit the website and report the player who messaged you.",
                "Only on the RuneScape website.",
                "Don't type in my password backwards and report the player.",
                "Virus scan my device then change my password.",
                "No, you should never allow anyone to level your account.",
                "Don't give out your password to anyone. Not even close friends.",
                "Report the stream as a scam. Real Jagex streams have a 'verified' mark.",
                "Read the text and follow the advice given.",
                "No way! I'm reporting you to Jagex!",
                "Talk to any banker in RuneScape.",
                "Secure my device and reset my RuneScape password.",
                "Don't share your information and report the player."
        ));
    }

    public boolean isAnswer(String answer) {
        return set.contains(answer);
    }

}
