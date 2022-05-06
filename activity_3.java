package www.unt.seniordesignappv2;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class activity_3 extends AppCompatActivity {
    Button button5;
    Button readNode2;
    TextView displayNode2;
    TextView displayNode2bool;
    public static final String CHANNEL_2_ID = "channel2";
    private NotificationManagerCompat notificationManager2;

    @SuppressLint("WrongViewCast") //from notifications
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_3);
        displayNode2 = (TextView)findViewById(R.id.textview10); //display raw data
        displayNode2bool = (TextView)findViewById(R.id.displaybooleannode2); //display boolean data

        button5 = (Button)findViewById(R.id.button5); //button for returning to main page
        button5.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                openMainActivity();
            }
        });

        readNode2 = (Button)findViewById(R.id.buttonNode2); //BUTTON FOR READING IN NODE 2
        readNode2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) { readSen2File(); readSen2boolFile(); loadImage2();}
        });

        notificationManager2 = NotificationManagerCompat.from(this);
    }

    public void readSen2File(){ //READ IN/DISPLAY NODE 2 DATA START
        try {
            FileInputStream fis2 = new FileInputStream (new File("/data/data/www.unt.seniordesignappv2/node2.txt"));  // raw node2 data
            InputStreamReader inputStreamReader = new InputStreamReader(fis2);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuilder stringBuffer = new StringBuilder();
            String lines2;
            while ((lines2= bufferedReader.readLine()) != null) {
                stringBuffer.append(lines2).append("\n");
            }
            fis2.close();
            displayNode2.setText(stringBuffer.toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    } //READ IN/DISPLAY NOTE 2 DATA END

    public void readSen2boolFile(){ //displays the boolean data
        try {
            FileInputStream fis3 = new FileInputStream (new File("/data/data/www.unt.seniordesignappv2/finalApp2.txt"));  // boolean data
            InputStreamReader inputStreamReader = new InputStreamReader(fis3);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuilder stringBuffer = new StringBuilder();
            String lines3;
            while ((lines3 = bufferedReader.readLine()) != null) {
                stringBuffer.append(lines3).append("\n");
                Log.d("tag",lines3);
                if(lines3.equals("1")){
                    Log.d("tag", "There is a 1 in Node 2");
                    sendNotification2();
                }
            }
            fis3.close();
            displayNode2bool.setText(stringBuffer.toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openMainActivity(){  //returning to main page
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void sendNotification2(){
        Log.d("tag","Notifications are being sent from Node 2");
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel1 = new NotificationChannel(
                    CHANNEL_2_ID,
                    "Channel 2",
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel1.setDescription("This is Channel 2");
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel1);
        }
        String title = "Brain Drain Crew";
        String message = "Node 2 has spotted abnormal readings";
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_2_ID)
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();

        notificationManager2.notify(1, notification);
    }

    public void loadImage2(){ //LOAD PNG START
        File imgFile2 = new File("/data/data/www.unt.seniordesignappv2/plantpng.png");
        //File imgFile = new File("/data/data/www.unt.seniordesignappv2/node2picture.ping");
        if(imgFile2.exists()){
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile2.getAbsolutePath());
            ImageView myImage = (ImageView) findViewById(R.id.imageView2);
            myImage.setImageBitmap(myBitmap);
        }
    } //LOAD PNG END
}