package com.entrego.entregouser.web.model.request


class AddCardBody(val name: String,
                  val number: Long,
                  val month: Int,
                  val year: Int,
                  val cvv: Short){

    override fun toString(): String {
        return "AddCardBody(name='$name', number=$number, month=$month, year=$year, cvv=$cvv)"
    }
}