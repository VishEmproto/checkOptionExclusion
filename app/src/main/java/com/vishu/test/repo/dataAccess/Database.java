package com.vishu.test.repo.dataAccess;

import androidx.room.RoomDatabase;

import com.vishu.test.models.Exclusion;
import com.vishu.test.models.Facility;
import com.vishu.test.models.Property;
import com.vishu.test.models.Option;

//@androidx.room.Database(entities = {Facility.class, Exclusion.class, Model.class, Option.class}, version =0)
public abstract class Database extends RoomDatabase {
    public abstract propertyDao propertyDao();
}
