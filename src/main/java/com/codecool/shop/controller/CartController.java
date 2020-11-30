package com.codecool.shop.controller;

import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.implementation.mems.ProductDaoMem;
import com.codecool.shop.model.Cart;
import com.codecool.shop.model.Product;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = {"/cart"})
public class CartController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int productId = Integer.parseInt(req.getParameter("product-id"));
        Gson gson = new Gson();
        ProductDao productDao = ProductDaoMem.getInstance();

        Cart cart = Cart.getInstance();
        PrintWriter out = resp.getWriter();

        if (req.getServletPath().equals("/cart")) {
            Product product = productDao.find(productId);
            cart.add(product);
            out.print(gson.toJson(cart.getShoppingCart()));
        }
    out.flush();
    }
}
