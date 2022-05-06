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


public class Activity2 extends AppCompatActivity {
    private Button button2; //button back to main
    TextView displayText;
    TextView sensorDisplayText;
    Button readSen1Button;
    public static final String CHANNEL_1_ID = "channel1";
    private NotificationManagerCompat notificationManager;
    ImageView cropImage;

    @SuppressLint("WrongViewCast") //from notifications
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);
        displayText = (TextView)findViewById(R.id.textView8); //boolean data display
        sensorDisplayText = (TextView)findViewById(R.id.textView11); //sensor data display

        cropImage = (ImageView)findViewById(R.id.imageView); //reading in png file

        button2 = (Button)findViewById(R.id.button2); //button for returning to main page
        button2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) { openMainActivity(); }
        });

        readSen1Button = (Button)findViewById(R.id.button6); //reading in node 1 data (readSenFile) and  reading in boolean data (readFile)
        readSen1Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { readSenFile(); readFile(); loadImage(); }
        });
        notificationManager = NotificationManagerCompat.from(this);
    }

    public void openMainActivity(){ //Open main page button declaration
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void readFile() { //READ/DISPLAY BOOLEAN FILE START
       try {
            FileInputStream fis = new FileInputStream (new File("/data/data/www.unt.seniordesignappv2/finalApp1.txt"));  //boolean data
            InputStreamReader inputStreamReader = new InputStreamReader(fis);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuilder stringBuffer = new StringBuilder();
            String lines;
            Log.d("tag", "This is in the loop reading part");
            while ((lines = bufferedReader.readLine()) != null) {
                stringBuffer.append(lines).append("\n");
                Log.d("tag",lines);
                if(lines.equals("1")){
                    Log.d("tag", "There is a 1 in Node 1");
                    sendNotification();
                }
            }
            fis.close();
            displayText.setText(stringBuffer.toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }//READ/DISPLAY BOOLEAN FILE END

    public void readSenFile(){ //READ/DISPLAY SENSOR FILE START
        try {
            FileInputStream fis1 = new FileInputStream (new File("/data/data/www.unt.seniordesignappv2/node1.txt"));  // raw node1 data
            InputStreamReader inputStreamReader = new InputStreamReader(fis1);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuilder stringBuffer = new StringBuilder();
            String lines1;
            while ((lines1 = bufferedReader.readLine()) != null) {
                stringBuffer.append(lines1).append("\n");
            }
            fis1.close();
            sensorDisplayText.setText(stringBuffer.toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    } //READ/DISPLAY SENSOR FILE END

    public void sendNotification(){  //NOTIFICATIONS START currently using edittext. need to have fixed messages
        Log.d("tag", "Notifications are being sent from Node 1");
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    NotificationChannel channel1 = new NotificationChannel(
                            CHANNEL_1_ID,
                            "Channel 1",
                            NotificationManager.IMPORTANCE_HIGH
                    );
                    channel1.setDescription("This is Channel 1");
                    NotificationManager manager = getSystemService(NotificationManager.class);
                    manager.createNotificationChannel(channel1);
                }
        String title = "Brain Drain Crew";
        String message = "Node 1 has spotted abnormal readings";
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();

        notificationManager.notify(1, notification);
    } //NOTIFICATIONS END

   public void loadImage(){ //LOAD PNG START
       File imgFile = new File("/data/data/www.unt.seniordesignappv2/plantpng.png");
       //File imgFile = new File("/data/data/www.unt.seniordesignappv2/node1picture.ping");
       if(imgFile.exists()){
           Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
           ImageView myImage = (ImageView) findViewById(R.id.imageView);
           myImage.setImageBitmap(myBitmap);
       }
   } //LOAD PNG END
}