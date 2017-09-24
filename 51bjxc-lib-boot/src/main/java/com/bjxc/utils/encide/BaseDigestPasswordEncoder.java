/*
 * �?          �?:  DEP业务基础平台
 * �?   �?  �?:  BaseDigestPasswordEncoder.java
 * �?          �?:  深圳市迪博企业管理技术有限公�? Copyright 2011-2020,  All rights reserved
 * �?          �?:  DEP业务基础平台是深圳迪博企业风险管理技术有限公司自主研发的业务基础平台。是面向
 *            业务应用的管理软件开发平台�?�帮助软件开发人员突破技术瓶颈，实现少写源代码或
 *            不写源代码�?�快速地�?发应用软件的目的�?
 * �?   �?  �?:  wenbing.zhang
 * 创建时间:  2012-5-23
 */
package com.bjxc.utils.encide;
/**
 * <p>Convenience base for digest password encoders.</p>
 *
 * @author colin sampaleanu
 * @version $Id: BaseDigestPasswordEncoder.java 2217 2007-10-27 00:45:30Z luke_t $
 */
public abstract class BaseDigestPasswordEncoder extends BasePasswordEncoder {
    //~ Instance fields ================================================================================================

    private boolean encodeHashAsBase64 = false;

    //~ Methods ========================================================================================================

    public boolean getEncodeHashAsBase64() {
        return encodeHashAsBase64;
    }

    /**
     * The encoded password is normally returned as Hex (32 char) version of the hash bytes. Setting this
     * property to true will cause the encoded pass to be returned as Base64 text, which will consume 24 characters.
     *
     * @param encodeHashAsBase64 set to true for Base64 output
     */
    public void setEncodeHashAsBase64(boolean encodeHashAsBase64) {
        this.encodeHashAsBase64 = encodeHashAsBase64;
    }
}
