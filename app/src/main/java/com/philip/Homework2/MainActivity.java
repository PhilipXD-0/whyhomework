package com.philip.Homework2;

import android.graphics.Bitmap;

import android.graphics.drawable.BitmapDrawable;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.QRCodeDetector;

public class MainActivity extends AppCompatActivity {
    ImageView imgView;
    TextView txtView;

//philip
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (!OpenCVLoader.initDebug()) {
            Log.d("Philip", "dead");
        } else {
            Log.d("Philip", " alive");
        }

        imgView = findViewById(R.id.imageView);
        txtView = findViewById(R.id.textView);

        Log.d("Philip", " save");
        Button butt = findViewById(R.id.button);


        butt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                final Bitmap fbi = ((BitmapDrawable) imgView.getDrawable()).getBitmap();

                Mat mat = new Mat(fbi.getWidth(), fbi.getHeight(), CvType.CV_8UC4);
                Utils.bitmapToMat(fbi, mat);
                String ans = decodeQRcode(fbi);
                String[] output = ans.split(";");
                Scalar lineColor = new Scalar(255,0,0,255);
                int lineWidth = 3;

                for(int k = 0 ; k<output.length ; k++){
                    Point[]points={new Point(), new Point()};
                    String [] Space = output[k].split(" ");
                    for(int j = 0 ; j < Space.length ; j++){
                        String [] abc = Space[j].split(",");
                        points[j] = new Point(Integer.parseInt(abc[0]),Integer.parseInt(abc[1]) );

                    }
                    Imgproc.line(mat, points[0], points[1], lineColor, lineWidth);
                    //Imgproc.line(mat, points[1], points[2], lineColor, lineWidth);
                    //Imgproc.line(mat, points[2], points[3], lineColor, lineWidth);
                    //Imgproc.line(mat, points[3], points[4], lineColor, lineWidth);
                    //Imgproc.line(mat, points[4], points[0], lineColor, lineWidth);
                }

                    Bitmap bitmap = Bitmap.createBitmap(mat.width(), mat.height(), Bitmap.Config.ARGB_8888);
                    Utils.matToBitmap(mat, bitmap);
                    ImageView imgView = findViewById(R.id.imageView);
                    imgView.setImageBitmap(bitmap);



            }
        });

    }
    String decodeQRcode (Bitmap bitmap){
        Mat mat = new Mat(bitmap.getWidth(), bitmap.getHeight(), CvType.CV_8UC4);
        Utils.bitmapToMat(bitmap, mat);
        QRCodeDetector qrCodeDetector = new QRCodeDetector();
        String result = qrCodeDetector.detectAndDecode(mat);
        Log.d("Philip", "result" + result);
        txtView.setText(result);
        return result;
    }
}
