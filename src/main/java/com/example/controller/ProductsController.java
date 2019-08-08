package com.example.controller;

import com.example.entity.Product;
import com.example.entity.Role;
import com.example.entity.User;
import com.example.service.CartService;
import com.example.service.ProductService;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;

// класс контроллер помечен аннотацией, что бы Spring внедрил его в необходимое место. взаимодействует с отображением и сервисами
@Controller
public class ProductsController {

    private CartService cartService;
    private ProductService productService;
    private UserService userService;
    private HttpSession httpSession;
    private String currentParam = "all";
    private Integer currentPage = 1;

    // внедряем объекты сервисов
    @Autowired
    public void setCartService(CartService cartService) {
        this.cartService = cartService;
    }

    @Autowired
    public void setHttpSession(HttpSession httpSession) {
        this.httpSession = httpSession;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    // получаем параметры с номером страницы для пагинации или параметром сортировки
    @RequestMapping(value = "/userPage", method = RequestMethod.GET)
    public ModelAndView userPage(HttpSession httpSession, @RequestParam(required = false) Integer page, @RequestParam(required = false) String param) {
        ModelAndView modelAndView = new ModelAndView();
        // из http сессии получаем активного юзера
        User user = (User) httpSession.getAttribute("user");
        boolean admin = false;
        for (Role role : user.getRoles()) {
            if (role.getId() == 2L) {
                admin = true;
                break;
            }
        }
        // если пользователь - админ, передаем на страницу объект админ
        modelAndView.addObject("admin", admin);
        if (!user.getLoggingIn()) {
            modelAndView.setViewName("views/login");
            return modelAndView;
        }
        // если пользователь юзер, передаем на страницу юзера
        modelAndView.setViewName("views/userPage");
        modelAndView.addObject("user", user);
        // отображение страниц с товарами в зависимости от сортировки и от выбранной страницы
        if (page != null) {
            currentPage = page;
        }
        if (param != null) {
            if (param.equals("")) {
                param = "all";
            }
            currentParam = param;
            currentPage = 1;
        }
        List<Product> productList = productService.productList(currentParam);
        modelAndView.addObject("productList", productList);
        PagedListHolder<Product> pagedListHolder = new PagedListHolder<>(productList);
        pagedListHolder.setPageSize(10);
        modelAndView.addObject("maxPages", pagedListHolder.getPageCount());
        if (currentPage < 1 || currentPage > pagedListHolder.getPageCount()) {
            currentPage = 1;
        }
        modelAndView.addObject("page", currentPage);
        if (currentPage > pagedListHolder.getPageCount()) {
            pagedListHolder.setPage(0);
            modelAndView.addObject("productList", pagedListHolder.getPageList());
        } else if (currentPage <= pagedListHolder.getPageCount()) {
            pagedListHolder.setPage(currentPage - 1);
            modelAndView.addObject("productList", pagedListHolder.getPageList());
        }
        // для админа показываем общую стоимость товаров
        if (!admin) {
            List<Product> cart = productService.getCart(user.getId());
            Double totalCost = cart.stream().mapToDouble(Product::getPrice).sum();
            modelAndView.addObject("cart", cart);
            modelAndView.addObject("totalCost", totalCost);
        } else {
            // юзеру показываем стоимость его покупки
            List<Product> allProducts = productService.productList("all");
            Double totalCost = allProducts.stream().mapToDouble(Product::getPrice).sum();
            modelAndView.addObject("totalCostAdmin", totalCost);
        }
        return modelAndView;
    }

    @RequestMapping(value = "/addToCart/{id}")
    public String addToCart(@PathVariable("id") Long id) {
        User user = (User) httpSession.getAttribute("user");
        cartService.addProductToCart(user.getId(), id);
        return "redirect:/userPage";
    }

    @RequestMapping(value = "/removeFromCart/{id}")
    public String removeProductFromCart(@PathVariable("id") Long id) {
        cartService.removeProductFromCartById(id);
        return "redirect:/userPage";
    }

    @RequestMapping(value = "/removeProduct/{id}")
    public String removeProduct(@PathVariable("id") Long id) {
        productService.removeProductById(id);
        return "redirect:/userPage";
    }

    // контроллер для обновления инфо о товаре для админа
    @RequestMapping(value = "/update")
    public String updateProduct(@RequestParam Long id, @RequestParam String productname, @RequestParam String description,
                                @RequestParam Double price, @RequestParam Long count) {
        if (id == null) {
            return "redirect:/userPage";
        }
        Product product = productService.getProductById(id);
        if (productname != null) {
            product.setProductname(productname);
            productService.updateProduct(product);
        }
        if (description != null) {
            product.setDescription(description);
            productService.updateProduct(product);
        }
        if (price != null) {
            product.setPrice(price);
            productService.updateProduct(product);
        }
        if (count != null) {
            product.setCount(count);
            productService.updateProduct(product);
        }
        return "redirect:/userPage";
    }

    // контроллер для добавления товара для админа
    @RequestMapping(value = "/add")
    public String updateProduct(@RequestParam String productname, @RequestParam String description,
                                @RequestParam Double price, @RequestParam Long count) {
        if (productname == null || description == null || price == null || count == null) {
            return "redirect:/userPage";
        }
        Product product = new Product(productname, description, price, count);
        productService.addProduct(product);
        return "redirect:/userPage";
    }

    // кнопка выхода
    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public String logout() {
        User user = (User) httpSession.getAttribute("user");
        user.setLoggingIn(false);
        userService.update(user);
        return "views/login";
    }

    // кнопка покупки
    @RequestMapping(value = "/buyCart", method = RequestMethod.POST)
    public String buyCart() {
        cartService.buyCart();
        return "redirect:/userPage";
    }

}
