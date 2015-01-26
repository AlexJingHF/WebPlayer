package com.alexjing.webplayer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.alexjing.webplayer.ui.VideoActivity;

public class MainActivity extends Activity {
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_play).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "/mnt/sdcard/Movies/[武林外传].05.展红纹千里定扒手.郭芙蓉一心迷盗圣.rmvb";
                String[] urls=new String[] {url};
                Intent intent=new Intent(MainActivity.this,VideoActivity.class);
                intent.putExtra("video_path", urls);
                intent.putExtra("video_name", "[武林外传].05.展红纹千里定扒手.郭芙蓉一心迷盗圣.rmvb");
                MainActivity.this.startActivity(intent);

            }
        });
    }
}
