

import java.io.File;
import java.io.FileOutputStream;
import java.util.Properties;

import com.sap.conn.jco.*;
import com.sap.conn.jco.ext.DestinationDataProvider;

public class SapRFC {

	private JCoFunction function;

	// 输入参数列表
	private JCoParameterList inPara = null;

	// 输出参数列表
	private JCoParameterList outPara = null;

	private JCoParameterList tabPara = null;

	private String functionName;

	private static String ABAP_AS ="ABAP_AS_WITHOUT_POOL";

	private JCoDestination destination;

	SapRFC(String portId) throws Exception {
	connect(); // 连接SAP
	}

	// 调用SapRFC
	public static SapRFC getInstance(String portId) throws Exception, JCoException {
	//RFC接口调用开始 ========== 
	SapRFC common = new SapRFC(portId);
	return common;
	}

	// 连接 SAP
	public void connect() throws Exception {

	// set properties参数，
	String clientName ="100";
	String userid ="SBI";
	String password ="SwireSBI1234";
	String language ="ZH";
	String host ="192.168.48.22";
	String system ="31";

	// 设置SAP的连接参数
	Properties connectProperties = new Properties();
	connectProperties.setProperty(DestinationDataProvider.JCO_ASHOST, host);
	connectProperties.setProperty(DestinationDataProvider.JCO_SYSNR, system);
	connectProperties.setProperty(DestinationDataProvider.JCO_CLIENT, clientName);
	connectProperties.setProperty(DestinationDataProvider.JCO_USER, userid);
	connectProperties.setProperty(DestinationDataProvider.JCO_PASSWD, password);
	connectProperties.setProperty(DestinationDataProvider.JCO_LANG, language);
	connectProperties.setProperty(DestinationDataProvider.JCO_CODEPAGE, "8400");

	try {
	// 创建DestinationDataProvider，
	createDataFile(ABAP_AS,"jcoDestination", connectProperties);
	destination = JCoDestinationManager.getDestination(ABAP_AS);

	} catch (JCoException ex) {
	throw new Exception("SAP连接失败"+ ex.getMessage());
	}
	}

	// 执行方法
	public SapRFC prepare(String functionName) throws Exception {
	this.functionName = functionName;
	try {
	// 取得要执行的方法
	function = destination.getRepository().getFunction(functionName);
	inPara = function.getImportParameterList();
	inPara.setValue("IM_BUKRS","3006");
	inPara.setValue("IM_SBI_REQ_ID","1");
	} catch (JCoException e) {
	throw new Exception("SAP获取方法"+ functionName +"失败："+ e.getMessage());
	}
	if (function == null) {
	throw new Exception(functionName +"方法不存在");
	}
	function.execute(destination);
	// 取得参数列表
	inPara = function.getImportParameterList();
	outPara = function.getExportParameterList();
	tabPara = function.getTableParameterList();
	
	  JCoTable table1 = tabPara.getTable("ET_RETURN");
	  JCoTable table2 = tabPara.getTable("T_OUTPUT");
	  System.out.println("count:"+table2.getNumRows());
     
     for(int i=0; i<table1.getNumRows(); i++, table1.nextRow()){
   	  System.out.println("TYPE: "+table1.getString("TYPE")+" NUMBER: "+table1.getString("NUMBER")+" ID: "+table1.getString("ID"));
     }
     for(int i=0; i<table2.getNumRows(); i++, table2.nextRow()){
   	  System.out.println("BUKRS: "+table2.getString("BUKRS")+" WERKS "+table2.getString("WERKS")+" ARBPL "+table2.getString("ARBPL")+" KTEXT "+table2.getString("KTEXT"));
     }
	//logger.info(functionName +"方法调用开始");
	return this;
	}
	

	// SAP传入参数为列表
	public JCoTable getParamTableList(String tableName) {
	return function.getTableParameterList().getTable(tableName);
	}

	// DisConnect
	public void close() {
	// if (client != null)
	// client.disconnect();
	}


	// Creates a connection configuration file based on parameters given above
	static void createDataFile(String name, String suffix, Properties properties) throws Exception {
	File cfg = new File(name +"."+ suffix);
	// if (!cfg.exists()) {
	try {
	FileOutputStream fos = new FileOutputStream(cfg, false);
	properties.store(fos,"Destination - ABAP_AS_WITHOUT_POOL");
	fos.close();
	} catch (Exception e) {
	throw new Exception("不能创建SAP连接需要的Destination文件"+ cfg.getName(), e);
	}
	//}
	}

	public String convertNull(String str){
	if (str == null)
	return"";
	return str;
	}
}
