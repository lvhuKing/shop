package shop.product.fallback;

import com.common.model.Const;
import com.common.model.JsonResult;
import org.springframework.stereotype.Component;
import shop.product.SimpleProductAPI;
import com.product.entity.TProduct;

@Component
public class SimpleProductAPIFallback implements SimpleProductAPI {
    @Override
    public JsonResult testProduct1(String param) {
        return new JsonResult(Const.Context.ERROR,"product.testProduct1",Const.Context.ERRORMSG);
    }

    @Override
    public JsonResult getAll() {
        return new JsonResult(Const.Context.ERROR,"product.getAll",Const.Context.ERRORMSG);
    }

    @Override
    public JsonResult addProduct(TProduct tProduct) {
        return new JsonResult(Const.Context.ERROR,"product.addProduct",Const.Context.ERRORMSG);
    }

    @Override
    public JsonResult editProduct(TProduct tProduct) {
        return new JsonResult(Const.Context.ERROR,"product.editProduct",Const.Context.ERRORMSG);
    }

    @Override
    public JsonResult deleteProduct(Long tpId) {
        return new JsonResult(Const.Context.ERROR,"product.deleteProduct",Const.Context.ERRORMSG);
    }

    @Override
    public TProduct getOne(Long tpId) {
        return null;
    }
}
