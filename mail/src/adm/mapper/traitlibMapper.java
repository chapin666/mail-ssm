package adm.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;

import adm.bean.traitlib;

public interface traitlibMapper {
	/**查询所有*/
	public List<traitlib> selectTraitlibs(traitlib t);	
	/** 统计记录条数  */
	public int traitlibCount();
	
	/**删除特征库记录*/
	@Delete("delete from traitlib where id = #{id}")
	public void delTraitlibs(traitlib t);
}
