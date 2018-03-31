
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.math.BigDecimal;
import java.util.Scanner;

/**
 * @author dengbenbo696@lu.com
 * @date 2018/3/30
 */
public class SqlGen {


    public static void main(String args[]) throws IOException {

        String baseUrl = "\\files" + (new SimpleDateFormat("yyyyMMdd_hhmmss").format(new Date()));
        File path = new File(baseUrl);
        if (!path.exists()) {
            path.mkdir();
        }
        String suffix = ".sql";
        String datePlaceHolder = "{DATE}";

        System.out.println(">>>>>>>>>>>>> SQL with placeHolder'{DATE}', end with Key Word [end] : ");
        final String tmpSql = scanBlockWithEnd();

        System.out.println(">>>>>>>>>>>>> file Prefix: ");
        Scanner scanner = new Scanner(System.in);
        final String prefix = scanner.next();

        System.out.println(">>>>>>>>>>>>> Trade Days,split with comma',' : ");
        scanner = new Scanner(System.in);
        final String dateStr = scanner.nextLine();

        System.out.println(">>>>>>>>>>>>> Page Size : ");
        scanner = new Scanner(System.in);
        int pageSize = scanner.nextInt();

        String[] dates = dateStr.split(",");
        int totalCount = (dates.length / pageSize) + (dates.length % pageSize == 0 ? 0 : 1);

        System.out.println("===============  total file counts: " + totalCount);
        int fileOrder = 1;
        File file = null;
        StringBuilder buf = new StringBuilder("");
        for (int i = 0; i < dates.length; i++) {

            buf.append(tmpSql.replace(datePlaceHolder, dates[i])).append("\r\n \r\n");

			boolean isLastRecordOrCurrentPageEnd = (i == dates.length - 1) || (i+1) % pageSize == 0;
            
			if (isLastRecordOrCurrentPageEnd) {
				PrintWriter printWriter = null;
				try {
					if (file == null) {
						file = new File(path + "\\" + prefix + "_" + (fileOrder++) + suffix);
					}
					printWriter = new PrintWriter(file);
					printWriter.write(buf.toString());
					printWriter.flush();
					System.out.println(file.getAbsoluteFile() + " write successed.");
					
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					if (printWriter != null) {
						printWriter.close();
					}
				}
				// INIT NEW FILE and REBUILD BUFFER
                file = new File(path + "\\" + prefix + "_" + (fileOrder++) + suffix);
                buf = new StringBuilder("");
            }
        }
		System.out.println("100% is complete ");

        scanner.close();
    }


    public static String scanBlockWithEnd() {
        Scanner scn = new Scanner(System.in);
        String line;
        StringBuilder buf = new StringBuilder("");
        while (true) {
            line = scn.nextLine();
            if ("end".equalsIgnoreCase(line)) {
                return buf.toString();
            }
            buf.append(line).append("\r\n");
        }
    }
}
