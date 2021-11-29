package cn.linshenkx.blog.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.linshenkx.blog.enums.SourceType;
import cn.linshenkx.blog.enums.SourceTypeImpl;
import cn.linshenkx.blog.props.AliOssProp;
import cn.linshenkx.blog.service.ImageService;
import com.aliyun.oss.OSS;
import com.aliyun.oss.model.ListObjectsV2Request;
import com.aliyun.oss.model.ListObjectsV2Result;
import com.aliyun.oss.model.OSSObjectSummary;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Slf4j
@Service
@SourceTypeImpl(SourceType.ALI_OSS)
public class ImageServiceAliOssImpl implements ImageService {

    @Resource
    private OSS ossClient;

    @Resource
    private AliOssProp aliOssProp;

    private final AtomicReference<List<String>> imageUrlList = new AtomicReference<>();

    //阿里的仅能对jpg图片进行处理（如降低质量、调整大小等）
    private final Set<String> imageExtNameSet = Sets.newHashSet("jpg", "jpeg");


    @Override
    public List<String> getImageUrlList() {
        return imageUrlList.get();
    }

    @Override
    public String formatImageUrl(Map<String, Object> param, String url) {
        String style;
        if (param.containsKey("home")) {
            style = aliOssProp.getImageStyle().getMax();
        } else {
            style = aliOssProp.getImageStyle().getMin();
        }
        return url + style;
    }


    @Override
    public void syncImageUrlList() {
        String bucketName = aliOssProp.getBucket();
        final int maxKeys = 200;
        final String keyPrefix = aliOssProp.getPrefix();
        String nextContinuationToken = null;
        ListObjectsV2Result result = null;
        List<OSSObjectSummary> objectSummaryList = Lists.newArrayList();
        // 指定返回结果使用URL编码，则您需要对结果中的prefix、delemiter、startAfter、key和commonPrefix进行URL解码。
        do {
            ListObjectsV2Request listObjectsV2Request = new ListObjectsV2Request(bucketName).withMaxKeys(maxKeys);
            listObjectsV2Request.setPrefix(keyPrefix);
            listObjectsV2Request.setEncodingType("url");
            listObjectsV2Request.setContinuationToken(nextContinuationToken);
            result = ossClient.listObjectsV2(listObjectsV2Request);
            objectSummaryList.addAll(result.getObjectSummaries());
            nextContinuationToken = result.getNextContinuationToken();

        } while (result.isTruncated());
        List<OSSObjectSummary> targetOssObjectList = Lists.newArrayList();
        for (OSSObjectSummary objectSummary : objectSummaryList) {
            //筛除文件夹和无效文件
            if (objectSummary.getSize() <= 0) {
                continue;
            }
            //筛除大图片
            if (objectSummary.getSize() >= aliOssProp.getMaxFileSize()) {
                continue;
            }
            //筛选特定后缀
            String extName = FileUtil.extName(objectSummary.getKey());
            if (!imageExtNameSet.contains(extName)) {
                continue;
            }
            targetOssObjectList.add(objectSummary);
        }
        log.info("检索到文件/文件夹数量：{}，其中符合条件的图片数为：{}", objectSummaryList.size(), targetOssObjectList.size());
        imageUrlList.set(targetOssObjectList.stream()
                .map(ossObject -> aliOssProp.getHostname() + "/" + ossObject.getKey())
                .collect(Collectors.toList()));
    }
}
