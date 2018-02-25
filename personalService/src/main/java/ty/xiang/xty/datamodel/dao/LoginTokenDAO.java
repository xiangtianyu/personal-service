package ty.xiang.xty.datamodel.dao;

import org.springframework.data.repository.CrudRepository;
import ty.xiang.xty.datamodel.domain.LoginToken;

import javax.transaction.Transactional;

@Transactional
public interface LoginTokenDAO extends CrudRepository<LoginToken, Long> {
    LoginToken findByTokenAndValid (String token, int valid);

    LoginToken findByUidAndValid (int uid, int valid);
}
