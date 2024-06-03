package com.example.springwebtask.Controller;

import com.example.springwebtask.Entity.productsRecord;
import com.example.springwebtask.Entity.usersRecord;
import com.example.springwebtask.Form.InsertForm;
import com.example.springwebtask.Form.LoginForm;
import com.example.springwebtask.Service.PmProductService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class TestController {

    @Autowired
    PmProductService pmProductService;

    @Autowired
    private HttpSession session;

    @GetMapping("/login")
    public String loginScreen(@ModelAttribute("login") LoginForm Form) {
        System.out.println(pmProductService.categoriesName());
        return "index";
    }

    @PostMapping("/login")
    public String menu(@Validated @ModelAttribute("login") LoginForm Form, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return "index";
        }

        //userが入力されたログインID（login_id）を元に持ってきたレコード
        var user = pmProductService.findByid(Form.getLogin_id());

        //テーブル内に存在しないlogin_idが入力された際、レコード（user）がNULLになるため、その時点でエラーメッセージを出力し、パスワードの比較にそもそもいけないようにする
        if (user == null) {
            model.addAttribute("error", "IDまたはパスワードが不正です");
            return "index";
        }

        if (Form.getPassword().equals(user.password())) {
            var users = new usersRecord(user.id(), user.login_id(), user.password(), user.name(), user.role());
            session.setAttribute("sessionUser", users);
            return "redirect:/menu";
        }

        model.addAttribute("error", "IDまたはパスワードが不正です");
        return "index";
    }

    @GetMapping("/menu")
    public String menu(@RequestParam(name = "keyword", defaultValue = "all") String word, Model model) {

        if (word.equals("all")) {
            model.addAttribute("products", pmProductService.findAll());
            return "menu";
        }
        if (word == null){
            model.addAttribute("products", pmProductService.findAll());
            return "menu";
        }
        List<productsRecord> list = pmProductService.searchProducts(word);
        if (list == null){
            model.addAttribute("products", list);
            return "menu";
        }
        model.addAttribute("products", pmProductService.searchProducts(word));
        return "menu";
    }

    @GetMapping("/insert")
    public String insert(@ModelAttribute("insertForm")InsertForm insertForm, Model model) {
        model.addAttribute("categories", pmProductService.categoriesName());
        return "insert.html";
    }

    @PostMapping("insert")
    public String insertPost(@ModelAttribute("insertForm")InsertForm insertForm) {
//
//        System.out.println(insertForm.getProductId());
//        System.out.println(insertForm.getName());
//        System.out.println(insertForm.getPrice());
//        System.out.println(insertForm.getCategoryId());
//        System.out.println(insertForm.getDescription());
//        return "insert.html";

//        if (bindingResult.hasErrors()) {
//            return "insert.html";
//        }

        pmProductService.categories(insertForm.getProductId(), insertForm.getName(),
                insertForm.getPrice(), insertForm.getCategoryId(), insertForm.getDescription());
        return "redirect:/insert";
    }

//    @PostMapping("/product-add")
//    public String addproduct(@Validated @ModelAttribute("addForm") AddForm addForm, BindingResult bindingResult) {
//
//        if (bindingResult.hasErrors()) {
//            return "addProduct";
//        }
//        pgProductService.insert(addForm.getProductName(), addForm.getPrice());
//        return "redirect:/product-list";
//    }
//    selectタグの中にあるcategoryの情報を取得したいけどやり方が分からない
//    htmlの記述で画面に表示はできているのでhtmlで情報は保持できていると思う


}
