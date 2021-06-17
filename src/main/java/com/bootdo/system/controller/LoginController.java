package com.bootdo.system.controller;

import com.bootdo.common.annotation.Log;
import com.bootdo.common.config.BootdoConfig;
import com.bootdo.common.controller.BaseController;
import com.bootdo.common.domain.FileDO;
import com.bootdo.common.domain.Tree;
import com.bootdo.common.service.FileService;
import com.bootdo.common.utils.MD5Utils;
import com.bootdo.common.utils.R;
import com.bootdo.common.utils.ShiroUtils;
import com.bootdo.common.utils.ValidateCodeUtils;
import com.bootdo.system.domain.MenuDO;
import com.bootdo.system.service.MenuService;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import io.swagger.models.auth.In;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class LoginController extends BaseController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 登录验证码SessionKey
     */
    public static final String LOGIN_VALIDATE_CODE = "login_validate_code";

    @Autowired
    MenuService menuService;
    @Autowired
    FileService fileService;
    @Autowired
    BootdoConfig bootdoConfig;
    @Resource
    private DefaultKaptcha captchaProducer;

    @GetMapping({"/", ""})
    String welcome(Model model) {

        return "redirect:/index";
    }

    @GetMapping({"/validateCode", ""})
    String validateCode(Model model) {

        return "validateCode";
    }

    @Log("请求访问主页")
    @GetMapping({"/index"})
    String index(Model model) {
        List<Tree<MenuDO>> menus = menuService.listMenuTree(getUserId());
        model.addAttribute("menus", menus);
        model.addAttribute("name", getUser().getName());
        FileDO fileDO = fileService.get(getUser().getPicId());
        if (fileDO != null && fileDO.getUrl() != null) {
            if (fileService.isExist(fileDO.getUrl())) {
                model.addAttribute("picUrl", fileDO.getUrl());
            } else {
                model.addAttribute("picUrl", "/img/photo_s.jpg");
            }
        } else {
            model.addAttribute("picUrl", "/img/photo_s.jpg");
        }
        model.addAttribute("username", getUser().getUsername());
        return "index_v1";
    }

    @GetMapping("/login")
    String login(Model model) {
        model.addAttribute("username", bootdoConfig.getUsername());
        model.addAttribute("password", bootdoConfig.getPassword());
        return "login";
    }

//    @Log("登录")
//    @PostMapping("/login")
//    @ResponseBody
//    R ajaxLogin(String username, String password) {
//
//        password = MD5Utils.encrypt(username, password);
//        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
//        Subject subject = SecurityUtils.getSubject();
//        try {
//            subject.login(token);
//            return R.ok();
//        } catch (AuthenticationException e) {
//            return R.error("用户或密码错误");
//        }
//    }

    @Log("登录")
    @PostMapping("/login")
    @ResponseBody
    R ajaxLogin(String username, String password, String validateCode,HttpServletRequest request) {
        String loginValidateCode = request.getSession().getAttribute(LOGIN_VALIDATE_CODE).toString();
        if(loginValidateCode == null){
            return R.error("验证码过期");
        }else if(!loginValidateCode.equals(validateCode)){
            return R.error("验证码不正确");//验证码不正确
        }else if(loginValidateCode.equals(validateCode)){
            password = MD5Utils.encrypt(username, password);
            UsernamePasswordToken token = new UsernamePasswordToken(username, password);
            Subject subject = SecurityUtils.getSubject();
            try {
                subject.login(token);
                return R.ok();
            } catch (AuthenticationException e) {
                return R.error("用户或密码错误");
            }
        }
        return R.error("系统未知异常，请联系管理员");

    }

    @GetMapping("/logout")
    String logout() {
        ShiroUtils.logout();
        return "redirect:/login";
    }

    @GetMapping("/main")
    String main() {
        return "main";
    }

    @Log("前端获取当前用户信息")
    @PostMapping("/EmyGetUserInfo")
    @ResponseBody
    R EmergencyGetUserInfo() {
        Map<String,Object> map =new HashMap<String, Object>();
        try {
            map.put("name", getUser().getName());
            map.put("username", getUser().getUsername());
            return R.ok(map);
        } catch (AuthenticationException e) {
            return R.error("获取用户信息失败");
        }
    }

    /**
     * 登录验证码图片
     */
    @RequestMapping(value = {"/loginValidateCode"})
    public void loginValidateCode(HttpServletRequest request, HttpServletResponse response) throws Exception{
        ValidateCodeUtils.validateCode(request,response,captchaProducer,LOGIN_VALIDATE_CODE);
    }
}
