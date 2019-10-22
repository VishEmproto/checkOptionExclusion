package com.vishu.test.Callbacks;

import com.vishu.test.models.Exclusion;
import com.vishu.test.models.Facility;
import com.vishu.test.models.Option;

import java.util.List;

public interface ExcludeOptionsListener
{
    public void itemsToBeExcluded(List<Exclusion> facilitiesExcluded);

}
