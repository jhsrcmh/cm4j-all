package com.cm4j.web.action.base;

/**
 * 用户权限 - 淘宝用户购买软件版本
 * 
 * @author yang.hao
 * @since 2011-7-27 下午03:42:19
 * 
 */
public enum VisitorPrivilege {
	/**
	 * 初级版
	 */
	junior(1),
	/**
	 * 中级版
	 */
	middle(2),
	/**
	 * 高级版
	 */
	senior(3);

	private VisitorPrivilege(int versionNo) {
		this.versionNo = versionNo;
	}

	private int versionNo;

	public int getVersionNo() {
		return versionNo;
	}
	
	public static VisitorPrivilege getPrivilege (int versionNo){
		VisitorPrivilege[] privileges = VisitorPrivilege.values();
		for (VisitorPrivilege visitorPrivilege : privileges) {
			if (visitorPrivilege.getVersionNo() == versionNo){
				return visitorPrivilege;
			}
		}
		return null;
	}
}