package com.example.apteki.network

import java.io.IOException

class NoConnectivityException: IOException() {
    override val message: String?
        get() = "Нет связи с сервером. \nВозможно отключена сеть!"
}