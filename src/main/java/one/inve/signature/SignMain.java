package one.inve.signature;

import one.inve.mnemonic.Mnemonic;
import one.inve.utils.ByteUtil;
import one.inve.utils.DSA;
import org.bitcoinj.core.ECKey;
import org.bitcoinj.core.Sha256Hash;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

import static org.bitcoinj.core.Sha256Hash.wrap;

/**
 * @Author: zz
 * @Description:
 * @Date: 下午 5:35 2018/7/4 0004
 * @Modified By
 */
public class SignMain {

   /* public static void main(String[] args) throws Exception {
        Mnemonic mn = new Mnemonic("","");
        byte[] priKey = mn.getxPrivKey();
        System.out.println("私钥" + ByteUtil.bytes2hex(priKey));

        byte[] pubKey = mn.getPubKey();
        String pubStr = DSA.encryptBASE64(pubKey);
        System.out.println("公钥" + pubStr);

        System.out.println("Welcome ~!");
        Scanner sc;
        boolean flag = true;
        while(flag) {
            System.out.println("Please enter the secret prikey(hexadecimal)：");
            sc = new Scanner(System.in);

            String prikey = sc.nextLine();
            if(prikey.length() != 64) {
                System.out.println("The private key is wrong ~! Please try again ~!");
                continue;
            }

//            System.out.println(prikey);

            try {
                sc = new Scanner(System.in);

                ECKey key = ECKey.fromPrivate(ByteUtil.hexString2bytes(prikey));
                System.out.println("your private key is : " + ByteUtil.bytes2hex(key.getPrivKeyBytes()));
                System.out.println("Please enter your messages：");

                String message = new String(sc.nextLine().getBytes(),"utf8");
                System.out.println("your messages is : " + message);

                String signStr = signStr(key, message );

                if(signStr != null && signStr.length() > 0) {
                    flag = false;
                    System.out.println("File saving successful ~!");
                }else {
                    System.out.println("File saving failed ~! Please try again ~!");
                }

                System.out.println("DO you want to verify your signature now ~? (y or no)");
                String str = sc.nextLine();
                if(str.equals("y") || str.equals("Y") || str.equals("yes")) {
                    System.out.println("Please enter the secret message : ");
                    String _message = sc.nextLine();
                    System.out.println("Please enter the secret signature : ");
                    String _signStr = sc.nextLine();
                    System.out.println("Please enter the secret pubKey(Base64) : ");
                    String _pubStr = sc.nextLine();

                    boolean  ff = verify(_message ,_signStr ,_pubStr);
                    if(ff){
                        System.out.println("Verify successful ~!");
                    }
                }else {
                    System.exit(0);
                }

            } catch (Exception e) {
                flag = false;
                System.out.println("Enter a wrong private key ~! \r\nPlease enter the right ~!");
            }



        }
    }*/

    //signStr
    public static String signStr(ECKey key, String message ) {
        String signStr = null;
        try{
            System.out.println("start sign... wait...");
            byte[] hash = Sha256Hash.hash(message.getBytes());
            Sha256Hash ZERO_HASH = wrap(hash);
            ECKey.ECDSASignature sign = key.sign(ZERO_HASH);
            signStr = DSA.encryptBASE64(sign.encodeToDER()).trim().replaceAll("\r|\n", "");;
            System.out.println("your signature is: " + signStr +"\r\nPlease keep it carefully ~!");
            File file = new File("key.txt");
            PrintStream ps = new PrintStream(new FileOutputStream(file));
            ps.println("your private key is : " + ByteUtil.bytes2hex( key.getPrivKeyBytes()));
//            ps.println("your public key is : " + pubStr);
            ps.println("your messages is : " + message);
            ps.println("your signature is: " + signStr);
            System.out.println("Sign successful ~!");
        } catch (FileNotFoundException e) {
            System.out.println("File saving failed ~! Please try again ~!");
            e.printStackTrace();
        }

        return signStr;
    }


    public static boolean verify(String message , String signStr , String pubkey) {
        try {
            ECKey key = ECKey.fromPublicOnly(DSA.decryptBASE64(pubkey));
            message = new String(message.getBytes() ,"utf8");
            byte[] sha = message.getBytes();
            byte[] hash = Sha256Hash.hash(sha);
            return key.verify(hash , DSA.decryptBASE64(signStr));
        } catch (Exception e) {
            System.out.println("Verify faild ~! Please try again ~! ");
            e.printStackTrace();
            return false;
        }

    }

}
