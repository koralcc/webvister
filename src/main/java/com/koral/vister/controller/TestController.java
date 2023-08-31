package com.koral.vister.controller;

import com.koral.vister.tenant.TenantContext;
import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.core.HyperLogLogOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

@Controller
@Slf4j
public class TestController {
    @Resource(name = "stringRedisTemplate")
    private StringRedisTemplate redisTemplate;

    private final ThreadLocal<String> threadLock = new ThreadLocal<>();

    @Resource
    private Environment environment;

    @Resource
    private Redisson redisson;

    @GetMapping("/index")
    @ResponseBody
    public Long index() {
        HyperLogLogOperations<String, String> hyperLogLogOperations = redisTemplate.opsForHyperLogLog();
        return hyperLogLogOperations.size(environment.getProperty("spring.application.name"));
    }


    @GetMapping("/modelandview")
    public ModelAndView modelandview() {
        return new ModelAndView("hello");

    }

    @PostMapping("/alipay")
    @ResponseBody
    public ResponseEntity<String> alipay() {
        // validate
        RLock lock = redisson.getLock("alipaylock:orderid");
        try {
            boolean b = lock.tryLock();
            if (!b) {
                log.warn("分布式锁占用");
                return ResponseEntity.status(500).body("正在执行，请勿频繁操作");
            }
            log.info("执行alipay支付逻辑");
            Thread.sleep(1000);
            return ResponseEntity.ok("hello");
        } catch (Exception e) {
            log.error("分布式锁获取异常");
            return ResponseEntity.status(500).body("分布式锁获取失败");
        } finally {
            if (lock.isHeldByCurrentThread()) {
                log.info("执行完释放锁{}", lock.getName());
                lock.unlock();
            }
        }
    }

    @PostMapping("/threadLocal")
    @ResponseBody
    public ResponseEntity<String> threadLocal() {
        // validate
        threadLock.set("hallo");
        return ResponseEntity.ok("hahhhaha");
    }

    @PostMapping("/tenant")
    @ResponseBody
    public ResponseEntity<String> tenant() throws InterruptedException {
        // validate
        log.info("租户请求...");
        Thread t = new Thread(() -> log.info("thread 执行。。"), "tenantThread");
        t.start();
        t.join();
        log.info("租户请求完毕。");
        return ResponseEntity.ok(TenantContext.getTenantCode());
    }
}
