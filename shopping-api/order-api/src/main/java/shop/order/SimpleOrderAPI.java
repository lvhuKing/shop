package shop.order;

import com.common.model.JsonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import com.order.entity.SellRequestDTO;
import com.order.entity.TOrder;
import shop.order.fallback.SimpleOrderAPIFallback;

@FeignClient(value = "order-service", fallback = SimpleOrderAPIFallback.class, configuration = SimpleOrderAPIFallback.class)
public interface SimpleOrderAPI {

    @GetMapping("/testFeignProduct1")
    JsonResult testFeignProduct1(String param);
    
    @GetMapping("getAll")
    JsonResult getAll();
    
    @PostMapping("/addOrder")
    JsonResult addOrder(@RequestBody TOrder tOrder);
    
    @PutMapping("/editOrder")
    JsonResult editOrder(@RequestBody TOrder tOrder);
    
    @DeleteMapping("/deleteOrder/{toId}")
    JsonResult deleteOrder(@PathVariable(value = "toId") Long toId);
    
    @PostMapping("/sellProduct")
    JsonResult sellProduct(@RequestBody SellRequestDTO dto);
    
}
