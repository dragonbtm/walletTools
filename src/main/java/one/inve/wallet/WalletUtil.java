package one.inve.wallet;

import com.alibaba.fastjson.JSONObject;
import one.inve.mnemonic.Mnemonic;
import one.inve.utils.KeyStoreUtil;
import org.apache.commons.lang.StringUtils;

import java.io.IOException;
import java.util.Scanner;

/**
 * @Author: zz
 * @Description:
 * @Date: 上午 11:54 2019/1/25 0025
 * @Modified By
 */
public class WalletUtil {
    private static String keystoreName = "zz.keystore";


    public static void main(String[] args) throws IOException {
        System.out.println("Welcome ~!");
        Scanner sc;
        boolean flag = true;

        System.out.println("请输入随机盐,默认为空(按ENTER确认):");
        sc = new Scanner(System.in);
        String salt = sc.nextLine();

        if(salt == null) {
            salt = "";
        }
        Mnemonic mn = null;
        try {
            mn = new Mnemonic("",salt);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String words = StringUtils.join(mn.getMnemonic().toArray() ," ");
        JSONObject json = new JSONObject();
        System.out.println("生成助记词:" + words);
        json.put("mnemonic",words);
        json.put("passphrase",salt);
        String data = json.toJSONString();

        System.out.println(words);
        String path = WalletUtil.class.getProtectionDomain().getCodeSource().getLocation().getPath().substring(1);
        System.out.println(path+keystoreName);
        System.out.println("生成keystore文件:("+ path + keystoreName + ")");

        try {
            KeyStoreUtil.generateKeyStore(null,data,"D:/zz.keystore");
//            String result  = KeyStoreUtil.readkeyStore("888999",path+keystoreName);
//            System.out.println(result);
        } catch (IOException e) {
            System.out.println("keystore生成失败~!");
        }

    }

}