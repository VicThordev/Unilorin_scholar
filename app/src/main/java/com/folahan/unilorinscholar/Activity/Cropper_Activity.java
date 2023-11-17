package com.folahan.unilorinscholar.Activity;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.folahan.unilorinscholar.R;
import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;

import java.io.IOException;

public class Cropper_Activity extends AppCompatActivity {
    private TextView txtCopy;
    private Button capture, btnCopy, btnGallery;
    private ImageView mImgCopy;
    ActivityResultLauncher<String> mGetContent;
    private static final int REQUEST_CAMERA_CODE = 100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cropper);
        txtCopy = findViewById(R.id.txt_copy);
        mImgCopy = findViewById(R.id.img_crop);
        capture = findViewById(R.id.btn_capture);
        btnCopy = findViewById(R.id.btn_copy);
        btnGallery = findViewById(R.id.btn_gallery);

        capture.setOnClickListener(view -> mGetContent.launch("image/*"));

        btnCopy.setOnClickListener(task -> {
            String scannedText = txtCopy.getText().toString();
            copyToCLipBoard(scannedText);
        });

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA
        )!= PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.CAMERA
            }, REQUEST_CAMERA_CODE);
        }

        mGetContent = registerForActivityResult(new ActivityResultContracts.GetContent(),
                result -> {
                    Intent intent = new Intent(Cropper_Activity.this,
                            CropImageActivity.class);
                    intent.putExtra("DATA", result.toString());
                    startActivityForResult(intent, 101);
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==-1 && requestCode == 101) {
            assert data != null;
            String result = data.getStringExtra("RESULT");
            Uri resultUri = null;
            if (result!=null) {
                resultUri=Uri.parse(result);
            }
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), resultUri);
                getTextFromImage(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }

            btnCopy.setVisibility(View.VISIBLE);

            mImgCopy.setImageURI(resultUri);
        }
    }

    private void getTextFromImage(Bitmap uri) {
        TextRecognizer recognizer = new TextRecognizer.Builder(this).build();
        if (!recognizer.isOperational()) {
            Toast.makeText(this, "Error Occurred", Toast.LENGTH_SHORT).show();
        } else {
            Frame frame = new Frame.Builder().setBitmap(uri).build();
            SparseArray<TextBlock> sparseArray = recognizer.detect(frame);
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i<sparseArray.size(); i++) {
                TextBlock textBlock = sparseArray.valueAt(i);
                builder.append(textBlock.getValue());
                builder.append("\n");
            }
            txtCopy.setText(builder.toString());
            btnCopy.setVisibility(View.VISIBLE);

        }
    }

    private void copyToCLipBoard(String text) {
        ClipboardManager manager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("Copied Data", text);
        manager.setPrimaryClip(clip);
        Toast.makeText(this, "Copied to Clipboard", Toast.LENGTH_SHORT).show();
    }
}