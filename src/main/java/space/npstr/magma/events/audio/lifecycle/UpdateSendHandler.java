/*
 * Copyright 2018 Dennis Neufeld
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package space.npstr.magma.events.audio.lifecycle;

import net.dv8tion.jda.api.audio.AudioSendHandler;
import org.immutables.value.Value;
import space.npstr.magma.Member;
import space.npstr.magma.immutables.ImmutableLcEvent;

import java.util.Optional;

/**
 * Created by napster on 24.04.18.
 */
@Value.Immutable
@ImmutableLcEvent
public abstract class UpdateSendHandler implements LifecycleEvent {

    @Override
    public abstract Member getMember();

    public abstract Optional<AudioSendHandler> getAudioSendHandler();
}
