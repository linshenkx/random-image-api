package cn.linshenkx.blog.controller;

import cn.hutool.core.util.RandomUtil;
import cn.linshenkx.blog.service.ImageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/image")
@CrossOrigin(originPatterns = "*", allowedHeaders = "*", maxAge = 3600)
public class ImageController {

    @Resource
    private ImageService imageService;

    @GetMapping("/random")
    public String getRandomImage(Integer th,HttpServletResponse response){

        try {
            String url = RandomUtil.randomEle(imageService.getJpgImageUrlList());
            String style;
//            if(th!=null){
//                style= "?x-oss-process=image/format,webp/resize,m_lfit,l_"+th;
//            }else {
//                style= "?x-oss-process=image/quality,q_80";
//            }
            if(th!=null){
                style= "?x-oss-process=image/quality,q_20";
            }else {
                style= "?x-oss-process=image/quality,q_80";
            }
            url=url+style;
            response.sendRedirect(url);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return "Hello";
    }

}
