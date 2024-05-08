package com.example.dummytestapprichit.utils

import com.example.dummytestapprichit.beans.Item

class TestClass {
}

fun main(vararg args: String){
    println("hello")

    val inputnumber = 17
    var itemArray = arrayOf("")
    for(i in 1 until inputnumber){

        if(i%3==0 && i%5==0) {
//            itemArray.plus("threefive ")
            itemArray += "threefive "
        } else if(i%3==0){
            itemArray +="Three "
        } else if(i%5==0) {
            itemArray +="five "
        } else {
            itemArray +="${i} "
        }
    }
//    itemArray.forEach {
        println(itemArray.joinToString())
//    }


 }