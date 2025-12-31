package me.dabao1955.quickformatdata;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.UserHandle;
import android.provider.Settings;
import android.widget.Toast;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 不再检查 root，而是直接提供功能（由系统权限控制是否生效）
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("要留清白在人间");
        builder.setMessage("请问你真的要留住清白吗？\n此操作将恢复出厂设置，清除所有用户数据！");

        builder.setPositiveButton("是的", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(MainActivity.this, "正在执行恢复出厂设置...", Toast.LENGTH_SHORT).show();

                // 使用标准 MASTER_CLEAR Intent
                Intent intent = new Intent(Intent.ACTION_MASTER_CLEAR);
                intent.addFlags(Intent.FLAG_RECEIVER_FOREGROUND);
                intent.putExtra(Intent.EXTRA_REASON, "user_requested");
                intent.putExtra(Intent.EXTRA_WIPE_EXTERNAL_STORAGE, false); // 可选：是否清除SD卡

                // 必须指定包名（某些 ROM 要求）
                intent.setPackage("android");

                // 发送给系统 MasterClearReceiver
                sendBroadcastAsUser(intent, UserHandle.SYSTEM);
            }
        });

        builder.setNegativeButton("我偏不", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(MainActivity.this, "清白再见👋🏻", Toast.LENGTH_SHORT).show();
                finish(); // 或直接退出
            }
        });

        builder.setCancelable(false);
        builder.show();
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                Toast.makeText(this, "别点了，没什么用的，只是用来测试而已", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_about:
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("关于");
                                final TextView aboutTextView = new TextView(this);
                aboutTextView.setText("作者：dabao1955\n开源地址：https://github.com/SekaiMoe/qingbai");
                                aboutTextView.setAutoLinkMask(Linkify.WEB_URLS);
                                aboutTextView.setMovementMethod(LinkMovementMethod.getInstance());
                                aboutTextView.setPadding(50, 50, 50, 50);
                                builder.setView(aboutTextView);
                                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                                public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                }
                                });
                                builder.show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
