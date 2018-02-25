package ty.xiang.xty.datamodel.dao;

import org.springframework.data.repository.CrudRepository;
import ty.xiang.xty.datamodel.domain.BgUser;

import javax.transaction.Transactional;

@Transactional
public interface BgUserDAO extends CrudRepository<BgUser, Long> {
    BgUser findByUsernameAndPasswordAndValid (String username, String password, int valid);
}
