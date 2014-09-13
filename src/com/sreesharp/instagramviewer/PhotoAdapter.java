package com.sreesharp.instagramviewer;

import java.util.List;

import com.squareup.picasso.Picasso;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


public class PhotoAdapter extends ArrayAdapter<InstagramPhoto> {

	public PhotoAdapter(Context context, List<InstagramPhoto> photos) {
		super(context,android.R.layout.simple_list_item_1 , photos);
		// TODO Auto-generated constructor stub
	}


	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		InstagramPhoto photo = getItem(position);
        if (v == null) {
           v = LayoutInflater.from(getContext()).inflate(R.layout.photo_item, parent, false);
        }
        
        TextView tvUserName = (TextView)v.findViewById(R.id.tvUserName);
        tvUserName.setText(photo.userName);
        

        ImageView imgPhoto = (ImageView)v.findViewById(R.id.imgPhoto);
        ImageView imgProfile = (ImageView)v.findViewById(R.id.imgProfile);

        imgPhoto.getLayoutParams().height = (int)(parent.getHeight() * 0.7);
        imgPhoto.setImageResource(0);
        Picasso.with(v.getContext()).load(photo.imageUrl).into(imgPhoto);

        imgProfile.getLayoutParams().height = (int)(parent.getHeight() * 0.1);;
        imgProfile.setImageResource(0);
        Picasso.with(v.getContext()).load(photo.userProfileUrl).into(imgProfile);
        TextView tvLikes = (TextView)v.findViewById(R.id.tvLikes);
        tvLikes.setText(photo.likesCount + " likes");
        
        TextView tvCaption = (TextView)v.findViewById(R.id.tvCaption);
        tvCaption.setText(photo.caption);
        return v;
	}
	
	

}
