package com.example.sr_adrobot;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class Frag1 extends Fragment
{
	public View layout1;
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) 
	{
		if (container == null) 
            return null;
		layout1 = inflater.inflate(R.layout.layout_frag1, container, false);
        PorterDuffColorFilter filter = new PorterDuffColorFilter(Color.BLUE, PorterDuff.Mode.SRC_ATOP);
        for(int i=0;i<16;i++)
        {
        	layout1.findViewById(R.id.b000+i).getBackground().setColorFilter(filter);
        }
		return layout1;
	}
}
