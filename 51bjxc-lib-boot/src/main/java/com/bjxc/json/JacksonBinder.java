package com.bjxc.json;

import java.io.IOException;
import java.lang.reflect.AnnotatedElement;
import java.text.SimpleDateFormat;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.Annotated;
import com.fasterxml.jackson.databind.introspect.AnnotatedMethod;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;

public class JacksonBinder {
    //private static Logger logger = LoggerFactory.getLogger(JacksonBinder.class);     
    private ObjectMapper mapper;   
  
    public JacksonBinder(Include  include ) {   
        mapper = new ObjectMapper();
        //�����������������   
       mapper.setSerializationInclusion(include);   
        //��������ʱ����JSON�ַ����д��ڶ�Java����ʵ��û�е�����   
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false); 
        mapper.configure(DeserializationFeature.READ_DATE_TIMESTAMPS_AS_NANOSECONDS, false); 
        mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        //���ڸ�ʽ����
        mapper.setAnnotationIntrospectors(new JacksonAnnotationIntrospector() {
            @Override  
            public Object findSerializer(Annotated a) {  
                    if(a instanceof AnnotatedMethod) {  
                            AnnotatedElement m=a.getAnnotated();  
                            DateTimeFormat an=m.getAnnotation(DateTimeFormat.class);  
                            if(an!=null) {  
                                   return new JsonDateSerializer(an.pattern());  
                            }  
                    }  
                    return super.findSerializer(a);  
            }  
    },new JacksonAnnotationIntrospector() {
        @Override  
        public Class<? extends JsonDeserializer<?>> findDeserializer(Annotated a) {  
                if(a instanceof AnnotatedMethod) {  
                        AnnotatedElement m=a.getAnnotated();  
                        DateTimeFormat an=m.getAnnotation(DateTimeFormat.class);  
                        if(an!=null) {
                        	if("yyyy-MM-dd".equals(an.pattern()) || "yyyyMMdd".equals(an.pattern())|| "yyyy/MM/dd".equals(an.pattern())){
                        		return JsonDateYMDDeserializer.class;  
                        	}
                        }  
                }  
                return (Class<? extends JsonDeserializer<?>>) super.findDeserializer(a);
        }  
});  
    }   
  
    /**  
     * �������ȫ�����Ե�Json�ַ�����Binder.  
    */  
    public static JacksonBinder buildNormalBinder() {   
        return new JacksonBinder(Include.ALWAYS);   
    }   
  
    /**  
     * ����ֻ����ǿ����Ե�Json�ַ�����Binder.  
     */  
    public static JacksonBinder buildNonNullBinder() {   
        return new JacksonBinder(Include.NON_NULL);   
    }   
  
    /**  
     * ����ֻ�����ʼֵ���ı�����Ե�Json�ַ�����Binder.  
     */  
    public static JacksonBinder buildNonDefaultBinder() {   
        return new JacksonBinder(Include.NON_DEFAULT);   
    }   
  
    /**  
     * ���JSON�ַ���ΪNull��"null"�ַ���,����Null.  
     * ���JSON�ַ���Ϊ"[]",���ؿռ���.  
     *   
     * �����ȡ������List/Map,�Ҳ���List<String>���ּ�����ʱʹ���������:  
     * List<MyBean> beanList = binder.getMapper().readValue(listString, new TypeReference<List<MyBean>>() {});  
     */  
    public <T> T fromJson(String jsonString, Class<T> clazz) {   
        if (StringUtils.isEmpty(jsonString)) {   
            return null;   
        }   
  
        try {   
            return mapper.readValue(jsonString, clazz);   
        } catch (IOException e) {   
            //logger.warn("parse json string error:" + jsonString, e);   
            return null;   
        }   
    }   
  
    /**  
     * �������ΪNull,����"null".  
     * �������Ϊ�ռ���,����"[]".  
     */  
    public String toJson(Object object) {   
  
        try {   
            return mapper.writeValueAsString(object);   
        } catch (IOException e) {   
            //logger.warn("write to json string error:" + object, e);   
            return null;   
        }   
    }   
  
    /**  
     * ȡ��Mapper����һ�������û�ʹ���������л�API.  
     */  
    public ObjectMapper getMapper() {   
        return mapper;   
    }   

}
