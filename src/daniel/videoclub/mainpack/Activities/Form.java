package daniel.videoclub.mainpack.Activities;

import java.awt.Font;
import java.awt.SystemColor;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;

import daniel.videoclub.mainpack.ActivityManager;
import daniel.videoclub.mainpack.FileList;
import daniel.videoclub.mainpack.FileManager;
import daniel.videoclub.mainpack.Frame;
import daniel.videoclub.mainpack.products.Film;
import daniel.videoclub.mainpack.products.Product;
import daniel.videoclub.mainpack.products.TVShow;
import daniel.videoclub.mainpack.users.Employee;

public class Form extends JPanel {


	private static final long serialVersionUID = 1L;
	
	private JTextField textField_1;
	private JTextField textField_2;
	private JComboBox comboBox;
	private JComboBox comboBox_1;
	private JComboBox comboBox_2;
	private JSpinner spinnerStock;
	private JSpinner spinnerSeason;
	private ButtonGroup availability;
	private FileList fileToEdit;
	private String buttonResult;
	private String formTarget;
	private Product productData;
	private Employee employee;

	private List<String> formResult;

	public Form(FileList file, Object object, String formTarget) {

		buttonResult = "true";
		this.formTarget = formTarget;
		this.fileToEdit = file;
		formResult = new ArrayList<>();

		loadGeneralSettings();

		if (object.getClass() == Film.class || object.getClass() == TVShow.class
				|| object.getClass() == Product.class) {

			productData = (Product) object;

			loadProductForm(productData);

		} else {

			employee = (Employee) object;
			loadUserloginForm(employee);
		}

	}

	private void loadGeneralSettings() {

		setBackground(SystemColor.activeCaption);
		setLayout(null);

	}

	private void loadUserButtons() {
		
		JButton btnSend = new JButton("Confirm");
		btnSend.setBounds(500, 332, 95, 31);
		add(btnSend);

		JButton btnCancel = new JButton("Cancel");
		btnCancel.setBounds(333, 332, 89, 31);
		add(btnCancel);
		btnCancel.addActionListener(e -> backToPreviousActivity());

		btnSend.addActionListener(e -> {
			formResult.add(String.valueOf(Employee.getLastID() + 1000));
			formResult.add(textField_1.getText());
			formResult.add(textField_2.getText());

			if (!isDataVoid()) {
				String data = loadDataInString();

				if (isEmployeeCreated(data)) {
					JOptionPane.showMessageDialog(null, "Employee already exists");
					backToPreviousActivity();
					return;
				}

				if (formTarget == "create") {

					FileManager.addNewDataToFile(data, fileToEdit);
					JOptionPane.showMessageDialog(null, "User created correctly");
				}

				backToPreviousActivity();
			} else {

				JOptionPane.showMessageDialog(null, "The fields cannot be empty");
				formResult.removeAll(formResult);

			}

		});

	}

	private boolean isEmployeeCreated(String employee) {

		List<String> dataList = FileManager.loadFileDataIntoList(FileList.USERLOGIN);

		for (String line : dataList) {

			if (line.substring(5).equalsIgnoreCase(employee.substring(5))) {

				return true;
			}

		}
		return false;

	}

	private void loadUserloginForm(Employee employee2) {

		loadUserButtons();
		textField_1 = new JTextField(employee2.getUsername());
		textField_1.setColumns(10);
		textField_1.setBounds(333, 171, 264, 31);
		add(textField_1);

		textField_2 = new JTextField("");
		textField_2.setColumns(10);
		textField_2.setBounds(333, 244, 264, 31);
		add(textField_2);

		JLabel lblUsername = new JLabel("Username:");
		lblUsername.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblUsername.setHorizontalAlignment(SwingConstants.RIGHT);
		lblUsername.setBounds(234, 177, 76, 14);
		add(lblUsername);

		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblPassword.setBounds(234, 250, 74, 14);
		add(lblPassword);

		JLabel lblUsersManagament = new JLabel("Users Managament");
		lblUsersManagament.setHorizontalAlignment(SwingConstants.CENTER);
		lblUsersManagament.setFont(new Font("Tahoma", Font.PLAIN, 24));
		lblUsersManagament.setBounds(345, 80, 228, 59);
		add(lblUsersManagament);

	}

	private void loadProductForm(Product product) {

		loadProductButtons();

		textField_1 = new JTextField(product.getName());
		textField_1.setColumns(10);
		textField_1.setBounds(333, 151, 264, 31);
		add(textField_1);

		textField_2 = new JTextField(product.getDirector());
		textField_2.setColumns(10);
		textField_2.setBounds(333, 224, 264, 31);
		add(textField_2);

		comboBox = new JComboBox();
		comboBox.setBounds(333, 193, 67, 20);

		comboBox.addItem(product.getReleaseYear());
		for (int i = 2020; i > 1900; i--) {
			comboBox.addItem(i);
		}
		add(comboBox);

		SpinnerModel spinnerModel = new SpinnerNumberModel(product.getStock(), 0, 100, 1);
		spinnerStock = new JSpinner(spinnerModel);
		spinnerStock.setFont(new Font("Tahoma", Font.PLAIN, 14));
		spinnerStock.setBounds(520, 193, 67, 20);
		add(spinnerStock);

		if (product.getClass() == Film.class) {

			Film film = (Film) product;
			comboBox_1 = new JComboBox();
			comboBox_1.setBounds(333, 269, 89, 20);
			comboBox_1.addItem(film.getGenre());
			comboBox_1.addItem("Science Fiction");
			comboBox_1.addItem("Animation");
			add(comboBox_1);
			comboBox_2 = new JComboBox();
			comboBox_2.setBounds(530, 268, 67, 23);
			comboBox_2.addItem(film.getSubGenre());
			comboBox_2.addItem("none");
			add(comboBox_2);
			comboBox_1.addActionListener(e -> {

				if (comboBox_1.getSelectedItem().equals("Animation")) {
					comboBox_2.removeAllItems();
					comboBox_2.addItem("Traditional");
					comboBox_2.addItem("2D animation");
					comboBox_2.addItem("3D animation");
					comboBox_2.addItem("Clay animation.");
					comboBox_2.addItem("Stop-motion animation");
				} else {
					comboBox_2.removeAllItems();
					comboBox_2.addItem("none");

				}

			});

			JLabel lblGenre = new JLabel("Genre:");
			lblGenre.setHorizontalAlignment(SwingConstants.RIGHT);
			lblGenre.setFont(new Font("Tahoma", Font.PLAIN, 15));
			lblGenre.setBounds(248, 272, 61, 14);
			add(lblGenre);

			JLabel lblSubgenre = new JLabel("SubGenre:");
			lblSubgenre.setHorizontalAlignment(SwingConstants.RIGHT);
			lblSubgenre.setFont(new Font("Tahoma", Font.PLAIN, 13));
			lblSubgenre.setBounds(432, 268, 88, 20);
			add(lblSubgenre);
		} else {

			TVShow show = (TVShow) product;

			SpinnerModel spinnerModeltoTVShows = new SpinnerNumberModel(show.getNumOfSeasons(), 1, 1000, 1);

			spinnerSeason = new JSpinner(spinnerModeltoTVShows);
			spinnerSeason.setFont(new Font("Tahoma", Font.PLAIN, 14));
			spinnerSeason.setBounds(333, 269, 89, 20);
			add(spinnerSeason);

			JLabel lblSeason = new JLabel("Season: ");
			lblSeason.setHorizontalAlignment(SwingConstants.RIGHT);
			lblSeason.setFont(new Font("Tahoma", Font.PLAIN, 15));
			lblSeason.setBounds(248, 272, 61, 14);
			add(lblSeason);

		}

		JLabel lblFilmManagament = new JLabel("Product Managament");
		lblFilmManagament.setHorizontalAlignment(SwingConstants.CENTER);
		lblFilmManagament.setFont(new Font("Tahoma", Font.PLAIN, 24));
		lblFilmManagament.setBounds(345, 70, 228, 59);
		add(lblFilmManagament);

		JLabel lblStock = new JLabel("Stock:");
		lblStock.setHorizontalAlignment(SwingConstants.CENTER);
		lblStock.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblStock.setBounds(450, 193, 67, 20);
		add(lblStock);

		JLabel lblTittle = new JLabel("Tittle:");
		lblTittle.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblTittle.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTittle.setBounds(234, 157, 76, 14);
		add(lblTittle);

		JLabel lblDirector = new JLabel("Director:");
		lblDirector.setHorizontalAlignment(SwingConstants.RIGHT);
		lblDirector.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblDirector.setBounds(234, 230, 74, 14);
		add(lblDirector);

		JLabel lblReleaseYear = new JLabel("Release Year:");
		lblReleaseYear.setHorizontalAlignment(SwingConstants.RIGHT);
		lblReleaseYear.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblReleaseYear.setBounds(203, 191, 107, 20);
		add(lblReleaseYear);

		availability = new ButtonGroup();

		if (product.isAvailable()) {

		}

		JRadioButton rdbtnAvailable = new JRadioButton("Available");
		rdbtnAvailable.setBackground(SystemColor.activeCaption);
		rdbtnAvailable.setFont(new Font("Tahoma", Font.BOLD, 12));
		rdbtnAvailable.setSelected(true);
		rdbtnAvailable.setBounds(360, 308, 109, 23);
		add(rdbtnAvailable);
		rdbtnAvailable.addActionListener(e -> buttonResult = "true");
		availability.add(rdbtnAvailable);

		JRadioButton rdbtnInavailable = new JRadioButton("Inavailable");
		rdbtnInavailable.setBackground(SystemColor.activeCaption);
		rdbtnInavailable.setFont(new Font("Tahoma", Font.BOLD, 12));
		rdbtnInavailable.setBounds(471, 308, 109, 23);
		add(rdbtnInavailable);
		availability.add(rdbtnInavailable);
		rdbtnInavailable.addActionListener(e -> buttonResult = "false");

		JLabel lblAvailability = new JLabel("Availability:");
		lblAvailability.setHorizontalAlignment(SwingConstants.RIGHT);
		lblAvailability.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblAvailability.setBounds(214, 311, 95, 14);
		add(lblAvailability);

	}

	private void loadProductButtons() {

		JButton btnSend = new JButton("Confirm");
		btnSend.setBounds(500, 362, 95, 31);
		add(btnSend);

		btnSend.addActionListener(e -> {

			formResult.add(textField_1.getText());
			formResult.add(comboBox.getSelectedItem().toString());
			formResult.add(textField_2.getText());
			if (productData.getClass() == Film.class) {

				formResult.add(comboBox_1.getSelectedItem().toString());
				formResult.add(comboBox_2.getSelectedItem().toString());
			} else {
				formResult.add(spinnerSeason.getValue().toString());
				formResult.add("no");
			}
			if ((int) spinnerStock.getValue() <= 0) {
				JOptionPane.showMessageDialog(null,
						"The status of the product has been changed to unavailable because it does not have stock");
				formResult.add("false");
			} else {
				formResult.add(buttonResult);

			}

			formResult.add(spinnerStock.getValue().toString());
			formResult.add(String.valueOf(productData.getNumOfRented()));
			formResult.add("0.0");
			formResult.add("0.0");
			if (productData.getClass() == TVShow.class) {

				TVShow show = (TVShow) productData;
				formResult.add(show.createSeasons((Integer) spinnerSeason.getValue()));
			}
			if (!isDataVoid()) {

				String data = loadDataInString();
				if (formTarget == "create") {

					System.out.println("AÑADIDO NUEVOS DATOS : " + data);
					FileManager.addNewDataToFile(data, fileToEdit);
					JOptionPane.showMessageDialog(null, "Product created correctly");

				} else {
					System.out.println(productData.getposInFile());

					FileManager.editLineFromFileWithString(productData.getposInFile(), fileToEdit, data);

					JOptionPane.showMessageDialog(null, "Product modified correctly");

				}

				backToPreviousActivity();
			} else {

				JOptionPane.showMessageDialog(null, "The fields cannot be empty");
				formResult.removeAll(formResult);

			}

		});

		JButton btnCancel = new JButton("Cancel");
		
		btnCancel.setBounds(333, 362, 89, 31);
		
		add(btnCancel);
		
		btnCancel.addActionListener(e -> backToPreviousActivity());

	}

	private boolean isDataVoid() {

		for (String data : formResult) {

			if (data.length() <= 0) {
				return true;
			}

		}
		return false;
	}

	private String loadDataInString() {

		StringBuilder dataProduct = new StringBuilder();

		for (String field : formResult) {

			dataProduct.append(field + "#");

		}

		return dataProduct.toString();
	}

	private void backToPreviousActivity() {

		ActivityManager.setActualActivity(CatalogManager.getInstance());
		
		Frame.refresh();
		
		this.setVisible(false);

	}

}
