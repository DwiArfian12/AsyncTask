package com.dwiarfian.asynctask;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    TextView textView;
    Button button;
    ProgressBar progressBar, progressIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);
        button = findViewById(R.id.button);
        progressBar = findViewById(R.id.progressBar);
        progressIndicator = findViewById(R.id.progressIndicator);
    }

    public void uploadTask(View view) {
        new UploadTask().execute("This is the string passed.");
    }

    class UploadTask extends AsyncTask<String, Integer, String> {
        @Override
        protected void onPreExecute() {
            Log.i(TAG, "onPreExecute: " + Thread.currentThread().getName());

            textView.setText("uploading data...");
            progressIndicator.setVisibility(View.VISIBLE);
            button.setEnabled(false);
        }

        @Override
        protected String doInBackground(String... strings) {
            Log.i(TAG, "doInBackground: string passed: " + strings[0]);
            Log.i(TAG, "doInBackground: Thread: " + Thread.currentThread().getName());

            int totalSteps = 10;
            for (int i = 0; i < totalSteps; i++) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // Calculate percentage progress and publish it
                int progress = (int) ((i + 1) / (float) totalSteps * 100);
                publishProgress(progress);
            }

            return "finally the task is complete";
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            progressBar.setProgress(values[0] + 1);
            Log.i(TAG, "onProgressUpdate: " + Thread.currentThread().getName());
        }

        @Override
        protected void onPostExecute(String string) {
            Log.i(TAG, "onPostExecute: " + Thread.currentThread().getName());

            textView.setText(string);
            progressIndicator.setVisibility(View.INVISIBLE);
            button.setEnabled(true);
        }
    }
}
