package com.example.akshat.innervoicewritepost;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v4.view.GravityCompat;
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

import com.soundcloud.android.crop.Crop;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

import static android.text.Layout.Alignment.ALIGN_CENTER;
import static android.text.Layout.Alignment.ALIGN_NORMAL;
import static android.text.Layout.Alignment.ALIGN_OPPOSITE;

/**
 * Created by Akshat on 5/3/2017.
 */

public class NextActivity extends AppCompatActivity {
    TextView quote;
    Button black, blue, red, gold, postImage;
    private int xDelta;
    private int yDelta;
    private int xDelta1;
    private int yDelta1;
    SeekBar seekBar;
    ImageView quoteBg;

    final int RQS_IMAGE1 = 1;
    Uri source1, inputUri, outputUri;
    String quoote;
    int quoteSize = 14;

    int x;
    int y;
    float x1;
    float y1;
    int x1new;
    int y1new;
    static int WIDTH = 1080;
    static float ratio;

    File mypath;

    private String uniqueId;
    public static String tempDir;
    public String current = null;
    Bitmap newBitmap;
    float wid, hig, wid1, hig1;
    Typeface gotham;
    String color = "#ffffff";
    ImageView getImage;
    Button chooseImg;

    private final int GALLERY_ACTIVITY_CODE=200;
    private final int RESULT_CROP = 400;
    String align = "ALIGN_NORMAL";
    Button leftAlign, centerAlign, rightAlign;
    float xS=0f, yS=0f;

    float factorx ;
    float factory ;


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
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
        getImage = (ImageView) findViewById(R.id.getImage);

        chooseImg = (Button) findViewById(R.id.choose_image);
        postImage = (Button) findViewById(R.id.post_image);

        leftAlign = (Button) findViewById(R.id.left_align);
        centerAlign = (Button) findViewById(R.id.center_align);
        rightAlign = (Button) findViewById(R.id.right_align);

        gotham = Typeface.createFromAsset(getAssets(),"gotham-medium.ttf");

        source1 = Uri.parse("android.resource://com.example.akshat.innervoicewritepost/drawable/mclr");
        quoteBg.setImageResource(R.drawable.mclr);

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
                    quote.setTextSize(12);
                    quoteSize=12;
                }
                else if (i<=30){
                    quote.setTextSize(14);
                    quoteSize=14;
                }
                else if (i<=40){
                    quote.setTextSize(16);
                    quoteSize=16;
                }
                else if (i<=50){
                    quote.setTextSize(18);
                    quoteSize=18;
                }
                else if (i<=60){
                    quote.setTextSize(20);
                    quoteSize=20;
                }
                else if (i<=70){
                    quote.setTextSize(22);
                    quoteSize=22;
                }
                else if (i<=80){
                    quote.setTextSize(24);
                    quoteSize=24;
                }
                else if (i<=90){
                    quote.setTextSize(26);
                    quoteSize=26;
                }
                else if (i<=100){
                    quote.setTextSize(28);
                    quoteSize=28;
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });


        MediaScannerConnection.scanFile(this, new String[] { Environment.getExternalStorageDirectory().toString() }, null, new MediaScannerConnection.OnScanCompletedListener() {
            /*
             *   (non-Javadoc)
             * @see android.media.MediaScannerConnection.OnScanCompletedListener#onScanCompleted(java.lang.String, android.net.Uri)
             */
            public void onScanCompleted(String path, Uri uri)
            {
            }
        });


        String strRplc;
        final Intent i = getIntent();
        strRplc = i.getStringExtra("quote");
        quoote = strRplc + "";

        quote.setText(quoote);
        quote.setTypeface(gotham);

        quote.measure(0,0);
        factorx = quote.getMeasuredWidth() / 2.0f;
        factory = quote.getMeasuredHeight() / 2.0f;


        black.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                source1 = Uri.parse("android.resource://com.example.akshat.innervoicewritepost/drawable/ipad");
                quoteBg.setImageResource(R.drawable.ipad);
            }
        });

        blue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                source1 = Uri.parse("android.resource://com.example.akshat.innervoicewritepost/drawable/nitra");
                quoteBg.setImageResource(R.drawable.nitra);

            }
        });

        red.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                source1 = Uri.parse("android.resource://com.example.akshat.innervoicewritepost/drawable/mclr");
                quoteBg.setImageResource(R.drawable.mclr);

            }
        });

        gold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                source1 = Uri.parse("android.resource://com.example.akshat.innervoicewritepost/drawable/gold");
                quoteBg.setImageResource(R.drawable.gold);
            }
        });

        chooseImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, RQS_IMAGE1);
                if(newBitmap != null){
                    quoteBg.setImageBitmap(newBitmap);
                }

//                Intent gallery_Intent = new Intent(getApplicationContext(), GalleryUtil.class);
//                startActivityForResult(gallery_Intent, GALLERY_ACTIVITY_CODE);
            }
        });

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


        leftAlign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                quote.setGravity(Gravity.LEFT);
                align = "ALIGN_NORMAL";
                xS=0f;
                yS=0f;
            }
        });

        centerAlign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                quote.setGravity(Gravity.CENTER);
                align = "ALIGN_CENTER";
                xS=10f;
                yS=1f;

            }
        });

        rightAlign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                quote.setGravity(Gravity.RIGHT);
                align = "ALIGN_OPPOSITE";
                xS=15f;
                yS=1f;
            }
        });


        //quote.setOnTouchListener(onTouchListener());
        quoteBg.setOnTouchListener(ImageOnTouchListener());


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
                (c.get(Calendar.MINUTE) * 100) ;
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
                    inputUri = data.getData();
                    File file = new File(Environment.getExternalStorageDirectory()+File.separator + "img.png");
                    outputUri = Uri.fromFile(file);
                    Crop.of(inputUri, outputUri).asSquare().start(this);
                    break;
            }
        }

        if (requestCode == Crop.REQUEST_CROP && resultCode == RESULT_OK) {
            source1 = outputUri;
            try {
                Bitmap background = BitmapFactory.decodeStream(
                        getContentResolver().openInputStream(outputUri));
                quoteBg.setImageBitmap(background);
                quoteBg.setScaleType(ImageView.ScaleType.FIT_XY);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }


        /*if (requestCode == GALLERY_ACTIVITY_CODE) {
            if(resultCode == Activity.RESULT_OK){
                String picturePath = data.getStringExtra("picturePath");
                //perform Crop on the Image Selected from Gallery
                performCrop(picturePath);
            }
        }

        if (requestCode == RESULT_CROP ) {
            if(resultCode == Activity.RESULT_OK){
                Bundle extras = data.getExtras();
                Bitmap selectedBitmap = extras.getParcelable("data");
                quoteBg.setImageBitmap(selectedBitmap);
                quoteBg.setScaleType(ImageView.ScaleType.FIT_XY);
                FileOutputStream mFileOutStream = null;
                try {
                    File file = new File(Environment.getExternalStorageDirectory()+File.separator + "img.png");
                    mFileOutStream = new FileOutputStream(file);
                    selectedBitmap.compress(Bitmap.CompressFormat.PNG, 100, mFileOutStream);
                    mFileOutStream.flush();
                    mFileOutStream.close();
                    String url = MediaStore.Images.Media.insertImage(getContentResolver(), selectedBitmap, "title", null);
                    source1 = Uri.fromFile(file);

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }*/
    }


    private void performCrop(String picUri) {
        try {
            //Start Crop Activity

            Intent cropIntent = new Intent("com.android.camera.action.CROP");
            // indicate image type and Uri
            File f = new File(picUri);
            Uri contentUri = Uri.fromFile(f);

            cropIntent.setDataAndType(contentUri, "image/*");
            // set crop properties
            cropIntent.putExtra("crop", "true");
            // indicate aspect of desired crop
            cropIntent.putExtra("aspectX", 1);
            cropIntent.putExtra("aspectY", 1);
            // indicate output X and Y
            cropIntent.putExtra("outputX", 1080);
            cropIntent.putExtra("outputY", 1080);

            // retrieve data on return
            cropIntent.putExtra("return-data", true);
            // start the activity - we handle returning in onActivityResult
            startActivityForResult(cropIntent, RESULT_CROP);
        }
        // respond to users whose devices do not support the crop action
        catch (ActivityNotFoundException anfe) {
            // display an error message
            String errorMessage = "your device doesn't support the crop action!";
            Toast toast = Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT);
            toast.show();
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

            newBitmap = BitmapFactory.decodeStream(
                    getContentResolver().openInputStream(Uri.parse("android.resource://com.example.akshat.innervoicewritepost/drawable/wittywhite")));


            Bitmap.Config config = bm1.getConfig();
            if(config == null){
                config = Bitmap.Config.ARGB_8888;
            }

            Bitmap.Config config1 = newBitmap.getConfig();
            if(config1 == null){
                config1 = Bitmap.Config.ARGB_8888;
            }

            TextPaint textPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG
                    | Paint.LINEAR_TEXT_FLAG);
            textPaint.setStyle(Paint.Style.FILL);
            textPaint.setColor(Color.parseColor(color));
            textPaint.setTextAlign(Paint.Align.CENTER);
            textPaint.setTypeface(gotham);
            textPaint.setTextSize((quoteSize*2)-2);
            StaticLayout mTextLayout = new StaticLayout(text, textPaint,
                    textWidth, Layout.Alignment.valueOf(align), 1.0f, 0.0f, false);

            // Create bitmap and canvas to draw to
            b = Bitmap.createScaledBitmap(bm1, 700, 700, true);
            Canvas c = new Canvas(b);

            c.drawBitmap(bm1, 700, 700, null);
            c.drawBitmap(newBitmap, 530, 20, null);

            // Draw text
            c.save();

            Rect bounds = new Rect();
            textPaint.getTextBounds(text, 0, text.length(), bounds);
            int xlat = 0;
            int ylat = 0;
            ylat = (c.getHeight() / 2) - (bounds.height() / 2);
            xlat = (c.getWidth()/2);

            //c.translate((x1-factorx)-xS,(y1-factory)-yS);
            c.translate(xlat,ylat);
            mTextLayout.draw(c);
            c.restore();

            File file = new File(Environment.getExternalStorageDirectory()+File.separator + "post.png");
            FileOutputStream mFileOutStream = new FileOutputStream(file);
            b.compress(Bitmap.CompressFormat.PNG, 100, mFileOutStream);
            mFileOutStream.flush();
            mFileOutStream.close();
            String url = MediaStore.Images.Media.insertImage(getContentResolver(), b, "title", null);
            Bitmap urr;
            urr = BitmapFactory.decodeFile(file.getAbsolutePath());
            getImage.setImageBitmap(urr);


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

                Matrix inverse = new Matrix();
                quoteBg.getImageMatrix().invert(inverse);

// map touch point from ImageView to image
                float[] touchPoint = new float[] {event.getX(), event.getY()};
                inverse.mapPoints(touchPoint);
                System.out.println("touuuuuuuuucccccHHHHH" + touchPoint);


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


    private View.OnTouchListener ImageOnTouchListener() {
        return new View.OnTouchListener() {

            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View view, MotionEvent event) {

                float eventX = event.getX();
                float eventY = event.getY();
                float[] eventXY = new float[] {eventX, eventY};

                Matrix invertMatrix = new Matrix();
                ((ImageView)view).getImageMatrix().invert(invertMatrix);


                invertMatrix.mapPoints(eventXY);
                int x = Integer.valueOf((int)eventXY[0]);
                int y = Integer.valueOf((int)eventXY[1]);

// map touch point from ImageView to image
                x1 = eventX;
                y1 = eventY;

                quote.measure(0,0);
                int width = quote.getMeasuredWidth();
                int height = quote.getMeasuredHeight();
                wid = width/2;
                hig = height/2;
                wid1 = (float) (wid/1.5);
                hig1 = hig/3f;

                quote.setX(x1-factorx);
                quote.setY(y1-factory);

                return true;
            }
        };
    }


}
