package com.shopanbdecommerce.vendor;

import android.app.Dialog;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.geniusforapp.fancydialog.FancyAlertDialog;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.squareup.picasso.Picasso;

import java.util.List;

//My_product_Adapter
public class My_product_Adapter extends RecyclerView.Adapter<My_product_Adapter.myView> {
    private final List<GenericProductModel> data;
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;
    KProgressHUD progressHUD;

    public My_product_Adapter(List<GenericProductModel> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public My_product_Adapter.myView onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.cards_cardview_layout,parent,false);
        return new My_product_Adapter.myView(view);

    }

    @Override
    public void onBindViewHolder(@NonNull  My_product_Adapter.myView holder, final int position) {
        Float ff=data.get(position).getCardprice();
        String a=""+ff;
        String image=data.get(position).getCardimage();
        holder.login_desc.setText(data.get(position).getCardname());
        holder.login1.setText(a);

        try {
            Picasso.get().load(image).into(holder.imageView);
        }catch(Exception e) {
            e.printStackTrace();
        }
        firebaseFirestore=FirebaseFirestore.getInstance();
        firebaseAuth=FirebaseAuth.getInstance();
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                progressHUD = KProgressHUD.create(v.getContext());



                                final FancyAlertDialog.Builder alert = new FancyAlertDialog.Builder(v.getContext())
                                        .setBackgroundColor(R.color.colorAccent)
                                        .setimageResource(R.drawable.delete3)
                                        .setTextTitle("Delete")
                                        .setCancelable(true)
                                        .setTextSubTitle("Are you want to delete this order?")
                                        .setPositiveButtonText("Cancel")
                                        .setPositiveColor(R.color.colorPrimaryDark)
                                        .setOnPositiveClicked(new FancyAlertDialog.OnPositiveClicked() {
                                            @Override
                                            public void OnClick(View view, Dialog dialog) {
                                                dialog.dismiss();



                                            }
                                        }).setNegativeColor(R.color.toolbar)
                                        .setNegativeButtonText("Delete")
                                        .setOnNegativeClicked(new FancyAlertDialog.OnNegativeClicked() {
                                            @Override
                                            public void OnClick(View view, Dialog dialog) {
                                                dialog.dismiss();
                                                progress_check();
                                                final String key=data.get(position).getKey();
                                                final String uuid=data.get(position).getUid();
                                                final String key2=data.get(position).getKey2();
                                                firebaseFirestore.collection("Indivisiul_Products")
                                                        .document(firebaseAuth.getCurrentUser().getEmail())
                                                        .collection("List")
                                                        .document(uuid)
                                                        .delete()
                                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                if (task.isSuccessful()) {
                                                                    firebaseFirestore.collection("Products")
                                                                            .document(key2)
                                                                            .collection(key)
                                                                            .document(uuid)
                                                                            .delete()
                                                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                @Override
                                                                                public void onComplete(@NonNull Task<Void> task) {
                                                                                    if (task.isSuccessful()) {
                                                                                        progressHUD.dismiss();
                                                                                        v.getContext().startActivity(new Intent(v.getContext(),Main_Dash.class));
                                                                                    }
                                                                                }
                                                                            });
                                                                }
                                                            }
                                                        });





                                            }
                                        })
                                        .setBodyGravity(FancyAlertDialog.TextGravity.CENTER)
                                        .setTitleGravity(FancyAlertDialog.TextGravity.CENTER)
                                        .setSubtitleGravity(FancyAlertDialog.TextGravity.CENTER)
                                        .setCancelable(false)
                                        .build();
                                alert.show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class myView extends RecyclerView.ViewHolder{
        TextView login_desc,login1;
        ImageView imageView;

        public myView(@NonNull  View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.cardimage);
            login_desc=itemView.findViewById(R.id.cardcategory);
            login1=itemView.findViewById(R.id.cardprice);
        }
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