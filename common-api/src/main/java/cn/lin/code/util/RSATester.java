package cn.lin.code.util;

import java.util.Map;

public class RSATester {

	static String publicKey;
	static String privateKey;

	static {
		try {
			Map<String, Object> keyMap = RSAUtils.genKeyPairMap();
			publicKey = RSAUtils.getPublicKey(keyMap);
			privateKey = RSAUtils.getPrivateKey(keyMap);
			System.err.println("公钥: \n\r" + publicKey);
			System.err.println("私钥： \n\r" + privateKey);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws Exception {

		long t = System.currentTimeMillis();
		test();
//		 testSign();
		System.out.println(System.currentTimeMillis() - t);
	}

	static void test() throws Exception {

		System.err.println("公钥加密——私钥解密");
		String source = "这是一行没有任何意义的文字，你看完了等于没看，不是吗？";
		System.out.println("\r加密前文字：\r\n" + source);
		byte[] data = source.getBytes();
		byte[] encodedData = RSAUtils.encryptByPublicKey(data, publicKey);
		
		String retData = Base64Utils.encode(encodedData);
		System.out.println("加密后文字：\r\n" + retData);

//		String enStr = new String(encodedData);
//		String deStr = new String(RSAUtils.decryptByPrivateKey(enStr.getBytes(), privateKey));
//		System.out.println(deStr);

		byte[] decode = Base64Utils.decode(retData);
		 byte[] decodedData = RSAUtils.decryptByPrivateKey(decode, privateKey);
		 String target = new String(decodedData);
		 System.out.println("解密后文字: \r\n" + target);
	}

	public static String bytesToString(byte[] encrytpByte) {
	       String result = "";
	       for (Byte bytes : encrytpByte) {
	           result += bytes.toString() + " ";
	       }
	       return result;
	}
	
	static void testSign() throws Exception {

		System.err.println("私钥加密——公钥解密");
		String source = "这是一行测试RSA数字签名的无意义文字";
		System.out.println("原文字：\r\n" + source);
		byte[] data = source.getBytes();
		byte[] encodedData = RSAUtils.encryptByPrivateKey(data, privateKey);
		System.out.println("加密后：\r\n" + new String(encodedData));
		byte[] decodedData = RSAUtils.decryptByPublicKey(encodedData, publicKey);
		String target = new String(decodedData);
		System.out.println("解密后: \r\n" + target);
		System.err.println("私钥签名——公钥验证签名");
		String sign = RSAUtils.sign(encodedData, privateKey);
		System.err.println("签名:\r" + sign);
		boolean status = RSAUtils.verify(encodedData, publicKey, sign);
		System.err.println("验证结果:\r" + status);
	}

}
