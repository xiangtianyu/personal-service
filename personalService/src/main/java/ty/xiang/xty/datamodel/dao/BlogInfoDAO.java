package ty.xiang.xty.datamodel.dao;

import org.springframework.data.repository.CrudRepository;
import ty.xiang.xty.datamodel.domain.BlogInfo;

import java.util.List;

public interface BlogInfoDAO extends CrudRepository<BlogInfo, Long> {
    BlogInfo findByBidAndValid (int bid, int valid);

    List<BlogInfo> findAllByValid (int valid);
}
