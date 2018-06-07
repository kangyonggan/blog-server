package com.kangyonggan.server.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author kangyonggan
 * @since 6/7/18
 */
@Controller
@RequestMapping("/")
public class IndexController extends BaseController {

    /**
     * 门户
     *
     * @return
     */
    @GetMapping
    @RequestMapping("/")
    public String index() {
        return "web/index.html";
    }

    /**
     * 后管
     *
     * @return
     */
    @GetMapping
    @RequestMapping("admin")
    public String admin() {
        return "admin/index.html";
    }

}
