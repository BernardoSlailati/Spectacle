package com.slailati.android.spectacle.data.repository

import androidx.lifecycle.LiveData
import com.slailati.android.spectacle.data.model.Profile
import com.slailati.android.spectacle.data.model.Response
import com.slailati.android.spectacle.data.model.User

interface AuthRepository {

    fun registerUser(user: User): LiveData<Response<User>>

    fun login(user: User): LiveData<Response<Profile>>

    fun logout()

}