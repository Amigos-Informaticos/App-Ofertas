package com.example.recycler;

import com.example.recycler.model.MiembroOfercompas;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void email_valido(){
        assertTrue(MiembroOfercompas.validarEmail("rendon.luisgerardo@gmail.com"));
    }

    @Test
    public void email_invalido_sindominio(){
        assertFalse(MiembroOfercompas.validarEmail("rendon.luisgerardo@.com"));
    }

    @Test
    public  void email_invalido_desordenado(){
        assertFalse(MiembroOfercompas.validarEmail(".com.rendon@gmail"));
    }
    @Test
    public  void email_invalido_con_espacios(){
        assertFalse(MiembroOfercompas.validarEmail("rendon. luisgerardo@gmail.com"));
    }
}