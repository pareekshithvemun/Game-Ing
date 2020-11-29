package com.example.game_ing;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.IOException;

public class postadv extends AppCompatActivity implements View.OnClickListener{

    private static final int PICK_IMAGE_REQUEST = 7;
    private ImageView imageView;
    private EditText Description;
    private EditText Price;
    private EditText Address;
    private EditText Phone;
    String ImageUrl;
    private Button btnPost;


    // Folder path for Firebase Storage.

    // Root Database Name for Firebase Database.
    String Database_Path = "All_Image_Uploads_Database";
    String photostring;

    Uri filePath;
    boolean isImageAdded = false;
    private StorageReference storageReference;
    private DatabaseReference databaseReference;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_postadv);

        imageView = findViewById(R.id.imageview1);
        Description = findViewById(R.id.description);
        Price = findViewById(R.id.price);
        Address = findViewById(R.id.Address);
        Phone = findViewById(R.id.Phone);
        btnPost = findViewById(R.id.postad);

        imageView.setOnClickListener(this);
        btnPost.setOnClickListener(this);

        storageReference = FirebaseStorage.getInstance().getReference(Database_Path);
        databaseReference = FirebaseDatabase.getInstance().getReference(Database_Path);


    }

    //method to show file chooser
    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }


    //handling the image chooser activity result
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();

            Picasso.get().load(filePath).into(imageView);

        }
    }


    // Creating Method to get the selected image file Extension from File Path URI.
    public String GetFileExtension(Uri uri) {

        ContentResolver contentResolver = getContentResolver();

        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();

        // Returning the file Extension.
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri)) ;

    }

    @Override
    public void onClick(View view) {
        //if the clicked button is choose
        if (view == imageView) {
            showFileChooser();
        }
        //if the clicked button is upload
        else if (view == btnPost) {
            uploadFile();

        }
    }

    //this method will upload the file
    private void uploadFile() {
        //if there is a file to upload
        if (filePath != null) {


            final StorageReference storageReference2nd = storageReference.child(System.currentTimeMillis() + "." + GetFileExtension(filePath));
            storageReference2nd.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                           /* storageReference2nd.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    final String ImageUrl = uri.toString();

                                }
                            });*/

                            // Getting Description, Price, Address, Mobile from EditText and store into string variable.
                            String TempDescription = Description.getText().toString().trim();
                            String TempPrice = Price.getText().toString().trim();
                            String TempAddress = Address.getText().toString().trim();
                            String TempPhone = Phone.getText().toString().trim();
                            String TempImageUrl = ImageUrl;





                            //if the upload is successfull

                            //and displaying a success toast
                            Toast.makeText(postadv.this, "File Uploaded ", Toast.LENGTH_LONG).show();


                            @SuppressWarnings("VisibleForTests")
                            ImageUploadInfo imageUploadInfo = new ImageUploadInfo(TempDescription, TempPrice, TempAddress, TempPhone, storageReference2nd.getDownloadUrl().toString());

                            // Getting image upload ID.
                            String ImageUploadId = databaseReference.push().getKey();

                            // Adding image upload id s child element into databaseReference.
                            databaseReference.child(ImageUploadId).setValue(imageUploadInfo);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            //if the upload is not successfull
                            //hiding the progress dialog

                            //and displaying error message
                            Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
        }
        //if there is not any file
        else {
            Toast.makeText(postadv.this, "Failed", Toast.LENGTH_SHORT).show();
        }
    }



}
