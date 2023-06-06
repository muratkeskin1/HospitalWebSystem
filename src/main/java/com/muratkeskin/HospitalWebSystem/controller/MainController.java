package com.muratkeskin.HospitalWebSystem.controller;

import com.google.firebase.messaging.FirebaseMessagingException;
import com.muratkeskin.HospitalWebSystem.model.Apply;
import com.muratkeskin.HospitalWebSystem.model.Hospital;
import com.muratkeskin.HospitalWebSystem.service.ApplyService.ApplyServiceImpl;
import com.muratkeskin.HospitalWebSystem.service.HospitalService.HospitalServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.concurrent.ExecutionException;

@Controller
@RequestMapping("/")
public class MainController {
    @Autowired
    ApplyServiceImpl applyService;
    @Autowired
    HospitalServiceImpl hospitalService;
    @Autowired
    AuthenticationManager authenticationManager;
    private Hospital hospital1=new Hospital();
    @GetMapping
    public String main(){
        return "result2";
    }
    @GetMapping(value = "/save")
    public String form(Model model){
        Hospital hospital = new Hospital();
        model.addAttribute("hospital",hospital);
        return "index";
    }
    @GetMapping( "/login")
    public  String login(Model model){
        Hospital hospital = new Hospital();
        Apply apply= new Apply();
        model.addAttribute("hospital",hospital);
        model.addAttribute("apply",apply);
        return "login";
    }

    @RequestMapping( value = "/loginpost",method = RequestMethod.POST)
    public  String loginpost(HttpServletRequest servletRequest,HttpServletResponse response,Model model
            ,@ModelAttribute("hospital") Hospital hospital) throws ExecutionException, InterruptedException {

        if (hospitalService.login(hospital.getHospitalEmail(), hospital.getHospitalPassword())){

            this.hospital1=hospitalService.getHospital(hospital.getHospitalEmail());
            UsernamePasswordAuthenticationToken authReq
                    = new UsernamePasswordAuthenticationToken(hospital.getHospitalEmail(), hospital.getHospitalPassword());
            Authentication auth = authenticationManager.authenticate(authReq);
            SecurityContext sc = SecurityContextHolder.getContext();
            sc.setAuthentication(auth);
            HttpSession session = servletRequest.getSession(true);
            session.setAttribute("SPRING_SECURITY_CONTEXT", sc);
            response.setContentType("text/plain");
            System.out.println(hospital.getHospitalId()+"login içerisinde");
            Cookie cookie=new Cookie("email",hospital1.getHospitalEmail());
            Cookie cookie2=new Cookie("id",hospital1.getHospitalId());
            cookie.setPath("/applies");
            cookie2.setPath("/applies");
            response.addCookie(cookie);
            response.addCookie(cookie2);
            return "redirect:/applies";}
        else
            return "redirect:/login-error";
    }
    @RequestMapping(value = "/login-error",method = RequestMethod.GET)
    public RedirectView  loginError(Model model, RedirectAttributes redirectAttributes){
        redirectAttributes.addFlashAttribute("loginError",true);
        return new RedirectView("/login",true);
    }

    @RequestMapping(value = "/logout",method = RequestMethod.GET)
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            auth.setAuthenticated(false);
            new SecurityContextLogoutHandler().logout(request, null, null);
        }
        return "redirect:/login";
    }
    @PostMapping( "/save")
    public String mainpage(Model model, @ModelAttribute("hospital") Hospital hospital){
        System.out.println(hospital);
        //model.addAttribute("result",hospital);
        return "result";
    }
    @RequestMapping(value = "/applies",method = RequestMethod.GET)
    public String applies(Model model,@ModelAttribute("hospital") Hospital hospital) throws ExecutionException, InterruptedException {
            model.addAttribute("applies",applyService.getAll(hospital1.getHospitalEmail()));
            if(model.getAttribute("apply")==null)  {
                Apply apply= new Apply();
                model.addAttribute("apply",apply);
            }
            return "applies";
    }
    @RequestMapping(value = "/saveApply",method = RequestMethod.POST)
    public RedirectView saveApply(Model model,@ModelAttribute Apply apply) throws Exception {
        apply.setHospitalEmail(hospital1.getHospitalEmail());
        apply.setHospitalName(hospital1.getHospitalName());
        apply.setLocation(hospital1.getHospitalAddress());
        apply.setGeoPoint(hospital1.getLocation());
        applyService.save(apply);
        return  new RedirectView("/applies",true);
    }
    @RequestMapping(value = "/delete/{id}" , method = RequestMethod.GET)
    public String delete(@PathVariable String id) throws ExecutionException, InterruptedException {
        applyService.delete(id);
        Thread.sleep(100);
        return "redirect:/applies";
    }
}
