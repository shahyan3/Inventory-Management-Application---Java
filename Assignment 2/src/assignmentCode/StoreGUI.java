/**
 * 
 */
package assignmentCode;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

/**
 * The gui and main entry point of the application
 * @author Shahyan
 * @version 1.0
 */
public class StoreGUI extends JFrame implements ActionListener, Runnable {
	private static final long serialVersionUID = -7031008862559936404L;
	public static final int WIDTH = 1000;
	//524 may seem like an odd choice - it sets up the scroll pane to be the exact size of the table when
	//item_properties.csv  is loaded (24 rows, plus headings). On a windows 10 machine anyway.
	public static final int HEIGHT = 524;
	

	private JScrollPane pnlDisplay;
	private JPanel pnlLbl;
	private JPanel pnlFour;
	private JPanel pnlFive;
	
	private JButton btnLoadItemProperties;
	private JButton btnGenerateManifest;
	private JButton btnLoadSales;
	private JButton btnLoadManifest;
	private JPanel pnlBtn;
	
	private JLabel lblName;
	private JLabel lblCapital;
	
	private JTable tblDisplay;
	private DefaultTableModel model;
	
	private Store store;
	private manifestGenerator mGenerator; 
	private manifestParser mParser;
	private SalesLogParser sParser;
	
	private boolean hasLoadedProperties;
	
	private JFileChooser fileChooser;
	private FileNameExtensionFilter filter;
	private JFileChooser dirChooser;
	private File workingDirectory;
	
	/**
	 * @param title The title of the application
	 * @throws HeadlessException
	 */
	public StoreGUI(String title) throws HeadlessException {
		super(title);
		// initialise all the widgets and vaariables and classes
		fileChooser = new JFileChooser();
		filter = new FileNameExtensionFilter("CSV Files Only","csv");
		fileChooser.setFileFilter(filter);
		workingDirectory = new File(System.getProperty("user.dir"));
		fileChooser.setCurrentDirectory(workingDirectory);
		
		dirChooser = new JFileChooser();
		dirChooser.setCurrentDirectory(new java.io.File("."));
		dirChooser.setDialogTitle("Select the directory where you want the manifest to save");
		dirChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		dirChooser.setAcceptAllFileFilterUsed(false);
		dirChooser.setCurrentDirectory(workingDirectory);
	
		store = Store.getInstance();
		mGenerator = new manifestGenerator(); 
		mParser = new manifestParser();
		sParser = new SalesLogParser();
		hasLoadedProperties=false;
	}
	/**
	 * Build the GUI window
	 */
	private void createGUI() {
		setSize(WIDTH, HEIGHT);
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    setLayout(new BorderLayout());
	   
	    tblDisplay = createTable();
	    pnlDisplay = createScrollPane(tblDisplay);
	    pnlLbl = createPanel(Color.LIGHT_GRAY);
	    pnlBtn = createPanel(Color.LIGHT_GRAY);
	    pnlFour = createPanel(Color.LIGHT_GRAY);
	    pnlFive = createPanel(Color.LIGHT_GRAY);
	    
	    btnLoadItemProperties = createButton("Load Item Properties");
	    btnGenerateManifest = createButton(" Generate Manifest");
	    btnLoadSales = createButton(" Load Sales Log CSV ");
	    btnLoadManifest = createButton("Load Manifest CSV");
	  
	    pnlDisplay.setLayout(new ScrollPaneLayout());
	    
	    lblName = createLabel("Welcome to the "+store.getName()+ " ordering system");
	    lblCapital = createLabel(String.format("Capital $%.2f",store.getCapital()));
	    layoutButtonPanel(); 
	    layoutLabelPanel();
	    
	    this.getContentPane().add(pnlDisplay,BorderLayout.CENTER);
	    this.getContentPane().add(pnlLbl,BorderLayout.NORTH);
	    this.getContentPane().add(pnlBtn,BorderLayout.SOUTH);
	    this.getContentPane().add(pnlFour,BorderLayout.EAST);
	    this.getContentPane().add(pnlFive,BorderLayout.WEST);
	    repaint(); 
	    this.setVisible(true);
	}
	
	/**
	 * Create a panel in which to store and arrange widgets
	 * @param c The background colour of the panel
	 * @return the panel object
	 */
	private JPanel createPanel(Color c) {
		JPanel jp = new JPanel();
		jp.setBackground(c);
		return jp;
	}
	/**
	 * Create a scrollpane to house the main stock table. 
	 * @param table The table to be housed
	 * @return the scrollpane object
	 */
	private JScrollPane createScrollPane(JTable table) {
		JScrollPane sp = new JScrollPane(table);
		return sp;
	}
	/**
	 * Create a button with which mwe may interact with the gui
	 * @param str
	 * @return the button object
	 */
	private JButton createButton(String str) {
		JButton jb = new JButton(str); 
		jb.addActionListener(this);
		return jb; 
	}
	/**
	 * Create a label with which to display information
	 * @param str the button label
	 * @return the button object
	 */
	private JLabel createLabel(String str) {
		JLabel jl = new JLabel(str);
		jl.setFont(new Font("Times", Font.BOLD, 24));
		return jl;
	}
	/**
	 * Create the table that hold the data of the application
	 * @return the table object
	 */
	private JTable createTable() {
		
		String[] columnNames = {"Item Name",
								"Manufacturing Cost",
								"Sale Price",
								"Reorder Point",
								"Reorder Amount",
								"Minimum Temp",
								"Quantity in Stock"
		};
		
		String[][] data = {};
		
		model = new DefaultTableModel(data,columnNames);
		JTable jt = new JTable(model);	
		jt.setDefaultEditor(Object.class, null);
		return jt;
		
	}
/**
 * Lays out the panel the labels are held in
 */
	private void layoutLabelPanel() {
		GridBagLayout layout = new GridBagLayout();
		pnlLbl.setLayout(layout);
		
		//add components to grid
		GridBagConstraints constraints = new GridBagConstraints();
		//Defaults
		constraints.fill = GridBagConstraints.NONE;
		constraints.anchor = GridBagConstraints.CENTER;
		constraints.weightx = 100;
		constraints.weighty = 100;
		
		addToPanel(pnlLbl,lblName,constraints,0,0,2,1);
		addToPanel(pnlLbl,lblCapital,constraints,3,0,2,1);
		} 

	/**
	 * Lays out the panel the buttons are held in
	 */
	private void layoutButtonPanel() {
		GridBagLayout layout = new GridBagLayout();
	    pnlBtn.setLayout(layout);
	    
	    //add components to grid
	    GridBagConstraints constraints = new GridBagConstraints(); 
	    
	    //Defaults
	    constraints.fill = GridBagConstraints.NONE;
	    constraints.anchor = GridBagConstraints.SOUTH;
	    constraints.weightx = 100;
	    constraints.weighty = 100;
	    
	    addToPanel(pnlBtn, btnLoadItemProperties,constraints,0,0,2,1); 
	    addToPanel(pnlBtn, btnGenerateManifest,constraints,3,0,2,1); 
	    addToPanel(pnlBtn, btnLoadSales,constraints,0,2,2,1); 
	    addToPanel(pnlBtn, btnLoadManifest,constraints,3,2,2,1); 	
	}
	/**
	 * Updates the table with new data.
	 */
	private void updateTable() {
		Stock stock = store.getStock();
		
		String[][] data = new String[stock.get().size()][];
		for (int i=0;i<stock.get().size();i++) {
			Item item = stock.get().get(i);
			String temp = null;
			if (!(item.getItemTemp()==null)) {
				temp = item.getItemTemp().toString();
			}
			
			String[] details = {item.getName(),
					item.getCost().toString(),
					item.getSellPrice().toString(),
					item.getReorderPoint().toString(),
					item.getReorderAmount().toString(),
					temp,
					item.getItemQuantity().toString()
					};
			data[i]=details;
		}
		
		String[] columnNames = {"Item Name",
				"Manufacturing Cost",
				"Sale Price",
				"Reorder Point",
				"Reorder Amount",
				"Minimum Temp",
				"Quantity in Stock"
		};
		model.setDataVector(data, columnNames);
	}
	/**
     * 
     * A convenience method to add a component to given grid bag
     * layout locations. Code due to Cay Horstmann 
     *
     * @param c the component to add
     * @param constraints the grid bag constraints to use
     * @param x the x grid position
     * @param y the y grid position
     * @param w the grid width
     * @param h the grid height
     */
   private void addToPanel(JPanel jp,Component c, GridBagConstraints constraints, int x, int y, int w, int h) {  
      constraints.gridx = x;
      constraints.gridy = y;
      constraints.gridwidth = w;
      constraints.gridheight = h;
      jp.add(c, constraints);
   }

	
/**
 * Runs the application
 */
	@Override
	public void run() {
		createGUI(); 
	}

/**
 * When actions happen, do something
 */
	@Override
	public void actionPerformed(ActionEvent e) {
		//Get event source 
		Object src=e.getSource(); 
		      
		//Consider the alternatives - not all active at once. 
		// If we want to load a manifest
		if (src== btnLoadManifest) {
			//Check if we have loaded item properties
			if (hasLoadedProperties==false) {
				JOptionPane.showMessageDialog(this,"You need to initialise properties first",
						"GUI error",JOptionPane.ERROR_MESSAGE);
			}else {
				// if we have, get the manifest file and make sure it exists
				int returnVal = fileChooser.showOpenDialog(getParent());
				if(returnVal == JFileChooser.APPROVE_OPTION) {
					File f = new File (fileChooser.getSelectedFile().getPath());
					if (f.exists()&& !f.isDirectory()) {
						// if the file exists, parse it, process it, update gui and notify the user
						try {
							mParser.parse(fileChooser.getSelectedFile().getPath());
							store.deliver(mParser.getManifest());
							updateTable();
							JOptionPane.showMessageDialog(this,"You've just loaded "+fileChooser.getSelectedFile().getName()+" from: "+
									fileChooser.getSelectedFile().getPath(),
									"Manifest Loaded Successfully",JOptionPane.INFORMATION_MESSAGE);
						} catch (FileNotFoundException e1) {
							JOptionPane.showMessageDialog(this,"The file didn't exist",
									"File Error",JOptionPane.ERROR_MESSAGE);
						} catch (CSVFormatException e1) {
							JOptionPane.showMessageDialog(this,e1.getMessage(),
									"CSV Format Error",JOptionPane.ERROR_MESSAGE);
						} catch (DeliveryException e1) {
							JOptionPane.showMessageDialog(this,e1.getMessage(),
									"Delivery error",JOptionPane.ERROR_MESSAGE);
						}
					}else {
							JOptionPane.showMessageDialog(this,"The file does not exist",
									"File Error",JOptionPane.ERROR_MESSAGE);
						}
				
					 
				}else {
					JOptionPane.showMessageDialog(this,"You did not pick a manifest. Nothing has been loaded.",
							"Manifest Loading Failed",JOptionPane.WARNING_MESSAGE);
				}
				
				fileChooser.setCurrentDirectory(workingDirectory);
				lblCapital.setText(String.format("Capital $%.2f",store.getCapital()));
				
			
			}
			
		
		
		// if we want to load in tem properties
		} else if (src==btnLoadItemProperties) {
			if (hasLoadedProperties==false) {
				//If we havent already done it, choose a file and make sure it exists
				int returnVal = fileChooser.showOpenDialog(getParent());
				if(returnVal == JFileChooser.APPROVE_OPTION) {		
						File f = new File (fileChooser.getSelectedFile().getPath());
						
						if (f.exists()&& !f.isDirectory()) {
							// if it exists, parse and process, update the gui. 
							try {
								store.initialiseItems(fileChooser.getSelectedFile().getPath());
								updateTable();
								hasLoadedProperties=true;
								JOptionPane.showMessageDialog(this,"You've just loaded "+fileChooser.getSelectedFile().getName()+" from: "+
										fileChooser.getSelectedFile().getPath(),
										"Item Properties Loaded Successfully",JOptionPane.INFORMATION_MESSAGE);
							} catch (CSVFormatException e1) {
								JOptionPane.showMessageDialog(this,e1.getMessage(),
										"CSV Format Error",JOptionPane.ERROR_MESSAGE);
							}

						}
						else {
							JOptionPane.showMessageDialog(this,"The file does not exist",
									"File Error",JOptionPane.ERROR_MESSAGE);
						}
					
				}
				else {
					JOptionPane.showMessageDialog(this,"You did not pick a properties file. Nothing has been loaded.",
							"Loading Item Properties Failed",JOptionPane.WARNING_MESSAGE);
				}
				
				fileChooser.setCurrentDirectory(workingDirectory);
				
			}else {
				JOptionPane.showMessageDialog(this,"You have already initialised item properties",
						"GUI error",JOptionPane.ERROR_MESSAGE);
			}
		
		
		
		// if we want to generate a manifest and write to a csv
		} else if (src==btnGenerateManifest) {
			if (hasLoadedProperties==false) {
				JOptionPane.showMessageDialog(this,"You need to initialise properties first",
						"GUI error",JOptionPane.ERROR_MESSAGE);
			}
			else {
				mGenerator.generate(store.getStock());
				String input = JOptionPane.showInputDialog(null, "What do you want the manifest to be called (no extension)");
			    if (input!=null) {
					if (input.equals("")) {input="Default";}
			    	if (dirChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			    		try {
			    			mParser.write(mGenerator.get(),"docs/OUTPUTS/",input+".csv");
			    		} catch (IOException e1) {
			    			JOptionPane.showMessageDialog(this,"Could not write to file",
			    					"GUI error",JOptionPane.ERROR_MESSAGE);
			    		}
				
			    		JOptionPane.showMessageDialog(this,"Wrote manifest as: "+input+" at location: "+dirChooser.getSelectedFile() ,
			    				"Successfully Wrote Manifest",JOptionPane.INFORMATION_MESSAGE);
			    	} else {
			    		JOptionPane.showMessageDialog(this,"You did not pick a location. No manifest was generated or written to a file" ,
									"Failed to Make Manifest",JOptionPane.WARNING_MESSAGE);
			      
			    	}
			    }
			    else {
			    	JOptionPane.showMessageDialog(this,"You cancelled. No manifest was generated or written to a file" ,
							"Failed to Make Manifest",JOptionPane.WARNING_MESSAGE);
			    }
			    	
						
			}
		
	
		} else if (src==btnLoadSales) {
			if (hasLoadedProperties==false) {
				JOptionPane.showMessageDialog(this,"You need to initialise properties first",
						"GUI error",JOptionPane.ERROR_MESSAGE);
			}else {
				int returnVal = fileChooser.showOpenDialog(getParent());
				if(returnVal == JFileChooser.APPROVE_OPTION) {
					File f = new File (fileChooser.getSelectedFile().getPath());
					if (f.exists()&& !f.isDirectory()) {	   							
						try {
							sParser.parse(fileChooser.getSelectedFile().getPath());
							store.processSale(sParser.getStock());
							updateTable();
							JOptionPane.showMessageDialog(this,"You've just loaded "+fileChooser.getSelectedFile().getName()+" from: "+
									fileChooser.getSelectedFile().getPath(),
									"Sales Log Loaded Successfully",JOptionPane.INFORMATION_MESSAGE);
							fileChooser.setCurrentDirectory(workingDirectory);
							lblCapital.setText(String.format("Capital $%.2f",store.getCapital()));
							
							} catch (FileNotFoundException e1) {
								JOptionPane.showMessageDialog(this,"The file you selected could not be found",
									"File error",JOptionPane.ERROR_MESSAGE);
							} catch (CSVFormatException e1) {
								JOptionPane.showMessageDialog(this,e1.getMessage(),
										"CSV Format Error",JOptionPane.ERROR_MESSAGE);
							}
							catch (StockException e1) {
								JOptionPane.showMessageDialog(this,e1.getMessage(),
										"Sale Error",JOptionPane.ERROR_MESSAGE);
							}

					}else {							
						JOptionPane.showMessageDialog(this,"The file does not exist",
							"File Error",JOptionPane.ERROR_MESSAGE);
					}
				}
			
			}
						
		}
	}
	
	
	/**Entry point of the application
	 * @param args
	 */
	public static void main(String[] args) {
		Store store = Store.getInstance();
		store.setInitialCapital(100000);
		store.setName("SuperMart");
		String name = store.getName();
	    JFrame.setDefaultLookAndFeelDecorated(true);
        SwingUtilities.invokeLater(new StoreGUI(store.getName()+ " Inventory UI"));
	}

}
