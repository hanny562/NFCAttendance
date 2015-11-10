package cap2100.nfcattendance;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

public class About extends AppCompatActivity {

    WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        mWebView = (WebView) findViewById(R.id.webView);

        String text = "<html><body>"
                + "<h4>"
                + "About Our Team"
                + "</h4><p align=\"justify\">"
                + "We are a Team of three of final year students from Inti International College Penang that currently developing a prototype" +
                "Android Application that illutrate the real scenarios of attendance taking by lecturer in class with more efficient way."
                + "<br><br>"
                + "Team Members <br>"
                + "- Liu Guan Han <br>"
                + "- Tiffany Toh Qing Wei <br>"
                + "- Yew Pui Cheng <br><br>"
                + "Supervisor <br>"
                + "- Mr. Shahriman <br><br>"
                + "</p></body></html>";

        mWebView.loadData(text, "text/html", "utf-8");
    }
}
