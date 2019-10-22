package com.vishu.test.repo.dataAccess;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.vishu.test.models.Facility;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface propertyDao {
    @Insert(onConflict = REPLACE)
    Long insert(Facility user);

    /*@Query("SELECT * FROM Facility WHERE facilityId = :facilityId")
    Facility load(String facilityId);*/

}
