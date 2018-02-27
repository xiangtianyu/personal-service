package ty.xiang.xty.datamodel.dao;

import org.springframework.data.repository.CrudRepository;
import ty.xiang.xty.datamodel.domain.Xtag;
import ty.xiang.xty.datamodel.domain.XtagInBlog;

import java.util.List;

public interface XtagInBlogDAO extends CrudRepository<XtagInBlog, Long> {
    List<XtagInBlog> findByBidAndValid (int bid, int valid);

    XtagInBlog findByTidAndValid (int tid, int valid);
}
