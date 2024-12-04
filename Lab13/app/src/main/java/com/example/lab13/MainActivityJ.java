package com.example.lab13;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    // 建立 BroadcastReceiver 物件
    private final BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // 接收廣播後，解析 Intent 取得字串訊息
            if (intent.getExtras() != null) {
                TextView tvMsg = findViewById(R.id.tvMsg);
                tvMsg.setText(intent.getExtras().getString("msg", ""));
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Enable Edge-to-Edge Layout
        enableEdgeToEdge();

        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            WindowInsetsCompat systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // 設定按鈕的點擊事件
        findViewById(R.id.btnMusic).setOnClickListener(v -> register("music"));
        findViewById(R.id.btnNew).setOnClickListener(v -> register("new"));
        findViewById(R.id.btnSport).setOnClickListener(v -> register("sport"));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 解除註冊廣播接收器
        unregisterReceiver(receiver);
    }

    private void register(String channel) {
        // 建立 IntentFilter 物件來指定接收的頻道
        IntentFilter intentFilter = new IntentFilter(channel);

        // 註冊廣播接收器
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            registerReceiver(receiver, intentFilter, RECEIVER_EXPORTED);
        } else {
            registerReceiver(receiver, intentFilter);
        }

        // 建立 Intent 物件，使其夾帶頻道資料，並啟動 MyService 服務
        Intent i = new Intent(this, MyService.class);
        startService(i.putExtra("channel", channel));
    }

    private void enableEdgeToEdge() {
        // This function needs to be implemented for edge-to-edge functionality
        // It can be left as a stub if not specifically required in the Java version.
    }
}
