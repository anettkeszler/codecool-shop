package com.codecool.shop.controller;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.implementation.database.DatabaseManager;
import com.codecool.shop.dao.implementation.database.ProductCategoryDaoJdbc;
import com.codecool.shop.dao.implementation.database.ProductDaoJdbc;
import com.codecool.shop.dao.implementation.mems.ProductCategoryDaoMem;
import com.codecool.shop.dao.implementation.mems.ProductDaoMem;
import com.codecool.shop.config.TemplateEngineUtil;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;

@WebServlet(urlPatterns = {"/"})
public class ProductController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String categoryParameter = req.getParameter("category");

        var ctx = req.getServletContext();
        var dbManager = (DatabaseManager) ctx.getAttribute("dbManager");
        ProductDao productDataStore = dbManager.getProductDao();

        ProductCategoryDao productCategoryDataStore = dbManager.getProductCategoryDao();

        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());

        // set categories
        context.setVariable("categories", productCategoryDataStore.getAll());
        context.setVariable("openPage", "main.html");
        context.setVariable("css", "<link rel=\"stylesheet\" type=\"text/css\" href=\"/static/css/shopping-cart.css\" />\n");

        // if no category selected => ALL
        if (categoryParameter==null) {
            context.setVariable("products", productDataStore.getAll());
            context.setVariable("selected_category", "ALL");
        } else {
            int categoryId = Integer.parseInt(categoryParameter); // 1, 2, 3, 4, ...
            System.out.println(productDataStore);
            context.setVariable("products", productDataStore.getBy(productCategoryDataStore.find(categoryId)));
            context.setVariable("selected_category", String.valueOf(categoryId));
        }
        // Alternative setting of the template context
        engine.process("product/index.html", context, resp.getWriter());

    }
}
