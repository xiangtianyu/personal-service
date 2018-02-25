package ty.xiang.xty.datamodel.dao;

import org.springframework.data.repository.CrudRepository;
import ty.xiang.xty.datamodel.domain.Picture;

import javax.transaction.Transactional;

@Transactional
public interface PictureDAO extends CrudRepository<Picture, Long> {

}
