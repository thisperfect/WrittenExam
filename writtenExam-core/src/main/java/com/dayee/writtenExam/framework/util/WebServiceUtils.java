package com.dayee.writtenExam.framework.util;

/**
 * Created by tang on 2016/10/18.
 */
public class WebServiceUtils {

    /**
     * 获取xfire webservice
     * @param service
     * @param wsdlUrl
     * @return
     * @throws Exception
     */
    public static Object getServiceClient(Class<?> service, String wsdlUrl)
            throws Exception {

        //XFireClientFactoryBean bean = new XFireClientFactoryBean();
        //bean.setWsdlDocumentUrl(wsdlUrl);
        //bean.setServiceClass(service);
        //bean.afterPropertiesSet();
        //return bean.getObject();
        return null;
    }
}
