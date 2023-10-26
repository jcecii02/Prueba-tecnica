/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.cartApi.modelo;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author joel
 */
public class Cart {

    private Integer id;
    private List<Product> listaProductos;

    public Cart() {
        listaProductos = new ArrayList();
    }

    public Cart(Integer id) {
        this.id = id;
        listaProductos = new ArrayList();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Product> getListaProductos() {
        return listaProductos;
    }

    public void setListaProductos(List<Product> listaProductos) {
        this.listaProductos = listaProductos;
    }

}
