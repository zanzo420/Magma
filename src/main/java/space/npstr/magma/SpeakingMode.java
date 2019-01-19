package space.npstr.magma;

import edu.umd.cs.findbugs.annotations.Nullable;

import java.util.EnumSet;

public enum SpeakingMode {
    VOICE(1), SOUNDSHARE(2), PRIORITY(4);

    private final int key;

    SpeakingMode(int key) {
        this.key = key;
    }

    public int getKey() {
        return key;
    }

    public static int toMask(@Nullable EnumSet<SpeakingMode> mode)
    {
        if (mode == null || mode.isEmpty())
            return 0;
        int mask = 0;
        for (SpeakingMode m : mode)
            mask |= m.getKey();
        return mask;
    }
}
