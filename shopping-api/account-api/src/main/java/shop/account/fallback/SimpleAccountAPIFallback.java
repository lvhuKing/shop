package shop.account.fallback;

import com.common.model.Const;
import com.common.model.JsonResult;
import org.springframework.stereotype.Component;
import shop.account.SimpleAccountAPI;
import shop.account.entity.SimpleAccountDTORequest;
import shop.account.entity.TAccount;

@Component
public class SimpleAccountAPIFallback implements SimpleAccountAPI {

    @Override
    public JsonResult getAll() {
        return new JsonResult(Const.Context.ERROR,"account.getAll",Const.Context.FALLBACKMSG);
    }

    @Override
    public TAccount getOne(Long taId) {
        return null;
    }

    @Override
    public JsonResult addAccount(TAccount tAccount) {
        return new JsonResult(Const.Context.ERROR,"account.addAccount",Const.Context.FALLBACKMSG);
    }

    @Override
    public JsonResult editAccount(TAccount tAccount) {
        return new JsonResult(Const.Context.ERROR,"account.editAccount",Const.Context.FALLBACKMSG);
    }

    @Override
    public JsonResult deleteAccount(Long taId) {
        return new JsonResult(Const.Context.ERROR,"account.deleteAccount",Const.Context.FALLBACKMSG);
    }
}
