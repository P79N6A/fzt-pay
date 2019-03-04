
package cn.yesway.pay.order.rsa;

/**
 * @author GHK
 *  2017年2月10日下午2:51:56
 *  RSA测试方法
 */
public class TestMyRSA {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		
		
		/*String ming1="我的名字";
		String mi = null;
		try {
			//私钥加密
			mi = PrivateRSA.getInstance().encryptByPrivateKey(ming1);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String ming2 = null;
		try {
			//公钥解密
			//
			//mi = "c0ClSutaZWqH2yJ10IRojPCob55kcbH1ECvUKBWuYwQx9OcziBvI9fkQP0eaSHy7tMtJRh3887B82zzxyDexZzemhcFsK5Nco+L50JBi1jW8C3hTLYeKbmBWr8V9sQEDpmZjxwUJumMLWx73HyyQrpgA8zi8ZXYiT45dTRWoHnI=";
			ming2=PublicRSA.getInstance().decryptByPublicKey(mi);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(ming2);*/
		
		
		
		String data1="{'request_timestamp':'20170210153218','phone':'123456','bag_size':'30','你好'}";
		String data2 = null;
		
		try {
			//公钥加密
			data2 = PublicRSA.getInstance().encryptByPublicKey(data1);
			System.out.println("data2 : "+data2);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		String data3 = null;
		
		try {
			//私钥解密
			//mi = "K+D0CIDdGq/I1yA9EK2YghaBODcFkQ6WvhONcPGN89uiwQpGpK5JI//GhXNFvD/ttSmpDA0NxhymC2zbiCtYE0ukgcwN7o1Y3ugJFC4mNktMiF3EOwdtUFZaGGDStUGgOVL08588LtRs4BOi8YnUmCIiNUPyelZTyrFbYTUkQG8=bCZyXoObzduEfO4nrtB8R3j/O89EKnOZtkymz+RdY52F323yupBvqNanR2nF8tf387fBaDthhM9TEZPrsOrUS5KRiaeVR0jZb//LcHOhRnh0IzzNDUhRreoPnLl+xCzIjahfJd8bldrR0ciE8suiO8Imcte3vO2LO9UPH/YiJHQ=";
			data3=PrivateRSA.getInstance().decryptByPrivateKey(data2);
		} catch (Exception e) {
//			e.printStackTrace();
		}
		System.out.println(data3); 
		
		
	}

}
