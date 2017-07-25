package shahzaib.com.trafficupdate.fragments;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import shahzaib.com.trafficupdate.Activities.Main2Activity;
import shahzaib.com.trafficupdate.Model.Postitem;
import shahzaib.com.trafficupdate.R;

import static android.R.attr.resource;

/**
 * Created by shahzaib on 7/25/2017.
 */

public class PostStatusfragment extends BaseFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setFragmentView(inflater.inflate(R.layout.poststatus_fragment, container, false));


        final FirebaseAuth auth;
        auth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = auth.getCurrentUser();


        if (currentUser != null && currentUser.getPhotoUrl().toString() != null) {
            final ImageView imageView = (ImageView) findViewById(R.id.imageViewp);
            TextView textname = (TextView) findViewById(R.id.usernamep);
            textname.setText(currentUser.getDisplayName());

            Glide.with(this).load(currentUser.getPhotoUrl().toString()).asBitmap().override(150, 150).centerCrop().into(new BitmapImageViewTarget(imageView) {
                @Override
                protected void setResource(Bitmap resource) {
                    RoundedBitmapDrawable circularBitmapDrawable =
                            RoundedBitmapDrawableFactory.create(PostStatusfragment.this.getResources(), resource);
                    circularBitmapDrawable.setCircular(true);
                    imageView.setImageDrawable(circularBitmapDrawable);


                }
            });;


                final EditText etPost = (EditText) findViewById(R.id.etPost);
                final Button postBtn = (Button) findViewById(R.id.postBtn);

                postBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                    String postText = etPost.getText().toString();
                        if(postText.length() <= 0) {
                            etPost.setError("Enter some words");
                        }
                       final ProgressDialog progressDialog = new ProgressDialog(getContext());
                        progressDialog.setMessage("Posing...");
                        progressDialog.show();

                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                        FirebaseDatabase.getInstance().getReference().child("Posts").push().setValue(new Postitem(user.getDisplayName(),postText,user.getPhotoUrl().toString(), System.currentTimeMillis())).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                if (task.isComplete()) {
                                    Toast.makeText(getContext(), "Posted!", Toast.LENGTH_SHORT).show();
                                    etPost.setText("");


                                } else {
                                    Toast.makeText(getContext(), "Failed Try again later!", Toast.LENGTH_SHORT).show();
                                }
                                progressDialog.dismiss();
                            }
                        });
                    }
                });
        }


            return getFragmentView();
        }

}
