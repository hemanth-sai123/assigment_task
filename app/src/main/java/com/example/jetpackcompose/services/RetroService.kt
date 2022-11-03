package com.example.jetpackcompose.services

class RetroService {
    companion object {
        fun getBoomServiceRetroInstance() =
            RetroInstance.getRetroInstance().create(RetroServiceInstance::class.java)

    }

}