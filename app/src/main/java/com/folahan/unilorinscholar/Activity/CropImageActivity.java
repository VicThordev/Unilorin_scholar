package com.folahan.unilorinscholar.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.folahan.unilorinscholar.R;
import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.util.UUID;

public class CropImageActivity extends AppCompatActivity {

    private Uri fileUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_image);

        readIntent();

        UCrop.Options options = new UCrop.Options();

        String rest_uri = new StringBuilder((UUID.randomUUID().toString()))
                .append("*.jpg").toString();

        UCrop.of(fileUri, Uri.fromFile(new File(getCacheDir(), rest_uri)))
                .withOptions(options)
                .withAspectRatio(0,0)
                .useSourceImageAspectRatio()
                .withMaxResultSize(2000, 2000)
                .start(CropImageActivity.this);
    }

    private void readIntent() {
        Intent intent = getIntent();
        if (intent.getExtras() !=null) {
            String result = intent.getStringExtra("DATA");
            fileUri = Uri.parse(result);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, Cropper_Activity.class));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode==UCrop.REQUEST_CROP) {
            final Uri resultUri = UCrop.getOutput(data);
            Intent returnInetnt = new Intent();
            returnInetnt.putExtra("RESULT", resultUri+"");
            setResult(-1, returnInetnt);
            finish();
        } else if (resultCode == UCrop.RESULT_ERROR) {
            final Throwable cropError = UCrop.getError(data);
        }

    }


}