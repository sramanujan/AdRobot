package com.example.sr_adrobot;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class Frag2 extends Fragment
{
	public View layout2;
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) 
	{
		if (container == null) 
            return null;
		layout2 = inflater.inflate(R.layout.layout_frag2, container, false);
        PorterDuffColorFilter filter = new PorterDuffColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);
        for(int i=0;i<16;i++)
        {
        	layout2.findViewById(R.id.b100+i).getBackground().setColorFilter(filter);
        }
		return layout2;
	}
}
