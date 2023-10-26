/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.cartApi.controlador;

import com.example.cartApi.servicio.CartService;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author joel
 */
@RestController
public class CartCO {

    @Autowired
    private CartService servicio;

    @GetMapping("/obtenerCartPorId")
    public ResponseEntity<Object> obtenerCartPorId(HttpSession sesion,
            @RequestParam(required = true) String id) {
        return servicio.obtenerCartPorId(sesion, id);
    }

    @PostMapping("/altaCart")
    public ResponseEntity<Object> altaCart(HttpSession sesion) {
        return servicio.altaCart(sesion);
    }

    @GetMapping("/eliminarCart")
    public ResponseEntity<Object> eliminarCart(HttpSession sesion) {
        return servicio.eliminarCart(sesion);
    }

    @PostMapping("/anadirProducto")
    public ResponseEntity<Object> anadirProducto(HttpSession sesion,
            @RequestParam(required = true) String descProducto,
            @RequestParam(required = true) String cantidad) {
        return servicio.anadirProducto(sesion, descProducto, cantidad);
    }

}
