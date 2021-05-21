package shop.basic.fallback;

import com.common.model.JsonResult;
import org.springframework.stereotype.Component;
import shop.basic.UserAPI;
import shop.basic.entity.LoginDTORequest;
import shop.basic.entity.NoticeCodeDTORequest;
import shop.basic.entity.RegisteDTORequest;

import javax.validation.Valid;

@Component
public class UserAPIFallback implements UserAPI {
    @Override
    public JsonResult noticeCode(@Valid NoticeCodeDTORequest dto) {
        throw new RuntimeException();
    }

    @Override
    public JsonResult registe(RegisteDTORequest dto) {
        throw new RuntimeException();
    }

    @Override
    public JsonResult login(LoginDTORequest dto) {
        throw new RuntimeException();
    }

    @Override
    public JsonResult logout() {
        throw new RuntimeException();
    }
}
