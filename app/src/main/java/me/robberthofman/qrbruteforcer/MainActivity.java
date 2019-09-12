package me.robberthofman.qrbruteforcer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import com.journeyapps.barcodescanner.BarcodeEncoder;

import android.os.Handler;

public class MainActivity extends AppCompatActivity {

    private QRCodeWriter writer = new QRCodeWriter();
    private BitMatrix qrBitMatrix;
    private Bitmap qrBitmap;
    private int sizeInPixels = 200;
    private String toEncode = "0000-0000";
    private BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
    private SeekBar speedSeekBar;
    private TextView speedTextView;
    private SeekBar stringLengthSeekBar;
    private TextView stringLengthTextView;
    private int stringLength = 1;
    private int speed = 1;
    private boolean running = false;

    private Handler handler = new Handler();
    private Runnable timedTask = new Runnable(){
        @Override
        public void run() {
            if (running) {
                updateQrCode();
                handler.postDelayed(timedTask, (1000 / speed));
            }
        }};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); // set entire app to portrait
        setContentView(R.layout.activity_main);
        setup();

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!running) {
                    running = true;
                    handler.post(timedTask);
                }
                else {
                    running = false;
                }
            }
        });
    }


    void setup() {
        speedSeekBar = findViewById(R.id.speedSeekBar);
        speedTextView = findViewById(R.id.speedTextView);
        stringLengthSeekBar = findViewById(R.id.stringLengthSeekBar);
        stringLengthTextView = findViewById(R.id.stringLengthTextView);

        speedTextView.setText("speed:");
        stringLengthTextView.setText("length:");

        speedSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                updateSpeed(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        stringLengthSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                updateLength(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

    void updateSpeed(int newSpeed){
        speed = Math.max(1,newSpeed);
        speedTextView.setText("speed: "+speed);
    }

    void updateLength(int newLength){
        stringLength = Math.max(1, Math.round(newLength/5)); // brute force above 20 characters would take too long anyways
        stringLengthTextView.setText("length: "+stringLength);
    }

    void updateQrCode(){
        toEncode = RandomString.randomAlphaNumeric(stringLength);

        try {
             qrBitMatrix = writer.encode(toEncode, BarcodeFormat.QR_CODE, sizeInPixels, sizeInPixels);
        } catch (WriterException e) {
            e.printStackTrace();
        }
        qrBitmap = barcodeEncoder.createBitmap(qrBitMatrix);

        TextView textView = findViewById(R.id.textView);
        textView.setText(toEncode); // display encoded string

        ImageView imageView = findViewById(R.id.imageView);
        imageView.setImageBitmap(qrBitmap); // display new qr code
    }
}
