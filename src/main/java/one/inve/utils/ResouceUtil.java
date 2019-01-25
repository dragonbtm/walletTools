package one.inve.utils;

import org.apache.commons.io.IOUtils;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

/**
 * @Author: zz
 * @Description: 获取系统文件
 * @Date: 下午 7:58 2019/1/23 0023
 * @Modified By
 */
public class ResouceUtil {


    public static InputStream getResource(String location) throws IOException {
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        InputStream in = resolver.getResource(location).getInputStream();
        byte[] byteArray = IOUtils.toByteArray(in);
        in.close();
        return new ByteArrayInputStream(byteArray);
    }
}
