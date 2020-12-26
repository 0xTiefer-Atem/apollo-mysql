package org.springboot.apollo.demo.component;

import org.springboot.apollo.demo.utils.DateUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author： WangQian
 * @date： 2020/8/27 下午 4:57
 */
@Component
public class ScheduledTask {
//    @Scheduled(cron = "0/10 * * * * ?") //每10秒执行一次
//    public void scheduledTaskByCorn() {
//        System.out.println("定时任务开始 ByCorn：" + DateUtils.dateFormat());
//        scheduledTask();
//        System.out.println("定时任务结束 ByCorn：" + DateUtils.dateFormat());
//    }
//
//    @Scheduled(fixedRate = 10000) //每10秒执行一次
//    public void scheduledTaskByFixedRate() {
//        System.out.println("定时任务开始 ByFixedRate：" + DateUtils.dateFormat());
//        scheduledTask();
//        System.out.println("定时任务结束 ByFixedRate：" + DateUtils.dateFormat());
//    }
//
//    @Scheduled(fixedDelay = 10000) //每10秒执行一次
//    public void scheduledTaskByFixedDelay() {
//        System.out.println("定时任务开始 ByFixedDelay：" + DateUtils.dateFormat());
//        scheduledTask();
//        System.out.println("定时任务结束 ByFixedDelay：" + DateUtils.dateFormat());
//    }
//
//    private void scheduledTask() {
//        try {
//            Thread.sleep(5000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//    }
}
