package com.xuanli.oepcms.entity;

public class UserClasEntity {
    private Long id;

    private Long userId;

    private Long clasId;
    
    /**
     * 没有实际意义
     */
    private Integer allSize;
    
    

    /** 
	 * @return 返回 allSize 
	 */
	public Integer getAllSize() {
		return allSize;
	}

	/** 
	 * @setParam 设置allSize
	 */
	public void setAllSize(Integer allSize) {
		this.allSize = allSize;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getClasId() {
        return clasId;
    }

    public void setClasId(Long clasId) {
        this.clasId = clasId;
    }
}