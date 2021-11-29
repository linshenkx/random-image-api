package cn.linshenkx.blog.service.impl;

import cn.linshenkx.blog.enums.SourceType;
import cn.linshenkx.blog.enums.SourceTypeImpl;
import cn.linshenkx.blog.service.ImageService;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

@Slf4j
@Service
@SourceTypeImpl(SourceType.SIMPLE)
public class ImageServiceSimpleImpl implements ImageService {

    private final AtomicReference<List<String>> imageUrlList = new AtomicReference<>();

    @Override
    public List<String> getImageUrlList() {
        return imageUrlList.get();
    }

    @Override
    public String formatImageUrl(Map<String, Object> param, String url) {
        return url;
    }


    @Override
    public void syncImageUrlList() {
        imageUrlList.set(Lists.newArrayList("https://lian-gallery.oss-accelerate.aliyuncs.com/%E5%A3%81%E7%BA%B8%2Fllss%2F2015_05%2F29.jpg?x-oss-process=image/quality,q_20"));
    }
}
