package com.example.springwebtask.Controller;

import com.example.springwebtask.Entity.UpdateRecord;
import com.example.springwebtask.Entity.productsRecord;
import com.example.springwebtask.Entity.usersRecord;
import com.example.springwebtask.Form.InsertForm;
import com.example.springwebtask.Form.LoginForm;
import com.example.springwebtask.Form.UpdateForm;
import com.example.springwebtask.Service.PmProductService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class TestController {

    @Autowired
    PmProductService pmProductService;

    @Autowired
    private HttpSession session;

    @GetMapping("/login")
    public String loginScreen(@ModelAttribute("login") LoginForm Form) {
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

        if (session.getAttribute("sessionUser") == null) {
            return "redirect:/login";
        }
        if (word.equals("all")) {
            model.addAttribute("products", pmProductService.findAll());
            return "menu";
        }
        if (word == null) {
            model.addAttribute("products", pmProductService.findAll());
            return "menu";
        }
        List<productsRecord> list = pmProductService.searchProducts(word);
        if (list == null) {
            model.addAttribute("products", list);
            return "menu";
        }
        model.addAttribute("products", pmProductService.searchProducts(word));
        return "menu";
    }

    @GetMapping("/insert")
    public String insert(@ModelAttribute("insertForm") InsertForm insertForm, Model model) {

        if (session.getAttribute("sessionUser") == null) {
            return "redirect:/login";
        }
        model.addAttribute("categories", pmProductService.categoriesName());
        return "insert.html";
    }

    @PostMapping("insert")
    public String insertPost(@Validated @ModelAttribute("insertForm") InsertForm insertForm, BindingResult bindingResult,
                             Model model) {

        if (bindingResult.hasErrors()) {
//            ・登録ボタンを押してinsert.htmlを表示する際に、セレクトボックスの情報を持った状態で表示するために、
//            model.addAttribute()で情報を表示したいhtmlに渡す必要がある
//            ・この処理文がないと、登録を押した後表示されるinsert.htmlでセレクトボックスが所持されていない状態となる
            model.addAttribute("categories", pmProductService.categoriesName());
            return "insert.html";
        }
        var getProductIdRecord = pmProductService.findByproductId(insertForm.getProductId());
        if (getProductIdRecord != null) {
            model.addAttribute("Duplicationerror", "商品コードが重複しています");
            model.addAttribute("categories", pmProductService.categoriesName());
            return "insert.html";
        }
        pmProductService.insert(insertForm.getProductId(), insertForm.getName(),
                insertForm.getPrice(), insertForm.getCategoryId(), insertForm.getDescription());
        model.addAttribute("message", "登録に");
        return "success.html";
    }

    @GetMapping("/detail/{product_id}")
    public String detail(@PathVariable("product_id") String product_id, Model model) {

        if (session.getAttribute("sessionUser") == null) {
            return "redirect:/login";
        }
        var result = pmProductService.findByproductid(product_id);
        model.addAttribute("productDetail", result);
        model.addAttribute("productcategory", pmProductService.getCategoryName(result.category_id()));
        return "detail.html";
    }

    @PostMapping("/detail/{product_id}")
    public String delete(@PathVariable("product_id") String product_id, Model model) {
        pmProductService.delete(product_id);
        model.addAttribute("message", "削除に");
        return "success.html";
    }

    @GetMapping("/updateInput/{id}")
    public String update(@PathVariable("id") int id,
                         @ModelAttribute("updateForm") UpdateForm updateForm,
                         Model model){

        if (session.getAttribute("sessionUser") == null) {
            return "redirect:/login";
        }
        var product = pmProductService.findById(id);
        updateForm.setId(product.id());
        updateForm.setProduct_id(product.product_id());
        updateForm.setName(product.name());
        updateForm.setPrice(product.price());
        updateForm.setCategory_id(product.category_id());
        updateForm.setDescription(product.description());
        model.addAttribute("categories", pmProductService.categoriesName());
        return "updateInput.html";
    }

    @PostMapping("/updateInput/{id}")
    public String UpdateProduct(@Validated @ModelAttribute("updateForm") UpdateForm updateForm, BindingResult bindingResult,
                                @PathVariable("id") int id,
                                Model model){
        if (bindingResult.hasErrors()) {
            model.addAttribute("categories", pmProductService.categoriesName());
            return "updateInput.html";
        }
        var getProductIdRecord = pmProductService.findByproductid(updateForm.getProduct_id()) ;
        if (getProductIdRecord != null) {
            model.addAttribute("Duplicationerror", "商品コードが重複しています");
            model.addAttribute("categories", pmProductService.categoriesName());
            return "updateInput.html";
        }
        var updateRecord = new UpdateRecord(updateForm.getId(), updateForm.getProduct_id(), updateForm.getName(), updateForm.getPrice(), updateForm.getCategory_id(), updateForm.getDescription());
        pmProductService.update(updateRecord);
        model.addAttribute("message", "更新に");
        return "success.html";
    }

    @PostMapping("/logout")
    public String logout() {
        session.invalidate();
        return "logout";
    }

}
