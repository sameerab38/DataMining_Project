/**
 * @author Sameera Bammidi
 * Created On: 12/01/2017
 *
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.HashSet;

public class SNDA
{

	public static void main(String[] args) throws SQLException, IOException
	{
		/*
		 * Correlational Analysis of Transaction and Stock Prices
		 * 
		 * Return: Insiders with a significant statistical result
		 * 1: T = {}
		 * 2: for each insider I do
		 * 3: SI = {}
		 * 4: for each transaction of insider I for company C do
		 * 5: TD, TT, TP, Sum(ST) -> transaction date, type, price, sum of shares traded in all the transactions with the same TD, TT, and TP
		 * 6: CP = market closing price for company C�s stock on date TD
		 * 7: DV = dollar volume for company C�s stock on date TD
		 * 8: R = T P � Sum(ST)/DV
		 * 9: if T T = sale then
		 * 10: if CP < T P then
		 * 11: SI = SI U R
		 * 12: else
		 * 13: SI = SI U -R
		 * 14: if T T = purchase then
		 * 15: if CP > T P then
		 * 16: SI = SI U R
		 * 17: else
		 * 18: SI = SI U -R
		 * 19: T = T U {SI}
		 * 20: alpha-Bonferroni = 0.01/|T|
		 * 21: for each sample SI in T do
		 * 22: a = p-value from one tailed t-test with Ha : �SI > 0
		 * 23: if a < alpha-Bonferroni then
		 * 24: return I
		 * */
		
		// Get all insiders from the purchase_LCS_Egonets_NodeAndEdge_Count.csv.		
		File iinput = new File ("purchase_LCS_Egonets_NodeAndEdge_Count.csv");

		if(!iinput.exists())
		{
			System.out.println("file not found");
		}
		
		BufferedReader ibr = new BufferedReader(new FileReader(iinput));
		
		String iline;
		iline = ibr.readLine();// ignore first
		HashSet<Integer> insiderIdLCSPurchases = new HashSet<Integer>();
		
		while ((iline = ibr.readLine()) != null)
		{
			String[] tokens = iline.split(",");
			
			insiderIdLCSPurchases.add(Integer.parseInt(tokens[0]));
		}
		ibr.close();
		
		/* For each insider id from the above file if the insider Id from the purchases_trade_Price_SNDA_all_Data.csv file matches it,
		 * Write it to purchases_trade_Price_LCS_Data.csv.
		 */
		File input = new File ("purchases_trade_Price_SNDA_all_Data.csv");

		if(!input.exists())
		{
			System.out.println("file not found");
		}
		

		PrintWriter pwrp = new PrintWriter(new File("purchases_trade_Price_LCS_Data.csv"));
		pwrp.println("ID, Date, insider_id, stock_id, NumberOfShares, PurchasePrice, ClosingPrice, StockVolume");
		
		BufferedReader br = new BufferedReader(new FileReader(input));
		
		String line;
		line = br.readLine();// ignore first
		
		while ((line = br.readLine()) != null)
		{
			//System.out.println(line);
			String[] tokens = line.split(",");
			for(int insiderIdLCSPurchase : insiderIdLCSPurchases)
			{
				if(insiderIdLCSPurchase == Integer.parseInt(tokens[2]))
				{
					pwrp.println(line);
				}
			}
			
		}
		pwrp.close();
		br.close();
				
		/*
		 * For each purchase transaction compute the total purchase price = no.of shares purchased x purchase price.
		 * For each purchase transaction compute dollar volume = closingPrice x stock volume.
		 * For each date, for each insider Compute sdna = total purchase price/dollar volume.
		 * If closingPrice > purchase price the store +sdna else store -sdna. 
		 * Save all of the above to a file purchases_SDNA_Data.csv.
		 * */
		//HashMap<Integer, Set<Date>> uniquePurchaseDates = new HashMap<Integer, Set<Date>>();
		
		
		
		System.exit(1);
		
		// Get all insiders from the sale_LCS_Egonets_NodeAndEdge_Count.csv.		
		File sinput = new File ("sale_LCS_Egonets_NodeAndEdge_Count.csv");

		if(!sinput.exists())
		{
			System.out.println("file not found");
		}
		
		BufferedReader sbr = new BufferedReader(new FileReader(sinput));
		
		String sline;
		sline = sbr.readLine();// ignore first
		HashSet<Integer> insiderIdLCSSales = new HashSet<Integer>();
		
		while ((sline = sbr.readLine()) != null)
		{
			String[] tokens = sline.split(",");
			
			insiderIdLCSSales.add(Integer.parseInt(tokens[0]));
		}
		sbr.close();
		
		/* For each insider id from the above file if the insider Id from the sale_trade_Price_SNDA_all_Data.csv file matches it,
		 * Write it to sale_trade_Price_LCS_Data.csv.
		 */
		input = new File ("sale_trade_Price_SNDA_all_Data.csv");

		if(!input.exists())
		{
			System.out.println("file not found");
		}
		

		PrintWriter pwrs = new PrintWriter(new File("sale_trade_Price_LCS_Data.csv"));
		pwrs.println("ID, Date, insider_id, stock_id, NumberOfShares, PurchasePrice, ClosingPrice, StockVolume");
		
		br = new BufferedReader(new FileReader(input));
		
		line = br.readLine();// ignore first
		
		while ((line = br.readLine()) != null)
		{
			//System.out.println(line);
			String[] tokens = line.split(",");
			for(int insiderIdLCSale : insiderIdLCSSales)
			{
				if(insiderIdLCSale == Integer.parseInt(tokens[2]))
				{
					pwrs.println(line);
				}
			}
			
		}
		pwrs.close();
		br.close();
		
	}

}
