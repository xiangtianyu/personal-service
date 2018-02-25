package ty.xiang.xty.datamodel.dao;

import org.springframework.data.repository.CrudRepository;
import ty.xiang.xty.datamodel.domain.Xtag;

import javax.transaction.Transactional;
import java.util.List;


@Transactional
public interface XtagDAO extends CrudRepository<Xtag, Long> {
    Xtag findByTnameAndValid (String tname, int valid);

    Xtag findByTidAndValid (int tid, int valid);

    List<Xtag> findAllByValid (int valid);
}
