package cn.linshenkx.randomimageapi.scheduler;

import cn.linshenkx.randomimageapi.enums.SourceTypeImpl;
import cn.linshenkx.randomimageapi.props.GlobalProp;
import cn.linshenkx.randomimageapi.service.ImageService;
import cn.linshenkx.randomimageapi.util.SpringContextUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

@Slf4j
@Component
@Configurable
@EnableScheduling
@ConditionalOnClass(SpringContextUtil.class)
public class SchedulerTask {
    @Resource
    private GlobalProp globalProp;
    @Resource
    private SpringContextUtil springContextUtil;

    @PostConstruct
    @Scheduled(cron = "${my.jobs.schedule}")
    public void checkJobStatus() {
        ImageService imageService = null;
        try {
            imageService = SpringContextUtil.getBeansWithAnnotaionValue(ImageService.class, SourceTypeImpl.class, globalProp.getSourceType());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        try {
            imageService.syncImageUrlList();
            log.info("同步成功");
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

}
