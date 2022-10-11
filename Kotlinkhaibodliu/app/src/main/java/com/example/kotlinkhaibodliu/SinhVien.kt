package com.example.kotlinkhaibodliu

class SinhVien {
    private var HoTen : String = ""
    private var DiaChi : String = ""
    private var NamSinh : Int = 0

    fun getHoTen(): String {
        return HoTen
    }
    fun setHoTen(ht : String){
        HoTen = ht
    }
    fun getDiaChi(): String {
        return DiaChi
    }
    fun setDiaChi(dc : String){
        DiaChi = dc
    }
    fun getNamSinh(): Int {
        return NamSinh
    }
    fun setNamSinh(dc : Int){
        NamSinh = dc
    }
}