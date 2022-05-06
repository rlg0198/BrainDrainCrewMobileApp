package www.unt.seniordesignappv2;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private Button button;
    Button button2node;
    private boolean connection = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new MyTask().execute(); //Async task

        final Handler handler = new Handler();
        final int delay = 15000; // Value in milliseconds. Calls function every 15 seconds

        handler.postDelayed(new Runnable() {
            public void run() {
                System.out.println("Data has been refreshed!"); //calls the function to connect to server, download, and disconnect
                new MyTask().execute(); //Async task
                handler.postDelayed(this, delay);
            }
        }, delay);

        button = (Button)findViewById(R.id.button); //Button for activity 2 1st node
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                openActivity2();
            }
        });

        button2node = (Button)findViewById(R.id.button4); //Button for activity 3 2nd node
        button2node.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view2) { openActivity3(); }
        });
    }

    private class MyTask extends AsyncTask<Void, Void, Void>{ //Async thread task
        String result;
        @Override
        protected Void doInBackground(Void... voids){
            FTPConnection f = new FTPConnection(); //FTP CONNECTION FILE
            connection = f.ftpConnect();
            f.ftpDownload("/finalApp1.txt","/data/data/www.unt.seniordesignappv2/finalApp1.txt");
            f.ftpDownload("/finalApp2.txt","/data/data/www.unt.seniordesignappv2/finalApp2.txt");
            f.ftpDownload("/sensor_data/node1.txt","/data/data/www.unt.seniordesignappv2/node1.txt");
            f.ftpDownload("/sensor_data/node2.txt","/data/data/www.unt.seniordesignappv2/node2.txt");
            f.ftpDownload("/plantpng.png","/data/data/www.unt.seniordesignappv2/plantpng.png");
            //f.ftpDownload("/sensor_data/picture1.png","/data/data/www.unt.seniordesignappv2/node1picture.ping");
            f.ftpDisconnect();
            result = "returning result";
            Log.d("tag","Disconnection successful from doInBackground");
            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid){
            System.out.println("Finished on post execute");
            super.onPostExecute(aVoid);
        }
    }

    public void openActivity2(){ //Opening the second page for NODE 1
        Intent intent = new Intent(this, Activity2.class);
        startActivity(intent);
    }

    public void openActivity3(){ //Opening the third page for NODE 2
        Intent intent2 = new Intent(this,activity_3.class);
        startActivity(intent2);
    }
}