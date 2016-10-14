import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import com.sap.conn.jco.JCoContext;
import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoDestinationManager;
import com.sap.conn.jco.JCoException;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoMetaData;
import com.sap.conn.jco.JCoParameterList;
import com.sap.conn.jco.JCoStructure;
import com.sap.conn.jco.JCoTable;
import com.sap.conn.jco.ext.DestinationDataProvider;


public class JCO3 {

	public static JCoDestination destination;
	
	  private static class JCO3Singelton {    
	       private static final JCO3 INSTANCE = new JCO3(); 
	        
	    }    
	    private JCO3 (){
	    	//System.out.println("鍙皟鐢ㄤ竴娆�“);
	    	System.out.println("你");
	    	getJCoDestination("sapConnection","jcoDestination","****","31","100","SBI","SwireSBI1234","ZH");
	    }    
	    
	    public static final JCO3 getInstance() {    
	       return JCO3Singelton.INSTANCE;    
	    }
	
	
	private static boolean isFileExist(String filename, String stuffix) {
		File file = new File(filename + "." + stuffix);
		if (file.exists()) {
			try {
				System.out.println(""+file.getCanonicalPath());
			} catch (IOException e) {

				e.printStackTrace();
			}
			return true;
		} else {
			return false;
		}

	}  
	/**
	 * 鍓靛缓鏂版枃浠�
	 * 
	 * @param filename
	 *            鏂囦欢鍚�
	 * @param suffix
	 *            寰岀洞鍚�
	 * @param pros
	 *            Properties
	 * @return
	 */
	private static boolean createFiles(String filename, String suffix,
			Properties pros) {
		File file = new File(filename + "." + suffix);
		FileOutputStream fos = null;
		if (!file.exists()) {
			try {
				System.out.println("********* 姝ｅ湪瀵叆鏂囦欢 **********");
				fos = new FileOutputStream(file, false);

				pros.store(fos, "Connection Parmeter");
				fos.close();
				return true;

			} catch (FileNotFoundException e) {
				System.out.println("-------- 鎵句笉鍒版枃浠讹紒 ---------");
				e.printStackTrace();
				return false;
			} catch (IOException e) {
				System.out.println("-------- 鍏у瀵叆澶辨晽锛�---------");
				e.printStackTrace();
				return false;
			} finally {
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		} else {
			return false;
		}

	}

	/**
	 * 鍒涘缓灞炴�鏂囦欢
	 * 
	 * @param filename
	 *            鏂囦欢鍚�
	 * @param stuffix
	 *            寰岀洞鍚�
	 * @param host
	 *            涓绘鍦板潃
	 * @param sysnr
	 *            绯荤当ID
	 * @param client
	 *            瀹㈡埗绔�
	 * @param user
	 *            鐢ㄦ埗鍚�
	 * @param pass
	 *            瀵嗙⒓
	 * @param lang
	 *            瑾炶█
	 * @param pool_capacity  鏈�ぇ绌洪枓閫ｆ帴
	 * @param peak_limit 鏈�ぇ娲诲嫊閺堟帴鏁�
	 * @return
	 */
	public static boolean createSapPros(String filename, String stuffix,
			String host, String sysnr, String client, String user, String pass,
			String lang, String pool_capacity, String peak_limit) {
		Properties pros = new Properties();
		boolean iscreate = false;
		
		pros.clear(); // 鍏堟竻绌�
		pros.setProperty(DestinationDataProvider.JCO_ASHOST, host);
		pros.setProperty(DestinationDataProvider.JCO_SYSNR, sysnr);
		pros.setProperty(DestinationDataProvider.JCO_CLIENT, client);
		pros.setProperty(DestinationDataProvider.JCO_USER, user);
		pros.setProperty(DestinationDataProvider.JCO_PASSWD, pass);
		pros.setProperty(DestinationDataProvider.JCO_LANG, lang);
	      // JCO_POOL_CAPACITY - 绌洪棽杩炴帴鏁帮紝濡傛灉涓�锛屽垯娌℃湁杩炴帴姹犳晥鏋滐紝榛樿涓�
		pros.setProperty(DestinationDataProvider.JCO_POOL_CAPACITY,
				pool_capacity);
		 // JCO_PEAK_LIMIT - 鍚屾椂鍙垱寤虹殑鏈�ぇ娲诲姩杩炴帴鏁帮紝0琛ㄧず鏃犻檺鍒讹紝榛樿涓篔CO_POOL_CAPACITY鐨勫�
	     // 濡傛灉灏忎簬JCO_POOL_CAPACITY鐨勫�锛屽垯鑷姩璁剧疆涓鸿鍊硷紝鍦ㄦ病鏈夎缃甁CO_POOL_CAPACITY鐨勬儏鍐典笅涓�
		pros.setProperty(DestinationDataProvider.JCO_PEAK_LIMIT, peak_limit);

		iscreate = createFiles(filename, stuffix, pros);
		if (iscreate) {
			return true;
		} else {
			return false;
		}
	}


	 public  void getJCoDestination(String filename,
				String stuffix, String host, String sysnr, String client,
				String user, String pass, String lang) {

			boolean isexist = false; // 鍒ゆ柇鏂囦欢鏄惁瀛樺湪
			boolean isCreate = false; // 绌夸欢pro鏂囦欢
			//JCoDestination destination;

			isexist = isFileExist(filename, stuffix);
			if (isexist == true) {
				System.out.println("-------- 鏂囦欢宸茬稉瀛樺湪 -----------");
				try {
					destination = JCoDestinationManager.getDestination(filename);
				} catch (JCoException e) {
					System.out
							.println("---------- 鐛插彇JCoDestination澶辨晽! -----------");
					e.printStackTrace();
				}

			} else {
				isCreate = createSapPros(filename, stuffix, host, sysnr,
						client, user, pass, lang, "20", "10");
				if (isCreate == true) {
					try {
						destination = JCoDestinationManager
								.getDestination(filename);
						System.out
								.println("---------- 鐛插彇JCoDestination!鎴愬姛锛屾鍦ㄨ繑鍥炴暩鎿�-----------");
						
					} catch (JCoException e) {
						System.out
								.println("---------- 鐒℃硶鐛插彇JCoDestination! -----------");
						e.printStackTrace();

					}

				} 
			}

		}

	public JCoParameterList callSapRFC(String funName, Map<String, String> parmMap) throws Exception {
		JCoFunction function = null;
		JCoParameterList tmlist = null;
		try {
			function = destination.getRepository().getFunction(funName);
			
//			if (function == null){
//				System.out.println("function is null");
//			} else
//			{
//				System.out.println("function is :"+function.toString());
//			}
			
		} catch (JCoException e) {
			System.out.println("鏃犳硶鑾峰彇Function");
			e.printStackTrace();
		}

		try {
          
			//JCoParameterList imlist = function.getImportParameterList();
            JCoParameterList imlist = function.getTableParameterList();
			 if (imlist != null) {  
				             System.out.println("Function Import Structure : " + imlist.toString());  
				        }  else{
				        	
				        	 System.out.println("function import structure is null");
				        }
			
			 JCoTable table =imlist.getTable("IT_SDVBAK");  
			 JCoTable table_SDVABP =imlist.getTable("IT_SDVBAP");
			 
			 if (table != null) {  
	             System.out.println("Function Import Structure : " + table.toString());  
	        }  else{
	        	
	        	 System.out.println("function import structure is null");
	        }
			
			 table.appendRow();
			 table.setRow(1);
			 
			
			 table.setValue("TRANUM","FO0080000705515");
			 table.setValue("ZNUM","720162");
			 table.setValue("ZFLAG","2");
			 table.setValue("VBELN_SO","");
			 table.setValue("BSARK","Y010");
			 table.setValue("AUART","ZOR");
			 table.setValue("BUKRS","3006");
			 
			 table.setValue("KUNNR","0510000267");
			 table.setValue("KUNNR2","0510000267");
			 table.setValue("LIMITDATE","");
			 table.setValue("Z101STATUS","");
			 table.setValue("Z101MESSAGE","");
			 table.setValue("VDATU","20161010");
			 table.setValue("BATDK","00000000");
			 
			 table.setValue("BSTKD","");
			 table.setValue("ZPAYMENT","N");
			 table.setValue("ZTEXT01","ZTEXT01");
			 table.setValue("ZTEXT02","ZTEXT02");
			 table.setValue("ZDISCOUNT","0");
			 table.setValue("ZMANDDC","N");
			 table.setValue("ZCONKA","N");
			 
			 table.setValue("REJECT","");
			 table.setValue("ZCREDAT","2016-10-08");
			 table.setValue("ZCRETIM","11:04:53");
			 table.setValue("ZUPDDAT","2016-10-08");
			 table.setValue("ZCRETIM","110453");
	
			 
			 table.setValue("CALLINGDATE","20161008");
			 table.setValue("CALLINGROUTE","0872");
			 table.setValue("UPDDAT","20161008");
			 table.setValue("UPDTIM","104029");
			 table.setValue("/SC1/SYNCSTATUS","C");
			 table.setValue("ZFIELD01","ZFIELD01");
			 table.setValue("ZFIELD02","ZFIELD02");
			 
			 table.setValue("ZFIELD03","ZFIELD03");
			 table.setValue("ZFIELD04","ZFIELD04");
			 table.setValue("ZFIELD05","ZFIELD05");
			 table.setValue("ZFIELD06","ZFIELD06");
			 table.setValue("ZFIELD07","ZFIELD07");
			 table.setValue("ZFIELD08","ZFIELD08");
			 table.setValue("ZFIELD09","ZFIELD09");
			 table.setValue("ZFIELD10","ZFIELD10");
			
			 table_SDVABP.appendRow();
			 table_SDVABP.setRow(1);
			 
			 table_SDVABP.setValue("TRANUM","FO0080000705515");
			 table_SDVABP.setValue("POSNR","1");
			 table_SDVABP.setValue("MATNR","2351");
			 table_SDVABP.setValue("ZMENG","100");
			 table_SDVABP.setValue("VRKME","CS");
			 table_SDVABP.setValue("ZATPSTATUS","");
			 table_SDVABP.setValue("ZBMENG","0");
			 table_SDVABP.setValue("ZCUSPRICE","0");
			 table_SDVABP.setValue("ZVRKME","0");
			 
			 
			 table_SDVABP.setValue("ZFIELD01","ZFIELD01");
			 table_SDVABP.setValue("ZFIELD02","ZFIELD02");
			 table_SDVABP.setValue("ZFIELD03","ZFIELD03");
			 table_SDVABP.setValue("ZFIELD04","ZFIELD04");
			 table_SDVABP.setValue("ZFIELD05","ZFIELD05");
			 table_SDVABP.setValue("ZFIELD06","ZFIELD06");
			 table_SDVABP.setValue("ZFIELD07","ZFIELD07");
			 table_SDVABP.setValue("ZFIELD08","ZFIELD08");
			 table_SDVABP.setValue("ZFIELD09","ZFIELD09");
			 table_SDVABP.setValue("ZFIELD10","ZFIELD10");
			 
			 
//		       for (int i = 0; i < table.getNumRows(); i++) {  
//		    	  System.out.println("table size");
//		    	   //table.setRow(i);  
//		    	              System.out.println(table.getString("|VBELN_SO") + '\t' + table.getString("KUNNR"));  
//		    	       }  

		      
			//function.get
			// JCoParameterList emlist= function.getExportParameterList();
			// JCoParameterList cmlist= function.getChangingParameterList();
			//JCoStructure  HEADDATA =imlist.getStructure("IT_SDVBAK"); 
			//JCoStructure sFrom = imlist.getStructure("/SC1/VBAK_INF_S");
			//JCoStructure HEADDATA =imlist.getStructure("IT_SDVBAK");  
//			  System.out.println("23");
//			 JCoMetaData a=imlist.getMetaData();
			// System.out.println(HEADDATA.get);
            
			
			
			
			Iterator iter = parmMap.entrySet().iterator();
			while (iter.hasNext()) {
				Map.Entry<String, String> entry = (Entry<String, String>) iter
						.next();
				//imlist.setValue(entry.getKey(), entry.getValue());
			}
			
		
			JCoContext.begin(destination); // 寮�惎涓�釜浜嬪姟
			// 寮�鎵цfunction
			function.execute(destination);
			tmlist = function.getTableParameterList();
			JCoContext.end(destination);
			System.out.println("end");
		} catch (JCoException jcoEx) {
			System.out.println(jcoEx.getMessage());
		}
		return tmlist;
	}

}
