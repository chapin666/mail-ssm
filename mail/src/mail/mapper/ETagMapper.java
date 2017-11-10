package mail.mapper;

import java.util.List;
import mail.bean.ETag;

public interface ETagMapper {
			
	public ETag getETag(int id);
	
	public List<ETag> getETagList(int eid);
	
	public void addETag(ETag etag);

	public void deleteETag(String id);

	public List<ETag> getEidList(String tid);

	public List<ETag> getETagListByEids(String eids);

	public List<ETag> getETagListByUser(int userid);
	
}
