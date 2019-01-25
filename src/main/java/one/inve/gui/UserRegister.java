package one.inve.gui;

import one.inve.mnemonic.Mnemonic;
import one.inve.signature.SignMain;
import one.inve.utils.ByteUtil;
import one.inve.utils.DSA;
import org.bitcoinj.core.ECKey;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * @Author: zz
 * @Description:  签名小程序
 * @Date: 下午 4:37 2018/7/9 0009
 * @Modified By
 */
public class UserRegister extends JFrame implements ActionListener {

    //定义登录界面的组件
    JButton jb1,jb2,jb3=null;
    JRadioButton jrb1,jrb2=null;
    JPanel jp1,jp2,jp3 ,jp4,jp5=null;

    JLabel jlb1,jlb2=null;
    JLabel jlb3,jlb4=null;

    JTextField prikey=null;
    JTextField pubkey=null;
    JTextField signStr=null;
    JTextField signature=null;


    public static void main(String[] args)
    {
        UserRegister ur=new UserRegister();
    }

    public UserRegister()
    {
        try{
            ImageIcon imageIcon = new ImageIcon(getClass().getResource("/icon.png"));
            // 设置标题栏的图标为face.gif
            this.setIconImage(imageIcon.getImage());
        }catch (Exception e){}

        //创建组件
        jb1=new JButton("generate private key");
        jb2=new JButton("sign");
        jb3=new JButton("verify");
        //设置监听
        jb1.addActionListener(this);
        jb2.addActionListener(this);
        jb3.addActionListener(this);

        jlb1=new JLabel("Please enter the hexadecimal private key");
        jlb2=new JLabel("private key");
        jlb3=new JLabel("sign String");
        jlb4=new JLabel("signature");


        prikey=new JTextField(50);
//        prikey.setEnabled(false);
//        pubkey=new JTextField(50);
//        pubkey.setEnabled(false);
        signStr = new JTextField(50);
        signStr.setEnabled(false);
        signStr.setText("InterValue");
        signature = new JTextField(50);
//        signature.setEnabled(false);

        jp1=new JPanel();
        jp2=new JPanel();
        jp3=new JPanel();
        jp4=new JPanel();
        jp5=new JPanel();

        jp1.add(jlb1);
//        jp1.add(prikey);

        jp2.add(jlb2);
        jp2.add(prikey);

        jp3.add(jlb4);
        jp3.add(signStr);

        jp4.add(jlb3);
        jp4.add(signature);

//        jp5.add(jb1);
        jp5.add(jb2);
//        jp5.add(jb3);
        this.add(jp1);
        this.add(jp2);
        this.add(jp3);
        this.add(jp4);
        this.add(new JPanel());
        this.add(jp5);

        this.setVisible(true);
        this.setResizable(false);

        this.setTitle("sign");
        this.setLayout(new GridLayout(6,1));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setBounds(300, 200, 600, 300);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //监听各个按钮
//        if(e.getActionCommand()=="generate private key") {
//            generateKey();
//        }else
        if(e.getActionCommand()=="sign") {
            sign();
        }
//        else if(e.getActionCommand()=="verify") {
//            verify();
//        }

    }
//    //验证
//    private void verify() {
//        String pubkeystr = this.pubkey.getText();
//        String message = this.signStr.getText();
//        String signStr = this.signature.getText();
//        if(pubkeystr == null || pubkeystr.length() != 44) {
//            JOptionPane.showMessageDialog(null, "public key is wrong ~!", "error",JOptionPane.WARNING_MESSAGE);
//            return;
//        }
//        if(signStr == null || pubkeystr.length() != 96) {
//            JOptionPane.showMessageDialog(null, "signature is wrong ~!", "error",JOptionPane.WARNING_MESSAGE);
//            return;
//        }
//        if(signStr != null && !signStr.equals("")) {
//            boolean flag = one.inve.signature.SignMain.verify(message,signStr,pubkeystr);
//            if(flag) JOptionPane.showMessageDialog(null, "verify successful ~!", "info",JOptionPane.WARNING_MESSAGE);
//            else JOptionPane.showMessageDialog(null, "verify failed ~!", "error",JOptionPane.WARNING_MESSAGE);
//        }else {
//            JOptionPane.showMessageDialog(null, "sign string can not be null ~!", "error",JOptionPane.WARNING_MESSAGE);
//        }
//    }

    //签名
    private void sign() {
//        generateKey();
        String prikeystr = this.prikey.getText();
//        String pubkeystr = this.pubkey.getText();
        String message = this.signStr.getText();

        if(prikeystr == null || prikeystr.length() != 64) {
            JOptionPane.showMessageDialog(null, "private key is wrong ~!", "error",JOptionPane.WARNING_MESSAGE);
            return;
        }
//        if(pubkeystr == null || pubkeystr.length() != 44) {
//            JOptionPane.showMessageDialog(null, "public key is wrong ~!", "error",JOptionPane.WARNING_MESSAGE);
//            return;
//        }
        if(message != null && !message.equals("")){
            ECKey key = ECKey.fromPrivate(ByteUtil.hexString2bytes(prikeystr));
            String signStr = SignMain.signStr(key , message );
            if(signStr != null && signStr.length() > 0) {
                this.signature.setText(signStr);
                JOptionPane.showMessageDialog(null, "sign successful ~!", "info",JOptionPane.WARNING_MESSAGE);
            }else {
                JOptionPane.showMessageDialog(null, "sign failed ~!  Please try again", "error",JOptionPane.WARNING_MESSAGE);
            }
        }else {
            JOptionPane.showMessageDialog(null, "sign string can not be null ~!", "error",JOptionPane.WARNING_MESSAGE);
        }

    }

    //生成公私钥
    private void generateKey() {
        try{
        Mnemonic mn = new Mnemonic("","");
        String priKeyStr = ByteUtil.bytes2hex(mn.getxPrivKey());
//        System.out.println("私钥" + priKeyStr);

        String pubStr = DSA.encryptBASE64(mn.getPubKey());
//        System.out.println("公钥" + pubStr);

        this.prikey.setText(priKeyStr);
//        this.pubkey.setText(pubStr);
        this.signature.setText("");

        }catch (Exception e) {

        }
    }

}