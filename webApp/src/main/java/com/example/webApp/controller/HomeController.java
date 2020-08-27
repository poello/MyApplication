package com.example.webApp.controller;

import com.example.webApp.CreateQuery;
import com.example.webApp.SearchingQuery;
import com.example.webApp.User;
import com.example.webApp.UserList;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;

@Controller
public class HomeController {

    // inject via application.properties
    private String searchByLogin = "abcd";

    RestTemplate restTemplate = new RestTemplate();

    @GetMapping("/welcome")
    public String homePage() {
        return "welcome";
    }

    @GetMapping("/search")
    public String goToSearch(Model model) {
        model.addAttribute("searchingQuery", new SearchingQuery()); //adding to model so that we can use it in .html
        UserList users = restTemplate.getForObject("http://localhost:8080/users?userLogin=ą", UserList.class);
        model.addAttribute("users", users.getUsers());
//        model.addAttribute("searchByLogin", searchByLogin);

        return "search"; //view
    }

    @PostMapping("/search")
    public String retrieveUserList(@ModelAttribute("searchingQuery") SearchingQuery searchingQuery, Model model) {
        UserList users = restTemplate.getForObject("http://localhost:8080/users?userLogin=" + searchingQuery.getByLogin(), UserList.class);
        model.addAttribute("users", users.getUsers());

        return "search"; //view
    }

    @GetMapping("/searchById")
    public String goToSearchById(Model model) {
        model.addAttribute("searchingQuery", new SearchingQuery());
        User user = restTemplate.getForObject("http://localhost:8080/user?identyfikator=1", User.class);
        model.addAttribute("user", user);

        return "searchById";
    }

    @PostMapping("/searchById")
    public String retrieveUser(@ModelAttribute("searchingQuery") SearchingQuery searchingQuery, Model model) {
        User user = restTemplate.getForObject("http://localhost:8080/user?identyfikator=" + searchingQuery.getById(), User.class);
        model.addAttribute("user", user);

        return "searchById";
    }

    @GetMapping("/delete")
    public String goToDelete() {
        return "delete";
    }

//    @DeleteMapping("/delete")
//    public String deleteUser(@ModelAttribute("searchingQuery") SearchingQuery searchingQuery, Model model) {
//        User user = restTemplate.getForObject("http://localhost:8080/user?identyfikator=" + searchingQuery.getById(), User.class);
//        model.addAttribute("user", user);
//
//        return "delete";
//    }

    @GetMapping("/createUser")
    public String goToCreateUser() {
        return "createUser";
    }

    @PostMapping("/createUser")
    public String createUser(@ModelAttribute("createQuery") CreateQuery createQuery) {
        User user = restTemplate.getForObject("http://localhost:8080/createUser?userLogin=" + createQuery.getLogin()
                                + "&password=" + createQuery.getPassword() + "&firstName=" + createQuery.getFirstName()
                                + "&lastName=" + createQuery.getLastName(), User.class);

        return "createUser";
    }

//    // /hello?name=kotlin
//    @GetMapping("/index")
//    public String mainWithParam(
//            @RequestParam(name = "name", required = false, defaultValue = "")
//                    String name, Model model) {
//
//        model.addAttribute("message", name);
//
//        return "welcome"; //view
//    }
}
