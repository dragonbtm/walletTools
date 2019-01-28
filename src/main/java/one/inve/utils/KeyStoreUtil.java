package one.inve.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.*;
import java.security.cert.CertificateException;

/**
 * @Author: zz
 * @Description: KeyStore工具类  用于生成 和 读取keystore
 * @Date: 下午 7:47 2019/1/23 0023
 * @Modified By
 */
public class KeyStoreUtil {

    private static Logger log = LoggerFactory.getLogger(KeyStoreUtil.class);

    private static String KEYSTORE_NAME = "inve.keystore";


    public static void main(String[] args) throws KeyStoreException, IOException, CertificateException, NoSuchAlgorithmException {
        String storepass = "888999";
        String readPath = "zz.keystore";
        generateKeyStore(storepass,"123123123123",readPath);

        String AAA = readkeyStore(storepass,readPath);
    }



    /**
     * 生成keystore
     * @param password keystore 密码 // 该密钥库的密码"888999",storepass指定密钥库的密码(获取keystore信息所需的密码)
     * @param data 加密数据         // 用户要求保存于keystore文件中的密码
     * @param outpath 输出文件
     * @throws IOException
     */
    public static void generateKeyStore(String password,String data ,String outpath) throws IOException {
        InputStream fis = ResouceUtil.getResource("classpath:" + KEYSTORE_NAME);
        OutputStream os = null;
        try {
            // 读取keystore文件转换为keystore密钥库对象
            // 生成证书的类型为jceks
            KeyStore keyStore = KeyStore.getInstance("jceks");

            keyStore.load(fis, password.toCharArray());
            fis.close();
            // 一旦加载了 keystore，就能够从 keystore 读取现有条目，或向 keystore 写入新条目：
            String alias = "csdn";// 别名
            String keypass = "123456"; // 别名密码 ,keypass 指定别名条目的密码(私钥的密码)
            KeyStore.ProtectionParameter param = new KeyStore.PasswordProtection(keypass.toCharArray());
            KeyStore.PrivateKeyEntry pkEntry = (KeyStore.PrivateKeyEntry) keyStore.getEntry(alias, param);
            PrivateKey myPrivateKey = pkEntry.getPrivateKey();
            System.out.println("获取的私钥是：" + myPrivateKey.toString());
            // 根据给定的字节数组构造一个密钥
            String pd = "decryp pwd";
            javax.crypto.SecretKey mySecretKey = new SecretKeySpec(data.getBytes(), "JKS");
            KeyStore.SecretKeyEntry skEntry = new KeyStore.SecretKeyEntry(mySecretKey);
            keyStore.setEntry("desPws", skEntry, new KeyStore.PasswordProtection(pd.toCharArray()));
            //将此 keystore 存储到给定输出流，并用给定密码保护其完整性。
            os = new FileOutputStream(outpath);
            keyStore.store(os, password.toCharArray());
            os.close();

        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (UnrecoverableEntryException e) {
            e.printStackTrace();
        } finally {
            try {
                if(os != null)
                    os.close();
                if (fis != null)
                    fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public static String readkeyStore(String password,String readPath) {
        try {
            FileInputStream fis = null;
            // 读取keystore文件转换为keystore密钥库对象
            fis = new FileInputStream(readPath);
            // 因为生成证书的类型为JKS 也有其他的格式
            KeyStore keyStore = KeyStore.getInstance("jceks");
            // 该密钥库的密码"888999",storepass指定密钥库的密码(获取keystore信息所需的密码)
            keyStore.load(fis, password.toCharArray());
            fis.close();
            String pd = "decryp pwd";
            // 根据别名（alias=desPws）从证书（keyStore）获取密码并解密
            //keyStore.getKey返回与给定别名关联的密钥，并用给定密码来恢复它。
            Key key = keyStore.getKey("desPws", pd.toCharArray());
            //key.getEncoded()返回基本编码格式的密钥，如果此密钥不支持编码，则返回 null。
            System.out.println("从证书中获取的解密密码是：" + new String(key.getEncoded()));
            return new String(key.getEncoded());
        } catch (Exception e) {
            e.printStackTrace();
            log.error("读取keystory失败~!",e);
            return null;
        }
    }
}
