package daniel.videoclub.mainpack.Activities;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Component;
import java.awt.SystemColor;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JMenu;
import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

import daniel.videoclub.mainpack.ActivityManager;
import daniel.videoclub.mainpack.FileList;
import daniel.videoclub.mainpack.FileManager;
import daniel.videoclub.mainpack.Filter;
import daniel.videoclub.mainpack.Frame;
import daniel.videoclub.mainpack.products.Film;
import daniel.videoclub.mainpack.products.Product;
import daniel.videoclub.mainpack.products.TVShow;
import daniel.videoclub.mainpack.users.Employee;

import javax.swing.JButton;
import javax.swing.JMenuBar;
import javax.swing.JList;
import javax.swing.border.LineBorder;
import javax.swing.JToggleButton;
import javax.swing.ListCellRenderer;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.JScrollPane;
import javax.swing.JComboBox;

public class CatalogManager extends JPanel {

	private static final long serialVersionUID = 1L;

	private List<Product> productList;
	private List<Employee> employeeList;

	private FileList file;
	private Filter filter;

	private JTextField searchField;
	private JComboBox comboBoxOrderBy;
	private JList swingJList;

	private JButton btnAdd;
	private JButton btnModify;
	private JButton btnRemove;
	private JButton btnReturn;
	private JButton btnRent;
	private JButton btnAvailability;
	private JButton btnSearch;
	private JButton btnSeasons;

	private static boolean isChief;
	private static String username;
	private static String mode;

	private static CatalogManager managerPanel = null;

	static {
		isChief = false;
		username = "";
		mode = "";
	}

	private CatalogManager(boolean isChief, String username, String mode) {

		CatalogManager.isChief = isChief;
		CatalogManager.username = username;
		CatalogManager.mode = mode;

		file = FileList.FILMS;

		loadGeneralSettings();

		if (mode.equals("PRODUCT")) {

			loadProductManagament();

			loadFilms();

		} else {

			loadUserManagament();

			loadUsers();

		}
	}

	private void loadUserManagament() {

		loadJLabels();

		loadGeneralButtons();

		loadJList();

		loadUserJComboBox();


	}

	private void loadProductManagament() {

		loadJLabels();

		loadGeneralButtons();

		loadProductButtons();

		loadJList();

		loadProductJComboBox();

		loadProductButtonsActionListeners();

	}

	private void loadJLabels() {

		JLabel lblCambiar = new JLabel(username);
		lblCambiar.setFont(new Font("Lucida Fax", Font.BOLD, 14));
		lblCambiar.setBounds(10, 11, 89, 26);
		add(lblCambiar);

		JLabel lblCatalogManagament = new JLabel("VideoClub Guapismo");
		lblCatalogManagament.setHorizontalAlignment(SwingConstants.CENTER);
		lblCatalogManagament.setFont(new Font("Tahoma", Font.PLAIN, 30));
		lblCatalogManagament.setBounds(407, 23, 330, 37);
		add(lblCatalogManagament);

		JLabel lblOrderBy = new JLabel("Search by");
		lblOrderBy.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblOrderBy.setHorizontalAlignment(SwingConstants.CENTER);
		lblOrderBy.setBounds(843, 59, 58, 26);
		add(lblOrderBy);

	}

	private void loadGeneralButtons() {

		btnSearch = new JButton();
		btnSearch.setBounds(786, 90, 31, 23);
		btnSearch.setIcon(new ImageIcon(FileList.IMAGES.getPath() + "SearchIcon.png"));
		add(btnSearch);

		btnAdd = new JButton("Add");
		btnAdd.setBounds(78, 189, 112, 37);
		add(btnAdd);

		btnRemove = new JButton("Remove");
		btnRemove.setEnabled(false);
		btnRemove.setBounds(78, 314, 112, 37);
		add(btnRemove);
		btnRemove.addActionListener(e -> {

			Object object = swingJList.getSelectedValue();
			
			removeProduct(swingJList.getSelectedValue());
			
			JOptionPane.showMessageDialog(null, "The element " + object + " has been removed");
			
			desactivateButtons();
		});
		btnSearch.addActionListener(e ->{
			if (mode.equals("PRODUCT")) {
			
				searchProductByField(searchField.getText());
		
			}else {
				searchUsersByField(searchField.getText());
			}
			
		});
		btnAdd.addActionListener(e ->{
			System.out.println("btn");
			if (mode.equals("PRODUCT")) {
				
				addNewProduct();
			}else {
				addNewUser();
			}
		}
			
		);	

	}

	private void loadJList() {

		swingJList = new JList();
		swingJList.setVisibleRowCount(5);
		swingJList.setFont(new Font("Tahoma", Font.PLAIN, 17));
		swingJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		swingJList.setValueIsAdjusting(true);
		swingJList.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		swingJList.setBounds(404, 213, 633, 325);
		swingJList.setAutoscrolls(true);
		swingJList.setCellRenderer(getRenderer());
		add(swingJList);

		JScrollPane scrollBar = new JScrollPane(swingJList);
		scrollBar.setSize(633, 328);
		scrollBar.setLocation(278, 122);
		add(scrollBar);

		swingJList.addListSelectionListener(e -> {
			activateButtons();

			if (mode.equals("PRODUCT")) {

				Product prod = (Product) swingJList.getSelectedValue();

				if (prod == null) {
					return;
				}

				if (prod.getNumOfRented() <= 0) {

					btnReturn.setEnabled(false);
				}

				if (prod.isAvailable()) {

					btnAvailability.setText("Available");
					btnRent.setEnabled(true);
				} else {

					btnRent.setEnabled(false);
					btnReturn.setEnabled(false);
					btnAvailability.setText("Unavailable");
				}

			}

		});

	}

	private void loadUserJComboBox() {

		comboBoxOrderBy = new JComboBox();
		comboBoxOrderBy.setBounds(827, 91, 81, 20);
		comboBoxOrderBy.addItem("ID");
		comboBoxOrderBy.addItem("USERNAME");
		add(comboBoxOrderBy);

		comboBoxOrderBy.addActionListener(e -> {

			filter = Filter.valueOf(comboBoxOrderBy.getSelectedItem().toString());

			update();
		});
		
		filter = Filter.valueOf(comboBoxOrderBy.getSelectedItem().toString());

	}

	private void loadProductButtonsActionListeners() {

		btnModify.addActionListener(e -> modifyProduct(swingJList.getSelectedValue()));

		btnSeasons.addActionListener(e -> {

			TVShow show = (TVShow) swingJList.getSelectedValue();

			Integer numOfSeason = Integer.parseInt(JOptionPane.showInputDialog("What Season?"))-1;
			
			if (numOfSeason > show.getNumOfSeasons()) {
				
				JOptionPane.showMessageDialog(null, "This tv show not contains this season");
				return;
			}

			JOptionPane.showMessageDialog(null, show.getAllChaptersFromSeason(numOfSeason));

		});

		btnAvailability.addActionListener(e -> {

			Product prod = (Product) swingJList.getSelectedValue();
			
			
			if (prod.getStock() <= 0) {

				JOptionPane.showMessageDialog(null, "Stock must be greater than 0");
				return;
			}

			desactivateButtons();
			
			if (prod.isAvailable()) {
				
				btnAvailability.setText("Unavailable");
				FileManager.editFieldFromFileWithFilter(prod.getposInFile(), file, prod.getProductWrited(), "false", Filter.AVAILABILITY);
				JOptionPane.showMessageDialog(null, "Availability has been changed to unavailable");
			} else {
				btnAvailability.setText("Available");
				FileManager.editFieldFromFileWithFilter(prod.getposInFile(), file, prod.getProductWrited(), "true",	Filter.AVAILABILITY);
				JOptionPane.showMessageDialog(null, "Availability has been changed to available");
			}
			update();
		});

		btnRent.addActionListener(e -> {

			Product product = (Product) swingJList.getSelectedValue();

			product.subStock(file);

			JOptionPane.showMessageDialog(null, "you have rented the Product " + product.getName());
			
			desactivateButtons();

			update();
		});

		btnReturn.addActionListener(e -> {

			Product product = (Product) swingJList.getSelectedValue();

			Double assessment = (Double.parseDouble(JOptionPane.showInputDialog("Score from 0 to 5")));

			if (assessment > 5) {

				JOptionPane.showMessageDialog(null, "The score cannot be greater than 5");
				return;
			}
			product.sumStock(file);
			
			desactivateButtons();

			update();

			product.addResult(assessment, file);
			
			FileManager.editFieldFromFileWithFilter(product.getposInFile(), file, product.getProductWrited(), "true", Filter.AVAILABILITY);
			
			JOptionPane.showMessageDialog(null, "The product " + product.getName() + " has been returned");
			
			update();
		});
		

		
			
		
			
		}

	

	private void loadGeneralSettings() {

		setBackground(SystemColor.activeCaption);
		setLayout(null);

		searchField = new JTextField();
		searchField.setBounds(601, 90, 175, 22);
		add(searchField);
		searchField.setColumns(10);

		JMenuBar menuBar = new JMenuBar();
		menuBar.setBorderPainted(false);
		menuBar.setBackground(SystemColor.activeCaption);
		menuBar.setBounds(109, 11, 31, 31);
		add(menuBar);

		JMenu mnNewMenu = new JMenu("");
		mnNewMenu.setIcon(new ImageIcon(FileList.IMAGES.getPath() + "GearIcon.png"));
		menuBar.add(mnNewMenu);

		if (mode.equals("PRODUCT")) {

			if (isChief) {
				
				JMenuItem mntmNewMenuItem = new JMenuItem("Users managament");
				mnNewMenu.add(mntmNewMenuItem);
				mntmNewMenuItem.addActionListener(e -> GoToUsersManagement());
			}
		} else {

			JMenuItem mntmNewMenuItem = new JMenuItem("Product managament");
			mnNewMenu.add(mntmNewMenuItem);
			mntmNewMenuItem.addActionListener(e -> GoToProductManagement());

		}
		JMenuItem mntmNewMenuItem_1 = new JMenuItem("Sign off");
		mnNewMenu.add(mntmNewMenuItem_1);
		mntmNewMenuItem_1.addActionListener(e -> signOff());

	}

	private void loadProductJComboBox() {

		comboBoxOrderBy = new JComboBox();
		comboBoxOrderBy.setBounds(827, 91, 81, 20);
		comboBoxOrderBy.addItem("TITTLE");
		comboBoxOrderBy.addItem("DIRECTOR");
		comboBoxOrderBy.addItem("RELEASEYEAR");
		comboBoxOrderBy.addItem("STOCK");
		comboBoxOrderBy.addItem("GENRE");
		
		add(comboBoxOrderBy);

		comboBoxOrderBy.addActionListener(e -> {

			filter = Filter.valueOf(comboBoxOrderBy.getSelectedItem().toString());

			update();
		});
		filter = Filter.valueOf(comboBoxOrderBy.getSelectedItem().toString());

	}

	private void loadProductButtons() {

		btnAvailability = new JButton("Unavailable");
		btnAvailability.setEnabled(false);
		btnAvailability.setBounds(78, 379, 112, 37);
		add(btnAvailability);

		btnRent = new JButton("Rent");
		btnRent.setBounds(78, 216, 112, 37);
		add(btnRent);
		btnRent.setEnabled(false);

		btnModify = new JButton("Modify");
		btnModify.setEnabled(false);
		btnModify.setBounds(78, 251, 112, 37);
		add(btnModify);

		btnReturn = new JButton("Return");
		btnReturn.setBounds(78, 309, 112, 37);
		add(btnReturn);
		btnReturn.setEnabled(false);

		btnSeasons = new JButton("See Chapters");
		btnSeasons.setEnabled(false);
		btnSeasons.setBounds(78, 251, 112, 37);
		add(btnSeasons);

		btnSeasons.setVisible(false);

		loadProductToggleButtons();

		btnAvailability.setVisible(false);
		btnAdd.setVisible(false);
		btnModify.setVisible(false);
		btnRemove.setVisible(false);

	}

	private void loadProductToggleButtons() {

		JToggleButton tglbtnPeliculas = new JToggleButton("Films");
		tglbtnPeliculas.setBackground(UIManager.getColor("Button.background"));
		tglbtnPeliculas.setSelected(true);
		tglbtnPeliculas.setBounds(278, 90, 89, 23);
		add(tglbtnPeliculas);

		JToggleButton tglbtnSeries = new JToggleButton("TV Shows");
		tglbtnSeries.setBackground(UIManager.getColor("Button.background"));
		tglbtnSeries.setBounds(369, 90, 96, 23);
		add(tglbtnSeries);

		JToggleButton tglbtnRentOrReturn = new JToggleButton("Rent or Return");
		tglbtnRentOrReturn.setSelected(true);
		tglbtnRentOrReturn.setBounds(26, 122, 105, 23);
		add(tglbtnRentOrReturn);

		JToggleButton tglbtnManagament = new JToggleButton("Managament");
		tglbtnManagament.setBounds(141, 122, 112, 23);
		add(tglbtnManagament);
		
		tglbtnPeliculas.addActionListener(e -> {
			
			tglbtnPeliculas.setSelected(true);
			tglbtnSeries.setSelected(false);
			
			loadFilms();
			
			desactivateButtons();
			
			if (!btnReturn.isVisible()) {

				btnModify.setVisible(true);
				btnSeasons.setVisible(false);
			}
			file = FileList.FILMS;
		});

		tglbtnSeries.addActionListener(e -> {

			tglbtnSeries.setSelected(true);
			tglbtnPeliculas.setSelected(false);

			comboBoxOrderBy.removeItem("GENRE");
			
			loadTVShows();
			
			desactivateButtons();
			
			if (!btnReturn.isVisible()) {

				btnModify.setVisible(false);
				btnSeasons.setVisible(true);
			}
			file = FileList.TVSHOWS;
		});

		tglbtnRentOrReturn.addActionListener(e -> {

			tglbtnRentOrReturn.setSelected(true);
			tglbtnManagament.setSelected(false);

			btnAdd.setVisible(false);
			btnModify.setVisible(false);
			btnRemove.setVisible(false);
			btnAvailability.setVisible(false);
			btnSeasons.setVisible(false);

			btnRent.setVisible(true);
			btnReturn.setVisible(true);

		});
		tglbtnManagament.addActionListener(e -> {

			tglbtnManagament.setSelected(true);
			tglbtnRentOrReturn.setSelected(false);

			btnAdd.setVisible(true);
			
			if (file == FileList.FILMS) {

				btnModify.setVisible(true);
			} else {
				btnSeasons.setVisible(true);
			}
			
			btnRemove.setVisible(true);
			btnAvailability.setVisible(true);

			btnRent.setVisible(false);
			btnReturn.setVisible(false);

		});
	}
	private void addNewUser() {
	

		ActivityManager.setActualActivity(new Form(FileList.USERLOGIN, new Employee(), "create"));
		
		Frame.refresh();
		
		this.setVisible(false);
	}

	private void modifyButtonsAvailability(Boolean option) {

		Component[] componentList = getComponents();

		for (int i = 0; i < componentList.length; i++) {

			if (componentList[i].getClass() == JButton.class) {

				JButton auxButton = (JButton) componentList[i];

				if (!auxButton.equals(btnAdd) && !auxButton.equals(btnSearch)) {

					auxButton.setEnabled(option);
				}

			}

		}

	}

	private void desactivateButtons() {

		modifyButtonsAvailability(false);

	}

	private void activateButtons() {

		modifyButtonsAvailability(true);

	}

	// Create a line between objects in the JList
	private ListCellRenderer<? super String> getRenderer() {
		return new DefaultListCellRenderer() {
			@Override
			public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
					boolean cellHasFocus) {
				JLabel listCellRendererComponent = (JLabel) super.getListCellRendererComponent(list, value, index,
						isSelected, cellHasFocus);
				listCellRendererComponent.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));
				return listCellRendererComponent;
			}
		};
	}

	private void searchProductByField(String text) {
		
		List<Product> dataListSearchResult = new ArrayList<>();

		for (Product product : productList) {

			if (text.equalsIgnoreCase(FileManager.getFieldsFromLine('#', product.getProductWrited(), file).get(filter.getPosition()))) {

				dataListSearchResult.add(product);
			}
		}

		searchField.setText("");
		
		if (dataListSearchResult.isEmpty()) {
			
			JOptionPane.showMessageDialog(null, "No result found");
			return;
		}

		if (filter == Filter.TITTLE) {
			
			orderListWithFilter(dataListSearchResult, Filter.RELEASEYEAR);

		} else {
			orderListWithFilter(dataListSearchResult, Filter.TITTLE);
		}
		swingJList.setListData(dataListSearchResult.toArray());

	}

	private void searchUsersByField(String text) {

		List<Employee> dataListSearchResult = new ArrayList<>();

		for (Employee employee : employeeList) {

			if (text.equalsIgnoreCase(FileManager.getFieldsFromLine('#', employee.getEmployeeWrited(), FileList.USERLOGIN).get(filter.getPosition()))) {

				dataListSearchResult.add(employee);
			}
		}

		searchField.setText("");
		
		if (dataListSearchResult.isEmpty()) {
			
			JOptionPane.showMessageDialog(null, "No result found");
			return;
		}
		swingJList.setListData(dataListSearchResult.toArray());

	}

	private void addNewProduct() {

		if (file == FileList.FILMS) {

			ActivityManager.setActualActivity(new Form(file, new Film(), "create"));
			
		} else {
			ActivityManager.setActualActivity(new Form(file, new TVShow(), "create"));
		}

		Frame.refresh();
		
		this.setVisible(false);
	}

	private void modifyProduct(Object object) {

		ActivityManager.setActualActivity(new Form(file, object, "modify"));
		
		Frame.refresh();
		
		this.setVisible(false);
	}

	public void update() {
		

		if (mode.equals("PRODUCT")) {

			if (file.equals(FileList.FILMS)) {

				loadFilms();

			} else {
				
				loadTVShows();
			}
		} else {

			loadUsers();
		}
	}

	private void loadFilms() {

		Film.reset();

		List<String> dataList = FileManager.loadFileDataIntoList(FileList.FILMS);
		productList = new ArrayList<>();

		for (String line : dataList) {

			if (line.equals("")) {
				continue;
			}
			productList.add(new Film(line));
		}

		orderListWithFilter(productList, filter);

		swingJList.setListData(productList.toArray());
	}

	private void loadUsers() {

		Employee.reset();

		List<String> dataList = FileManager.loadFileDataIntoList(FileList.USERLOGIN);
		employeeList = new ArrayList<>();

		for (String line : dataList) {

			if (line.equals("")) {
				continue;
			}
			employeeList.add(new Employee(line));
		}

		orderListWithFilter(employeeList, filter);

		swingJList.setListData(employeeList.toArray());
	}

	private void loadTVShows() {

		TVShow.reset();

		List<String> dataList = FileManager.loadFileDataIntoList(FileList.TVSHOWS);
		productList = new ArrayList<>();

		for (String line : dataList) {

			if (line.equals("")) {
				continue;
			}

			productList.add(new TVShow(line));
		}

		orderListWithFilter(productList, filter);
		swingJList.setListData(productList.toArray());
	}

	private <T> void orderListWithFilter(List<T> list, Filter filter) {

		switch (filter) {

		case TITTLE:
			list.sort((Comparator<? super T>) Comparator.comparing(Product::getName));

			break;
		case RELEASEYEAR:
			list.sort((Comparator<? super T>) Comparator.comparing(Product::getReleaseYear).reversed());

			break;
		case DIRECTOR:
			list.sort((Comparator<? super T>) Comparator.comparing(Product::getDirector));

			break;

		case GENRE:
			list.sort((Comparator<? super T>) Comparator.comparing(Film::getGenre));

			break;

		case AVAILABILITY:
			list.sort((Comparator<? super T>) Comparator.comparing(Product::isAvailable));

			break;
		case STOCK:
			list.sort((Comparator<? super T>) Comparator.comparing(Product::getStock).reversed());

			break;
		case USERNAME:
			list.sort((Comparator<? super T>) Comparator.comparing(Employee::getUsername));

			break;
		case ID:
			list.sort((Comparator<? super T>) Comparator.comparing(Employee::getID));

			break;

		default:
			throw new RuntimeException("Explota explota me explo, explota explota mi corasón");

		}

	}

	private void setProductToShow(String product) {

		if (product.equals("TVShows")) {

			loadTVShows();
		}

	}

	private void removeProduct(Object object) {

		if (object.getClass() == Film.class) {

			FileManager.removeLineFromFile(((Product) object).getposInFile(), FileList.FILMS);
			loadFilms();

		} else if (object.getClass() == TVShow.class) {

			FileManager.removeLineFromFile(((Product) object).getposInFile(), FileList.TVSHOWS);

			loadTVShows();
		} else if (object.getClass() == Employee.class) {

			Employee user = (Employee) object;

			if (user.getID() == 1000) {

				JOptionPane.showMessageDialog(null, "Cannot remove the chief");
				return;
			}
			FileManager.removeLineFromFile(user.getposInFile(), FileList.USERLOGIN);
			loadUsers();
		}

	}

	private void GoToUsersManagement() {

		mode = "USERS";

		ActivityManager.setActualActivity(new CatalogManager(isChief, username, mode));
		Frame.refresh();
		this.setVisible(false);

	}

	private void GoToProductManagement() {

		mode = "PRODUCT";

		ActivityManager.setActualActivity(new CatalogManager(isChief, username, mode));
		Frame.refresh();
		this.setVisible(false);
	}

	private void signOff() {

		ActivityManager.setActualActivity(new Login());
		Frame.refresh();
		this.setVisible(false);

	}

	public static CatalogManager getInstance() {

		if (managerPanel == null) {

			throw new RuntimeException("PUUUM!");
		}
		return new CatalogManager(isChief, username, mode);// Load with the privious instance
	}

	public static CatalogManager getInstance(boolean isChief, String username, String mode) {

		if (managerPanel == null) {

			managerPanel = new CatalogManager(isChief, username, mode);
		}
		return new CatalogManager(isChief, username, mode);// new instance;
	}
}
