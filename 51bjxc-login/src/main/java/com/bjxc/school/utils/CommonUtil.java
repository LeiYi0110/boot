package com.bjxc.school.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;

import com.bjxc.json.JacksonBinder;
import com.bjxc.school.TemplateText;
import com.bjxc.school.exception.AuthenticateException;
import com.bjxc.school.service.PlatformUserService;
import com.bjxc.school.service.encode.CyptoUtils;
import com.bjxc.school.service.encode.EncryptUtil;

public class CommonUtil {
	private static final Logger logger=LoggerFactory.getLogger(PlatformUserService.class);
	private static StringRedisTemplate redisTemplate;
	private static JacksonBinder binder = JacksonBinder.buildNormalBinder();
	
	/**
	 * 正则表达式验证
	 * @return
	 */
	public static boolean startCheck(String reg,String string)  
    {  
        boolean tem=false;  
        Pattern pattern = Pattern.compile(reg);  
        Matcher matcher=pattern.matcher(string);  
        tem=matcher.matches();  
        return tem;  
    }  
	/**
	 * 登录令牌生成
	 * @return
	 */
	public static String createToken(int id){
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		StringBuffer buff = new StringBuffer(id+"");
		buff.append(formatter.format(currentTime));
		buff.append(getRandomString());
		String token=CyptoUtils.encode("123456790", buff.toString());
		return token;
	}
	/**
	 * 6位随机数
	 * @return
	 */
    public static String getRandomString(){  
        //String str="abcdefghijklmnopqrstuvwxyz0123456789";
    	String str="0123456789";  
    	
        Random random = new Random();  
        StringBuffer buff = new StringBuffer();  
        for(int i = 0 ; i < 6; ++i){  
            int number = random.nextInt(str.length());  
            buff.append(str.charAt(number));  
        }  
        return buff.toString();  
    }  
    /**
     *获得指定时间格式 
     */
    public static String getSimpleDate(Date date,String style){
		SimpleDateFormat format = new SimpleDateFormat(style);
		return format.format(date);
    }
    
    public static String sendTemplateSMS(String to,String textCode) throws IOException{
    	//EncryptUtil encryptUtil = new EncryptUtil(); //加密工具
    	
    	Properties pps = new Properties();     //读取配置文件
    	EncryptUtil encryptUtil = new EncryptUtil(); //加密工具
    	try {
    		InputStream in = CommonUtil.class.getClassLoader().getResourceAsStream("template.properties");
			pps.load(in);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new AuthenticateException("读不到文件");
		} 
    	String version=pps.getProperty("version");
    	String accountSid=pps.getProperty("accountSid");
    	String token=pps.getProperty("token");
    	String appId=pps.getProperty("appId");
    	String templateId=pps.getProperty("templateId");
    	String server=pps.getProperty("rest_server");
    	String timestamp=getSimpleDate(new Date(), "yyyyMMddHHmmss");
    	String sig=accountSid + token + timestamp;

    	try {
			String signature=encryptUtil.md5Digest(sig);
			StringBuffer url = new StringBuffer("https://");
			url.append(server).append("/").append(version)
					.append("/Accounts/").append(accountSid)
					.append("/Messages/templateSMS")
					.append("?sig=").append(signature).toString();
			TemplateText templateSMS=new TemplateText();
			templateSMS.setAppId(appId);
			templateSMS.setTemplateId(templateId);
			templateSMS.setTo(to);
			templateSMS.setParam(textCode);
			String body="{\"templateSMS\":"+binder.toJson(templateSMS)+"}";
			logger.info(url.toString());
			logger.info(body);
			URL urls=new URL(url.toString());
			HttpURLConnection connection = (HttpURLConnection)urls.openConnection();  
			// 发送POST请求必须设置如下两行
			connection.setDoOutput(true);  
            connection.setDoInput(true);  
            // 设置通用的请求属性	
            connection.setRequestMethod("POST"); // 设置请求方式  
            connection.setRequestProperty("Accept", "application/json"); // 设置接收数据的格式  
            connection.setRequestProperty("Content-Type", "application/json;charset=utf-8"); // 设置发送数据的格式  
            String src = accountSid + ":" + timestamp;
    		String auth = encryptUtil.base64Encoder(src);
    		connection.setRequestProperty("Authorization", auth);
    		connection.connect();
    		OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream(), "UTF-8"); // utf-8编码  
            out.append(body);
            out.flush();  
            out.close();  
            int length = (int) connection.getContentLength();// 获取长度  
            InputStream is = connection.getInputStream();  
            if (length != -1) {  
                byte[] data = new byte[length];  
                byte[] temp = new byte[512];  
                int readLen = 0;  
                int destPos = 0;  
                while ((readLen = is.read(temp)) > 0) {  
                    System.arraycopy(temp, 0, data, destPos, readLen);  
                    destPos += readLen;  
                }  
                String result = new String(data, "UTF-8"); // utf-8编码  
                logger.info(result);
                return result;  
            }
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return null;
    }
    
    @Resource
	public void setRedisTemplate(StringRedisTemplate redisTemplate) {
		CommonUtil.redisTemplate = redisTemplate;
	}
    
}
