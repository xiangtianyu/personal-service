package ty.xiang.xty.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ty.xiang.xty.datamodel.dto.ResultDTO;
import ty.xiang.xty.datamodel.parameter.LoginParameter;
import ty.xiang.xty.service.LoginService;

import java.util.List;

@CrossOrigin
@RestController
public class LoginController {
    @Autowired
    private LoginService    loginService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public @ResponseBody
    ResultDTO login(@RequestBody LoginParameter loginParameter){
        try {
            return loginService.login(loginParameter.getUsername(), loginParameter.getPassword());
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @ResponseBody
    @RequestMapping(value = "/checkLoginStatus", method = RequestMethod.GET)
    public ResultDTO checkLoginStatus (String token) {
        try {
            return loginService.checkLoginStatus(token);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
