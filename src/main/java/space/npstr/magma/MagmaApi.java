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

package space.npstr.magma;

import edu.umd.cs.findbugs.annotations.Nullable;
import net.dv8tion.jda.api.audio.AudioSendHandler;
import net.dv8tion.jda.api.audio.factory.IAudioSendFactory;
import org.xnio.OptionMap;
import org.xnio.XnioWorker;
import reactor.core.publisher.Flux;
import space.npstr.magma.events.api.MagmaEvent;

import java.net.DatagramSocket;
import java.util.EnumSet;
import java.util.List;
import java.util.function.Function;

/**
 * Created by napster on 24.04.18.
 * <p>
 * Public API. These methods may be called by users of Magma.
 */
@SuppressWarnings("unused")
public interface MagmaApi {

    /**
     * Please see full factory documentation below. Missing parameters on this factory method are optional.
     */
    static MagmaApi of(final Function<Member, IAudioSendFactory> sendFactoryProvider) {
        return of(sendFactoryProvider, OptionMap.builder().getMap());
    }

    /**
     * Create a new Magma instance. More than one of these is not necessary, even if you are managing several shards and
     * several bot accounts. A single instance of this scales automatically according to your needs and hardware.
     *
     * @param sendFactoryProvider
     *         a provider of {@link IAudioSendFactory}s. It will have members applied to it.
     * @param xnioOptions
     *         options to build the {@link XnioWorker} that will be used for the websocket connections
     */
    static MagmaApi of(final Function<Member, IAudioSendFactory> sendFactoryProvider,
                       final OptionMap xnioOptions) {
        return new Magma(sendFactoryProvider, xnioOptions);
    }

    /**
     * The UDP client used to NAT hole punch.
     * <br>This is closed by {@link #shutdown()}.
     *
     * @return The DatagramSocket
     */
    DatagramSocket getDatagramSocket();

    /**
     * Release all resources held.
     */
    void shutdown();

    /**
     * @return a Reactor stream that can be subscribed to for event handling
     */
    Flux<MagmaEvent> getEventStream();

    /**
     * Also see: https://discordapp.com/developers/docs/topics/voice-connections#retrieving-voice-server-information-example-voice-server-update-payload
     *
     * @param member
     *         Id of the bot account that this update belongs to composed with the id of the guild whose voice server
     *         shall be updated. The user id is something your use code should keep track of, the guild id can be
     *         extracted from the op 0 VOICE_SERVER_UPDATE event that should be triggering a call to this method in the
     *         first place.
     * @param serverUpdate
     *         A composite of session id, endpoint and token. Most of that information can be extracted from the op 0
     *         VOICE_SERVER_UPDATE event that should be triggering a call to this method in the first place.
     *
     * @see Member
     * @see ServerUpdate
     */
    void provideVoiceServerUpdate(final Member member, final ServerUpdate serverUpdate);

    /**
     * Set the {@link AudioSendHandler} for a bot member.
     *
     * @param member
     *         user id + guild id of the bot member for which the send handler shall be set
     * @param sendHandler
     *         The send handler to be set. You need to implement this yourself. This is a JDA interface so if you have
     *         written voice code with JDA before you reuse your existing code.
     *
     * @see Member
     */
    void setSendHandler(final Member member, final AudioSendHandler sendHandler);

    /**
     * The {@link space.npstr.magma.SpeakingMode SpeakingMode} to use.
     *
     * @param member
     *         user id + guild id of the bot member for which the speaking mode shall be set
     * @param mode
     *         EnumSet containing the speaking modes to apply
     *
     * @see Member
     */
    void setSpeakingMode(final Member member, @Nullable final EnumSet<SpeakingMode> mode);

    /**
     * Remove the {@link AudioSendHandler} for a bot member.
     *
     * @param member
     *         user id + guild id of the bot member for which the send handler shall be removed
     *
     * @see Member
     */
    void removeSendHandler(final Member member);

    /**
     * Close the audio connection for a bot member.
     *
     * @param member
     *         user id + guild id of the bot member for which the audio connection shall be closed
     *
     * @see Member
     */
    void closeConnection(final Member member);


    /**
     * @return a list of all {@link WebsocketConnectionState WebsocketConnectionStates} detailing the state of
     * the {@link AudioStack AudioStacks} managed by this {@link MagmaApi} instance
     */
    List<WebsocketConnectionState> getAudioConnectionStates();
}
