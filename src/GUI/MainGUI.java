package GUI;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.CardLayout;
import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.JRadioButton;
import java.awt.Image;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import webcrawler.data.TrainServiceDisruptionData;
import webcrawler.data.TwitterTweetData;
import webcrawler.extractor.TrainServiceDisruptionSheetExtractor;
import webcrawler.extractor.TwitterTweetsSearchExtractor;

import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;

public class MainGUI {

	private JFrame f;
	private JTable table;
	private JTable twitterTable;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainGUI window = new MainGUI();
					window.f.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		f = new JFrame();
		f.setTitle("TeamWork");
		f.setBounds(100, 100, 567, 449);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		CardLayout card = new CardLayout(0, 0);
		f.getContentPane().setLayout(card);
		f.setResizable(false);
		
		ButtonGroup bg = new ButtonGroup();
		
		JPanel jpMainPage = new JPanel();
		f.getContentPane().add(jpMainPage, "Main");
		jpMainPage.setLayout(null);
		
		JLabel lblTitle = new JLabel("MRT Web Crawler");
		lblTitle.setBounds(0, 152, 561, 85);
		jpMainPage.add(lblTitle);
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setFont(new Font("Tahoma", Font.PLAIN, 36));
		
		JComboBox jcbSelection = new JComboBox();
		jcbSelection.setModel(new DefaultComboBoxModel(new String[] {"Google Sheet Data", "Tweet Data"}));
		jcbSelection.setBounds(219, 257, 132, 27);
		jpMainPage.add(jcbSelection);
		
		JButton btnNext = new JButton("Start");
		btnNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(jcbSelection.getSelectedItem().equals("Google Sheet Data")) {
					//Download Crawled Data To JSON File
					TrainServiceDisruptionSheetExtractor data = new TrainServiceDisruptionSheetExtractor();
					List<TrainServiceDisruptionData> x = data.getData(true);
					
					card.show(f.getContentPane(), "GoogleSheet");
				}
					
				else if(jcbSelection.getSelectedItem().equals("Tweet Data")) {
					//Download Crawled Data To JSON File
					TwitterTweetsSearchExtractor data = new TwitterTweetsSearchExtractor();
					List<TwitterTweetData> x = data.getData(true);
					
					card.show(f.getContentPane(), "Tweet");
				}
				
			}
		});
		btnNext.setBounds(459, 376, 92, 33);
		jpMainPage.add(btnNext);
		btnNext.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		JLabel lblGroupIcon = new JLabel("");
		Image img = new ImageIcon(this.getClass().getResource("/logo.png")).getImage();
		lblGroupIcon.setIcon(new ImageIcon(img));
		lblGroupIcon.setBounds(23, 49, 538, 103);
		jpMainPage.add(lblGroupIcon);
		
		JLabel lbltrain = new JLabel("");
		Image img2 = new ImageIcon(this.getClass().getResource("/sgtrain.png")).getImage();
		lbltrain.setIcon(new ImageIcon(img2));
		lbltrain.setBounds(33, 293, 411, 100);
		jpMainPage.add(lbltrain);
		
		JPanel jpTweet = new JPanel();
		f.getContentPane().add(jpTweet, "Tweet");
		jpTweet.setLayout(null);
		
		JButton btnBack4 = new JButton("Back");
		btnBack4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				card.show(f.getContentPane(), "Main");
			}
		});
		btnBack4.setBounds(459, 376, 92, 33);
		jpTweet.add(btnBack4);
		
		JLabel lblTwitter = new JLabel("Twitter Data:");
		lblTwitter.setHorizontalAlignment(SwingConstants.CENTER);
		lblTwitter.setFont(new Font("Tahoma", Font.PLAIN, 30));
		lblTwitter.setBounds(26, 13, 185, 45);
		jpTweet.add(lblTwitter);
		
		JComboBox jcbTYear = new JComboBox();
		jcbTYear.setModel(new DefaultComboBoxModel(new String[] {"2017", "2018", "2019"}));
		jcbTYear.setBounds(253, 23, 84, 33);
		jpTweet.add(jcbTYear);
		
		JButton btnTGenerate = new JButton("Generate");
		btnTGenerate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if(twitterTable == null) {
					twitterTable = new JTable();
					twitterTable.setBounds(10, 60, 541, 300);
					
					JScrollPane js = new JScrollPane(twitterTable);
			        js.setVisible(true);
			        js.setBounds(10, 60, 541, 300);
			        
			        jpTweet.add(js);
				}
				
				DefaultTableModel dtm = new DefaultTableModel(0, 0);
				String header[] = new String[] { "Author", "Train Line", "Date",
			            "Tweet"};
				dtm.setColumnIdentifiers(header);
				twitterTable.setModel(dtm);
				
				SimpleDateFormat f = new SimpleDateFormat("dd/MM/YY");
				SimpleDateFormat f1 = new SimpleDateFormat("YYYY");
				
				TwitterTweetsSearchExtractor data = new TwitterTweetsSearchExtractor();
				List<TwitterTweetData> x = data.getData(false);
				
				for(int i = 0; i < x.size(); i++){
				Date d = x.get(i).getTweetTimestamp();
					
					if(jcbTYear.getSelectedItem().equals(f1.format(d))) {
						
						dtm.addRow(new Object[] { x.get(i).getTweetAuthor(), x.get(i).getTweetTrainLine(), f.format(d),
								x.get(i).getTweetMessage()});
					}
				}
			}
		});
		btnTGenerate.setBounds(357, 23, 92, 33);
		jpTweet.add(btnTGenerate);
		
		JLabel lblSortTweet = new JLabel("Sort By: ");
		lblSortTweet.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblSortTweet.setHorizontalAlignment(SwingConstants.CENTER);
		lblSortTweet.setBounds(10, 367, 84, 43);
		jpTweet.add(lblSortTweet);
		
		JButton btnLine = new JButton("Train Line");
		btnLine.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				int redLine = 0, yellowLine = 0, blueLine = 0, greenLine = 0, purpleLine = 0, otherLine = 0;
				String line = null;
				
				SimpleDateFormat f1 = new SimpleDateFormat("YYYY");
				
				//Get crawled Twitter data from JSON file downloaded earlier.
				TwitterTweetsSearchExtractor data = new TwitterTweetsSearchExtractor();
				List<TwitterTweetData> x = data.getData(false);
				
				for(int i = 0; i < x.size(); i++){
					
					Date d = x.get(i).getTweetTimestamp();
					
					if(jcbTYear.getSelectedItem().equals(f1.format(d))){
					
						line = x.get(i).getTweetTrainLine();
						if(line.contentEquals("North-South Line"))
							redLine += 1;
						else if(line.contentEquals("East-West Line"))
							greenLine += 1;
						else if(line.contentEquals("North-East Line"))
							purpleLine += 1;
						else if(line.contentEquals("Circle Line"))
							yellowLine += 1;
						else if(line.contentEquals("Downtown Line"))
							blueLine += 1;
						else
							otherLine += 1;
					}
				}

				DefaultPieDataset pie = new DefaultPieDataset();
				pie.setValue("North South Line", redLine);
				pie.setValue("East West Line", greenLine);
				pie.setValue("North East Line", purpleLine);
				pie.setValue("Circle Line", yellowLine);
				pie.setValue("Downtown Line", blueLine);
				pie.setValue("Others", otherLine);
				
				JFreeChart chart = ChartFactory.createPieChart("Twitter MRT Disruption Pie Chart " + jcbTYear.getSelectedItem(), pie, true, true, true);
				PiePlot p = (PiePlot)chart.getPlot();
				p.setSectionPaint(0, Color.red);
				p.setSectionPaint(1, Color.green);
				p.setSectionPaint(2, new Color(149,10,183));
				p.setSectionPaint(3, Color.orange);
				p.setSectionPaint(4, Color.blue);
				p.setSectionPaint(5, Color.gray);
				ChartFrame frame = new ChartFrame("Twitter MRT Disruption Pie Chart " + jcbTYear.getSelectedItem(), chart);
				frame.setVisible(true);
				frame.setSize(800, 600);
				
				//Output data to a picture format file.
				File pieChart = new File("Twitter MRT Disruption Pie Chart " + jcbTYear.getSelectedItem() + ".png"); 
				try {
					ChartUtilities.saveChartAsJPEG( pieChart , chart , 800 , 600 );
				}catch (IOException e1) {
					
				}
				
			}
		});
		btnLine.setBounds(119, 376, 92, 33);
		jpTweet.add(btnLine);
		
		JButton btnMonth = new JButton("Month");
		btnMonth.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				//Get crawled Twitter data from JSON file downloaded earlier.
				TwitterTweetsSearchExtractor twitterTweetsSearchExtractor = new TwitterTweetsSearchExtractor();
		        List<TwitterTweetData> data = twitterTweetsSearchExtractor.getData(false);

		        int jan = 0, feb = 0, mar = 0, apr = 0, may = 0, jun = 0;
		        int jul = 0, aug = 0, sep = 0, oct = 0, nov = 0, dec = 0;
		        
		        SimpleDateFormat f1 = new SimpleDateFormat("YYYY");
		        SimpleDateFormat f2 = new SimpleDateFormat("MMM");
		        
		        for (int i = 1; i<data.size();i++) {
		        	
		        	Date d = data.get(i).getTweetTimestamp();
		        	
		        	if(jcbTYear.getSelectedItem().equals(f1.format(d))){
		        		
		        		if (f2.format(d).contentEquals("Jan")){
		        			jan += 1;
		        		}
		        		else if (f2.format(d).contentEquals("Feb")){
		        			feb += 1;
		        		}
		        		else if (f2.format(d).contentEquals("Mar")){
		        			mar += 1;
		        		}
		        		else if (f2.format(d).contentEquals("Apr")){
		        			apr += 1;
		        		}
		        		else if (f2.format(d).contentEquals("May")){
		        			may += 1;
		        		}
		        		else if (f2.format(d).contentEquals("Jun")){
		        			jun += 1;
		        		}
		        		else if (f2.format(d).contentEquals("Jul")){
		        			jul += 1;
		        		}
		        		else if (f2.format(d).contentEquals("Aug")){
		        			aug += 1;
		        		}
		        		else if (f2.format(d).contentEquals("Sept")){
		        			sep += 1;
		        		}
		        		else if (f2.format(d).contentEquals("Oct")){
		        			oct += 1;
		        		}
		        		else if (f2.format(d).contentEquals("Nov")){
		        			nov += 1;
		        		}
		        		else if (f2.format(d).contentEquals("Dec")){
		        			dec += 1;
		        		}
		        	}
		        }
		        
		        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		        dataset.setValue(new Double(jan), "values", "Jan");
		        dataset.setValue(new Double(feb), "values", "Feb");
		        dataset.setValue(new Double(apr), "values", "Apr");
		        dataset.setValue(new Double(mar), "values", "Mar");
		        dataset.setValue(new Double(may), "values", "May");
		        dataset.setValue(new Double(jun), "values", "Jun");
		        dataset.setValue(new Double(jul), "values", "Jul");
		        dataset.setValue(new Double(aug), "values", "Aug");
		        dataset.setValue(new Double(sep), "values", "Sep");
		        dataset.setValue(new Double(oct), "values", "Oct");
		        dataset.setValue(new Double(nov), "values", "Nov");
		        dataset.setValue(new Double(dec), "values", "Dec");
		        
		        JFreeChart chart = ChartFactory.createLineChart("MRT Breakdowns " + jcbTYear.getSelectedItem(), "Month", "Breakdowns", dataset, PlotOrientation.VERTICAL, false, true, false);
		        CategoryPlot p = chart.getCategoryPlot();
		        ChartFrame frame = new ChartFrame("Bar Chart for",chart);
		        frame.setVisible(true);
		        frame.setSize(800,600);
		        
		        //Output data to a picture format file.
				File lineChart = new File("Twitter MRT Disruption Pie Chart " + jcbTYear.getSelectedItem() + ".png"); 
				try {
					ChartUtilities.saveChartAsJPEG( lineChart , chart , 800 , 600 );
				}catch (IOException e1) {
					
				}
			}
		});
		
		btnMonth.setBounds(225, 376, 92, 33);
		jpTweet.add(btnMonth);
		
		JPanel jpGoogleSheet = new JPanel();
		f.getContentPane().add(jpGoogleSheet, "GoogleSheet");
		jpGoogleSheet.setLayout(null);
		
		JLabel lblprogress = new JLabel("");
		Image img3 = new ImageIcon(this.getClass().getResource("/sgprogress.png")).getImage();
		lblprogress.setIcon(new ImageIcon(img3));
		lblprogress.setBounds(217, 125, 295, 205);
		jpGoogleSheet.add(lblprogress);
		
		JLabel lblMRTTitle = new JLabel("MRT Breakdown");
		lblMRTTitle.setBounds(100, 0, 365, 153);
		jpGoogleSheet.add(lblMRTTitle);
		lblMRTTitle.setFont(new Font("Tahoma", Font.PLAIN, 30));
		lblMRTTitle.setHorizontalAlignment(SwingConstants.CENTER);
		
		JRadioButton rbLine = new JRadioButton("Line Colour");
		rbLine.setBounds(66, 217, 140, 40);
		jpGoogleSheet.add(rbLine);
		
		JRadioButton rbTime = new JRadioButton("Date/Time");
		rbTime.setBounds(66, 257, 140, 40);
		jpGoogleSheet.add(rbTime);
		
		bg.add(rbLine);
		bg.add(rbTime);
		
		JButton btnBack = new JButton("Back");
		btnBack.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnBack.setBounds(459, 376, 92, 33);
		jpGoogleSheet.add(btnBack);
		
		JLabel lblSortBy = new JLabel("Sort by:");
		lblSortBy.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblSortBy.setBounds(66, 189, 117, 29);
		jpGoogleSheet.add(lblSortBy);
		
		JButton btnGo = new JButton("Go");
		btnGo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (rbLine.isSelected())
					card.show(f.getContentPane(), "Line");
				else if(rbTime.isSelected())
					card.show(f.getContentPane(), "mrtDate");
				else if(!rbTime.isSelected() && !rbLine.isSelected()) {
					//Popup error message if no radio button is selected.
					JOptionPane.showMessageDialog(null, "Please select one!", "No Option Selected", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		btnGo.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnGo.setBounds(357, 376, 92, 33);
		jpGoogleSheet.add(btnGo);
		
		JPanel jpDate = new JPanel();
		f.getContentPane().add(jpDate, "mrtDate");
		jpDate.setLayout(null);
		
		JButton btnBack2 = new JButton("Back");
		btnBack2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				card.show(f.getContentPane(), "GoogleSheet");
			}
		});
		btnBack2.setBounds(459, 376, 92, 33);
		btnBack2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		jpDate.add(btnBack2);
		
		JComboBox jcbMonth = new JComboBox();
		jcbMonth.setMaximumRowCount(12);
		jcbMonth.setModel(new DefaultComboBoxModel(new String[] {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"}));
		jcbMonth.setBounds(354, 19, 77, 33);
		jpDate.add(jcbMonth);
		
		JComboBox jcbYear = new JComboBox();
		jcbYear.setModel(new DefaultComboBoxModel(new String[] {"2002", "2003", "2004", "2005", "2006", "2007", "2008", "2009", "2010", "2011", "2012", "2013", "2014", "2015", "2016", "2017"}));
		jcbYear.setMaximumRowCount(12);
		jcbYear.setBounds(441, 19, 77, 33);
		jpDate.add(jcbYear);
		
		JLabel lblSelectMonthAnd = new JLabel("Select Month and Year:");
		lblSelectMonthAnd.setFont(new Font("Tahoma", Font.PLAIN, 30));
		lblSelectMonthAnd.setHorizontalAlignment(SwingConstants.CENTER);
		lblSelectMonthAnd.setBounds(10, 11, 334, 33);
		jpDate.add(lblSelectMonthAnd);
		
		JLabel lblNoData = new JLabel("No Data Available for the current Month and Year");
		lblNoData.setBounds(32, 73, 463, 27);
		jpDate.add(lblNoData);
		lblNoData.setVisible(false);
		
		JButton btnGenerateYM = new JButton("Generate");
		btnGenerateYM.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				lblNoData.setVisible(false);
				
				String mnth = jcbMonth.getSelectedItem().toString();
				String yr = jcbYear.getSelectedItem().toString();
				
				if(table == null) {
					table = new JTable();
					table.setBounds(20, 57, 510, 308);
					
					JScrollPane js = new JScrollPane(table);
			        js.setVisible(true);
			        js.setBounds(20, 57, 510, 308);
			        
			        jpDate.add(js);
				}

				DefaultTableModel dtm = new DefaultTableModel(0, 0);
				String header[] = new String[] { "Date", "Duration (Min)", "Line",
			            "Category", "Description"};
				dtm.setColumnIdentifiers(header);
				table.setModel(dtm);
				
				SimpleDateFormat f = new SimpleDateFormat("dd/MM/YY");
				SimpleDateFormat f1 = new SimpleDateFormat("MMMM");
				SimpleDateFormat f2 = new SimpleDateFormat("YYYY");
				
				//Get crawled Google Sheet data from JSON file downloaded earlier.
				TrainServiceDisruptionSheetExtractor data = new TrainServiceDisruptionSheetExtractor();
				List<TrainServiceDisruptionData> x = data.getData(false);
				
				for(int i = 0; i < x.size(); i++){
					
					if(f1.format(x.get(i).getStartDate()).toString().contentEquals(mnth) && f2.format(x.get(i).getStartDate()).contentEquals(yr)) {
						
						dtm.addRow(new Object[] { f.format(x.get(i).getStartDate()), x.get(i).getDuration(), x.get(i).getLine(),
								x.get(i).getCategory(), x.get(i).getDescription()});
					}	
				}
				
				//If no Data available display error message
				if(table.getRowCount() == 0) {
					lblNoData.setVisible(true);
				}
			}
		});
		btnGenerateYM.setBounds(359, 376, 92, 33);
		jpDate.add(btnGenerateYM);
		JComboBox jcbYearBarChart = new JComboBox();
		jcbYearBarChart.setModel(new DefaultComboBoxModel(new String[] {"2002", "2003", "2004", "2005", "2006", "2007", "2008", "2009", "2010", "2011", "2012", "2013", "2014", "2015", "2016", "2017"}));
		jcbYearBarChart.setMaximumRowCount(12);
		jcbYearBarChart.setBounds(42, 377, 77, 33);
		jpDate.add(jcbYearBarChart);
		
		
		JButton btnDurationBarChart = new JButton("Bar Chart(duration/Year)");
		btnDurationBarChart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			String year = jcbYearBarChart.getSelectedItem().toString();
			int less30 = 0, less1h = 0, less2h = 0 ,more2h =0;
			TrainServiceDisruptionSheetExtractor data = new TrainServiceDisruptionSheetExtractor();

			List<TrainServiceDisruptionData> x = data.getData(false);
			SimpleDateFormat f2 = new SimpleDateFormat("YYYY");
			for(int i = 0; i < x.size(); i++){
				
				if (f2.format(x.get(i).getStartDate()).contentEquals(year)) {
					if(x.get(i).getDuration()<30) {
					less30 += 1;
					}	
					else if(x.get(i).getDuration()>30 && x.get(i).getDuration()<60) {
					less1h += 1;
						
					}
					else if(x.get(i).getDuration()>60 && x.get(i).getDuration()<120) {
					less2h += 1;
					}
					else if(x.get(i).getDuration()>120) {
					more2h += 1;
					}
	
				}
				
			}

			DefaultCategoryDataset dataset = new DefaultCategoryDataset();
			
			dataset.setValue(less30,"","0 - 30min");
			dataset.setValue(less1h, "","30min - 1h");
			dataset.setValue(less2h,"","1h - 2h");
			dataset.setValue(more2h,"","2h++");
			
			JFreeChart chart = ChartFactory.createBarChart("Bar Chart Analysis", "Duration of Breakdown", "Number of Breakdowns", dataset,
					PlotOrientation.VERTICAL, false ,true,false);
			chart.setBackgroundPaint(Color.white);
			chart.getTitle().setPaint(Color.blue);
			CategoryPlot p = chart.getCategoryPlot();
			p.setRangeGridlinePaint(Color.GREEN);
			ChartFrame frame1 = new ChartFrame("BAR CHART",chart);
			frame1.setVisible(true);
			frame1.setSize(800,600);
			
			//Output data to a picture format file.
			File barChart = new File("MRT Breakdown Duration Bar Chart.png"); 
			try {
				ChartUtilities.saveChartAsJPEG( barChart , chart , 800 , 600 );
			}catch (IOException e1) {
				
			}
							
			}
			
		});
		btnDurationBarChart.setBounds(129, 377, 220, 33);
		jpDate.add(btnDurationBarChart);
		
		JPanel jpLine = new JPanel();
		f.getContentPane().add(jpLine, "Line");
		jpLine.setLayout(null);
		
		JButton btnPieChart = new JButton("Pie Chart");
		btnPieChart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int redLine = 0, yellowLine = 0, blueLine = 0, greenLine = 0, purpleLine = 0, otherLine = 0;
				String line = null;
				
				//Get crawled Google Sheet data from JSON file downloaded earlier.
				TrainServiceDisruptionSheetExtractor data = new TrainServiceDisruptionSheetExtractor();
				List<TrainServiceDisruptionData> x = data.getData(false);
				
				for(int i = 0; i < x.size(); i++){
					
					line = x.get(i).getLine();
					if(line.contentEquals("North-South Line"))
						redLine += 1;
					else if(line.contentEquals("East-West Line"))
						greenLine += 1;
					else if(line.contentEquals("North-East Line"))
						purpleLine += 1;
					else if(line.contentEquals("Circle Line"))
						yellowLine += 1;
					else if(line.contentEquals("Downtown Line"))
						blueLine += 1;
					else
						otherLine += 1;
					
				}

				DefaultPieDataset pie = new DefaultPieDataset();
				pie.setValue("North South Line", redLine);
				pie.setValue("East West Line", greenLine);
				pie.setValue("North East Line", purpleLine);
				pie.setValue("Circle Line", yellowLine);
				pie.setValue("Downtown Line", blueLine);
				pie.setValue("Others", otherLine);
				
				JFreeChart chart = ChartFactory.createPieChart("MRT Disruption Pie Chart", pie, true, true, true);
				PiePlot p = (PiePlot)chart.getPlot();
				p.setSectionPaint(0, Color.red);
				p.setSectionPaint(1, Color.green);
				p.setSectionPaint(2, new Color(149,10,183));
				p.setSectionPaint(3, Color.orange);
				p.setSectionPaint(4, Color.blue);
				p.setSectionPaint(5, Color.gray);
				ChartFrame frame = new ChartFrame("MRT Disruption Pie Chart", chart);
				frame.setVisible(true);
				frame.setSize(800, 600);
				
				//Output data to a picture format file.
				File pieChart = new File("MRT Disruption Pie Chart.png"); 
				try {
					ChartUtilities.saveChartAsJPEG( pieChart , chart , 800 , 600 );
				}catch (IOException e) {
					
				}
				
			}
		});
		btnPieChart.setBounds(357, 376, 92, 33);
		jpLine.add(btnPieChart);
		
		JLabel lblRedLine = new JLabel("North South Line");
		lblRedLine.setBackground(Color.RED);
		lblRedLine.setHorizontalAlignment(SwingConstants.CENTER);
		lblRedLine.setBounds(66, 46, 168, 46);
		lblRedLine.setOpaque(true);
		jpLine.add(lblRedLine);
		
		JLabel lblGreenLine = new JLabel("East West Line");
		lblGreenLine.setOpaque(true);
		lblGreenLine.setHorizontalAlignment(SwingConstants.CENTER);
		lblGreenLine.setBackground(Color.GREEN);
		lblGreenLine.setBounds(310, 46, 168, 46);
		jpLine.add(lblGreenLine);
		
		JLabel lblPurpleLine = new JLabel("North South Line");
		lblPurpleLine.setOpaque(true);
		lblPurpleLine.setHorizontalAlignment(SwingConstants.CENTER);
		lblPurpleLine.setBackground(new Color(153, 50, 204));
		lblPurpleLine.setBounds(66, 134, 168, 46);
		jpLine.add(lblPurpleLine);
		
		JLabel lblYellowLine = new JLabel("Circle Line");
		lblYellowLine.setOpaque(true);
		lblYellowLine.setHorizontalAlignment(SwingConstants.CENTER);
		lblYellowLine.setBackground(Color.ORANGE);
		lblYellowLine.setBounds(310, 134, 168, 46);
		jpLine.add(lblYellowLine);
		
		JLabel lblDowntownLine = new JLabel("Downtown Line");
		lblDowntownLine.setOpaque(true);
		lblDowntownLine.setHorizontalAlignment(SwingConstants.CENTER);
		lblDowntownLine.setBackground(Color.BLUE);
		lblDowntownLine.setBounds(66, 229, 168, 46);
		jpLine.add(lblDowntownLine);
		
		JLabel lblLrtLine = new JLabel("Other Lines");
		lblLrtLine.setOpaque(true);
		lblLrtLine.setHorizontalAlignment(SwingConstants.CENTER);
		lblLrtLine.setBackground(Color.GRAY);
		lblLrtLine.setBounds(310, 229, 168, 46);
		jpLine.add(lblLrtLine);
		
		JButton btnBack3 = new JButton("Back");
		btnBack3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				card.show(f.getContentPane(), "GoogleSheet");
			}
		});
		btnBack3.setBounds(459, 376, 92, 33);
		jpLine.add(btnBack3);
		
		JButton btnBarChart = new JButton("Bar Chart");
		btnBarChart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent Arg0) {
				int redLine = 0, yellowLine = 0, blueLine = 0, greenLine = 0, purpleLine = 0, otherLine = 0;
				String line = null;
				
				//Get Train line distruption data
				TrainServiceDisruptionSheetExtractor data = new TrainServiceDisruptionSheetExtractor();

				List<TrainServiceDisruptionData> x = data.getData(false);
				
				for(int i = 0; i < x.size(); i++){
					
					line = x.get(i).getLine();
					if(line.contentEquals("North-South Line"))
						redLine += 1;
					else if(line.contentEquals("East-West Line"))
						greenLine += 1;
					else if(line.contentEquals("North-East Line"))
						purpleLine += 1;
					else if(line.contentEquals("Circle Line"))
						yellowLine += 1;
					else if(line.contentEquals("Downtown Line"))
						blueLine += 1;
					else
						otherLine += 1;
					
				}

				DefaultCategoryDataset dataset = new DefaultCategoryDataset();
				
				dataset.setValue(redLine,"","North South Line");
				dataset.setValue(greenLine, "","East West Line");
				dataset.setValue(purpleLine,"","North East Line");
				dataset.setValue(yellowLine,"","Circle Line");
				dataset.setValue(blueLine,"","Downtown Line");
				dataset.setValue(otherLine,"","Others");
				
				JFreeChart chart = ChartFactory.createBarChart("Bar Chart Analysis", "MRT Lines", "Number of Breakdowns", dataset,
						PlotOrientation.VERTICAL, false ,true,false);
				chart.setBackgroundPaint(Color.white);
				chart.getTitle().setPaint(Color.blue);
				CategoryPlot p = chart.getCategoryPlot();
				p.setRangeGridlinePaint(Color.GREEN);
				ChartFrame frame1 = new ChartFrame("BAR CHART",chart);
				frame1.setVisible(true);
				frame1.setSize(800,600);
				
				//Output data to a picture format file.
				File barChart = new File("MRT Disruption Bar Chart.png"); 
				try {
					ChartUtilities.saveChartAsJPEG( barChart , chart , 800 , 600 );
				}catch (IOException e) {
					
				}
				
			}
			
});
		btnBarChart.setBounds(249, 376, 98, 33);
		jpLine.add(btnBarChart);

		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setBounds(117, 59, 46, 14);
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				card.show(f.getContentPane(), "Main");
			}
		});
	}
}
