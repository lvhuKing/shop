package shop.account;

import com.common.model.JsonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import shop.account.entity.TAccount;
import shop.account.fallback.SimpleAccountAPIFallback;

@FeignClient(value = "account-service", fallback = SimpleAccountAPIFallback.class, configuration = SimpleAccountAPIFallback.class)
public interface SimpleAccountAPI {
    
    @GetMapping("/getAll")
    JsonResult getAll();
    
    @GetMapping("/getOne/{taId}")
    TAccount getOne(@PathVariable(value = "taId")Long taId);
    
    @PostMapping("/addAccount")
    // @SentinelResource(value = "account-service", fallback = "addMoney")
    JsonResult addAccount(@RequestBody TAccount tAccount);
    
    @PutMapping("/editAccount")
    JsonResult editAccount(@RequestBody TAccount tAccount);
    
    @DeleteMapping("/deleteAccount/{taId}")
    JsonResult deleteAccount(@PathVariable(value = "taId") Long taId); 
}
