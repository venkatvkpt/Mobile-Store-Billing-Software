package application;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.nio.file.Files;
import java.text.DecimalFormat;
import java.util.ResourceBundle;

import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.Sides;

import beans.BillingBean;
import beans.ProductBean;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import utils.InvoiceGenerator;
import utils.ValidationFields;

public class BillController implements Initializable{

	private static DecimalFormat df = new DecimalFormat(".##");
	@FXML
	private TextField invoice,cName,cContact;
	@FXML
	private TextArea cAddress;
	@FXML
	private TextField pName,pBrand,pSerial,pPrice,pQty,ptotalPrice;
	@FXML
	private ComboBox<String> pType;
	@FXML
	private Label lbl_pSerial;
	@FXML
	private Button add_product,clear_product,new_bill,save_bill,print_bill;
	@FXML
	private TableView<ProductBean> addTable;
	@FXML
	private TableColumn<ProductBean, String> typeCol;
	@FXML
	private TableColumn<ProductBean, String> pDescriptionCol;
	@FXML
	private TableColumn<ProductBean, String> serialCol;
	@FXML
	private TableColumn<ProductBean, Double> unitPriceCol;
	@FXML
	private TableColumn<ProductBean, Integer> noOfUnitsCol;
	@FXML
	private TableColumn<ProductBean, Double> totalPriceCol;
	@FXML
	private TableColumn<ProductBean, String> eventCol;	
	@FXML
	private TextField subTotal,discount,discountTotal,tax,taxTotal,total;


	ObservableList<String> pTypeList = FXCollections.observableArrayList("Mobile","Accessories","Other");

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		invoice.setText("XE4289");
		pType.setItems(pTypeList);
		add_product.setCursor(Cursor.HAND);
		clear_product.setCursor(Cursor.HAND);
		tableColumns();
		onlyNumericTextField();
		productTotalChange();
		percentChange();
		setTotalPrice();
	}

	public void onlyNumericTextField() {
		pPrice.textProperty().addListener((observable, oldValue, newValue) -> {
			if(!newValue.matches("[0-9.]*")){
				pPrice.setText(oldValue);
			}
		});
		pQty.textProperty().addListener((observable, oldValue, newValue) -> {
			if(!newValue.matches("[0-9]*")){
				pQty.setText(oldValue);
			}
		});
		discount.textProperty().addListener((observable, oldValue, newValue) -> {
			if(!newValue.matches("[0-9.]*")){
				discount.setText(oldValue);
			}
		});
		tax.textProperty().addListener((observable, oldValue, newValue) -> {
			if(!newValue.matches("[0-9.]*")){
				tax.setText(oldValue);
			}
		});
		cContact.textProperty().addListener((observable, oldValue, newValue) -> {
			if(!newValue.matches("[0-9]*")){
				cContact.setText(oldValue);
			}
		});
	}

	public void onChangepType(ActionEvent event) {
		try {
			if(pType.getValue().equals("Mobile")) {
				lbl_pSerial.setText("IMEI Number");
			}else {
				lbl_pSerial.setText("Serial Number");
			}
		} catch (Exception e) {
			lbl_pSerial.setText("Serial Number");
		}		
	}

	public void productTotalChange() {
		pPrice.textProperty().addListener((observable, oldValue, newValue) -> {
			setProductTotalPrice();
		});
		pQty.textProperty().addListener((observable, oldValue, newValue) -> {
			setProductTotalPrice();
		});	
	}

	public void addProduct(ActionEvent event) {

		String valid = ValidationFields.validateProduct(pType,pName, pBrand, pSerial, pPrice, pQty);
		if(valid.equals("OK")) {
			ProductBean bean = ValidationFields.getProductData( pType, pName, pBrand, pSerial, pPrice, pQty, ptotalPrice);
			addTable.getItems().add(bean);
			clearProduct(event);
		}else {
			Alert.makeAlert("Error!", valid);						
		}
		setPrice();

	}

	public void tableColumns() {
		typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
		pDescriptionCol.setCellValueFactory(new PropertyValueFactory<>("productDescription"));
		serialCol.setCellValueFactory(new PropertyValueFactory<>("serialNumber"));
		unitPriceCol.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
		noOfUnitsCol.setCellValueFactory(new PropertyValueFactory<>("noOfUnits"));
		totalPriceCol.setCellValueFactory(new PropertyValueFactory<>("totalCost"));		
	}

	public void eventProductDelete(ActionEvent event) {
		ObservableList<ProductBean> selectedProduct,allProduct;
		allProduct = addTable.getItems();
		selectedProduct = addTable.getSelectionModel().getSelectedItems();
		selectedProduct.forEach(allProduct::remove);
		setPrice();
	}


	public void clearProduct(ActionEvent event) {
		pName.clear();
		pBrand.clear();
		pSerial.clear();
		pPrice.clear();
		pQty.setText("1");
		ptotalPrice.clear();
		pType.getSelectionModel().clearSelection();		
	}

	public void setPrice() {
		double totalPrice = 0 ;
		for (ProductBean item : addTable.getItems()) {
			totalPrice = totalPrice + item.getTotalCost();
		}
		subTotal.setText(totalPrice+"");
		total.setText(totalPrice+"");
	}

	public void percentChange() {
		discount.textProperty().addListener((observable, oldValue, newValue) -> {
			discountTotal.setText(percentageTotal(newValue));
			setTotalPrice();
		});
		tax.textProperty().addListener((observable, oldValue, newValue) -> {
			taxTotal.setText(percentageTotal(newValue));
			setTotalPrice();
		});
	}

	public void setProductTotalPrice() {
		try {
			double unitPriceValue = Double.parseDouble(pPrice.getText());
			double noUnitsValue = Double.parseDouble(pQty.getText());	
			ptotalPrice.setText(df.format((unitPriceValue * noUnitsValue)));
		} catch (Exception e) {}
	}

	public String percentageTotal(String value) {
		double result;
		double subTotalValue;
		try {
			subTotalValue = Double.parseDouble(subTotal.getText());
			if(value.isEmpty() || value == null) {
				result=0.0;
			}else {
				result=Double.parseDouble(value);
			}	
			result = result / 100;
			return df.format(result * subTotalValue);
		} catch (Exception e) {}
		return df.format("0.00");		
	}

	public void setTotalPrice() {
		double subTotalValue =Double.parseDouble(subTotal.getText());
		double discountTotalValue =Double.parseDouble(discountTotal.getText());
		double taxTotalValue =Double.parseDouble(taxTotal.getText());	
		total.setText(df.format((subTotalValue - discountTotalValue) + taxTotalValue));
	}

	public void newBill(ActionEvent event) {
		invoice.setText("XE4290");
		cName.clear();cContact.clear();cAddress.clear();		
		clearProduct(event);
		for ( int i = 0; i<addTable.getItems().size(); i++) {
			addTable.getItems().clear();
		}
		subTotal.setText("0.0");discount.setText("0");discountTotal.setText("0.0");
		tax.setText("0");taxTotal.setText("0.0");total.setText("0.0");

	}
	public void saveBill(ActionEvent event) {		
		File file =new File("src/resources/INVOICE.pdf");
		boolean isGenrated =false;
		BillingBean bean = checkBilling(event);

		if(! (bean == null)) {
			isGenrated =InvoiceGenerator.makePDF(file, bean);
		}

		if(isGenrated) {
			FileChooser fileChooser = new FileChooser();
			fileChooser.setInitialFileName("Invoice_No_"+invoice.getText()+".pdf");
			FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("PDF files (*.pdf)", "*.pdf");
			fileChooser.getExtensionFilters().add(extFilter);
			fileChooser.setTitle("Save Invoice");
			File dest = fileChooser.showSaveDialog(null); 
			if (dest != null) {
				try {
					Files.copy(file.toPath(), dest.toPath());
				} catch (Exception ex) {
					Alert.makeAlert("File Not Found", "Falied Saving File");
				}
			}
		}else {
			Alert.makeAlert("File Not Found", "Falied Saving File");
		}


	}

	public void printBill(ActionEvent event) {
		
		File file =new File("src/resources/INVOICE.pdf");
		boolean isGenrated =false;
		BillingBean bean = checkBilling(event);

		if(! (bean == null)) {
			isGenrated =InvoiceGenerator.makePDF(file, bean);
		}

		if(isGenrated) {

			DocFlavor flavor = DocFlavor.SERVICE_FORMATTED.PAGEABLE;
			PrintRequestAttributeSet patts = new HashPrintRequestAttributeSet();
			patts.add(Sides.DUPLEX);
			PrintService[] ps = PrintServiceLookup.lookupPrintServices(flavor, patts);
			PrintService myService = null;
			
			if (ps.length == 0) {
				Alert.makeAlert("Error", "Printer Not Found");
			}else {

				for (PrintService printService : ps) {
					System.out.println(printService);
					if (printService.getName().contains("Hp")) {
						myService = printService;
						break;
					}
				}
				
				if (myService == null) {
					Alert.makeAlert("Error", "Printer Not Found");
				}else {
					FileInputStream fis;
					try {
						fis = new FileInputStream(file);
						Doc pdfDoc = new SimpleDoc(fis, DocFlavor.INPUT_STREAM.AUTOSENSE, null);
						DocPrintJob printJob = myService.createPrintJob();
						printJob.print(pdfDoc, new HashPrintRequestAttributeSet());
						fis.close();
					} catch (Exception e) {
						e.printStackTrace();
					}
				} // end Print Job
			}

		}else {
			Alert.makeAlert("Error", "Printer not found");
		}

	}

	public BillingBean checkBilling(ActionEvent event) {
		String valid = ValidationFields.validateBilling(addTable, cName, cContact, cAddress);
		BillingBean bean=null;
		if(valid.equals("OK")) {
			bean = ValidationFields.getBillingData(addTable, invoice, cName, cContact, 
					cAddress, subTotal, discount, discountTotal, tax, taxTotal, total);
			return bean;

		}else {
			Alert.makeAlert("Empty", valid);
		}
		return bean;
	}
	
	


}
