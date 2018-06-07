package com.kangyonggan.server.controller.web;

import com.kangyonggan.server.controller.BaseController;
import com.kangyonggan.server.dto.Response;
import com.kangyonggan.server.model.Category;
import com.kangyonggan.server.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author kangyonggan
 * @since 6/7/18
 */
@RestController
@RequestMapping("web/category")
public class CategoryController extends BaseController {

    @Autowired
    private CategoryService categoryService;

    /**
     * 栏目列表
     *
     * @param type
     * @return
     */
    @GetMapping
    public Response list(@RequestParam("type") String type) {
        Response response = Response.getSuccessResponse();
        List<Category> categories = categoryService.findCategoriesByType(type);

        response.put("list", categories);
        return response;
    }

}
