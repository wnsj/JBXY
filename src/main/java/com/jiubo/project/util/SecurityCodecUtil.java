package com.jiubo.project.util;



import com.jiubo.project.common.Constant;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.lang.StringUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.*;


/**
 * 安全方面的Util，主要是提供常用的加解密算法
 */
public class SecurityCodecUtil {

    private static final String hexDigits[] = {"0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};

    /**
     * sha256WithRsa 算法请求类型
     */
    public static final String SIGN_ALGORITHMS = "SHA1WithRSA";
    public static final String SIGN_TYPE_RSA = "RSA";
    public static final String SIGN_TYPE_AES = "AES";
    public static final String SIGN_TYPE_AES_CBC_PKCS7 = "AES/CBC/PKCS7Padding";

    public static final String SIGN_TYPE_RSA2 = "RSA2";
    public static final String SIGN_TYPE_MD5 = "MD5";
    public static final String SIGN_SHA256RSA_ALGORITHMS = "SHA256WithRSA";

    private static int MAX_CONTEXT_LENHTH = 10 * 64 * 1024;

    //DES加解密处理
    private final static String DES = "DES";

    /**
     * Description 根据键值进行加密
     *
     * @param data
     * @param key  加密键byte数组
     * @return
     * @throws Exception
     */
    private static byte[] encrypt(byte[] data, byte[] key) throws Exception {
        // 生成一个可信任的随机数源
        SecureRandom sr = new SecureRandom();
        // 从原始密钥数据创建DESKeySpec对象
        DESKeySpec dks = new DESKeySpec(key);

        // 创建一个密钥工厂，然后用它把DESKeySpec转换成SecretKey对象
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
        SecretKey securekey = keyFactory.generateSecret(dks);

        // Cipher对象实际完成加密操作
        Cipher cipher = Cipher.getInstance(DES);

        // 用密钥初始化Cipher对象
        cipher.init(Cipher.ENCRYPT_MODE, securekey, sr);
        return cipher.doFinal(data);

    }

    /**
     * Description 根据键值进行解密
     *
     * @param data
     * @param key  加密键byte数组
     * @return
     * @throws Exception
     */
    private static byte[] decrypt(byte[] data, byte[] key) throws Exception {
        // 生成一个可信任的随机数源
        SecureRandom sr = new SecureRandom();
        // 从原始密钥数据创建DESKeySpec对象
        DESKeySpec dks = new DESKeySpec(key);
        // 创建一个密钥工厂，然后用它把DESKeySpec转换成SecretKey对象
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
        SecretKey securekey = keyFactory.generateSecret(dks);

        // Cipher对象实际完成解密操作
        Cipher cipher = Cipher.getInstance(DES);
        // 用密钥初始化Cipher对象
        cipher.init(Cipher.DECRYPT_MODE, securekey, sr);
        return cipher.doFinal(data);
    }

    /**
     * Description DESC根据键值进行加密
     *
     * @param data
     * @param key  加密键byte数组
     * @return
     * @throws Exception
     */
    public static String encryptDESC(String data, String key) throws Exception {
        try {
            //if (UpgCommonUtil.getKeySwitch()) {
            byte[] bt = encrypt(data.getBytes(), key.getBytes());
            String strs = org.apache.commons.codec.binary.Base64.encodeBase64String(bt);
            return strs;
            //}
            //return data;
        } catch (Exception e) {
            System.out.println("e:" + e);
        }
        return data;
    }

    /**
     * Description DESC根据键值进行加密
     *
     * @param data
     * @param key  加密键byte数组
     * @return
     * @throws Exception
     */
    public static String encryptDESC(String data, String key, String charSet) throws Exception {
        try {
            //if (UpgCommonUtil.getKeySwitch()) {
            byte[] bt = encrypt(data.getBytes(charSet), key.getBytes());
            String strs = org.apache.commons.codec.binary.Base64.encodeBase64String(bt);
            return strs;
            //}
            //return data;
        } catch (Exception e) {
            System.out.println("e:" + e);
        }
        return data;
    }

    /**
     * Description DESC根据键值进行解密
     *
     * @param data
     * @param key  加密键byte数组
     * @return
     * @throws IOException
     * @throws Exception
     */
    public static String decryptDESC(String data, String key, String charSet) throws IOException, Exception {
        try {
            if (data == null)
                return null;
            //  if (UpgCommonUtil.getKeySwitch()) {
            byte[] buf = org.apache.commons.codec.binary.Base64.decodeBase64(data);
            byte[] bt = decrypt(buf, key.getBytes());
            return new String(bt, charSet);
            //	}
            //  return data;
        } catch (Exception e) {
            System.out.println("e:" + e);
        }
        return data;
    }

    /**
     * Description DESC根据键值进行解密
     *
     * @param data
     * @param key  加密键byte数组
     * @return
     * @throws IOException
     * @throws Exception
     */
    public static String decryptDESC(String data, String key) throws IOException, Exception {
        try {
            if (data == null)
                return null;
            //  if (UpgCommonUtil.getKeySwitch()) {
            byte[] buf = org.apache.commons.codec.binary.Base64.decodeBase64(data);
            byte[] bt = decrypt(buf, key.getBytes());
            return new String(bt);
            //	}
            //  return data;
        } catch (Exception e) {
            System.out.println("e:" + e);
        }
        return data;
    }

    public static String getSha1(String str) {
        if (str == null || str.length() == 0) {
            return null;
        }
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        try {
            MessageDigest mdTemp = MessageDigest.getInstance("SHA1");
            mdTemp.update(str.getBytes());

            byte[] md = mdTemp.digest();
            int j = md.length;
            char buf[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                buf[k++] = hexDigits[byte0 >>> 4 & 0xf];
                buf[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(buf);
        } catch (Exception e) {
            return null;
        }
    }

    //随机生成RSA密钥对（公钥和私钥都是16进制的字符串）
    public static Map<String, Object> generateRSAKey() throws Exception {
        Map<String, Object> keyMap = null;
        try {
            keyMap = new HashMap<String, Object>();
            KeyPairGenerator caKeyPairGen = KeyPairGenerator.getInstance(SIGN_TYPE_RSA);
            caKeyPairGen.initialize(1024, new SecureRandom());
            KeyPair keypair = caKeyPairGen.genKeyPair();

            String privateKey = encodeByte2HexString(keypair.getPrivate().getEncoded());
            String publicKey = encodeByte2HexString(keypair.getPublic().getEncoded());

            System.out.println("privateKey：" + privateKey);
            System.out.println("publicKey：" + publicKey);

            keyMap.put("privateKey", privateKey);
            keyMap.put("publicKey", publicKey);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return keyMap;
    }

    /**
     * 将字节数组转换成16进制的字符串
     *
     * @param bytes
     * @return
     */
    public static String encodeByte2HexString(byte[] bytes) {
        if (bytes == null || bytes.length <= 0) {
            return null;
        }
        try {
            return new String(Hex.encodeHex(bytes));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 将16进制的字符串转换成字节数组
     *
     * @param hexStr
     * @return
     */
    public static byte[] decodeHexString2Byte(String hexStr) {
        if (hexStr == null || hexStr.length() <= 0) {
            return null;
        }
        try {
            return Hex.decodeHex(hexStr.toCharArray());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public static void main(String[] args) throws Exception {
        //SecurityCodecUtil.generateRSAKey();
        String str = "abc123456";
        String pubKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCO1ZGYnr44Z0IdvUGMYaUFEbQA+toaVYL6XecyX5VErEF4vRxhwJRbhLx2y9mZSaYeRZdZqssunTFxdDqO9v+TGhd2F8opRpw/U3nNtlI5ZbBto7LYCTIPzi7KNAL+ugOv0H9OyEechg1WId5lq1oY5msiJBAAIj37OLb23a23KwIDAQAB";
        String priKey = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAI7VkZievjhnQh29QYxhpQURtAD62hpVgvpd5zJflUSsQXi9HGHAlFuEvHbL2ZlJph5Fl1mqyy6dMXF0Oo72/5MaF3YXyilGnD9Tec22UjllsG2jstgJMg/OLso0Av66A6/Qf07IR5yGDVYh3mWrWhjmayIkEAAiPfs4tvbdrbcrAgMBAAECgYAC+Hgdt3d3TtZ2LeB2HfXDpDmdJ5pvn8WolVlgl9JP6lE2RKCZe9raCGmvAtBUus5kpMfgCohNQOGtXWB2Zrar/T+ZIF8R1tj8OE21QCba27qCCyVA3TLw/ezJJ96SdPBI9X6LuTanLMAbj81OAapj5T/7gX08ExXzXoRKcoQdsQJBAP6nAdOXWf9/J3TSHyWgnxR1lV8zCAh9enDUVBV4fFJqekbOMe/s79nraPs2AiUFDEmvJzoa+GxBMHXmZQch8okCQQCPlxMrLnNC06R+UP4KMbHfh4TAl9oPZFkZZ2fo4BgwnYxaRCK2ecekkQ7JtiK7aTqCFi4ZtmE9xv1ihsNFcT8TAkAvtizCby4ej71dxuytCR4lIC6/anlL1e616yJh73QjoO2ODJ4QnO0HgHLn9mOKehqmR5nq6hi3d7NTs7okDvk5AkA2DJsFjRWL2Ri5B4mokq4uwtdAm6hUVOSJAHiHmxrf6d3z+GPMpih1FLkpSmrmeqSwZOLzgMg3tdiZJrsIP6mLAkAjmy3ZZh/P8EoX3+4s52CqbhlRet65xSSJhgLzfB8YtmvmMGQSCwlpYz5PWXh6xRZpYZ10RYmFcdjBTUfNsagu";
        str = rsaEncrypt(str, pubKey, Constant.Charset.UTF8);
        System.out.println("str:" + str);
        str = rsaDecrypt(str, priKey, Constant.Charset.UTF8);
        System.out.println("str:" + str);
    }


    public static String signRSA2(String content, String privateKey, String charset) throws Exception {
        try {
            PrivateKey priKey = getPrivateKeyFromPKCS8(SIGN_TYPE_RSA, privateKey);
            Signature signature = Signature.getInstance(SIGN_SHA256RSA_ALGORITHMS);
            signature.initSign(priKey);
            if (StringUtils.isBlank(charset)) {
                signature.update(content.getBytes());
            } else {
                signature.update(content.getBytes(charset));
            }

            byte[] signed = signature.sign();
            return Base64.encodeBase64String(signed);
        } catch (Exception e) {
            throw e;
        }
    }

    public static boolean verifyRSA2(String content, String sign, String publicKey, String charset) throws Exception {
        try {
            KeyFactory keyFactory = KeyFactory.getInstance(SIGN_TYPE_RSA);
            byte[] encodedKey = Base64.encodeBase64(publicKey.getBytes(charset));
            PublicKey pubKey = keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));
            Signature signature = Signature.getInstance(SIGN_SHA256RSA_ALGORITHMS);
            signature.initVerify(pubKey);
            if (StringUtils.isEmpty(charset)) {
                signature.update(content.getBytes());
            } else {
                signature.update(content.getBytes(charset));
            }
            return signature.verify(Base64.decodeBase64(sign.getBytes()));
        } catch (Exception e) {
            throw e;
        }
    }

    //RSA加签
    public static String signRSA(String content, String privateKey, String input_charset) throws Exception {
        try {
            PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(Base64.decodeBase64(privateKey));
            KeyFactory keyf = KeyFactory.getInstance(SIGN_TYPE_RSA);
            PrivateKey priKey = keyf.generatePrivate(priPKCS8);

            Signature signature = Signature.getInstance(SIGN_ALGORITHMS);

            signature.initSign(priKey);
            signature.update(content.getBytes(input_charset));

            byte[] signed = signature.sign();
            return new String(Base64.encodeBase64(signed), input_charset);

        } catch (Exception e) {
            throw e;
        }

    }

    /**
     * RSA验签名检查
     *
     * @param content        待签名数据
     * @param sign           签名值
     * @param ali_public_key 支付宝公钥
     * @param input_charset  编码格式
     * @return 布尔值
     */
    public static boolean verifyRSA(String content, String sign, String ali_public_key, String input_charset) throws Exception {
        try {
            KeyFactory keyFactory = KeyFactory.getInstance(SIGN_TYPE_RSA);
            byte[] encodedKey = Base64.decodeBase64(ali_public_key);
            PublicKey pubKey = keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));

            Signature signature = Signature.getInstance(SIGN_ALGORITHMS);

            signature.initVerify(pubKey);
            signature.update(content.getBytes(input_charset));

            boolean bverify = signature.verify(Base64.decodeBase64(sign));
            return bverify;

        } catch (Exception e) {
            throw e;
        }

    }

    public static String getNonceStr() {
        Random random = new Random();
        String resultString = null;
        try {
            resultString = new String(String.valueOf(random.nextInt(10000)));
            MessageDigest md = MessageDigest.getInstance(SIGN_TYPE_MD5);
            resultString = byteArrayToHexString(md.digest(resultString
                    .getBytes(Constant.Charset.UTF8)));
        } catch (Exception exception) {
        }

        return resultString;

    }


    private static String byteArrayToHexString(byte b[]) {
        StringBuffer resultSb = new StringBuffer();
        for (int i = 0; i < b.length; i++)
            resultSb.append(byteToHexString(b[i]));

        return resultSb.toString();
    }

    private static String byteToHexString(byte b) {
        int n = b;
        if (n < 0)
            n += 256;
        int d1 = n / 16;
        int d2 = n % 16;
        return hexDigits[d1] + hexDigits[d2];
    }


    private static PrivateKey getPrivateKeyFromPKCS8(String algorithm, String key) throws Exception {

        KeyFactory keyFactory = KeyFactory.getInstance(algorithm);

        byte[] encodedKey = key.getBytes();

        encodedKey = Base64.decodeBase64(encodedKey);

        return keyFactory.generatePrivate(new PKCS8EncodedKeySpec(encodedKey));
    }


    public static String getAPSignContent(Map sortedParams) {
        StringBuffer content = new StringBuffer();
        List<String> keys = new ArrayList<String>(sortedParams.keySet());
        Collections.sort(keys);
        int index = 0;
        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = String.valueOf(sortedParams.get(key));
            if (!(StringUtils.isEmpty(key) || StringUtils.isEmpty(value))) {
                content.append((index == 0 ? "" : "&") + key + "=" + value);
                index++;
            }
        }
        return content.toString();
    }

    /**
     * MAP类型数组转换成NameValuePair类型
     *
     * @param properties MAP类型数组
     * @return NameValuePair类型数组
     */
    private static NameValuePair[] generatNameValuePair(Map<String, String> properties) {
        NameValuePair[] nameValuePair = new NameValuePair[properties.size()];
        int i = 0;
        for (Map.Entry<String, String> entry : properties.entrySet()) {
            nameValuePair[i++] = new NameValuePair(entry.getKey(), entry.getValue());
        }

        return nameValuePair;
    }


    public static String getWXSign(Map<String, String> requestMap, String partnetKey, String charser) throws Exception {
        //排序处理
        String prestr = getWXSignValue(requestMap);

        //追加密钥
        String SignTemp = prestr + "&key=" + partnetKey;

        //签名
        String mysign = MD5Sign(SignTemp, "", charser).toUpperCase();

        return mysign;
    }

    /**
     * 获取二进制的报文体，并转换成字符串
     */
    public static String getContext(HttpServletRequest request) throws IOException {
        byte[] buffer = new byte[MAX_CONTEXT_LENHTH];

        InputStream in = request.getInputStream();
        int length = in.read(buffer);
        if (length <= 0) {
            throw new IOException("请求报文为空，系统无法处理");
        }
        String encode = request.getCharacterEncoding();

        byte[] data = new byte[length];
        System.arraycopy(buffer, 0, data, 0, length);
        String context = new String(data, encode);

        return context;
    }


    //排序处理 设所有发送或者接收到的数据为集合M，将集合M内非空参数值的参数按照参数名ASCII码从小到大排序（字典序），使用URL键值对的格式（即key1=value1&key2=value2…）拼接成字符串stringA
    public static String getWXSignValue(Map<String, String> unsort_map) throws Exception {
        // TreeMap<String, String> result = new TreeMap<String, String>();
        Object[] unsort_key = unsort_map.keySet().toArray();
        Arrays.sort(unsort_key);
        StringBuffer sf = new StringBuffer();
        for (int i = 0; i < unsort_key.length; i++) {
            if (unsort_map.get(unsort_key[i]) != null && !unsort_map.get(unsort_key[i]).trim().equals("")) {
                if (i > 0) {
                    sf.append("&");
                }
                sf.append(unsort_key[i].toString()).append("=");

                sf.append(unsort_map.get(unsort_key[i]));
            }
            // result.put(unsort_key[i].toString(), unsort_map.get(unsort_key[i]));
        }
        //return result.tailMap(result.firstKey());
        return sf.toString();
    }

    /**
     * 签名字符串
     *
     * @param text          需要签名的字符串
     * @param key           密钥
     * @param input_charset 编码格式
     * @return 签名结果
     */
    public static String MD5Sign(String text, String key, String input_charset) {
        text = text + key;
        return DigestUtils.md5Hex(getContentBytes(text, input_charset));
    }

    /**
     * MD5验签
     */
    public static boolean verifyMD5(String context, String sign, String key, String input_charset) {

        //追加密钥
        context = context + "&key=" + key;

        return sign.equalsIgnoreCase(DigestUtils.md5Hex(getContentBytes(context, input_charset)));

    }

    /**
     * @param content
     * @param charset
     * @return
     */
    private static byte[] getContentBytes(String content, String charset) {
        if (charset == null || "".equals(charset)) {
            return content.getBytes();
        }
        try {
            return content.getBytes(charset);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("MD5签名过程中出现错误,指定的编码集不对,您目前指定的编码集是:" + charset);
        }
    }


    /**
     * AES128_ECB_PKCS7Padding加密，返回16进制形式的字符串
     * @param data
     * @param strKey
     * @return
     * @throws Exception
     */
    /**
     * AES解密
     *
     * @param content 密文
     * @return
     * @throws InvalidAlgorithmParameterException
     * @throws NoSuchProviderException
     */
    public static String decryptAES128_CBC_PKCS7Padding(String content, String keyByte, String ivByte) throws Exception {
        try {
            Security.addProvider(new BouncyCastleProvider());
            Cipher cipher = Cipher.getInstance(SIGN_TYPE_AES_CBC_PKCS7);
            Key sKeySpec = new SecretKeySpec(Base64.decodeBase64(keyByte), SIGN_TYPE_AES);
            AlgorithmParameters params = AlgorithmParameters.getInstance(SIGN_TYPE_AES);
            params.init(new IvParameterSpec(Base64.decodeBase64(ivByte)));
            cipher.init(Cipher.DECRYPT_MODE, sKeySpec, params);// 初始化
            byte[] result = cipher.doFinal(Base64.decodeBase64(content));
            return new String(WxPKCS7decode(result), Constant.Charset.UTF8);
        } catch (Exception e) {
            throw e;
        }
    }

    private static byte[] WxPKCS7decode(byte[] decrypted) {
        int pad = decrypted[decrypted.length - 1];
        if (pad < 1 || pad > 32) {
            pad = 0;
        }
        return Arrays.copyOfRange(decrypted, 0, decrypted.length - pad);
    }


    /**
     * @Description:
     * @param:
     * @return:
     * @author Gaodongdong
     * @createDate: 2018-11-14 15:15
     * @modifyDate: 2018-11-14 15:15
     */
    public static String encryptBase64(String data, String charset) {
        String encryptStr = null;
        try {
            encryptStr = org.apache.commons.codec.binary.Base64.encodeBase64String(data.getBytes(charset));
        } catch (Exception e) {

        }
        return encryptStr;
    }

    /**
     * @Description:
     * @param:
     * @return:
     * @author Gaodongdong
     * @createDate: 2018-11-14 15:15
     * @modifyDate: 2018-11-14 15:15
     */
    public static String decryptBase64(String encryptdata, String charset) {
        String decryptStr = null;
        try {
            decryptStr = new String(org.apache.commons.codec.binary.Base64.decodeBase64(encryptdata.getBytes(charset)), charset);
        } catch (Exception e) {

        }
        return decryptStr;
    }

    /**
     * RSA公钥加密
     *
     * @param str       加密字符串
     * @param publicKey 公钥
     * @return 密文
     * @throws Exception 加密过程中的异常信息
     */
    public static String rsaEncrypt(String str, String publicKey, String charset) throws Exception {
        //base64编码的公钥
        byte[] decoded = Base64.decodeBase64(publicKey);
        RSAPublicKey pubKey = (RSAPublicKey) KeyFactory.getInstance(SIGN_TYPE_RSA).generatePublic(new X509EncodedKeySpec(decoded));
        //RSA加密
        Cipher cipher = Cipher.getInstance(SIGN_TYPE_RSA);
        cipher.init(Cipher.ENCRYPT_MODE, pubKey);
        String outStr = Base64.encodeBase64String(cipher.doFinal(str.getBytes(charset)));
        return outStr;
    }

    /**
     * RSA私钥解密
     *
     * @param str        加密字符串
     * @param privateKey 私钥
     * @return 铭文
     * @throws Exception 解密过程中的异常信息
     */
    public static String rsaDecrypt(String str, String privateKey, String charset) throws Exception {
        //64位解码加密后的字符串
        byte[] inputByte = Base64.decodeBase64(str.getBytes(charset));
        //base64编码的私钥
        byte[] decoded = Base64.decodeBase64(privateKey);
        RSAPrivateKey priKey = (RSAPrivateKey) KeyFactory.getInstance(SIGN_TYPE_RSA).generatePrivate(new PKCS8EncodedKeySpec(decoded));
        //RSA解密
        Cipher cipher = Cipher.getInstance(SIGN_TYPE_RSA);
        cipher.init(Cipher.DECRYPT_MODE, priKey);
        String outStr = new String(cipher.doFinal(inputByte));
        return outStr;
    }
}

