import java.util.HashMap;
import java.util.Map;

import com.sap.conn.jco.JCoParameterList;
import com.sap.conn.jco.JCoTable;



public class Test implements Runnable {
	
	public String name;
	
	 public Test(String name) {
	        this.name = name;
	    }
	
	public static void main(String[] args) throws Exception {

		for(int i = 0;i<1;i++){
			Thread t1 = new Thread(new Test("线程1"),"线程"+i);
			t1.start();
		}
		
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		Map<String, String> inMap = new HashMap<String, String>();
		inMap.put("IM_BUKRS", "3006");
		inMap.put("IM_SBI_REQ_ID", "1");
		JCO3 jco = JCO3.getInstance();
		JCoParameterList outTable = null;
		JCoTable ET_RETURN = null;
		JCoTable ET_RETURN_HEAD = null;
		JCoTable ET_GIFT = null;
		try {
			//SC1/WORK_CENTER_MASTER
			///SC1/CIC_ORDER_IN
			outTable = jco.callSapRFC("/SC1/CIC_ORDER_IN",
					inMap);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println(Thread.currentThread().getName()+"error");
			e.printStackTrace();
		}
		
		System.out.println("XML:"+outTable.toXML());
		
		try{
			ET_RETURN = outTable.getTable("ET_RETURN");
			ET_RETURN_HEAD = outTable.getTable("ET_RETURN_HEAD");
			ET_GIFT = outTable.getTable("ET_GIFT");
		}catch(Exception e){
			System.out.println(Thread.currentThread().getName()+"error111111");
		}
		
		try{
			System.out.println("count:" + ET_RETURN.getNumRows()+"线程名"+Thread.currentThread().getName()+")))))))))):"+ET_RETURN.toString());
		}catch(Exception e){
			System.out.println(Thread.currentThread().getName()+"error222222");
		}
		
		System.out.println("ET_RETURN_HEAD"+ET_RETURN_HEAD.toString());
		System.out.println("ET_GIFT"+ET_GIFT.toString());
		
		for (int i =0 ; i<ET_RETURN.getNumRows();i++,ET_RETURN.nextRow()){
			System.out.print("BUKRS:"+ET_RETURN.getString("BUKRS") +"|");
			System.out.print("TRANUM:"+ET_RETURN.getString("TRANUM") +"|");
			System.out.print("ZNUM:"+ET_RETURN.getString("ZNUM") +"|");
			System.out.print("ZLINENO:"+ET_RETURN.getValue("ZLINENO")+"|");
			System.out.print("VBELN_SO:"+ET_RETURN.getString("VBELN_SO") +"|");
			System.out.print("ZSTATUS:"+ET_RETURN.getString("ZSTATUS") +"|");
			System.out.print("CREDAT:"+ET_RETURN.getString("CREDAT") +"|");
			System.out.print("CRETIM:"+ET_RETURN.getString("CRETIM") +"|");
			System.out.print("ZCODE:"+ET_RETURN.getString("ZCODE") +"|");
			System.out.print("MSLOG:"+ET_RETURN.getString("MSLOG") +"|");
			
			System.out.println("| ");
		}
		
		for (int i =0 ; i<ET_RETURN_HEAD.getNumRows();i++,ET_RETURN_HEAD.nextRow()){
			System.out.print("TRANUM:"+ET_RETURN_HEAD.getString("TRANUM") +"|");
			System.out.print("VBELN_SO:"+ET_RETURN_HEAD.getString("VBELN_SO") +"|");
			System.out.print("BUKRS:"+ET_RETURN_HEAD.getString("BUKRS") +"|");
			System.out.print("VDATU:"+ET_RETURN_HEAD.getValue("VDATU")+"|");
			System.out.print("PURPOSE:"+ET_RETURN_HEAD.getString("PURPOSE") +"|");
			System.out.print("LIFSK:"+ET_RETURN_HEAD.getString("LIFSK") +"|");
			System.out.print("ZREJECT:"+ET_RETURN_HEAD.getString("ZREJECT") +"|");
			System.out.print("ZNETPR:"+ET_RETURN_HEAD.getString("ZNETPR") +"|");
			System.out.print("ZONINVOICE:"+ET_RETURN_HEAD.getString("ZONINVOICE") +"|");
			System.out.print("ZOFFINVOICE:"+ET_RETURN_HEAD.getString("ZOFFINVOICE") +"|");
			
			System.out.print("ZDETPR:"+ET_RETURN_HEAD.getString("ZDETPR") +"|");
			System.out.print("ZWERKS:"+ET_RETURN_HEAD.getString("ZWERKS") +"|");
			System.out.print("UPDDAT:"+ET_RETURN_HEAD.getString("UPDDAT") +"|");
			System.out.print("UPDTIM:"+ET_RETURN_HEAD.getString("UPDTIM") +"|");
			System.out.println("| ");
		}
		
		for (int i =0 ; i<ET_GIFT.getNumRows();i++,ET_GIFT.nextRow()){
			System.out.print("VBELN_SO:"+ET_GIFT.getString("VBELN_SO") +"|");
			System.out.print("POSNR:"+ET_GIFT.getString("POSNR") +"|");
			System.out.print("MATNR:"+ET_GIFT.getString("MATNR") +"|");
			System.out.print("ZMENG:"+ET_GIFT.getValue("ZMENG")+"|");
			System.out.print("VRKME:"+ET_GIFT.getString("VRKME") +"|");
			
			System.out.println("| ");
		}
		
		
	//	JCoTable table1 = outTable.getTable("ET_RETURN");
//
//	for (int i = 0; i < table2.getNumRows(); i++, table2.nextRow()) {
//			System.out.println("BUKRS: " + table2.getString("BUKRS")
//					+ " WERKS " + table2.getString("WERKS") + " ARBPL "
//					+ table2.getString("ARBPL") + " KTEXT "
//					+ table2.getString("KTEXT"));
//		}
	}

}
