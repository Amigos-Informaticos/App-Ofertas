package com.example.consumirappi

import com.example.consumirappi.modelo.EmailValidator
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun verficarCorreoCorrecto(){
        var correo = "rendon.luisgerardo@gmail.com";
        var emailValidator =  EmailValidator()

        var bandera = emailValidator.isValidEmail(correo)


        assertTrue(bandera);
    }

    @Test
    fun verificarCorreoSinDominio(){
        var correo = "rendon.luisgerardo"
        var emailValidator =  EmailValidator()
        var bandera = emailValidator.isValidEmail(correo)




        assertFalse(bandera);

    }
}