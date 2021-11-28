package cn.linshenkx.blog.scheduler;

import cn.linshenkx.blog.service.ImageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Slf4j
@Component
@Configurable
@EnableScheduling
@EnableAsync
public class SchedulerTask {
    @Resource
    private ImageService imageService;


    @Async("taskExecutor")
    @Scheduled(fixedDelay = 60 * 1000)
    public void checkJobStatus() {
        try {
            imageService.syncImageUrlList();
            log.info("同步成功");
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

}
