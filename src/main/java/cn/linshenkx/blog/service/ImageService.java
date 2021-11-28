package cn.linshenkx.blog.service;

import java.io.UnsupportedEncodingException;
import java.util.List;

public interface ImageService {

    List<String> getImageUrlList() throws UnsupportedEncodingException;
    List<String> getJpgImageUrlList() throws UnsupportedEncodingException;

    void syncImageUrlList() throws UnsupportedEncodingException;
}
