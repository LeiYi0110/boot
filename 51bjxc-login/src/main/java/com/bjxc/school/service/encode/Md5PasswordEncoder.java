/*
 * 产          品:  DEP业务基础平台
 * 文   件  名:  Md5PasswordEncoder.java
 * 版          权:  深圳市迪博企业管理技术有限公司 Copyright 2011-2020,  All rights reserved
 * 描          述:  DEP业务基础平台是深圳迪博企业风险管理技术有限公司自主研发的业务基础平台。是面向
 *            业务应用的管理软件开发平台。帮助软件开发人员突破技术瓶颈，实现少写源代码或
 *            不写源代码、快速地开发应用软件的目的。
 * 创   建  人:  wenbing.zhang
 * 创建时间:  2012-5-23
 */
package com.bjxc.school.service.encode;

/**
 * <p>MD5 implementation of PasswordEncoder.</p>
 * <p>If a <code>null</code> password is presented, it will be treated as an empty <code>String</code> ("")
 * password.</p>
 * <P>As MD5 is a one-way hash, the salt can contain any characters.</p>
 *
 * This is a convenience class that extends the
 * {@link MessageDigestPasswordEncoder} and passes MD5 as the algorithm to use.
 *
 * @author Ray Krueger
 * @author colin sampaleanu
 * @author Ben Alex
 * @version $Id: Md5PasswordEncoder.java 2217 2007-10-27 00:45:30Z luke_t $
 */
public class Md5PasswordEncoder extends MessageDigestPasswordEncoder {

    public Md5PasswordEncoder() {
        super("MD5");
    }
}
