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
			
			//49.7
			System.setProperty("java.security.krb5.conf",
					  "/home/amansjain/Downloads/keytab/49.7/krb5.conf");
		    Configuration conf = new Configuration(false);
			conf.set("hadoop.security.authentication", "kerberos");
			conf.set("hadoop.security.authorization", "true");
			conf.set("dfs.namenode.kerberos.principal", "nn/_HOST@IMPETUS");
		    conf.set("fs.defaultFS", "hdfs://nameservice");
		    conf.set("fs.default.name", conf.get("fs.defaultFS"));
		    conf.set("dfs.nameservices","nameservice");
		    conf.set("dfs.ha.namenodes.nameservice", "nn1,nn2");
		    //conf.set("privacy","");
		    conf.set("dfs.namenode.rpc-address.nameservice.nn1","impetus-i0321.impetus.co.in:8020");
		    conf.set("dfs.namenode.rpc-address.nameservice.nn2", "impetus-i0322.impetus.co.in:8020");
		    conf.set("dfs.client.failover.proxy.provider.nameservice","org.apache.hadoop.hdfs.server.namenode.ha.ConfiguredFailoverProxyProvider");

/*		    FileSystem fs =  FileSystem.get(URI.create(args[0]), conf);
		    Path srcPath = new Path(args[1]);
		    Path dstPath = new Path(args[0]);
		    //in case the same file exists on remote location, it will be overwritten
		    fs.copyFromLocalFile(false, true, srcPath, dstPath);
		    
/*		    //49.37
		    System.setProperty("java.security.krb5.conf",
					  "/home/amansjain/Downloads/keytab/49.37/krb5.conf");
		    Configuration conf = new Configuration();
			conf.set("hadoop.security.authentication", "kerberos");
			conf.set("hadoop.security.authorization", "true");
			conf.set("dfs.namenode.kerberos.principal", "hdfs/_HOST@IMPETUS");
		//	conf.set("kerberos.hive.metastore.kerberos.principal",");"
			conf.set("hadoop.rpc.protection", "privacy");
			conf.set("dfs.encrypt.data.transfer", "true");
		    conf.set("fs.defaultFS", "hdfs://nameservice1");
//		    conf.set("fs.default.name", conf.get("fs.defaultFS"));
		    conf.set("dfs.nameservices","nameservice1");
		    conf.set("dfs.ha.namenodes.nameservice1", "namenode63,namenode128");
		    conf.set("dfs.namenode.rpc-address.nameservice1.namenode63","impetus-i0326.impetus.co.in:8020");
		    conf.set("dfs.namenode.rpc-address.nameservice1.namenode128", "impetus-i0328.impetus.co.in:8020");
		    conf.set("dfs.client.failover.proxy.provider.nameservice1","org.apache.hadoop.hdfs.server.namenode.ha.ConfiguredFailoverProxyProvider");
    	 //   conf.set("fs.hdfs.impl", "org.apache.hadoop.hdfs.DistributedFileSystem");
*/		    
		    UserGroupInformation.setConfiguration(conf);
			UserGroupInformation.loginUserFromKeytab("sax",
					"/home/amansjain/Downloads/keytab/49.7/sax.service.keytab");

			UserGroupInformation
					.setLoginUser(UserGroupInformation.getCurrentUser());

			FileSystem	fs = FileSystem.get(conf);

	
			Path src = new Path("/home/amansjain/Downloads/sax/sax-automation-saxsanity-07112017/saxsanity/src/test/resources/data/pub");
			Path dest = new Path("/automation/SparkBatch_TC20");
				 
			fs.copyFromLocalFile(src, dest);
			
			FileStatus[] fsStatus = fs.listStatus(new Path("/automation/"));
			for (int i = 0; i < fsStatus.length; i++) {
				System.out.println(fsStatus[i].getPath().toString());
			}
		}
}
