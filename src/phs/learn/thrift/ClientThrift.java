package phs.learn.thrift;

import org.apache.thrift.TException;

public class ClientThrift {
	private static final String LOCAL_HOST = "localhost";

	public static void main(String[] args) {
//		TTransport transport = new TSocket(LOCAL_HOST,ServerThrift.SERVER_PORT);
//		try {
//			transport.open();
//			TProtocol protocol = new TBinaryProtocol(transport);
//			YellowBook.Client client = new YellowBook.Client(protocol);
//			System.out.println("Age = " + client.getAge(new Person(31, "Son")));
//			System.out.println("Age = " + client.getAge(null));
//		} catch (TException e) {
//			e.printStackTrace();			
//		} finally {
//			try { transport.close(); } catch (Exception e) {}
//		}
		YellowBook.Iface service = ads.common.rpc.Rpc.connectService(
				ServerThrift.PHS_LEARN_THRIFT,
				LOCAL_HOST, 
				ServerThrift.SERVER_PORT, 
				0, YellowBook.Iface.class);
		try {
			System.out.println(service.getAge(new Person(31, "Son")));
			System.out.println(service.getAge(null));
		} catch (TException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
