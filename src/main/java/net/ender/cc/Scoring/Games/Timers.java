package net.ender.cc.Scoring.Games;

public enum Timers {
    PRE_GAME(0, "Does nothing", 0),
    START(1, "Game Begins", 15),
    RELEASE(2, "Release",30),
    FIRST_SHRINK(3, "First Shrink",600),
    SECOND_SHRINK(4, "Second Shrink", 840),
    THIRD_SHINK(5, "Third Shrink", 1380),
    END(6, "Game Ends", 1800);
    private final long seconds;
    private final int index;
    private final String displayName;
    Timers (int index, String displayName, long seconds) {
        this.index = index;
        this.seconds = seconds;
        this.displayName = displayName;
    }

    public long getSeconds() {
        return seconds;
    }

    public String getDisplayName() {
        return displayName;
    }

    public int getIndex() {
        return index;
    }

    public static Timers getTimerFromIndex(int index) {
        for (Timers timer : Timers.values()) {
            if (index == timer.getIndex()) {
                return timer;
            }
        }
        return null;
    }
}
