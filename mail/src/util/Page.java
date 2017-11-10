package util;

public class Page {
	
	private int totalsize;
	private int pagenow;
	private int pagesize;

	public int getTotalsize() {
		return totalsize;
	}

	public void setTotalsize(int totalsize) {
		this.totalsize = totalsize;
	}

	public int getPagenow() {
		return pagenow;
	}

	public void setPagenow(int pagenow) {
		this.pagenow = pagenow;
	}

	public int getPagesize() {
		return pagesize;
	}

	public void setPagesize(int pagesize) {
		this.pagesize = pagesize;
	}

	public String pagebar(){
		
		StringBuffer pages = new StringBuffer();
		int mod=totalsize%pagesize;
		int max;
		if(mod!=0){
			max=totalsize/pagesize+1;
		}else{
			max=totalsize/pagesize;
		}
		if(max<11){
			if(pagenow<=1){
				pages.append("<a style=\"cursor:inherit;background:#f5f5f5;color:#999999\">首页</a>");
				pages.append("<a style=\"cursor:inherit;background:#f5f5f5;color:#999999\">上一页</a>");
			}else{
				pages.append("<a onclick=\"goPage(1);\">首页</a>");
				pages.append("<a onclick=\"goPage("+(pagenow-1)+");\">上一页</a>");
			}
			for(int i=0;i<max;i++){
				if(i==pagenow-1){
					pages.append("<a style=\"cursor:inherit\" class=\"numclick\"><font color=\"#ffffff\">"+(i+1)+"</font></a>");
				}else{
					pages.append("<a onclick=\"goPage("+(i+1)+");\">"+(i+1)+"</a>");
				}
			}
			if(max<=pagenow){
				pages.append("<a style=\"cursor:inherit;background:#f5f5f5;color:#999999\">下一页</a>");
				pages.append("<a style=\"cursor:inherit;background:#f5f5f5;color:#999999\">尾页</a>");
			}else{
				pages.append("<a onclick=\"goPage("+(pagenow+1)+");\">下一页</a>");
				pages.append("<a onclick=\"goPage("+max+");\">尾页</a>");
			}
		}else{
			
			if(pagenow<=1){
				pages.append("<a style=\"cursor:inherit;background:#f5f5f5;color:#999999\">首页</a>");
				pages.append("<a style=\"cursor:inherit;background:#f5f5f5;color:#999999\">上一页</a>");
			}else{
				pages.append("<a onclick=\"goPage(1);\">首页</a>");
				pages.append("<a onclick=\"goPage("+(pagenow-1)+");\">上一页</a>");
			}
			if(pagenow<6){
				for(int i=0;i<6;i++){
					if(i==pagenow-1){
						pages.append("<a style=\"cursor:inherit\" class=\"numclick\"><font color=\"#ffffff\">"+(i+1)+"</font></a>");
					}else{
						pages.append("<a onclick=\"goPage("+(i+1)+");\">"+(i+1)+"</a>");
					}
				}
				pages.append("<a style=\"cursor:inherit;background:#f5f5f5;color:#999999\">……</a>");
				pages.append("<a onclick=\"goPage("+(max-1)+");\">"+(max-1)+"</a>");
				pages.append("<a onclick=\"goPage("+(max)+");\">"+(max)+"</a>");
			}else{
				pages.append("<a onclick=\"goPage(1);\">1</a>");
				pages.append("<a onclick=\"goPage(2);\">2</a>");
				pages.append("<a style=\"cursor:inherit;background:#f5f5f5;color:#999999\">……</a>");
				if(pagenow>max-5){
					for(int i=max-4;i<=max;i++){
						if(i==pagenow){
							pages.append("<a style=\"cursor:inherit\" class=\"numclick\"><font color=\"#ffffff\">"+(i)+"</font></a>");
						}else{
							pages.append("<a onclick=\"goPage("+(i)+");\">"+(i)+"</a>");
						}
					}
				}else{
					for(int i=pagenow;i<pagenow+4;i++){
						if(i==pagenow){
							pages.append("<a style=\"cursor:inherit\" class=\"numclick\"><font color=\"#ffffff\">"+(i)+"</font></a>");
						}else{
							pages.append("<a onclick=\"goPage("+(i)+");\">"+(i)+"</a>");
						}
					}
					pages.append("<a style=\"cursor:inherit;background:#f5f5f5;color:#999999\">……</a>");
					pages.append("<a onclick=\"goPage("+(max-1)+");\">"+(max-1)+"</a>");
					pages.append("<a onclick=\"goPage("+(max)+");\">"+(max)+"</a>");
				}
			}
			if(max<=pagenow){
				pages.append("<a style=\"cursor:inherit;background:#f5f5f5;color:#999999\">下一页</a>");
				pages.append("<a style=\"cursor:inherit;background:#f5f5f5;color:#999999\">尾页</a>");
			}else{
				pages.append("<a onclick=\"goPage("+(pagenow+1)+");\">下一页</a>");
				pages.append("<a onclick=\"goPage("+max+");\">尾页</a>");
			}
		}
		return pages.toString();
	}
	
	public String pagebar1(){
		
		StringBuffer pages = new StringBuffer();
		int mod=totalsize%pagesize;
		int max;
		if(mod!=0){
			max=totalsize/pagesize+1;
		}else{
			max=totalsize/pagesize;
		}
		if(max<11){
			if(pagenow<=1){
				pages.append("<a style=\"cursor:inherit;background:#f5f5f5;color:#999999\">首页</a>");
				pages.append("<a style=\"cursor:inherit;background:#f5f5f5;color:#999999\">上一页</a>");
			}else{
				pages.append("<a onclick=\"goPage1(1);\">首页</a>");
				pages.append("<a onclick=\"goPage1("+(pagenow-1)+");\">上一页</a>");
			}
			for(int i=0;i<max;i++){
				if(i==pagenow-1){
					pages.append("<a style=\"cursor:inherit\" class=\"numclick\"><font color=\"#ffffff\">"+(i+1)+"</font></a>");
				}else{
					pages.append("<a onclick=\"goPage1("+(i+1)+");\">"+(i+1)+"</a>");
				}
			}
			if(max<=pagenow){
				pages.append("<a style=\"cursor:inherit;background:#f5f5f5;color:#999999\">下一页</a>");
				pages.append("<a style=\"cursor:inherit;background:#f5f5f5;color:#999999\">尾页</a>");
			}else{
				pages.append("<a onclick=\"goPage1("+(pagenow+1)+");\">下一页</a>");
				pages.append("<a onclick=\"goPage1("+max+");\">尾页</a>");
			}
		}else{
			
			if(pagenow<=1){
				pages.append("<a style=\"cursor:inherit;background:#f5f5f5;color:#999999\">首页</a>");
				pages.append("<a style=\"cursor:inherit;background:#f5f5f5;color:#999999\">上一页</a>");
			}else{
				pages.append("<a onclick=\"goPage1(1);\">首页</a>");
				pages.append("<a onclick=\"goPage1("+(pagenow-1)+");\">上一页</a>");
			}
			if(pagenow<6){
				for(int i=0;i<6;i++){
					if(i==pagenow-1){
						pages.append("<a style=\"cursor:inherit\" class=\"numclick\"><font color=\"#ffffff\">"+(i+1)+"</font></a>");
					}else{
						pages.append("<a onclick=\"goPage1("+(i+1)+");\">"+(i+1)+"</a>");
					}
				}
				pages.append("<a style=\"cursor:inherit;background:#f5f5f5;color:#999999\">……</a>");
				pages.append("<a onclick=\"goPage1("+(max-1)+");\">"+(max-1)+"</a>");
				pages.append("<a onclick=\"goPage1("+(max)+");\">"+(max)+"</a>");
			}else{
				pages.append("<a onclick=\"goPage1(1);\">1</a>");
				pages.append("<a onclick=\"goPage1(2);\">2</a>");
				pages.append("<a style=\"cursor:inherit;background:#f5f5f5;color:#999999\">……</a>");
				if(pagenow>max-5){
					for(int i=max-4;i<=max;i++){
						if(i==pagenow){
							pages.append("<a style=\"cursor:inherit\" class=\"numclick\"><font color=\"#ffffff\">"+(i)+"</font></a>");
						}else{
							pages.append("<a onclick=\"goPage1("+(i)+");\">"+(i)+"</a>");
						}
					}
				}else{
					for(int i=pagenow;i<pagenow+4;i++){
						if(i==pagenow){
							pages.append("<a style=\"cursor:inherit\" class=\"numclick\"><font color=\"#ffffff\">"+(i)+"</font></a>");
						}else{
							pages.append("<a onclick=\"goPage1("+(i)+");\">"+(i)+"</a>");
						}
					}
					pages.append("<a style=\"cursor:inherit;background:#f5f5f5;color:#999999\">……</a>");
					pages.append("<a onclick=\"goPage1("+(max-1)+");\">"+(max-1)+"</a>");
					pages.append("<a onclick=\"goPage1("+(max)+");\">"+(max)+"</a>");
				}
			}
			if(max<=pagenow){
				pages.append("<a style=\"cursor:inherit;background:#f5f5f5;color:#999999\">下一页</a>");
				pages.append("<a style=\"cursor:inherit;background:#f5f5f5;color:#999999\">尾页</a>");
			}else{
				pages.append("<a onclick=\"goPage1("+(pagenow+1)+");\">下一页</a>");
				pages.append("<a onclick=\"goPage1("+max+");\">尾页</a>");
			}
		}
		return pages.toString();
	}
	
	public String pagebar2(){
		
		StringBuffer pages = new StringBuffer();
		int mod=totalsize%pagesize;
		int max;
		if(mod!=0){
			max=totalsize/pagesize+1;
		}else{
			max=totalsize/pagesize;
		}
		if(max<11){
			if(pagenow<=1){
				pages.append("<a style=\"cursor:inherit;background:#f5f5f5;color:#999999\">首页</a>");
				pages.append("<a style=\"cursor:inherit;background:#f5f5f5;color:#999999\">上一页</a>");
			}else{
				pages.append("<a onclick=\"goPage2(1);\">首页</a>");
				pages.append("<a onclick=\"goPage2("+(pagenow-1)+");\">上一页</a>");
			}
			for(int i=0;i<max;i++){
				if(i==pagenow-1){
					pages.append("<a style=\"cursor:inherit\" class=\"numclick\"><font color=\"#ffffff\">"+(i+1)+"</font></a>");
				}else{
					pages.append("<a onclick=\"goPage2("+(i+1)+");\">"+(i+1)+"</a>");
				}
			}
			if(max<=pagenow){
				pages.append("<a style=\"cursor:inherit;background:#f5f5f5;color:#999999\">下一页</a>");
				pages.append("<a style=\"cursor:inherit;background:#f5f5f5;color:#999999\">尾页</a>");
			}else{
				pages.append("<a onclick=\"goPage2("+(pagenow+1)+");\">下一页</a>");
				pages.append("<a onclick=\"goPage2("+max+");\">尾页</a>");
			}
		}else{
			
			if(pagenow<=1){
				pages.append("<a style=\"cursor:inherit;background:#f5f5f5;color:#999999\">首页</a>");
				pages.append("<a style=\"cursor:inherit;background:#f5f5f5;color:#999999\">上一页</a>");
			}else{
				pages.append("<a onclick=\"goPage2(1);\">首页</a>");
				pages.append("<a onclick=\"goPage2("+(pagenow-1)+");\">上一页</a>");
			}
			if(pagenow<6){
				for(int i=0;i<6;i++){
					if(i==pagenow-1){
						pages.append("<a style=\"cursor:inherit\" class=\"numclick\"><font color=\"#ffffff\">"+(i+1)+"</font></a>");
					}else{
						pages.append("<a onclick=\"goPage2("+(i+1)+");\">"+(i+1)+"</a>");
					}
				}
				pages.append("<a style=\"cursor:inherit;background:#f5f5f5;color:#999999\">……</a>");
				pages.append("<a onclick=\"goPage2("+(max-1)+");\">"+(max-1)+"</a>");
				pages.append("<a onclick=\"goPage2("+(max)+");\">"+(max)+"</a>");
			}else{
				pages.append("<a onclick=\"goPage2(1);\">1</a>");
				pages.append("<a onclick=\"goPage2(2);\">2</a>");
				pages.append("<a style=\"cursor:inherit;background:#f5f5f5;color:#999999\">……</a>");
				if(pagenow>max-5){
					for(int i=max-4;i<=max;i++){
						if(i==pagenow){
							pages.append("<a style=\"cursor:inherit\" class=\"numclick\"><font color=\"#ffffff\">"+(i)+"</font></a>");
						}else{
							pages.append("<a onclick=\"goPage2("+(i)+");\">"+(i)+"</a>");
						}
					}
				}else{
					for(int i=pagenow;i<pagenow+4;i++){
						if(i==pagenow){
							pages.append("<a style=\"cursor:inherit\" class=\"numclick\"><font color=\"#ffffff\">"+(i)+"</font></a>");
						}else{
							pages.append("<a onclick=\"goPage2("+(i)+");\">"+(i)+"</a>");
						}
					}
					pages.append("<a style=\"cursor:inherit;background:#f5f5f5;color:#999999\">……</a>");
					pages.append("<a onclick=\"goPage2("+(max-1)+");\">"+(max-1)+"</a>");
					pages.append("<a onclick=\"goPage2("+(max)+");\">"+(max)+"</a>");
				}
			}
			if(max<=pagenow){
				pages.append("<a style=\"cursor:inherit;background:#f5f5f5;color:#999999\">下一页</a>");
				pages.append("<a style=\"cursor:inherit;background:#f5f5f5;color:#999999\">尾页</a>");
			}else{
				pages.append("<a onclick=\"goPage2("+(pagenow+1)+");\">下一页</a>");
				pages.append("<a onclick=\"goPage2("+max+");\">尾页</a>");
			}
		}
		return pages.toString();
	}
	
	// 不显示页数序号的分页方式
	public String pagebar3(){
		
		StringBuffer pages = new StringBuffer();
		int mod=totalsize%pagesize;
		int max;
		if(mod!=0){
			max=totalsize/pagesize+1;
		}else{
			max=totalsize/pagesize;
		}
		if(max<11){
			if(pagenow<=1){
				pages.append("<a style=\"cursor:inherit;\">上一页</a>");
			}else{
				pages.append("<a onclick=\"goPage("+(pagenow-1)+");\">上一页</a>");
			}
			if(max<=pagenow){
				pages.append("<a style=\"cursor:inherit;\">&nbsp;下一页</a>");
			}else{
				pages.append("<a onclick=\"goPage("+(pagenow+1)+");\">&nbsp;下一页</a>");
			}
		}else{
			
			if(pagenow<=1){
				pages.append("<a style=\"cursor:inherit;\">上一页</a>");
			}else{
				pages.append("<a onclick=\"goPage("+(pagenow-1)+");\">上一页</a>");
			}
			if(max<=pagenow){
				pages.append("<a style=\"cursor:inherit;\">&nbsp;下一页</a>");
			}else{
				pages.append("<a onclick=\"goPage("+(pagenow+1)+");\">&nbsp;下一页</a>");
			}
		}
		return pages.toString();
	}
}
