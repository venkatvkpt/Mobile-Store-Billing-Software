package utils;

import beans.BillingBean;
import beans.ProductBean;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ValidationFields {

	public static String validateProduct(ComboBox<String> pType,TextField pName,TextField pBrand,TextField pSerial,
			TextField pPrice,TextField pQty) {
		if(pType.getSelectionModel().isEmpty()) {
			return "Product Type is Not Selected.";
		}
		else if(pName.getText().isEmpty()) {
			return "Product Name is Not Entered.";
		}else if(pBrand.getText().isEmpty()) {
			return "Brand Name is Not Entered.";
		}else if(pSerial.getText().isEmpty()) {
			return "Identification Number is Not Entered.";
		}else if(pPrice.getText().isEmpty()) {
			return "Unit Price is Not Entered.";
		}else if(pQty.getText().isEmpty()) {
			return "Number of Units is Not Entered.";
		}else {
			return "OK";
		}

	}

	public static ProductBean getProductData(ComboBox<String> pType,TextField pName,TextField pBrand,TextField pSerial,
			TextField pPrice,TextField pQty,TextField ptotalPrice) {
		ProductBean bean =new ProductBean();
		bean.setType(pType.getSelectionModel().getSelectedItem());;
		bean.setProductDescription(pBrand.getText()+" "+pName.getText());
		bean.setSerialNumber(pSerial.getText().toUpperCase());
		bean.setUnitPrice(Double.parseDouble(pPrice.getText()));
		bean.setNoOfUnits(Integer.parseInt(pQty.getText()));
		bean.setTotalCost(Double.parseDouble(ptotalPrice.getText()));		
		return bean;	
	}

	public static String validateBilling(TableView<ProductBean> table,TextField cName,TextField cContact,TextArea cAddress) {
		ObservableList<ProductBean> items = table.getItems();
		if (items.isEmpty()) {
			return "No Products Found!";
		}else if(cName.getText().isEmpty()){
			return "Customer Name is Not Entered.";
		}else if(cContact.getText().isEmpty()){
			return "Customer Contact is Not Entered.";
		}else if(cAddress.getText().isEmpty()){
			return "Customer Address is Not Entered.";
		}else {
			return "OK";
		}

	}
	public static BillingBean getBillingData(TableView<ProductBean> table,TextField invoice,TextField cName,
			TextField cContact,TextArea cAddress,TextField subTotal,TextField discount,
			TextField discountTotal,TextField tax,TextField taxTotal,TextField total) {
		BillingBean bean = new BillingBean();
		
		bean.setInvoiceNumber(invoice.getText());
		bean.setCustomerName(cName.getText());
		bean.setCustomerContact(cContact.getText());
		bean.setCustomerAddress(cAddress.getText());
		bean.setSubTotalPrice(subTotal.getText());
		bean.setDiscount(discount.getText());
		bean.setDiscountPrice(discountTotal.getText());
		bean.setTax(tax.getText());
		bean.setTaxPrice(taxTotal.getText());
		bean.setTotalPrice(total.getText());
		bean.setProducts(table.getItems());
		
		return bean;	
	}
}
