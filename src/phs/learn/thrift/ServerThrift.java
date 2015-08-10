package phs.learn.thrift;

import org.apache.thrift.TException;

public class ServerThrift {
	public static final String PHS_LEARN_THRIFT = "phs.learn.thrift";

	public static class QueryHandler implements YellowBook.Iface {

		@Override
		public int getAge(Person p) throws TException {
			if ( p == null ) return -1;
			return p.getAge();
		}

	}

	static public final int SERVER_PORT = 17061;

	public static class CPUHandler implements phs.learn.thrift.YellowBook.Iface {

		@Override
		public int getAge(Person p) throws TException {
			if ( p == null ) return -1;
			return p.getAge();
		}

	}

	public static void main(String[] args) {
//		try {
//			YellowBook.Iface handler = new CPUHandler();
//			TServerTransport serverTransport = new TServerSocket(SERVER_PORT);
//			TProcessor processor = new YellowBook.Processor<Iface>(handler);
//			TServer server  = new TThreadPoolServer(new TThreadPoolServer.Args(serverTransport).processor(processor));
//			Logger.getDefaultLogger().logInfo("Service started at port: " + SERVER_PORT + " at " + Converters.toHumanReadableTime(System.currentTimeMillis()));
//			server.serve();
//		} catch (TTransportException e) {
//			e.printStackTrace();
//		}
		
		ads.common.rpc.Rpc.register(PHS_LEARN_THRIFT,new QueryHandler());
		ads.common.rpc.Rpc.startServer(SERVER_PORT);
	}
}
