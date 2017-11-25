package uietdig.volleydemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

public class DescriptionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);
        ModelFields item = (ModelFields) getIntent().getSerializableExtra("DesObj");
        TextView desHead = findViewById(R.id.Aheading);
        TextView desDes = findViewById(R.id.Adescription);
        String plainText = Html.fromHtml(item.getDescription()).toString();

        desHead.setText(item.getHeading());
        desDes.setText(plainText);
    }
}
