package shop.product;

import com.common.model.JsonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import com.product.entity.TProduct;
import shop.product.fallback.SimpleProductAPIFallback;

/**
* fallback：return备用类
* configuration：feign客户端的自定义配置类，可以包含替代@Bean等
* RequestParam：被feign调用，纠正参数映射，否则报错405
* @Description 作用：生产者
* @Author ccl
* @CreateDate 2020/9/9 13:38
**/
@FeignClient(value = "product-service", fallback = SimpleProductAPIFallback.class, configuration = SimpleProductAPIFallback.class)
public interface SimpleProductAPI {
    
    /**被feign调用，RequestParam：纠正参数映射，否则报错405*/
    @GetMapping("/testProduct1")
    JsonResult testProduct1(@RequestParam("param") String param);
    
    /**查看所有商品*/
    @GetMapping("/getAll")
    JsonResult getAll();
    
    /**录入商品信息*/
    @PostMapping("/addProduct")
    JsonResult addProduct(@RequestBody TProduct tProduct);
    
    /**变更商品信息*/
    @PutMapping("/editProduct")
    JsonResult editProduct(@RequestBody TProduct tProduct);
    
    /**删除商品信息*/
    @DeleteMapping("/deleteProduct/{tpId}")
    JsonResult deleteProduct(@PathVariable(value = "tpId") Long tpId);
    
    @GetMapping("/getOne/{tpId}")
    TProduct getOne(@PathVariable(value = "tpId")Long tpId);
    
    
}
