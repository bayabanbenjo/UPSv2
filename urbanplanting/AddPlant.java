package com.app.urbanplanting;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

public class AddPlant extends AppCompatActivity {
    private DatabaseReference reference = FirebaseDatabase.getInstance().getReference("ListedPlants");
    ImageView plantImg;
    EditText pName,pDesc,pPrice,pWater,pSun,pTemp;
    Button savePlant,cancelPlant;
    public static Uri imageUri;

    private StorageReference storageReference;
    private FirebaseStorage storage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_plant);

        getSupportActionBar().hide();
        plantImg = findViewById(R.id.add_plant_img);
        pName = findViewById(R.id.name_of_plant);
        pDesc = findViewById(R.id.desc_of_plant);
        pPrice = findViewById(R.id.price_of_plant);
        pWater = findViewById(R.id.watering);
        pSun = findViewById(R.id.sunlight);
        pTemp = findViewById(R.id.temperature);
        savePlant = findViewById(R.id.save_add_plant);
        cancelPlant = findViewById(R.id.cancel_add_plant);

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        plantImg.setOnClickListener(view -> choosePicture());

        savePlant.setOnClickListener(view -> {
            String plantName = pName.getText().toString();
            String plantDesc = pDesc.getText().toString();
            String plantPrice = pPrice.getText().toString();
            String plantWater = pWater.getText().toString();
            String plantSun = pSun.getText().toString();
            String plantTemp = pTemp.getText().toString();
            if(imageUri!=null && !TextUtils.isEmpty(plantName) && !TextUtils.isEmpty(plantDesc) && !TextUtils.isEmpty(plantPrice) && !TextUtils.isEmpty(plantWater) && !TextUtils.isEmpty(plantSun) && !TextUtils.isEmpty(plantTemp)){
                uploadPicture(imageUri,plantName,plantDesc,plantPrice,plantWater,plantSun,plantTemp);

            }else {
                Toast.makeText(AddPlant.this,"Complete All Fields",Toast.LENGTH_SHORT).show();
                return;
            }


        });

        cancelPlant.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(),AdminPlants.class);
            startActivity(intent);
            finishAffinity();
        });
    }
    private void choosePicture() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,1);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode==RESULT_OK && data!=null && data.getData()!=null){
            imageUri = data.getData();
            plantImg.setImageURI(imageUri);
        }
    }
    private void uploadPicture(Uri uri, String plantName, String plantDesc, String plantPrice, String plantWater, String plantSun, String plantTemp) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Uploading Image...");
        progressDialog.show();

// Create a reference to 'images/mountains.jpg'
        final String randomKey = UUID.randomUUID().toString();
        StorageReference imageRef = storageReference.child(plantName+randomKey+"."+getfileExtension(uri));

        imageRef.putFile(imageUri).addOnSuccessListener(taskSnapshot -> {
            progressDialog.dismiss();
            imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri1) {
                    progressDialog.dismiss();
                    PlantsModel categoryModel = new PlantsModel(uri1.toString(),plantName,plantPrice,plantDesc,plantWater,plantSun,plantTemp,randomKey);
                    reference.child(randomKey).setValue(categoryModel);

                    Intent intent = new Intent(AddPlant.this,AdminPlants.class);
                    startActivity(intent);
                    finishAffinity();

                }
            });


        }).addOnFailureListener(e -> {
            progressDialog.dismiss();
            Snackbar.make(findViewById(android.R.id.content),"Error While Saving", Snackbar.LENGTH_SHORT).show();
        })
                .addOnProgressListener(snapshot -> {
                    double progressPercent = (100.00 * snapshot.getBytesTransferred()/snapshot.getTotalByteCount());
                    progressDialog.setMessage("Progress: "+progressPercent +"%");
                    Snackbar.make(findViewById(android.R.id.content),"Saved", Snackbar.LENGTH_SHORT).show();


                });
    }
    private String getfileExtension(Uri mUri){
        ContentResolver cr = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(cr.getType(mUri));
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(AddPlant.this,AdminPlants.class);
        startActivity(intent);
        finishAffinity();
    }

    public void Back(View view) {
        onBackPressed();
    }
}