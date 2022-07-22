package com.example.onlineShopApp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class ProductController {
    @Autowired
    UserSession userSession;

    @Autowired
    ProductService productService;

    @Autowired
    OrderLinesDao orderLinesDao;

    @PostMapping("/add-to-cart")
    public ModelAndView addToCart(@RequestParam("productId") int id){
        ModelAndView modelAndView = new
                ModelAndView("dashboard");
        userSession.addToCart(id);
        modelAndView.addObject("shoppingCartSize", userSession.getCartSize());
        modelAndView.addObject("products", productService.getAllProducts());

        return modelAndView;
    }

    @GetMapping("/productDetails")
    public ModelAndView getProductDetails(@RequestParam("productId") int id){
        ModelAndView modelAndView = new ModelAndView("product");
        Product p = productService.findProductById(id);
        modelAndView.addObject("p", p);
        return modelAndView;
    }

    @GetMapping("/shoppingCart")
    public ModelAndView modelAndView(){
        ModelAndView modelAndView = new ModelAndView("cart");
        List<Cart> cartItems = new ArrayList<>();
        double totalPretComanda = 0;
        for (Map.Entry<Integer, Integer> set : userSession.getShoppingCart().entrySet()) {
            Cart c = new Cart();

            Product product = new Product();
            product = productService.findProductById(set.getKey());

            c.setCantitateCos(set.getValue());
            c.setSumaTotala(c.getCantitateCos()*product.getPret());
            c.setNume(product.getNume());
            c.setId(product.getId());

            cartItems.add(c);

            totalPretComanda = totalPretComanda + c.getSumaTotala();
        }
        modelAndView.addObject("cartItems", cartItems);
        modelAndView.addObject("totalPretComanda", totalPretComanda);
        return modelAndView;
    }

    @PostMapping("/sendOrder")
    public ModelAndView sendOrder(){
        ModelAndView modelAndView = new ModelAndView("orderSuccess");
        Order order = new Order();
        List<OrderLines> orderLinesList = new ArrayList<>();
        order.setUser_id(userSession.getId());

        for (Map.Entry<Integer, Integer> set : userSession.getShoppingCart().entrySet()) {

            Product product = productService.findProductById(set.getKey());
            OrderLines orderLines = new OrderLines();
            orderLines.setProduct_id(set.getKey());
            orderLines.setQuantity(set.getValue());
            orderLines.setTotalPrice(set.getValue() * product.getPret());
            orderLines.setOrder(order);
            //orderLinesList.add(orderLines);
            orderLinesDao.save(orderLines);
        }
        userSession.clearCart();
        return modelAndView;
    }
}
