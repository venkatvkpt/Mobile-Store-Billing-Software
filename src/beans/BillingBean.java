package beans;

import java.util.List;

import utils.CamelCase;

public class BillingBean {
	
	private String invoiceNumber;
	private String customerName;
	private String customerContact;
	private String customerAddress;
	private String subTotalPrice;
	private String discount;
	private String discountPrice;
	private String tax;
	private String taxPrice;
	private String totalPrice;
	private List<ProductBean> products;
	
	public String getInvoiceNumber() {
		return invoiceNumber;
	}
	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = CamelCase.toCamelCase(customerName);
	}
	public String getCustomerContact() {
		return customerContact;
	}
	public void setCustomerContact(String customerContact) {
		this.customerContact = customerContact;
	}
	public String getCustomerAddress() {
		return customerAddress;
	}
	public void setCustomerAddress(String customerAddress) {
		this.customerAddress = CamelCase.toCamelCase(customerAddress);
	}
	public String getSubTotalPrice() {
		return subTotalPrice;
	}
	public void setSubTotalPrice(String subTotalPrice) {
		this.subTotalPrice = subTotalPrice;
	}
	public String getDiscount() {
		return discount;
	}
	public void setDiscount(String discount) {
		this.discount = discount;
	}
	public String getDiscountPrice() {
		return discountPrice;
	}
	public void setDiscountPrice(String discountPrice) {
		this.discountPrice = discountPrice;
	}
	public String getTax() {
		return tax;
	}
	public void setTax(String tax) {
		this.tax = tax;
	}
	public String getTaxPrice() {
		return taxPrice;
	}
	public void setTaxPrice(String taxPrice) {
		this.taxPrice = taxPrice;
	}
	public String getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(String totalPrice) {
		this.totalPrice = totalPrice;
	}
	public List<ProductBean> getProducts() {
		return products;
	}
	public void setProducts(List<ProductBean> products) {
		this.products = products;
	}
	@Override
	public String toString() {
		return "BillingBean [invoiceNumber=" + invoiceNumber + ", customerName=" + customerName + ", customerContact="
				+ customerContact + ", customerAddress=" + customerAddress + ", subTotalPrice=" + subTotalPrice
				+ ", discount=" + discount + ", discountPrice=" + discountPrice + ", tax=" + tax + ", taxPrice="
				+ taxPrice + ", totalPrice=" + totalPrice + ", products=" + products + "]";
	}
	
	
	
}
