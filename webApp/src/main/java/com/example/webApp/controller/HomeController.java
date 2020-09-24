package com.example.webApp.controller;

import com.example.webApp.CreateQuery;
import com.example.webApp.SearchingQuery;
import com.example.webApp.User;
import com.example.webApp.UserList;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;

import javax.websocket.server.PathParam;

@Controller
public class HomeController {

    // inject via application.properties

    RestTemplate restTemplate = new RestTemplate(); // used for communicate between services

    @GetMapping("/welcome")
    public String homePage() {
        return "welcome";
    }

    @GetMapping("/search")
    public String goToSearch(Model model) {
        UserList users = restTemplate.getForObject("http://localhost:8080/users?userLogin=", UserList.class);
        model.addAttribute("users", users.getUsers()); //adding to model so that we can use it in .html

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
        model.addAttribute("user", new User());
        return "searchById"; //view
    }

    @PostMapping("/searchById")
    public String retrieveUser(@ModelAttribute("searchingQuery") SearchingQuery searchingQuery, Model model) {
        User user = restTemplate.getForObject("http://localhost:8080/user?identyfikator=" + searchingQuery.getById(), User.class);
        if (user == null) {
            model.addAttribute("user", new User());
        } else {
            model.addAttribute("user", user);
        }

        return "searchById"; //view
    }

    @GetMapping("/delete")
    public String deleteUser(@PathParam("id") Long id) {
        restTemplate.delete("http://localhost:8080/user?identyfikator=" + id);

        return "redirect:/search";
    }

    @GetMapping("/updatePassword")
    public String updatePasswordById(@PathParam("id") Long id, Model model) {
        User user = restTemplate.getForObject("http://localhost:8080/user?identyfikator=" + id, User.class);
        if (user == null) {
            return "redirect:/search";
        } else {
            model.addAttribute("user", user);
        }

        return "updatePassword";
    }

    @PostMapping("/updatePassword")
    public String updatePasswordById(@ModelAttribute("user") User user) {
        RestTemplate template = new RestTemplate(new HttpComponentsClientHttpRequestFactory());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        JSONObject personJsonObject = new JSONObject();
        personJsonObject.put("id", user.getId());
        personJsonObject.put("password", user.getPassword());
        personJsonObject.put("firstName", user.getFirstName());
        personJsonObject.put("lastName", user.getLastName());

        HttpEntity<String> request =
                new HttpEntity<>(personJsonObject.toString(), headers);

        template.patchForObject("http://localhost:8080/user", request, String.class);

        return "redirect:/search";
    }

    @GetMapping("/createUser")
    public String goToCreateUser() {
        return "createUser";
    }

    @PostMapping("/createUser")
    public String createUser(@ModelAttribute("createQuery") CreateQuery createQuery) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        JSONObject personJsonObject = new JSONObject();
        personJsonObject.put("login", createQuery.getLogin());
        personJsonObject.put("password", createQuery.getPassword());
        personJsonObject.put("firstName", createQuery.getFirstName());
        personJsonObject.put("lastName", createQuery.getLastName());

        HttpEntity<String> request =
                new HttpEntity<>(personJsonObject.toString(), headers);

        restTemplate.postForObject("http://localhost:8080/createUser", request, String.class);

        return "createUser";
    }

}

//    @PostMapping("/updatePassword")
//    public String updatePasswordById(@ModelAttribute("user") User user, Model model) {
//        RestTemplate template = new RestTemplate(new HttpComponentsClientHttpRequestFactory());
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.TEXT_PLAIN);
//
//        HttpEntity<String> request =
//                new HttpEntity<>(user.getPassword(), headers);
//
//        template.patchForObject("http://localhost:8080/user?identyfikator=" + user.getId(), request, User.class);
//
//        return "redirect:/search";
//    }
