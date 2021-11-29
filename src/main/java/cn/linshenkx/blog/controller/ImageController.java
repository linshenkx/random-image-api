package cn.linshenkx.blog.controller;

import cn.hutool.core.util.RandomUtil;
import cn.linshenkx.blog.enums.SourceTypeImpl;
import cn.linshenkx.blog.props.GlobalProp;
import cn.linshenkx.blog.service.ImageService;
import cn.linshenkx.blog.util.SpringContextUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/image")
@CrossOrigin(originPatterns = "*", allowedHeaders = "*", maxAge = 3600)
public class ImageController {

    @Resource
    private GlobalProp globalProp;
    @Resource
    private SpringContextUtil springContextUtil;

    @GetMapping("/random")
    public String getRandomImage(@RequestParam Map<String, Object> paramsMap, HttpServletResponse response) {
        ImageService imageService = null;
        try {
            imageService = SpringContextUtil.getBeansWithAnnotaionValue(ImageService.class, SourceTypeImpl.class, globalProp.getSourceType());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        try {
            String url = RandomUtil.randomEle(imageService.getImageUrlList());
            response.sendRedirect(imageService.formatImageUrl(paramsMap, url));
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        SourceTypeImpl.class.getClass();
        return "Hello";
    }

}
