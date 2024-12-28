package com.shopanbdecommerce.vendor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.squareup.picasso.Picasso;

import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;

public class Step_2 extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    ImageView imageView;
    private EditText edtname, edtemail, edtpass, edtcnfpass, edtnumber;

    CircleImageView image;
    ImageView upload;
    private Uri mainImageURI = null;
    boolean IMAGE_STATUS = false;
    Bitmap profilePicture;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private final String TAG = this.getClass().getSimpleName();
    private String userId;

    private StorageReference storageReference;


    private Bitmap compressedImageFile;
    private KProgressHUD progressDialog;
    ImageButton image_button;

    private final int PICK_IMAGE_REQUEST = 71;
    private Uri filePath;//Firebase

    DocumentReference documentReference;
    Button floatingActionButton;
    FirebaseStorage storage;
    private static final int CAMERA_REQUEST = 1888;
    Button generate_btn;
    //doctor
    private static final int READCODE = 1;
    private static final int WRITECODE = 2;

    private Uri mainImageUri = null;
    EditText product_name,product_des,porduct_price;
    Button add_product;
    String url;
    KProgressHUD progressHUD;
    EditText phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_2);
        Toolbar toolbar = findViewById(R.id.profile_toolbar);
        storageReference = FirebaseStorage.getInstance().getReference();
        firebaseFirestore=FirebaseFirestore.getInstance();
        firebaseAuth=FirebaseAuth.getInstance();
        url=getIntent().getStringExtra("url");
        progressHUD = KProgressHUD.create(Step_2.this);
        phone=findViewById(R.id.phone);



        toolbar.setTitle("Add Product");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setElevation(10.0f);
        imageView=findViewById(R.id.productimage);
        product_name=findViewById(R.id.product_name);
        product_des=findViewById(R.id.product_des);
        porduct_price=findViewById(R.id.porduct_price);
        add_product=findViewById(R.id.add_product);
        try {
            Picasso.get().load(url).into(imageView);
        }catch (Exception e) {
        }
        add_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Random myRandom = new Random();
                final String randomkey=String.valueOf(myRandom.nextInt(100000));
                int main_id=Integer.parseInt(randomkey);
                String name=product_name.getText().toString();
                String por_de=product_des.getText().toString();
                String product_price1=porduct_price.getText().toString();
                if (TextUtils.isEmpty(name)|| TextUtils.isEmpty(por_de)|| TextUtils.isEmpty(product_price1)||TextUtils.isEmpty(phone.getText().toString())) {
                    Toast.makeText(Step_2.this, "Error", Toast.LENGTH_SHORT).show();
                    return;
                }
                else {
                    upload();
                }

            }
        });
    }

    private void upload() {
        String option[]={"Grocery"
                ,"Events","Tickets and Vehicle","Gents","Parlour","Education","Sports","Ladies","Furnitures","Crockries and Gifts",
                "Medical and Ambulance","Medicine","Hotel and Houses","Food","Electronics"};
        AlertDialog.Builder builder=new AlertDialog.Builder(Step_2.this);
        builder.setTitle("Select a option")
                .setItems(option, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        //1
                          if (which==0) {
                            progress_check();
                            Random myRandom = new Random();
                            final String randomkey=String.valueOf(myRandom.nextInt(100000));
                            int id=Integer.parseInt(randomkey);
                            final GenericProductModel genericProductModel=new GenericProductModel(id,product_name.getText().toString(),url,product_des.getText().toString(),
                                    Float.parseFloat(porduct_price.getText().toString()),firebaseAuth.getCurrentUser().getEmail(),phone.getText().toString(),randomkey,"Bags","110");
                            firebaseFirestore.collection("Indivisiul_Products")
                                    .document(firebaseAuth.getCurrentUser().getEmail())
                                    .collection("List")
                                    .document(randomkey)
                                    .set(genericProductModel)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                firebaseFirestore.collection("Products")
                                                        .document("110")
                                                        .collection("Bags")
                                                        .document(randomkey)
                                                        .set(genericProductModel)
                                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                if (task.isSuccessful()) {
                                                                    progressHUD.dismiss();
                                                                    startActivity(new Intent(getApplicationContext(),Main_Dash.class));
                                                                }
                                                            }
                                                        });
                                            }
                                        }
                                    });
                        }
                        //2
                        else  if (which==1) {
                            progress_check();
                            Random myRandom = new Random();
                            final String randomkey=String.valueOf(myRandom.nextInt(100000));
                            int id=Integer.parseInt(randomkey);
                            final GenericProductModel genericProductModel=new GenericProductModel(id,product_name.getText().toString(),url,product_des.getText().toString(),
                                    Float.parseFloat(porduct_price.getText().toString()),firebaseAuth.getCurrentUser().getEmail(),phone.getText().toString(),randomkey,"Bags","111");
                            firebaseFirestore.collection("Indivisiul_Products")
                                    .document(firebaseAuth.getCurrentUser().getEmail())
                                    .collection("List")
                                    .document(randomkey)
                                    .set(genericProductModel)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                firebaseFirestore.collection("Products")
                                                        .document("111")
                                                        .collection("Bags")
                                                        .document(randomkey)
                                                        .set(genericProductModel)
                                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                if (task.isSuccessful()) {
                                                                    progressHUD.dismiss();
                                                                    startActivity(new Intent(getApplicationContext(),Main_Dash.class));
                                                                }
                                                            }
                                                        });
                                            }
                                        }
                                    });
                        }
                        //3
                        else  if (which==2) {
                            progress_check();
                            Random myRandom = new Random();
                            final String randomkey=String.valueOf(myRandom.nextInt(100000));
                            int id=Integer.parseInt(randomkey);
                            final GenericProductModel genericProductModel=new GenericProductModel(id,product_name.getText().toString(),url,product_des.getText().toString(),
                                    Float.parseFloat(porduct_price.getText().toString()),firebaseAuth.getCurrentUser().getEmail(),phone.getText().toString(),randomkey,"Bags","112");
                            firebaseFirestore.collection("Indivisiul_Products")
                                    .document(firebaseAuth.getCurrentUser().getEmail())
                                    .collection("List")
                                    .document(randomkey)
                                    .set(genericProductModel)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                firebaseFirestore.collection("Products")
                                                        .document("112")
                                                        .collection("Bags")
                                                        .document(randomkey)
                                                        .set(genericProductModel)
                                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                if (task.isSuccessful()) {
                                                                    progressHUD.dismiss();
                                                                    startActivity(new Intent(getApplicationContext(),Main_Dash.class));
                                                                }
                                                            }
                                                        });
                                            }
                                        }
                                    });
                        }
                        //4
                        else  if (which==3) {
                            progress_check();
                            Random myRandom = new Random();
                            final String randomkey=String.valueOf(myRandom.nextInt(100000));
                            int id=Integer.parseInt(randomkey);
                            final GenericProductModel genericProductModel=new GenericProductModel(id,product_name.getText().toString(),url,product_des.getText().toString(),
                                    Float.parseFloat(porduct_price.getText().toString()),firebaseAuth.getCurrentUser().getEmail(),phone.getText().toString(),randomkey,"Bags","113");
                            firebaseFirestore.collection("Indivisiul_Products")
                                    .document(firebaseAuth.getCurrentUser().getEmail())
                                    .collection("List")
                                    .document(randomkey)
                                    .set(genericProductModel)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                firebaseFirestore.collection("Products")
                                                        .document("113")
                                                        .collection("Bags")
                                                        .document(randomkey)
                                                        .set(genericProductModel)
                                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                if (task.isSuccessful()) {
                                                                    progressHUD.dismiss();
                                                                    startActivity(new Intent(getApplicationContext(),Main_Dash.class));
                                                                }
                                                            }
                                                        });
                                            }
                                        }
                                    });
                        }
                        //5
                        else  if (which==4) {
                            progress_check();
                            Random myRandom = new Random();
                            final String randomkey=String.valueOf(myRandom.nextInt(100000));
                            int id=Integer.parseInt(randomkey);
                            final GenericProductModel genericProductModel=new GenericProductModel(id,product_name.getText().toString(),url,product_des.getText().toString(),
                                    Float.parseFloat(porduct_price.getText().toString()),firebaseAuth.getCurrentUser().getEmail(),phone.getText().toString(),randomkey,"Bags","114");
                            firebaseFirestore.collection("Indivisiul_Products")
                                    .document(firebaseAuth.getCurrentUser().getEmail())
                                    .collection("List")
                                    .document(randomkey)
                                    .set(genericProductModel)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                firebaseFirestore.collection("Products")
                                                        .document("114")
                                                        .collection("Bags")
                                                        .document(randomkey)
                                                        .set(genericProductModel)
                                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                if (task.isSuccessful()) {
                                                                    progressHUD.dismiss();
                                                                    startActivity(new Intent(getApplicationContext(),Main_Dash.class));
                                                                }
                                                            }
                                                        });
                                            }
                                        }
                                    });
                        }
                        //6
                        else  if (which==5) {
                            progress_check();
                            Random myRandom = new Random();
                            final String randomkey=String.valueOf(myRandom.nextInt(100000));
                            int id=Integer.parseInt(randomkey);
                            final GenericProductModel genericProductModel=new GenericProductModel(id,product_name.getText().toString(),url,product_des.getText().toString(),
                                    Float.parseFloat(porduct_price.getText().toString()),firebaseAuth.getCurrentUser().getEmail(),phone.getText().toString(),randomkey,"Bags","115");
                            firebaseFirestore.collection("Indivisiul_Products")
                                    .document(firebaseAuth.getCurrentUser().getEmail())
                                    .collection("List")
                                    .document(randomkey)
                                    .set(genericProductModel)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                firebaseFirestore.collection("Products")
                                                        .document("115")
                                                        .collection("Bags")
                                                        .document(randomkey)
                                                        .set(genericProductModel)
                                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                if (task.isSuccessful()) {
                                                                    progressHUD.dismiss();
                                                                    startActivity(new Intent(getApplicationContext(),Main_Dash.class));
                                                                }
                                                            }
                                                        });
                                            }
                                        }
                                    });
                        }
                        //7
                        else  if (which==6) {
                            progress_check();
                            Random myRandom = new Random();
                            final String randomkey=String.valueOf(myRandom.nextInt(100000));
                            int id=Integer.parseInt(randomkey);
                            final GenericProductModel genericProductModel=new GenericProductModel(id,product_name.getText().toString(),url,product_des.getText().toString(),
                                    Float.parseFloat(porduct_price.getText().toString()),firebaseAuth.getCurrentUser().getEmail(),phone.getText().toString(),randomkey,"Bags","116");
                            firebaseFirestore.collection("Indivisiul_Products")
                                    .document(firebaseAuth.getCurrentUser().getEmail())
                                    .collection("List")
                                    .document(randomkey)
                                    .set(genericProductModel)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                firebaseFirestore.collection("Products")
                                                        .document("116")
                                                        .collection("Bags")
                                                        .document(randomkey)
                                                        .set(genericProductModel)
                                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                if (task.isSuccessful()) {
                                                                    progressHUD.dismiss();
                                                                    startActivity(new Intent(getApplicationContext(),Main_Dash.class));
                                                                }
                                                            }
                                                        });
                                            }
                                        }
                                    });
                        }
                        //8
                        else  if (which==7) {
                            progress_check();
                            Random myRandom = new Random();
                            final String randomkey=String.valueOf(myRandom.nextInt(100000));
                            int id=Integer.parseInt(randomkey);
                            final GenericProductModel genericProductModel=new GenericProductModel(id,product_name.getText().toString(),url,product_des.getText().toString(),
                                    Float.parseFloat(porduct_price.getText().toString()),firebaseAuth.getCurrentUser().getEmail(),phone.getText().toString(),randomkey,"Bags","117");
                            firebaseFirestore.collection("Indivisiul_Products")
                                    .document(firebaseAuth.getCurrentUser().getEmail())
                                    .collection("List")
                                    .document(randomkey)
                                    .set(genericProductModel)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                firebaseFirestore.collection("Products")
                                                        .document("117")
                                                        .collection("Bags")
                                                        .document(randomkey)
                                                        .set(genericProductModel)
                                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                if (task.isSuccessful()) {
                                                                    progressHUD.dismiss();
                                                                    startActivity(new Intent(getApplicationContext(),Main_Dash.class));
                                                                }
                                                            }
                                                        });
                                            }
                                        }
                                    });
                        }
                        //9
                        else  if (which==8) {
                            progress_check();
                            Random myRandom = new Random();
                            final String randomkey=String.valueOf(myRandom.nextInt(100000));
                            int id=Integer.parseInt(randomkey);
                            final GenericProductModel genericProductModel=new GenericProductModel(id,product_name.getText().toString(),url,product_des.getText().toString(),
                                    Float.parseFloat(porduct_price.getText().toString()),firebaseAuth.getCurrentUser().getEmail(),phone.getText().toString(),randomkey,"Bags","118");
                            firebaseFirestore.collection("Indivisiul_Products")
                                    .document(firebaseAuth.getCurrentUser().getEmail())
                                    .collection("List")
                                    .document(randomkey)
                                    .set(genericProductModel)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                firebaseFirestore.collection("Products")
                                                        .document("118")
                                                        .collection("Bags")
                                                        .document(randomkey)
                                                        .set(genericProductModel)
                                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                if (task.isSuccessful()) {
                                                                    progressHUD.dismiss();
                                                                    startActivity(new Intent(getApplicationContext(),Main_Dash.class));
                                                                }
                                                            }
                                                        });
                                            }
                                        }
                                    });
                        }
                        //10
                        else  if (which==9) {
                            progress_check();
                            Random myRandom = new Random();
                            final String randomkey=String.valueOf(myRandom.nextInt(100000));
                            int id=Integer.parseInt(randomkey);
                            final GenericProductModel genericProductModel=new GenericProductModel(id,product_name.getText().toString(),url,product_des.getText().toString(),
                                    Float.parseFloat(porduct_price.getText().toString()),firebaseAuth.getCurrentUser().getEmail(),phone.getText().toString(),randomkey,"Bags","119");
                            firebaseFirestore.collection("Indivisiul_Products")
                                    .document(firebaseAuth.getCurrentUser().getEmail())
                                    .collection("List")
                                    .document(randomkey)
                                    .set(genericProductModel)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                firebaseFirestore.collection("Products")
                                                        .document("119")
                                                        .collection("Bags")
                                                        .document(randomkey)
                                                        .set(genericProductModel)
                                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                if (task.isSuccessful()) {
                                                                    progressHUD.dismiss();
                                                                    startActivity(new Intent(getApplicationContext(),Main_Dash.class));
                                                                }
                                                            }
                                                        });
                                            }
                                        }
                                    });
                        }
                        //11
                        else  if (which==10) {
                            progress_check();
                            Random myRandom = new Random();
                            final String randomkey=String.valueOf(myRandom.nextInt(100000));
                            int id=Integer.parseInt(randomkey);
                            final GenericProductModel genericProductModel=new GenericProductModel(id,product_name.getText().toString(),url,product_des.getText().toString(),
                                    Float.parseFloat(porduct_price.getText().toString()),firebaseAuth.getCurrentUser().getEmail(),phone.getText().toString(),randomkey,"Bags","120");
                            firebaseFirestore.collection("Indivisiul_Products")
                                    .document(firebaseAuth.getCurrentUser().getEmail())
                                    .collection("List")
                                    .document(randomkey)
                                    .set(genericProductModel)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                firebaseFirestore.collection("Products")
                                                        .document("120")
                                                        .collection("Bags")
                                                        .document(randomkey)
                                                        .set(genericProductModel)
                                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                if (task.isSuccessful()) {
                                                                    progressHUD.dismiss();
                                                                    startActivity(new Intent(getApplicationContext(),Main_Dash.class));
                                                                }
                                                            }
                                                        });
                                            }
                                        }
                                    });
                        }
                        //121
                        else  if (which==11) {
                            progress_check();
                            Random myRandom = new Random();
                            final String randomkey=String.valueOf(myRandom.nextInt(100000));
                            int id=Integer.parseInt(randomkey);
                            final GenericProductModel genericProductModel=new GenericProductModel(id,product_name.getText().toString(),url,product_des.getText().toString(),
                                    Float.parseFloat(porduct_price.getText().toString()),firebaseAuth.getCurrentUser().getEmail(),phone.getText().toString(),randomkey,"Bags","121");
                            firebaseFirestore.collection("Indivisiul_Products")
                                    .document(firebaseAuth.getCurrentUser().getEmail())
                                    .collection("List")
                                    .document(randomkey)
                                    .set(genericProductModel)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                firebaseFirestore.collection("Products")
                                                        .document("121")
                                                        .collection("Bags")
                                                        .document(randomkey)
                                                        .set(genericProductModel)
                                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                if (task.isSuccessful()) {
                                                                    progressHUD.dismiss();
                                                                    startActivity(new Intent(getApplicationContext(),Main_Dash.class));
                                                                }
                                                            }
                                                        });
                                            }
                                        }
                                    });
                        }//13
                        else  if (which==12) {
                            progress_check();
                            Random myRandom = new Random();
                            final String randomkey=String.valueOf(myRandom.nextInt(100000));
                            int id=Integer.parseInt(randomkey);
                            final GenericProductModel genericProductModel=new GenericProductModel(id,product_name.getText().toString(),url,product_des.getText().toString(),
                                    Float.parseFloat(porduct_price.getText().toString()),firebaseAuth.getCurrentUser().getEmail(),phone.getText().toString(),randomkey,"Bags","122");
                            firebaseFirestore.collection("Indivisiul_Products")
                                    .document(firebaseAuth.getCurrentUser().getEmail())
                                    .collection("List")
                                    .document(randomkey)
                                    .set(genericProductModel)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                firebaseFirestore.collection("Products")
                                                        .document("122")
                                                        .collection("Bags")
                                                        .document(randomkey)
                                                        .set(genericProductModel)
                                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                if (task.isSuccessful()) {
                                                                    progressHUD.dismiss();
                                                                    startActivity(new Intent(getApplicationContext(),Main_Dash.class));
                                                                }
                                                            }
                                                        });
                                            }
                                        }
                                    });
                        }
                        //14
                        else  if (which==13) {
                              progress_check();
                              Random myRandom = new Random();
                              final String randomkey=String.valueOf(myRandom.nextInt(100000));
                              int id=Integer.parseInt(randomkey);
                              final GenericProductModel genericProductModel=new GenericProductModel(id,product_name.getText().toString(),url,product_des.getText().toString(),
                                      Float.parseFloat(porduct_price.getText().toString()),firebaseAuth.getCurrentUser().getEmail(),phone.getText().toString(),randomkey,"Smartphones","105");
                              firebaseFirestore.collection("Indivisiul_Products")
                                      .document(firebaseAuth.getCurrentUser().getEmail())
                                      .collection("List")
                                      .document(randomkey)
                                      .set(genericProductModel)
                                      .addOnCompleteListener(new OnCompleteListener<Void>() {
                                          @Override
                                          public void onComplete(@NonNull Task<Void> task) {
                                              if (task.isSuccessful()) {
                                                  firebaseFirestore.collection("Products")
                                                          .document("105")
                                                          .collection("Smartphones")
                                                          .document(randomkey)
                                                          .set(genericProductModel)
                                                          .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                              @Override
                                                              public void onComplete(@NonNull Task<Void> task) {
                                                                  if (task.isSuccessful()) {
                                                                      progressHUD.dismiss();
                                                                      startActivity(new Intent(getApplicationContext(),Main_Dash.class));
                                                                  }
                                                              }
                                                          });
                                              }
                                          }
                                      });
                        }
                        //115
                        else  if (which==14) {
                            progress_check();
                            Random myRandom = new Random();
                            final String randomkey=String.valueOf(myRandom.nextInt(100000));
                            int id=Integer.parseInt(randomkey);
                            final GenericProductModel genericProductModel=new GenericProductModel(id,product_name.getText().toString(),url,product_des.getText().toString(),
                                    Float.parseFloat(porduct_price.getText().toString()),firebaseAuth.getCurrentUser().getEmail(),phone.getText().toString(),randomkey,"Bags","124");
                            firebaseFirestore.collection("Indivisiul_Products")
                                    .document(firebaseAuth.getCurrentUser().getEmail())
                                    .collection("List")
                                    .document(randomkey)
                                    .set(genericProductModel)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                firebaseFirestore.collection("Products")
                                                        .document("124")
                                                        .collection("Bags")
                                                        .document(randomkey)
                                                        .set(genericProductModel)
                                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                if (task.isSuccessful()) {
                                                                    progressHUD.dismiss();
                                                                    startActivity(new Intent(getApplicationContext(),Main_Dash.class));
                                                                }
                                                            }
                                                        });
                                            }
                                        }
                                    });
                        }


                    }
                }).create().show();


    }
    private void progress_check() {
        progressHUD.setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait")
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();
    }
}