package org.pntest.pubnubv4;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.pubnub.api.PNConfiguration;
import com.pubnub.api.PubNub;
import com.pubnub.api.enums.PNReconnectionPolicy;

import java.util.Collections;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String PUB_KEY = "";
    private static final String SUB_KEY = "";
    private static final String SEC_KEY = "";

    private PubNub pubnub;
    private TextView logView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        logView = (TextView) findViewById(R.id.log);

        findViewById(R.id.sub_a).setOnClickListener(this);
        findViewById(R.id.sub_b).setOnClickListener(this);
        findViewById(R.id.unsub_a).setOnClickListener(this);
        findViewById(R.id.unsub_b).setOnClickListener(this);
        findViewById(R.id.clear).setOnClickListener(this);

        pubnub = createPubNub();
        pubnub.addListener(new PubNubListener() {
            @Override
            protected void log(String record) {
                MainActivity.this.log(record);
            }
        });
    }

    private PubNub createPubNub() {
        PNConfiguration config = new PNConfiguration();
        config.setPublishKey(PUB_KEY);
        config.setSubscribeKey(SUB_KEY);
        config.setSecretKey(SEC_KEY);

        config.setOrigin("ps.pndsn.com");
        config.setReconnectionPolicy(PNReconnectionPolicy.EXPONENTIAL);

        return new PubNub(config);
    }

    private void log(final String record) {
        logView.post(new Runnable() {
            @Override
            public void run() {
                logView.setText(record + "\n\n" + logView.getText().toString());
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sub_a:
                subscribe("a");
                break;

            case R.id.sub_b:
                subscribe("b");
                break;

            case R.id.unsub_a:
                unsubscribe("a");
                break;

            case R.id.unsub_b:
                unsubscribe("b");
                break;

            case R.id.clear:
                logView.setText(null);
                break;

        }
    }

    private void unsubscribe(String channel) {
        pubnub.unsubscribe()
                .channels(Collections.singletonList(channel))
                .execute();
    }

    private void subscribe(String channel) {
        pubnub.subscribe()
                .channels(Collections.singletonList(channel))
                .execute();
    }
}
