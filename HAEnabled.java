package myclasses;

import java.net.URI;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.security.UserGroupInformation;

public class HAEnabled {


		public static void main(String[] args) throws Exception {
/*		    if (args.length != 2){
		        System.out.println("Usage: pgm <hdfs:///path/to/copy> </local/path/to/copy/from>");
		        System.exit(1);
		    }*/
			
			FileSystem	fs;
			
			String PRINCIPAL = "abc@Realm";
			final String NAME_SERVICE = "nameservice";
			String NAME_NODE_1 = "namenode1";
			String NAME_NODE_2 = "namenode2";
			String NAME_NODE_1_RPC = "namenode1.co.in:8020";
			String NAME_NODE_2_RPC = "namenode2.co.in:8020";

			String loginUser = "abbc";
			
		    System.setProperty("java.security.krb5.conf",
					  "/home/keytabfolder/krb5.conf");
		    Configuration conf = new Configuration();
			conf.set("hadoop.security.authentication", "kerberos");
			conf.set("hadoop.security.authorization", "true");
			conf.set("dfs.namenode.kerberos.principal", PRINCIPAL);
		    conf.set("fs.defaultFS", "hdfs://"+ NAME_SERVICE);
		    conf.set("dfs.nameservices",NAME_SERVICE);
		    conf.set("dfs.ha.namenodes."+NAME_SERVICE, NAME_NODE_1 +"," +NAME_NODE_2);
		    conf.set("dfs.namenode.rpc-address."+NAME_SERVICE+"."+NAME_NODE_1,NAME_NODE_1_RPC);
		    conf.set("dfs.namenode.rpc-address."+NAME_SERVICE+"."+NAME_NODE_2,NAME_NODE_2_RPC);
		    conf.set("dfs.client.failover.proxy.provider."+NAME_SERVICE,"org.apache.hadoop.hdfs.server.namenode.ha.ConfiguredFailoverProxyProvider");
		    conf.set("fs.hdfs.impl", "org.apache.hadoop.hdfs.DistributedFileSystem");
		    
		    //for ssl
			conf.set("hadoop.rpc.protection", "privacy");
			conf.set("dfs.encrypt.data.transfer", "true");   
  	       
		    UserGroupInformation.setConfiguration(conf);
			UserGroupInformation.loginUserFromKeytab(loginUser,
					"/home/keytabfolder/krb5.conf");

			UserGroupInformation
					.setLoginUser(UserGroupInformation.getCurrentUser());

			fs = FileSystem.get(conf);

	
			//Path src = new Path("/home/test/resources/dataFolder");
			//Path dest = new Path("/remoteMachineFolderPath");
			//fs.copyFromLocalFile(src, dest);
			
			FileStatus[] fsStatus = fs.listStatus(new Path("/remoteMachineFolderPath/"));
			for (int i = 0; i < fsStatus.length; i++) {
				System.out.println(fsStatus[i].getPath().toString());
			}
		}
}
