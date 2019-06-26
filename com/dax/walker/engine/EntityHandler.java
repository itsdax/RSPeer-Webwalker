package com.dax.walker.engine;

import com.allatori.annotations.DoNotRename;
import com.dax.walker.engine.definitions.PathHandleState;
import com.dax.walker.engine.definitions.StrongHoldAnswers;
import com.dax.walker.engine.definitions.WalkCondition;
import com.dax.walker.engine.pathfinding.BFSMapCache;
import com.dax.walker.engine.utils.RunManager;
import org.rspeer.runetek.adapter.Positionable;
import org.rspeer.runetek.adapter.component.InterfaceComponent;
import org.rspeer.runetek.adapter.scene.Npc;
import org.rspeer.runetek.api.commons.Time;
import org.rspeer.runetek.api.component.Dialog;
import org.rspeer.runetek.api.movement.position.Position;
import org.rspeer.runetek.api.movement.transportation.CharterShip;
import org.rspeer.runetek.api.scene.Npcs;

import java.util.Arrays;
import java.util.Comparator;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Pattern;

@DoNotRename
public class EntityHandler {

    private static final Pattern LIKELY = Pattern.compile("(?i)(yes|ok(ay)?|sure|alright|fine|(.*search away.*)|(.*can i.*?)).*");
    private static final Pattern UNLIKELY = Pattern.compile("(?i)no(.|.thank.*|.sorry*+)?");

    public static PathHandleState handleWithAction(Pattern action, Position start, Position end, WalkCondition walkCondition) {
        Npc target = Arrays.stream(Npcs.getLoaded(npc -> npc.containsAction(s -> action.matcher(s).matches())))
                .min(Comparator.comparingDouble(Positionable::distance))
                .orElse(null);

        if (target == null) return PathHandleState.FAILED;
        if (!target.interact(s -> action.matcher(s).matches())) return PathHandleState.FAILED;

        AtomicBoolean exitCondition = new AtomicBoolean(false);
        if (waitFor(end, exitCondition, walkCondition) && exitCondition.get()) return PathHandleState.EXIT;
        if (!handleConversation()) return PathHandleState.FAILED;
        if (waitFor(end, exitCondition, walkCondition) && exitCondition.get()) return PathHandleState.EXIT;
        return new BFSMapCache().canReach(end) ? PathHandleState.SUCCESS : PathHandleState.FAILED;
    }

    public static PathHandleState handleCharter(CharterShip.Destination destination, WalkCondition walkCondition) {
        if (!CharterShip.open()) return PathHandleState.FAILED;
        AtomicBoolean exitCondition = new AtomicBoolean(false);
        if (waitFor(destination.getPosition(), exitCondition, walkCondition) && exitCondition.get()) return PathHandleState.EXIT;
        if (!CharterShip.isInterfaceOpen()) return PathHandleState.FAILED;
        return CharterShip.charter(destination) ? PathHandleState.SUCCESS : PathHandleState.FAILED;
    }

    public static boolean selectOption() {
        InterfaceComponent option = Arrays.stream(Dialog.getChatOptions())
                .max(Comparator.comparingInt(o -> getResponseValue(o.getText())))
                .orElse(null);
        return option != null && option.click();
    }

    public static boolean handleConversation() {
        while (Dialog.isOpen()) {
            DialogState state = getDialogState();
            if (state == null) break;
            switch (state) {
                case CONTINUE:
                    if (!Dialog.processContinue()) return false;
                    waitNextDialogAction();
                    break;
                case CHOOSE:
                    if (!selectOption()) return false;
                    waitNextDialogAction();
                    break;
                case WAITING:
                    waitNextDialogAction();
                    break;
                case CLOSED:
                    return true;
            }
        }
        return true;
    }

    public static void waitNextDialogAction() {
        Time.sleep(100, 200);
        Time.sleepUntil(() -> {
            DialogState state = getDialogState();
            return state == DialogState.CONTINUE || state == DialogState.CHOOSE || !Dialog.isOpen();
        }, 3500);
    }

    private static DialogState getDialogState() {
        if (!Dialog.isOpen()) return DialogState.CLOSED;
        if (Dialog.isProcessing()) return DialogState.WAITING;
        if (Dialog.canContinue()) return DialogState.CONTINUE;
        if (Dialog.getChatOptions().length > 0) return DialogState.CHOOSE;
        return null;
    }

    private static boolean waitFor(Position end, AtomicBoolean exitCondition, WalkCondition walkCondition) {
        RunManager runManager = new RunManager();
        return Time.sleepUntil(() -> {
            if (!runManager.isWalking()) {
                return true;
            }
            if (walkCondition.getAsBoolean()) {
                exitCondition.set(true);
                return true;
            }
            return new BFSMapCache().canReach(end);
        }, 12000);
    }

    private static int getResponseValue(String text) {
        int a = 0;
        if (LIKELY.matcher(text).matches()) a++;
        if (UNLIKELY.matcher(text).matches()) a--;
        if (StrongHoldAnswers.getInstance().isAnswer(text)) a += 2;
        return a;
    }

    private enum DialogState {
        CONTINUE,
        CHOOSE,
        WAITING,
        CLOSED,
    }

}
