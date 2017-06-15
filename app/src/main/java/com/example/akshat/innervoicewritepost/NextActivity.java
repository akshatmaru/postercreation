package com.example.akshat.innervoicewritepost;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.RelativeLayout.LayoutParams;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Akshat on 5/3/2017.
 */

public class NextActivity extends AppCompatActivity {
    TextView quote;
    Button black, blue, red, gold, postImage;
    private int xDelta;
    private int yDelta;
    SeekBar seekBar;
    ImageView quoteBg;

    final int RQS_IMAGE1 = 1;
    Uri source1;
    String quoote;
    int quoteSize = 14;

    int x, y;
    static int WIDTH = 1080;
    static float ratio;

    File mypath;

    private String uniqueId;
    public static String tempDir;
    public String current = null;
    Bitmap newBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.next_page_main);

        quote = (TextView) findViewById(R.id.quote_text);
        black = (Button) findViewById(R.id.black_button);
        blue = (Button) findViewById(R.id.blue_button);
        red = (Button) findViewById(R.id.red_button);
        gold = (Button) findViewById(R.id.gold_button);
        seekBar = (SeekBar) findViewById(R.id.seek_bar);
        quoteBg = (ImageView) findViewById(R.id.quote_bg_image);

        postImage = (Button) findViewById(R.id.post_image);

        source1 = Uri.parse("android.resource://com.example.akshat.innervoicewritepost/drawable/blue");
        quoteBg.setImageResource(R.drawable.blue);

        tempDir = Environment.getExternalStorageDirectory() + "/InnerVoice/";
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        File directory = cw.getDir("InnerVoice", Context.MODE_PRIVATE);

        prepareDirectory();

        uniqueId = getTodaysDate() + "_" + getCurrentTime() + "_" + Math.random();
        current = uniqueId + ".png";
        mypath= new File(directory,current);


        seekBar.setProgress(20);
        seekBar.setMax(100);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if (i<=10){
                    quote.setTextSize(10);
                    quoteSize=10;
                }
                else if (i<=20){
                    quote.setTextSize(14);
                    quoteSize=14;
                }
                else if (i<=30){
                    quote.setTextSize(18);
                    quoteSize=18;
                }
                else if (i<=40){
                    quote.setTextSize(22);
                    quoteSize=22;
                }
                else if (i<=50){
                    quote.setTextSize(26);
                    quoteSize=26;
                }
                else if (i<=60){
                    quote.setTextSize(30);
                    quoteSize=30;
                }
                else if (i<=70){
                    quote.setTextSize(34);
                    quoteSize=34;
                }
                else if (i<=80){
                    quote.setTextSize(38);
                    quoteSize=38;
                }
                else if (i<=90){
                    quote.setTextSize(42);
                    quoteSize=42;
                }
                else if (i<=100){
                    quote.setTextSize(46);
                    quoteSize=46;
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        String strRplc;
        final Intent i = getIntent();
        strRplc = i.getStringExtra("quote");
        quoote = strRplc + "  ";

        quote.setText(quoote);

        black.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                source1 = Uri.parse("android.resource://com.example.akshat.innervoicewritepost/drawable/mountain");
                quoteBg.setImageResource(R.drawable.mountain);
            }
        });

        blue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                source1 = Uri.parse("android.resource://com.example.akshat.innervoicewritepost/drawable/blue");
                quoteBg.setImageResource(R.drawable.blue);

            }
        });

        red.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                source1 = Uri.parse("android.resource://com.example.akshat.innervoicewritepost/drawable/redchx");
                quoteBg.setImageResource(R.drawable.redchx);

            }
        });

        gold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                source1 = Uri.parse("android.resource://com.example.akshat.innervoicewritepost/drawable/goldrplc");
                quoteBg.setImageResource(R.drawable.goldrplc);
            }
        });

        /*chooseImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, RQS_IMAGE1);
                if(newBitmap != null){
                    quoteBg.setImageBitmap(newBitmap);
                }
            }
        });*/

        postImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (source1 != null){
                    quoteBg.setImageBitmap(drawText(quoote,quote.getWidth(),quoteSize));
                    quote.setVisibility(View.GONE);
                }

                /*if(source1 != null){
                    Bitmap processedBitmap = ProcessingBitmap();
                    if(processedBitmap != null){
                        quoteBg.setImageBitmap(processedBitmap);
                        Toast.makeText(getApplicationContext(),
                                "Done",
                                Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(getApplicationContext(),
                                "Something wrong in processing!",
                                Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(getApplicationContext(),
                            "Select both image!",
                            Toast.LENGTH_LONG).show();
                }*/
            }
        });


       quote.setOnTouchListener(onTouchListener());

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        ratio = ((float) metrics.widthPixels) / ((float) WIDTH);

    }


    private String getTodaysDate() {

        final Calendar c = Calendar.getInstance();
        int todaysDate = (c.get(Calendar.YEAR) * 10000) +
                ((c.get(Calendar.MONTH) + 1) * 100) +
                (c.get(Calendar.DAY_OF_MONTH));
        return(String.valueOf(todaysDate));

    }



    private String getCurrentTime() {

        final Calendar c = Calendar.getInstance();
        int currentTime =     (c.get(Calendar.HOUR_OF_DAY) * 10000) +
                (c.get(Calendar.MINUTE) * 100) +
                (c.get(Calendar.SECOND));
        Log.w("TIME:",String.valueOf(currentTime));
        return(String.valueOf(currentTime));

    }


    private boolean prepareDirectory()
    {
        try
        {
            if (makedirs())
            {
                return true;
            } else {
                return false;
            }
        } catch (Exception e)
        {
            e.printStackTrace();
            Toast.makeText(this, "Could not initiate File System.. Is Sdcard mounted properly?", 1000).show();
            return false;
        }
    }


    private boolean makedirs()
    {
        File tempdir = new File(tempDir);
        if (!tempdir.exists())
            tempdir.mkdirs();

        if (tempdir.isDirectory())
        {
            File[] files = tempdir.listFiles();
            for (File file : files)
            {
                if (!file.delete())
                {
                    System.out.println("Failed to delete " + file);
                }
            }
        }
        return (tempdir.isDirectory());
    }





    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            switch (requestCode){
                case RQS_IMAGE1:
                    source1 = data.getData();
                    break;
            }
        }
    }


    public Bitmap drawText(String text, int textWidth, int textSize) {
        // Get text dimensions

        Bitmap bm1 = null;
        newBitmap = null;
        Bitmap b = null;

        try {
            bm1 = BitmapFactory.decodeStream(
                    getContentResolver().openInputStream(source1));

            Bitmap.Config config = bm1.getConfig();
            if(config == null){
                config = Bitmap.Config.ARGB_8888;
            }

            TextPaint textPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG
                    | Paint.LINEAR_TEXT_FLAG);
            textPaint.setStyle(Paint.Style.FILL);
            textPaint.setColor(Color.WHITE);
            textPaint.setTextSize(quoteSize*2);
            StaticLayout mTextLayout = new StaticLayout(text, textPaint,
                    textWidth, Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false);

            // Create bitmap and canvas to draw to
            b = Bitmap.createBitmap(bm1.getWidth(), bm1.getHeight(), config);
            newBitmap = Bitmap.createBitmap(bm1.getWidth(), bm1.getHeight(), config);
            Canvas c = new Canvas(b);

            c.drawBitmap(bm1, 0, 0, null);


            // Draw text
            c.save();
            c.translate((x - xDelta),(y - yDelta)*1.30f);
            mTextLayout.draw(c);
            c.restore();

            FileOutputStream mFileOutStream = new FileOutputStream(mypath);
            b.compress(Bitmap.CompressFormat.PNG, 90, mFileOutStream);
            mFileOutStream.flush();
            mFileOutStream.close();
            String url = MediaStore.Images.Media.insertImage(getContentResolver(), b, "title", null);


        }catch (Exception e){

        }

        return b;
    }


   /* private Bitmap ProcessingBitmap(){
        Bitmap bm1 = null;
        Bitmap newBitmap = null;

        try {
            bm1 = BitmapFactory.decodeStream(
                    getContentResolver().openInputStream(source1));

            Bitmap.Config config = bm1.getConfig();
            if(config == null){
                config = Bitmap.Config.ARGB_8888;
            }

            newBitmap = Bitmap.createBitmap(bm1.getWidth(), bm1.getHeight(), config);
            Canvas newCanvas = new Canvas(newBitmap);

            newCanvas.drawBitmap(bm1, 0, 0, null);

            String captionString = quoote;
            if(captionString != null){

                Paint paintText = new Paint(Paint.ANTI_ALIAS_FLAG);
                paintText.setColor(Color.BLACK);
                paintText.setTextSize(50);
                paintText.setStyle(Paint.Style.FILL);

                Rect rectText = new Rect();
                paintText.getTextBounds(captionString, 0, captionString.length(), rectText);

                newCanvas.drawText(captionString,
                        xDelta, yDelta, paintText);

                Toast.makeText(getApplicationContext(),
                        "drawText: " + captionString,
                        Toast.LENGTH_LONG).show();

            }else{
                Toast.makeText(getApplicationContext(),
                        "caption empty!",
                        Toast.LENGTH_LONG).show();
            }

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return newBitmap;
    }*/



    private View.OnTouchListener onTouchListener() {
        return new View.OnTouchListener() {

            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View view, MotionEvent event) {

                x = (int) event.getRawX();
                y = (int) event.getRawY();

                System.out.println(" this is x " + x);
                System.out.println(" this is y " + y);


                switch (event.getAction() & MotionEvent.ACTION_MASK) {

                    case MotionEvent.ACTION_DOWN:
                        RelativeLayout.LayoutParams lParams = (RelativeLayout.LayoutParams)
                                view.getLayoutParams();

                        xDelta = x - lParams.leftMargin;
                        yDelta = y - lParams.topMargin;

                        System.out.println(" this is xdelta " + xDelta);
                        System.out.println(" this is ydelta " + yDelta);
                        break;

                    case MotionEvent.ACTION_UP:
                        break;

                    case MotionEvent.ACTION_MOVE:
                        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view
                                .getLayoutParams();
                        layoutParams.leftMargin = x - xDelta;
                        layoutParams.topMargin = y - yDelta;
                        layoutParams.rightMargin = 0;
                        layoutParams.bottomMargin = 0;
                        view.setLayoutParams(layoutParams);

                        System.out.println(" this is x - xdelta " + (x - xDelta));
                        System.out.println(" this is y - ydelta " + (y - yDelta));

                        break;
                }
                quoteBg.invalidate();
                return true;
            }
        };
    }
}
