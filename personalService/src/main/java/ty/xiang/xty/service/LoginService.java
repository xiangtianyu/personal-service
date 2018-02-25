package ty.xiang.xty.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ty.xiang.xty.datamodel.dao.BgUserDAO;
import ty.xiang.xty.datamodel.dao.LoginTokenDAO;
import ty.xiang.xty.datamodel.domain.BgUser;
import ty.xiang.xty.datamodel.domain.LoginToken;
import ty.xiang.xty.datamodel.dto.ResultDTO;

import java.util.Date;
import java.util.UUID;

@Service
public class LoginService {
    @Autowired
    private BgUserDAO       bgUserDAO;

    @Autowired
    private LoginTokenDAO   loginTokenDAO;

    public ResultDTO login (String username, String password) {
        BgUser bgUser = bgUserDAO.findByUsernameAndPasswordAndValid(username, password, 1);
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setResult(0);
        resultDTO.setMessage("登录失败");

        if (bgUser != null && bgUser.getUid() >= 0) {
            String uuid = UUID.randomUUID().toString();

            Date date = new Date();

            LoginToken loginToken = loginTokenDAO.findByUidAndValid(bgUser.getUid(), 1);
            if (loginToken != null) {
                loginToken.setToken(uuid);
                loginToken.setExpireTime(date.getTime() + 1000 * 60 * 60 *24);
                loginTokenDAO.save(loginToken);
            }
            else {
                LoginToken lt = new LoginToken();
                lt.setToken(uuid);
                lt.setUid(bgUser.getUid());
                lt.setValid(1);
                lt.setExpireTime(date.getTime() + 1000 * 60 * 60 *24);
                loginTokenDAO.save(lt);
            }

            resultDTO.setResult(1);
            resultDTO.setMessage(uuid);
            return resultDTO;

        }

        return resultDTO;
    }

    public ResultDTO checkLoginStatus (String token) {
        LoginToken loginToken = loginTokenDAO.findByTokenAndValid(token, 1);
        ResultDTO resultDTO = new ResultDTO();

        if (loginToken == null) {
            resultDTO.setResult(0);
            resultDTO.setMessage("Token不存在");
            return resultDTO;
        }
        else {
            Date date = new Date();
            if (date.getTime() > loginToken.getExpireTime()) {
                resultDTO.setResult(0);
                resultDTO.setMessage("Token已过期");
                return resultDTO;
            }
            else {
                resultDTO.setResult(1);
                return resultDTO;
            }
        }
    }
}
