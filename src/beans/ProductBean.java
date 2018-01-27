package beans;

import utils.CamelCase;

public class ProductBean {
	
	private String type;
	private String productDescription;
	private String serialNumber;
	private double unitPrice;
	private int noOfUnits;
	private double totalCost;
	

	public ProductBean() {
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getProductDescription() {
		return productDescription;
	}

	public void setProductDescription(String productDescription) {
		this.productDescription = CamelCase.toCamelCase(productDescription);
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public double getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(double unitPrice) {
		this.unitPrice = unitPrice;
	}

	public int getNoOfUnits() {
		return noOfUnits;
	}

	public void setNoOfUnits(int noOfUnits) {
		this.noOfUnits = noOfUnits;
	}

	public double getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(double totalCost) {
		this.totalCost = totalCost;
	}

	@Override
	public String toString() {
		return "ProductBean [type=" + type + ", productDescription=" + productDescription + ", serialNumber="
				+ serialNumber + ", unitPrice=" + unitPrice + ", noOfUnits=" + noOfUnits + ", totalCost=" + totalCost
				+ "]";
	}
	
	
}
