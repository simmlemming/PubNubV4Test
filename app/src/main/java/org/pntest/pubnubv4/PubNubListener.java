package org.pntest.pubnubv4;

/**
 * Created by mtkachenko on 18/01/17.
 */

import com.pubnub.api.PubNub;
import com.pubnub.api.callbacks.SubscribeCallback;
import com.pubnub.api.models.consumer.PNStatus;
import com.pubnub.api.models.consumer.pubsub.PNMessageResult;
import com.pubnub.api.models.consumer.pubsub.PNPresenceEventResult;


/**
 * Created by mtkachenko on 13/01/17.
 */

abstract class PubNubListener extends SubscribeCallback {

    @Override
    public void status(PubNub pubnub, PNStatus status) {
        log("Listener.status(...):\n" + toString(status));
    }

    @Override
    public void message(PubNub pubnub, PNMessageResult message) {
        log("Listener.message(...)\n" +  toString(message));
    }

    @Override
    public void presence(PubNub pubnub, PNPresenceEventResult presence) {
        log("Listener.presence() called with: " + "pubnub = [" + pubnub + "], presence = [" + presence + "]");
    }

    protected abstract void log(String record);

    private String toString(PNMessageResult message) {
        StringBuilder m = new StringBuilder("Message");

        m.append("\n    txt = ");
        m.append(message.getMessage());

        m.append("\n    ch  = ");
        m.append(message.getChannel());

        m.append("\n    sub = ");
        m.append(message.getSubscription());

        return m.toString();
    }

    private String toString(PNStatus status) {
        StringBuilder result = new StringBuilder("Status");

        result.append("\n    cat = ");
        result.append(status.getCategory());

        result.append("\n    op  = ");
        result.append(status.getOperation());

        result.append("\n    ch  = ");
        result.append(String.valueOf(status.getAffectedChannels()));

        result.append("\n    err = ");
        result.append(String.valueOf(status.isError()));

        if (status.isError()) {
            result.append("\n    err  = ");
            result.append(status.getErrorData().getThrowable());
        }

        if (status.getClientRequest() != null) {
            result.append("\n    req = ");
            result.append(status.getClientRequest().getClass().getName());
        }

        return result.toString();
    }
}
