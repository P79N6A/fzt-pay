package cn.yesway.pay.center.util;

import java.text.SimpleDateFormat;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtils {
    static ObjectMapper objectMapper;
    static {
        objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")); 
    }

    public static ObjectMapper getMapper() {
        return objectMapper;
    }

    public static String toJSon(Object object) throws Exception{
        return objectMapper.writeValueAsString(object);
    }

    /**
     * 读取json中的根对象
     * 
     * @param strJson
     * @return
     */
    public static JsonNode getJsonRoot(String strJson) throws Exception{
        return objectMapper.readTree(strJson);
    }

    /**
     * 反序列化POJO或简单Collection如List<String>.
     * 
     * 如果JSON字符串为Null或"null"字符串, 返回Null. 如果JSON字符串为"[]", 返回空集合.
     * 
     * 如需反序列化复杂Collection如List<MyBean>, 请使用fromJson(String,JavaType)
     * 
     * @see #fromJson(String, JavaType)
     */
    public static <T> T fromJson(String jsonString,Class<T> clazz) throws Exception{
        return objectMapper.readValue(jsonString, clazz);
    }

    /**
     * 反序列化复杂Collection如List<Bean>, 先使用函數createCollectionType构造类型,然后调用本函数.
     * 
     * @see #createCollectionType(Class, Class...)
     */
    @SuppressWarnings("unchecked")
    public static <T> T fromJson(String jsonString,JavaType javaType) throws Exception{
        return (T) objectMapper.readValue(jsonString, javaType);
    }

    /**
     * 構造泛型的Collection Type如: ArrayList<MyBean>,
     * 则调用constructCollectionType(ArrayList.class,MyBean.class) HashMap
     * <String,MyBean>, 则调用(HashMap.class,String.class, MyBean.class)
     */
    public static JavaType createCollectionType(Class<?> collectionClass, Class<?>... elementClasses) {
        return objectMapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);
    }


    public static void main(String[] args) {
        //String json = "{\"messageheader\":{\"resultcode\":-4,\"key\":\"12345\",\"message\":\"服务异常\"},\"adcode\":\"12345\"}";
        String json = "231312";
        try {
            JsonNode node = getJsonRoot(json);
            JsonNode header = node.get("messageheader");
            System.out.println(header.isNull());
            //MessageHeader header = JsonUtil.fromJson(node.get("messageheader").toString(), MessageHeader.class);
            //System.out.println(toJSon(header));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
