package com.test;

import java.io.Serializable;

/**
 * 验证码模型数据
 */
//@JsonIgnoreProperties(ignoreUnknown = true)
public class Captcha implements Serializable {

	private static final long serialVersionUID = 7413486108627996294L;
	/**
     * 验证码id
     */
    public String captcha_id;
    public String name;
}
