package com.example.webApp.controller;

import com.example.webApp.SearchingQuery;
import com.example.webApp.UserList;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Controller
public class HomeController {

    // inject via application.properties
    private String searchByLogin = "abcd";

    RestTemplate restTemplate = new RestTemplate();

    private List<String> tasks = Arrays.asList("a", "b", "c", "d", "e", "f", "g");

    @GetMapping("/search")
    public String main(Model model) {
        model.addAttribute("searchingQuery", new SearchingQuery());
        UserList users = restTemplate.getForObject("http://localhost:8080/users?userLogin=a", UserList.class);
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

    // /hello?name=kotlin
    @GetMapping("/hello")
    public String mainWithParam(
            @RequestParam(name = "name", required = false, defaultValue = "")
                    String name, Model model) {

        model.addAttribute("message", name);

        return "welcome"; //view
    }

}
