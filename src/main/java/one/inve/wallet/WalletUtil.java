package one.inve.wallet;


import com.alibaba.fastjson.JSONObject;
import one.inve.mnemonic.Mnemonic;
import one.inve.utils.KeyStoreUtil;
import one.inve.utils.StringUtils;

import java.io.IOException;
import java.util.Scanner;

/**
 * @Author: zz
 * @Description:
 * @Date: 上午 11:54 2019/1/25 0025
 * @Modified By
 */
public class WalletUtil {
    private static String keystoreName = "wallet.keystore";


    public static void main(String[] args) throws IOException {
        System.out.println("-------------start---------------");
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

        json.put("mnemonic",words);
        json.put("passphrase",salt);
        String data = json.toJSONString();
        System.out.println("----------------------------");
        System.out.println("生成電子錢包:");
        System.out.println("words: " + words);
        System.out.println("passphrase: " + salt);
        System.out.println("----------------------------");

        String path = WalletUtil.class.getProtectionDomain().getCodeSource().getLocation().getPath().substring(1);
        System.out.println("生成keystore文件:(" + keystoreName + ")");
        System.out.println("----------------------------");
        String password = "888999";
        try {
            KeyStoreUtil.generateKeyStore(password,data, keystoreName);
//            String result  = KeyStoreUtil.readkeyStore("888999",path+keystoreName);
//            System.out.println(result);
            System.out.println("keystore生成成功~!");
            System.out.println("-------------end---------------");
        } catch (IOException e) {
            System.out.println("keystore生成失败~!");
        }

    }

}
