package com.kangyonggan.server.controller.mobile;

import com.kangyonggan.server.dto.Response;
import com.kangyonggan.server.model.Category;
import com.kangyonggan.server.service.CategoryService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author kangyonggan
 * @date 8/24/17
 */
@RestController
@RequestMapping("mobile/category")
@Log4j2
public class MobileCategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * 查找所有分类
     *
     * @return
     */
    @RequestMapping(value = "all", method = RequestMethod.POST)
    public Response categories() {
        Response response = Response.getSuccessResponse();

        try {
            List<Category> categories = categoryService.findAllCategoryWithNovelCount();
            response.put("categories", categories);
        } catch (Exception e) {
            log.warn("查询小说全部分类异常", e);
            response.failure("查询小说全部分类异常");
        }

        return response;
    }
}
