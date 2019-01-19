package space.npstr.magma.events.audio.lifecycle;

import edu.umd.cs.findbugs.annotations.Nullable;
import org.immutables.value.Value;
import space.npstr.magma.Member;
import space.npstr.magma.SpeakingMode;
import space.npstr.magma.immutables.ImmutableLcEvent;

import java.util.EnumSet;

@Value.Immutable
@ImmutableLcEvent
public abstract class UpdateSpeakingMode implements LifecycleEvent {
    @Override
    public abstract Member getMember();
    @Nullable
    public abstract EnumSet<SpeakingMode> getSpeakingModes();
}
