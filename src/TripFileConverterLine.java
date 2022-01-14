import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class TripFileConverterLine
{
	private final String STR_FILE_PATH = "C:\\PDS\\PC_PDS\\Data\\Output";

	private final String STR_LINE_STATUS = "A"; // A : 넣기 , D : 빼기 //

	public static void main(String[] args)
	{
		// TODO Auto-generated method stub
		TripFileConverterLine tfcl = new TripFileConverterLine();
		
		File listFile[] = tfcl.getFileList(tfcl.STR_FILE_PATH);
		
		try
		{
			tfcl.converterFiles(listFile);
		}
		catch (IOException ioe)
		{

		}

	}
	
	private void converterFiles(File listFile[]) throws IOException
	{
		for (File file : listFile)
		{
			String strFileName = file.getName();

			this.convertTripData(file);
		}
	}
	
	
	private void convertTripData(File file) throws IOException
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "EUC-KR"));
		
		if (br != null)
		{
			String strFullData = "";
			String strData = "";
			StringBuilder sb = new StringBuilder();
			
			int cur = 0;
			
			if (STR_LINE_STATUS.equals("A"))
			{
				
				byte[] arrByte = null;
				
				int intPos = 0;
				
				while ((strData = br.readLine()) != null)
				{
					arrByte = strData.getBytes();
					
					while (intPos < arrByte.length)
					{
						if (cur == 0)
						{
							String strVData = new String(arrByte, 0, 82);
							sb.append(strVData).append(System.getProperty("line.separator").toString());
							
							intPos += 82;
						}
						else
						{
							String strVData = new String(arrByte, intPos, 68);
							sb.append(strVData).append(System.getProperty("line.separator").toString());
							
							intPos += 68;
						}
						
						cur++;
					}
				}
				
				strFullData = sb.toString();
				
			}
			else if (STR_LINE_STATUS.equals("D"))
			{
				while ((strData = br.readLine()) != null)
				{
					sb.append(strData);
				}
				
				strFullData = sb.toString().replace(System.getProperty("line.separator").toString(), "");
				
				
			}
			
			br.close();
			
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "EUC-KR"));
			bw.write(strFullData);
			bw.close();
			
			this.writeLog(">>>>>>>>>>>>>>> File Convert Line Count " + cur + " End <<<<<<<<<<<<<<<<");
			
		}	
		
		
		
	}
	
	/**
	 * Log 기록
	 * 
	 * @param strLog
	 */
	private void writeLog(String strLog)
	{
		System.out.println(strLog);
	}
	
	private File[] getFileList(String strFilePath)
	{
		// this.writeLog("[getFileList] strFilePath : " + strFilePath);

		File f = new File(strFilePath);

		File listFile[] = f.listFiles();

		// this.writeLog("[getFileList] listFile.length : " + listFile.length);

		return listFile;
	}

}
