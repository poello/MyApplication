package com.example.webApp.controller;

import com.example.webApp.CreateQuery;
import com.example.webApp.SearchingQuery;
import com.example.webApp.User;
import com.example.webApp.UserList;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
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
        model.addAttribute("searchByLogin", searchByLogin);

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
    public String goToDelete(Model model) {
        model.addAttribute("user", new User());
        return "delete";
    }

    @DeleteMapping("/delete")
//    @RequestMapping(value = "/delete", method = {RequestMethod.DELETE, RequestMethod.POST})
    public String deleteUser(@ModelAttribute("searchingQuery") SearchingQuery searchingQuery, Model model) {
        User user = restTemplate.getForObject("http://localhost:8080/user?identyfikator=" + searchingQuery.getById(), User.class);
        if (user == null) {
            model.addAttribute("user", new User());
        } else {
            model.addAttribute("user", user);
        }

        return "delete";
    }

    @GetMapping("/updatePassword")
    public String goToUpdatePassword() {
        return "updatePassword";
    }

    @PatchMapping("/updatePassword")
    public String updatePasswordById(@ModelAttribute("createQuery") CreateQuery createQuery, Model model) {
        User user = restTemplate.getForObject("http://localhost:8080/user?identyfikator=" + createQuery.getId(), User.class);
        if (user == null) {
            model.addAttribute("user", new User());
        } else {
            model.addAttribute("user", user);
        }

        return "updatePassword";
    }

    @GetMapping("/createUser")
    public String goToCreateUser() {
        return "createUser";
    }

    @PostMapping("/createUser")
    public String createUser(@ModelAttribute("createQuery") CreateQuery createQuery) {
        restTemplate.postForEntity("http://localhost:8080/createUser?userLogin=" + createQuery.getLogin()
                                + "&password=" + createQuery.getPassword() + "&firstName=" + createQuery.getFirstName()
                                + "&lastName=" + createQuery.getLastName(), null, String.class);

        return "createUser";
    }
}


//restTemplate.postForEntity("http://localhost:8080/createUser?userLogin=" + createQuery.getLogin()
//        + "&password=" + createQuery.getPassword() + "&firstName=" + createQuery.getFirstName()
//        + "&lastName=" + createQuery.getLastName(), request, User.class);

//    HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//
//                User user = new User();
//                user.setLogin(createQuery.getLogin());
//                user.setPassword(createQuery.getPassword());
//                user.setFirstName(createQuery.getFirstName());
//                user.setLastName(createQuery.getLastName());
//
//                HttpEntity<User> request = new HttpEntity<>(new User(), headers);
//        restTemplate.postForEntity("http://localhost:8080/createUser?userLogin=" + createQuery.getLogin()
//        + "&password=" + createQuery.getPassword() + "&firstName=" + createQuery.getFirstName()
//        + "&lastName=" + createQuery.getLastName(), request, User.class);
//        restTemplate.postForEntity("http://localhost:8080/createUser", request, User.class);