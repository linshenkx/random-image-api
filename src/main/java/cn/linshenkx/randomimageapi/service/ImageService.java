package cn.linshenkx.randomimageapi.service;

import java.util.List;
import java.util.Map;

public interface ImageService {

    List<String> getImageUrlList();

    String formatImageUrl(Map<String, Object> param, String url);

    void syncImageUrlList();
}
