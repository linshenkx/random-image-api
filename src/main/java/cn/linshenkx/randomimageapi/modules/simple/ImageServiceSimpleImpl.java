package cn.linshenkx.randomimageapi.modules.simple;

import cn.linshenkx.randomimageapi.enums.SourceType;
import cn.linshenkx.randomimageapi.enums.SourceTypeImpl;
import cn.linshenkx.randomimageapi.service.ImageService;
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
        imageUrlList.set(Lists.newArrayList("https://lian-gallery.oss-cn-guangzhou.aliyuncs.com/halo/20211119231827_1637418874326.png"));
    }
}
