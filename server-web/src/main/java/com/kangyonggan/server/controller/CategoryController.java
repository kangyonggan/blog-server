package com.kangyonggan.server.controller;

import com.kangyonggan.server.dto.Response;
import com.kangyonggan.server.model.Category;
import com.kangyonggan.server.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author kangyonggan
 * @since 5/15/18
 */
@RestController
@RequestMapping("category")
public class CategoryController extends BaseController {

    @Autowired
    private CategoryService categoryService;

    /**
     * 根据类型查找栏目
     *
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public Response type() {
        Response response = Response.getSuccessResponse();
        List<Category> categories = categoryService.findCategoriesByType(getStringParam("type"));

        response.put("list", categories);
        return response;
    }

}
