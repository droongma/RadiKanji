package edu.skku.cs.radikanji;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.util.Linkify;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// This activity shows app information, including API and License.
public class AboutActivity extends AppCompatActivity {

    TextView appInfoText2,appInfoText3,appInfoText4,appInfoText5, appInfoText7,appInfoText8;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        appInfoText2 = findViewById(R.id.appInfoText2);
        appInfoText3 = findViewById(R.id.appInfoText3);
        appInfoText4 = findViewById(R.id.appInfoText4);
        appInfoText5 = findViewById(R.id.appInfoText5);
        appInfoText7 = findViewById(R.id.appInfoText7);
        appInfoText8 = findViewById(R.id.appInfoText8);
        Linkify.TransformFilter mTransform = new Linkify.TransformFilter(){
            @Override
            public String transformUrl(Matcher matcher, String s) {
                return "";
            }
        };
        String[] urls = {"https://kanjiapi.dev/", "https://github.com/onlyskin/kanjiapi.dev"
            , "https://kanjiapi.dev/#!/documentation", "https://app.kanjialive.com/api/docs"
            , "https://rapidapi.com/KanjiAlive/api/learn-to-read-and-write-japanese-kanji/"
            , "https://github.com/kanjialive/kanji-data-media"};
        Pattern pattern2 = Pattern.compile("kanjiapi.dev");
        Pattern pattern3 = Pattern.compile("kanjiapi.dev Github");
        Pattern pattern4 = Pattern.compile("https://kanjiapi.dev/#!/documentation");
        Pattern pattern5 = Pattern.compile("Kanji Alive API");
        Pattern pattern7 = Pattern.compile("https://rapidapi.com/KanjiAlive/api/learn-to-read-and-write-japanese-kanji/");
        Pattern pattern8 = Pattern.compile("Kanji Alive Github");

        Linkify.addLinks(appInfoText2, pattern2, urls[0],null, mTransform);
        Linkify.addLinks(appInfoText3, pattern3, urls[1],null, mTransform);
        Linkify.addLinks(appInfoText4, pattern4, urls[2],null, mTransform);
        Linkify.addLinks(appInfoText5, pattern5, urls[3],null, mTransform);
        Linkify.addLinks(appInfoText7, pattern7, urls[4],null, mTransform);
        Linkify.addLinks(appInfoText8, pattern8, urls[5],null, mTransform);
        Linkify.addLinks(appInfoText8, pattern5, urls[3],null, mTransform);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }
}