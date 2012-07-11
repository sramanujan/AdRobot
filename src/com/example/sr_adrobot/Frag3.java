package com.example.sr_adrobot;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class Frag3 extends Fragment
{
	public View layout3;
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) 
	{
		if (container == null) 
            return null;
		layout3 = inflater.inflate(R.layout.layout_frag3, container, false);
        PorterDuffColorFilter filter = new PorterDuffColorFilter(Color.GREEN, PorterDuff.Mode.SRC_ATOP);
        for(int i=0;i<16;i++)
        {
        	layout3.findViewById(R.id.b200+i).getBackground().setColorFilter(filter);
        }
		return layout3;
	}
}
