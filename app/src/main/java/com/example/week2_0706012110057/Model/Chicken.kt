package com.example.week2_0706012110057.Model

class Chicken(override var name: String?, override var type:String?, override var age: Int?): Animal("","", 0) {
    override var sound: String = "Bok... Bok... Bok..."
    override var eat: String = "You feed your chicken biji-bijian?"

}