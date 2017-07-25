package shahzaib.com.trafficupdate.fragments;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import shahzaib.com.trafficupdate.Model.Postitem;
import shahzaib.com.trafficupdate.R;

/**
 * Created by shahzaib on 7/25/2017.
 */

public class Homefragment extends BaseFragment {
    RecyclerView postlist;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;
    ArrayList<Postitem> postitemArrayList;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setFragmentView(inflater.inflate(R.layout.home_fragment,container,false));

        //setting view

        postlist = (RecyclerView) findViewById(R.id.statuslist);
        layoutManager = new LinearLayoutManager(getContext());

        postitemArrayList = new ArrayList<>();
        adapter = new Postadapter(postitemArrayList);

        postlist.setLayoutManager(layoutManager);
        postlist.setAdapter(adapter);

        FirebaseDatabase.getInstance().getReference().child("Posts").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Postitem postitem = dataSnapshot.getValue(Postitem.class);
                if(postitem != null && postitem.getName() != null) {
                    postitemArrayList.add(postitem);
                    Collections.reverse(postitemArrayList);
                    adapter.notifyItemInserted(postitemArrayList.size() - 1);





                }





            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        return getFragmentView();
    }



 public  class Postadapter extends RecyclerView.Adapter<PostViewHolder> {

    ArrayList<Postitem> postItems;

    public Postadapter(ArrayList<Postitem> postItems) {
        this.postItems = postItems;
    }

    @Override
    public PostViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PostViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.feed_items,parent,false));
    }

    @Override
    public void onBindViewHolder(final PostViewHolder holder, int position) {
        Postitem postitem = postItems.get(position);
        Glide.with(getContext()).load(postitem.getImage()).asBitmap().override(150, 150).centerCrop().into(new BitmapImageViewTarget(holder.profile) {
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable circularBitmapDrawable =
                        RoundedBitmapDrawableFactory.create(getContext().getResources(), resource);
                circularBitmapDrawable.setCircular(true);
                holder.profile.setImageDrawable(circularBitmapDrawable);

            }
        });
        holder.name.setText(postitem.getName());
        holder.textmessange.setText(postitem.getTextmessage());
        CharSequence ago =
                DateUtils.getRelativeTimeSpanString(postitem.getTime(), System.currentTimeMillis(), DateUtils.MINUTE_IN_MILLIS);
        holder.time.setText(ago);

    }

    @Override
    public int getItemCount() {
      return  postItems.size();
    }
}
public  class PostViewHolder extends RecyclerView.ViewHolder {
    TextView name,textmessange;
    ImageView profile;
    TextView time;



    public PostViewHolder(View itemView) {
        super(itemView);
        name = (TextView) itemView.findViewById(R.id.name);
        textmessange = (TextView) itemView.findViewById(R.id.txtStatusMsg);
        time = (TextView) itemView.findViewById(R.id.timestamp);
        profile = (ImageView) itemView.findViewById(R.id.profilePic);
    }
}
















}
