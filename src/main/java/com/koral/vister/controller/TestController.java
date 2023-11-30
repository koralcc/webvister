package com.koral.vister.controller;

import com.koral.vister.tenant.TenantContext;
import com.koral.vister.test.ReferenceCountingGC;
import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.core.HyperLogLogOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@Slf4j
public class TestController {
    @Resource(name = "stringRedisTemplate")
    private StringRedisTemplate redisTemplate;

    private final ThreadLocal<String> threadLock = new ThreadLocal<>();

    @Resource
    private Environment environment;

    @Resource
    private Redisson redisson;

    private boolean lazyInitialization;

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

    @PostMapping("/alipay/{orderSN}")
    public ResponseEntity<String> alipay(@PathVariable("orderSN") String orderSN) {
        // validate
        RLock lock = redisson.getLock("alipaylock:"+orderSN);
        try {
            boolean b = lock.tryLock();
            if (!b) {
                log.warn("分布式锁占用");
                return ResponseEntity.status(500).body("正在执行，请勿频繁操作");
            }
            log.info("执行alipay支付逻辑");
            Thread.sleep(100000);
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

    @PostMapping("/gc")
    @ResponseBody
    public ResponseEntity<String> gc_loopDepend() throws InterruptedException {
        // validate
        ReferenceCountingGC.testGC();
        return ResponseEntity.ok(null);
    }


    @GetMapping("/change-username")
    @ResponseBody
    public String setCookie(HttpServletResponse response, HttpServletRequest request) {
        // 创建一个 cookie
        Cookie cookie = new Cookie("username", "Jovan");
        //设置 cookie过期时间
        cookie.setMaxAge(10); // expires in 7 days
        //添加到 response 中
        response.addCookie(cookie);

        return "Username is changed!";
    }

    @GetMapping("/binder")
    @ResponseBody
    public String getBinder() {
        TestController testController = Binder.get(environment).bind("spring.main", Bindable.of(TestController.class)).get();
        Map<String, String> stringStringMap = Binder.get(environment).bind("spring.main", Bindable.mapOf(String.class, String.class)).get();
        return "";
    }

    public void setLazyInitialization(boolean lazyInitialization) {
        this.lazyInitialization = lazyInitialization;
    }

    public boolean isLazyInitialization() {
        return lazyInitialization;
    }
}
