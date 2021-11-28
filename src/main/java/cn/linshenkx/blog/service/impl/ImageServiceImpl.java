package cn.linshenkx.blog.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.linshenkx.blog.props.AliOssProp;
import cn.linshenkx.blog.service.ImageService;
import com.aliyun.oss.OSS;
import com.aliyun.oss.model.ListObjectsRequest;
import com.aliyun.oss.model.OSSObjectSummary;
import com.aliyun.oss.model.ObjectListing;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ImageServiceImpl implements ImageService {

    @Resource
    private OSS ossClient;

    @Resource
    private AliOssProp aliOssProp;

    private AtomicReference<List<String>> imageUrlList= new AtomicReference<>();
    private AtomicReference<List<String>> jpgImageUrlList= new AtomicReference<>();

    private final Set<String> imageExtNameSet= Sets.newHashSet("jpg","jpeg","png");
    private final Set<String> jpgExtNameSet= Sets.newHashSet("jpg","jpeg");


    @Override
    public List<String> getImageUrlList() throws UnsupportedEncodingException {
        return imageUrlList.get();
    }

    @Override
    public List<String> getJpgImageUrlList() throws UnsupportedEncodingException {
        return jpgImageUrlList.get();
    }

    @Override
    public void syncImageUrlList() throws UnsupportedEncodingException {
        String bucketName=aliOssProp.getBucket();

        final int maxKeys = 200;
// 指定前缀，例如exampledir/object。
        final String keyPrefix = aliOssProp.getPrefix();
// 设置marker，例如objecttest.txt。
        String nextMarker = "objecttest.txt";
        ObjectListing objectListing;

        do {
            ListObjectsRequest listObjectsRequest = new ListObjectsRequest(bucketName);
            listObjectsRequest.setPrefix(keyPrefix);
            listObjectsRequest.setMaxKeys(maxKeys);
            listObjectsRequest.setMarker(nextMarker);

            // 指定文件名称编码。
            listObjectsRequest.setEncodingType("url");
            objectListing = ossClient.listObjects(listObjectsRequest);
//            // 文件解码。
//            for (OSSObjectSummary objectSummary: objectListing.getObjectSummaries()) {
//                System.out.println("Key:" + URLDecoder.decode(objectSummary.getKey(), "UTF-8"));
//            }
//            // commonPrefixes解码。
//            for (String commonPrefixes: objectListing.getCommonPrefixes()) {
//                System.out.println("CommonPrefixes:" + URLDecoder.decode(commonPrefixes, "UTF-8"));
//            }

            // nextMarker解码。
            if (objectListing.getNextMarker() != null) {
                nextMarker = URLDecoder.decode(objectListing.getNextMarker(), "UTF-8");
            }
        } while (objectListing.isTruncated());
        // objectListing.getObjectSummaries获取所有文件的描述信息。
        List<OSSObjectSummary> ossObjectList= Lists.newArrayList();
        for (OSSObjectSummary objectSummary : objectListing.getObjectSummaries()) {
            if(objectSummary.getSize()<=0){
                continue;
            }
            if(objectSummary.getSize()>=1024*1024*6){
                continue;
            }
            String extName = FileUtil.extName(objectSummary.getKey());
            if(!imageExtNameSet.contains(extName)){
                continue;
            }
            ossObjectList.add(objectSummary);
        }
        log.info("检索到文件/文件夹数量：{}，其中符合条件的图片数为：{}",objectListing.getObjectSummaries().size(),ossObjectList.size());
        imageUrlList.set(ossObjectList.stream().map(ossObject-> aliOssProp.getHostname()+"/"+ossObject.getKey()).collect(Collectors.toList()));
        jpgImageUrlList.set(getImageUrlList().stream().filter(url->jpgExtNameSet.contains(FileUtil.extName(url))).collect(Collectors.toList()));
    }
}
