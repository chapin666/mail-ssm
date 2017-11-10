package mail.service;

import java.util.List;
import mail.bean.ETag;

public interface ETagService {
		
	public ETag getETag(int id);

	public List<ETag> getETagList(int tid);
	
	public void addETag(ETag etag);

	public void deleteETag(String id);

	public List<ETag> getEidList(String tid);

	public List<ETag> getETagListByEids(String eids);

	public void addBatETag(String tid, String[] ids, List<ETag> eidlist,String type);
	/**垃圾箱邮件绑定标签*/
	public void rubmaildoAddETag(String tid, String[] ids, List<ETag> eidlist,String type);
	/**删除箱邮件绑定标签*/
	public void deladdBatETag(String tid, String[] ids, List<ETag> eidlist,String[] type);

//	public void addBatETag(String tid, String[] ids, List<ETag> eidlist, String [] frommails, String username);

	public void addBatETag2(int eid, String[] ids);

	public List<ETag> getETagListByUser(int userid);
	
}