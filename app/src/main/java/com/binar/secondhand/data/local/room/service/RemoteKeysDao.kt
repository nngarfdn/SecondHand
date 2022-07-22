package com.binar.secondhand.data.local.room.service

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.binar.secondhand.data.local.room.model.RemoteKeys

@Dao
interface RemoteKeysDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
     fun insertAll(remoteKey: List<RemoteKeys>)

    @Query("SELECT * FROM remote_keys WHERE productId = :productId")
     fun remoteKeysProductId(productId: Int): RemoteKeys?

    @Query("DELETE FROM remote_keys")
     fun clearRemoteKeys()

}