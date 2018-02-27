package ty.xiang.xty.datamodel.dao;

import org.springframework.data.repository.CrudRepository;
import ty.xiang.xty.datamodel.domain.BlogContent;

public interface BlogContentDAO extends CrudRepository<BlogContent, Long> {
    BlogContent findByBid (int bid);
}
