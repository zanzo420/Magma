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

package space.npstr.magma.events.audio.ws;

import org.immutables.value.Value;
import org.json.JSONObject;
import space.npstr.magma.events.audio.ws.in.InboundWsEvent;
import space.npstr.magma.events.audio.ws.out.OutboundWsEvent;
import space.npstr.magma.immutables.ImmutableWsEvent;

/**
 * Created by napster on 21.04.18.
 */
@Value.Immutable
@ImmutableWsEvent
public abstract class Speaking implements InboundWsEvent, OutboundWsEvent {

    @Override
    public int getOpCode() {
        return OpCode.SPEAKING;
    }

    public abstract String getUserId();

    public abstract int getSpeakingMask();

    public abstract int getSsrc();

    @Override
    public Object getData() {
        return new JSONObject()
                .put("speaking", this.getSpeakingMask())
                .put("delay", 0)
                .put("ssrc", getSsrc())
                ;
    }
}
