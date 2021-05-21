package shop.order.fallback;

import com.common.model.Const;
import com.common.model.JsonResult;
import org.springframework.stereotype.Component;
import shop.order.SimpleOrderAPI;
import com.order.entity.SellRequestDTO;
import com.order.entity.TOrder;

@Component
public class SimpleOrderAPIFallback implements SimpleOrderAPI {
    @Override
    public JsonResult testFeignProduct1(String param) {
        return new JsonResult(Const.Context.ERROR,"order.testFeignProduct1",Const.Context.ERRORMSG);
    }

    @Override
    public JsonResult getAll() {
        return new JsonResult(Const.Context.ERROR,"order.getAll",Const.Context.ERRORMSG);
    }

    @Override
    public JsonResult addOrder(TOrder tOrder) {
        return new JsonResult(Const.Context.ERROR,"order.addOrder",Const.Context.ERRORMSG);
    }

    @Override
    public JsonResult editOrder(TOrder tOrder) {
        return new JsonResult(Const.Context.ERROR,"order.editOrder",Const.Context.ERRORMSG);
    }

    @Override
    public JsonResult deleteOrder(Long toId) {
        return new JsonResult(Const.Context.ERROR,"order.deleteOrder",Const.Context.ERRORMSG);
    }

    @Override
    public JsonResult sellProduct(SellRequestDTO dto) {
        return new JsonResult(Const.Context.ERROR,"order.sellProduct",Const.Context.ERRORMSG);
    }
}
