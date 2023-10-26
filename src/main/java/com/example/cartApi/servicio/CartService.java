/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.cartApi.servicio;

import com.example.cartApi.modelo.Cart;
import com.example.cartApi.modelo.Product;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import javax.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 *
 * @author joel
 */
@Service
public class CartService {

    public static final String CART = "cart";
    public static final String MENSAJE_ERROR_NO_CART = "No existe carrito";
    private static final String MENSAJE_CART_CREADO_OK = "Se ha creado el carrito correctamente";
    private static final String MENSAJE_CART_CREADO_KO = "Ya existe un carrito";
    private static final String MENSAJE_CART_VACIADO = "Se ha vaciado el carrito";
    private static final String MENSAJE_ERROR_PARAMS_ALTA_PRODUCTO = "Error en los parámetros de entrada";
    private static final String MENSAJE_PRODUCTO_ANADIDO_OK = "Se ha añadido el producto correctamente";
    public static final String ID_CART = "1";

    private Instant tiempoInicial = Instant.now();
    private boolean reiniciarContador = false;
    private HttpSession sesion = null;

    /**
     * Hacer que cada segundo compruebe en segundo plano el tiempo que lleva sin
     * modificarse el carrito
     *
     */
    @Scheduled(fixedRate = 1000) // Ejecuta el método cada segundo
    public void realizarComprobacionInactividad() {
        if (sesion != null) {
            realizarComprobacionInactividad(sesion);
        }
    }

    private void setearSesion(HttpSession sesion) {
        this.sesion = sesion;
    }

    /**
     * Obtener datos carrito
     *
     * @param sesion
     * @param id
     * @return
     */
    public ResponseEntity<Object> obtenerCartPorId(HttpSession sesion, String id) {
        Cart cart = (Cart) sesion.getAttribute(CART + id);
        if (cart != null) {
            setearSesion(sesion);
            return new ResponseEntity<>(cart, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(MENSAJE_ERROR_NO_CART, HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Metodo para dar de alta un carrito
     *
     * @param sesion
     * @return
     */
    public ResponseEntity<Object> altaCart(HttpSession sesion) {
        Cart cart = (Cart) sesion.getAttribute(CART + ID_CART);
        // En caso de que existan carritos dados de alta creamos el nuevo a raíz del último id
        if (cart == null) {
            cart = new Cart(1);
            sesion.setAttribute(CART + cart.getId().toString(), cart);
            tiempoInicial = Instant.now();
            setearSesion(sesion);
            return new ResponseEntity<>(MENSAJE_CART_CREADO_OK, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(MENSAJE_CART_CREADO_KO, HttpStatus.FOUND);
        }
    }

    /**
     * Metodo para eliminar el carrito
     *
     * @param sesion
     * @return
     */
    public ResponseEntity<Object> eliminarCart(HttpSession sesion) {
        Cart cart = (Cart) sesion.getAttribute(CART + ID_CART);
        if (cart == null) {
            return new ResponseEntity<>(MENSAJE_ERROR_NO_CART, HttpStatus.NOT_FOUND);
        } else {
            sesion.invalidate();
            setearSesion(null);
            return new ResponseEntity<>(MENSAJE_CART_VACIADO, HttpStatus.OK);
        }
    }

    /**
     * Metodo para añadir un producto al carrito
     *
     * @param sesion
     * @param descProducto
     * @param cantidad
     * @return
     */
    public ResponseEntity<Object> anadirProducto(HttpSession sesion, String descProducto, String cantidad) {
        Cart cart = (Cart) sesion.getAttribute(CART + ID_CART);
        if (cart != null) {
            if (validarProducto(descProducto, cantidad)) {
                boolean existeProducto = false;
                ArrayList<Product> listaProductos = (ArrayList<Product>) cart.getListaProductos();
                // Iteramos los productos que ya contiene el carrito, en caso de que ya tenga el producto que se está añadiendo se suma la cantidad
                for (Product producto : listaProductos) {
                    if (producto.getDescripcion().equalsIgnoreCase(descProducto)) {
                        existeProducto = true;
                        producto.setCantidad(producto.getCantidad() + Integer.parseInt(cantidad));
                    }
                }
                // Si el carrito no contiene el producto lo añadimos
                if (!existeProducto) {
                    Product producto = new Product(listaProductos.size() + 1, descProducto, Integer.parseInt(cantidad));
                    listaProductos.add(producto);
                }

                sesion.setAttribute(CART + ID_CART, cart);
                // Reiniciamos el contador de inactividad
                reiniciarContador = true;
                setearSesion(sesion);
                return new ResponseEntity<>(MENSAJE_PRODUCTO_ANADIDO_OK, HttpStatus.OK);

            } else {
                return new ResponseEntity<>(MENSAJE_ERROR_PARAMS_ALTA_PRODUCTO, HttpStatus.NOT_ACCEPTABLE);
            }

        } else {
            return new ResponseEntity<>(MENSAJE_ERROR_NO_CART, HttpStatus.NOT_FOUND);
        }
    }

    private boolean validarProducto(String nombreProducto, String cantidad) {
        boolean pasaValidacion = true;

        if (nombreProducto == null || nombreProducto.trim().equals("")) {
            pasaValidacion = false;
        } else if (cantidad == null || cantidad.trim().equals("")) {
            pasaValidacion = false;
        } else {
            try {
                Integer tmpCantidad = Integer.parseInt(cantidad);
                if (tmpCantidad < 1) {
                    pasaValidacion = false;
                }
            } catch (NumberFormatException e) {
                pasaValidacion = false;
            }
        }

        return pasaValidacion;
    }

    /**
     * Eliminar el carrito si lleva 10 minutos inactivos
     *
     * @param sesion
     */
    public void realizarComprobacionInactividad(HttpSession sesion) {
        Cart cart = (Cart) sesion.getAttribute(CART + ID_CART);
        if (cart != null) {
            // Si se modifica el carrito se reinicia el tiempo de inactividad
            if (reiniciarContador) {
                tiempoInicial = Instant.now();
                reiniciarContador = false;
            }

            Instant tiempoActual = Instant.now();
            Duration duracionTranscurrida = Duration.between(tiempoInicial, tiempoActual);

            // Si el carrito lleva mas de 10 minutos sin ser modificado se elimina
            if (duracionTranscurrida.getSeconds() >= 600) {
                eliminarCart(sesion);
                reiniciarContador = false;
            }
        }

    }

}
