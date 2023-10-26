/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package com.example.cartApi.servicio;

import com.example.cartApi.modelo.Cart;
import static com.example.cartApi.servicio.CartService.CART;
import static com.example.cartApi.servicio.CartService.ID_CART;
import javax.servlet.http.HttpSession;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 *
 * @author joel
 */
@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class CartServiceTest {
    
    public CartServiceTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }
    
    @InjectMocks
    private CartService miServicio;
    
    @Mock
    private HttpSession httpSession;
    
    /**
     * Test of obtenerCartPorId method, of class CartService.
     */
    @Test
    public void testObtenerCartPorId() {
        String id = "1";
        // Configurar el comportamiento de la HttpSession simulada
        when(httpSession.getAttribute(CART + id)).thenReturn(new Cart());
        
        ResponseEntity<Object> resultado = miServicio.obtenerCartPorId(httpSession, id);
        ResponseEntity<Object> resultadoEsperado = new ResponseEntity<>(null, HttpStatus.OK);
        
        // Verificar que el método ha hecho lo que se esperaba
        assertEquals(resultadoEsperado.getStatusCode(), resultado.getStatusCode());
    }

    /**
     * Test of altaCart method, of class CartService.
     */
    @Test
    public void testAltaCart() {
        // Configurar el comportamiento de la HttpSession simulada
        when(httpSession.getAttribute(CART + ID_CART)).thenReturn(null);
        
        ResponseEntity<Object> resultado = miServicio.altaCart(httpSession);
        ResponseEntity<Object> resultadoEsperado = new ResponseEntity<>(null, HttpStatus.OK);
    
        // Verificar que el método ha hecho lo que se esperaba
        assertEquals(resultadoEsperado.getStatusCode(), resultado.getStatusCode());
    }

    /**
     * Test of eliminarCart method, of class CartService.
     */
    @Test
    public void testEliminarCart() {
        // Configurar el comportamiento de la HttpSession simulada
        when(httpSession.getAttribute(CART + ID_CART)).thenReturn(new Cart());
        
        ResponseEntity<Object> resultado = miServicio.eliminarCart(httpSession);
        ResponseEntity<Object> resultadoEsperado = new ResponseEntity<>(null, HttpStatus.OK);
    
        // Verificar que el método ha hecho lo que se esperaba
        assertEquals(resultadoEsperado.getStatusCode(), resultado.getStatusCode());
    }

    /**
     * Test of anadirProducto method, of class CartService.
     */
    @Test
    public void testAnadirProducto() {
        // Configurar el comportamiento de la HttpSession simulada
        when(httpSession.getAttribute(CART + ID_CART)).thenReturn(new Cart(1));
        
        ResponseEntity<Object> resultado = miServicio.anadirProducto(httpSession, "prueba producto", "2");
        ResponseEntity<Object> resultadoEsperado = new ResponseEntity<>(null, HttpStatus.OK);
    
        // Verificar que el método ha hecho lo que se esperaba
        assertEquals(resultadoEsperado.getStatusCode(), resultado.getStatusCode());
    }

    
}
