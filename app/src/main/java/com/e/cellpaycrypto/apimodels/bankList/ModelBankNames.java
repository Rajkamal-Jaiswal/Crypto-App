package com.e.cellpaycrypto.apimodels.bankList;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ModelBankNames{

	@SerializedName("bank_list")
	private List<BankListItem> bankList;

	@SerializedName("resultCode")
	private String resultCode;

	@SerializedName("message")
	private String message;

	public List<BankListItem> getBankList(){
		return bankList;
	}

	public String getResultCode(){
		return resultCode;
	}

	public String getMessage(){
		return message;
	}
}