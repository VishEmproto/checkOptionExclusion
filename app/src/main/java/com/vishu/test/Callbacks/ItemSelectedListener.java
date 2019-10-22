package com.vishu.test.Callbacks;

import android.view.View;

import com.vishu.test.models.Facility;
import com.vishu.test.models.Option;

import java.util.List;

public interface ItemSelectedListener
{
    public void onOptionSelected(View view,Facility facility,Option option);
}
