package com.czbank.market.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/** 
 * @author      qiuqingqing 
 * @create      2017-9-22
 */  
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "MARKET_INFO")
public class MarketTask {
	@XmlElement(name = "LOAN_SEQ_ID")
	private String loanSeqId;
	@XmlElement(name = "BUSINESS_TYPE")
	private String businessType;
	@XmlElement(name = "IOU_NUM")
	private String iouNum;
	@XmlElement(name = "INTEREST_AMT")
	private String interestAmt;
	@XmlElement(name = "OPERATION_BRANCH")
	private String operationBranch;
	@XmlElement(name = "CLIENT_MANAGER")
	private String clientManager;
	@XmlElement(name = "AFTER_DELI_MANAGER")
	private String afterDeliManager;
	@XmlElement(name = "PROJECT_NAME")
	private String projectName;
	@XmlElement(name = "CONTRACT_BALANCE")
	private String contractBalance;
	@XmlElement(name = "VALUE_DATE")
	private String valueDate;
	@XmlElement(name = "PAY_RATE")
	private String payRate;
	@XmlElement(name = "STOCK_CODE")
	private String stockCode;
	@XmlElement(name = "STOCK_SNAME")
	private String stockSname;
	@XmlElement(name = "STOCK_CIRCUL_FLAG")
	private String stockCirculFlag;
	@XmlElement(name = "MATURITY_DATE")
	private String maturityDate;
	@XmlElement(name = "BEGIN_STOCK_NUM")
	private String beginStockNum;
	@XmlElement(name = "APPEND_STOCK_NUM")
	private String appendStockNum;
	@XmlElement(name = "BOND_BALANCE")
	private String bondBalance;
	@XmlElement(name = "OTHER_RISK_RELE_AMT")
	private String otherRiskReleAmt;
	@XmlElement(name = "APPEND_RISK_RELE_AMT")
	private String appendRiskReleAmt;
	@XmlElement(name = "WARN_LINE_LIMIT")
	private String warnLineLimit;
	@XmlElement(name = "WARN_RULE_LIMIT")
	private String warnRuleLimit;
	@XmlElement(name = "WARN_LINE_UNLOCK")
	private String warnLineUnlock;
	@XmlElement(name = "WARN_RULE_UNLOCK")
	private String warnRuleUnlock;
	@XmlElement(name = "LIQUID_LINE")
	private String liquidLine;
	@XmlElement(name = "LIQUID_RULE")
	private String liquidRule;
	@XmlElement(name = "BEGIN_PLDG_RATE")
	private String beginPldgRate;
	@XmlElement(name = "MAINTAIN_RATE")
	private String maintainRate;
	@XmlElement(name = "NET_ASSETS_WARNLINE")
	private String netAssetsWarnline;
	
	
	public String getOperationBranch() {
		return operationBranch;
	}


	public void setOperationBranch(String operationBranch) {
		this.operationBranch = operationBranch;
	}


	public String getClientManager() {
		return clientManager;
	}


	public void setClientManager(String clientManager) {
		this.clientManager = clientManager;
	}


	public String getAfterDeliManager() {
		return afterDeliManager;
	}


	public void setAfterDeliManager(String afterDeliManager) {
		this.afterDeliManager = afterDeliManager;
	}


	public String getProjectName() {
		return projectName;
	}


	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}


	public String getContractBalance() {
		return contractBalance;
	}


	public void setContractBalance(String contractBalance) {
		this.contractBalance = contractBalance;
	}


	public String getValueDate() {
		return valueDate;
	}


	public void setValueDate(String valueDate) {
		this.valueDate = valueDate;
	}


	public String getPayRate() {
		return payRate;
	}


	public void setPayRate(String payRate) {
		this.payRate = payRate;
	}


	public String getStockCode() {
		return stockCode;
	}


	public void setStockCode(String stockCode) {
		this.stockCode = stockCode;
	}


	public String getStockSname() {
		return stockSname;
	}


	public void setStockSname(String stockSname) {
		this.stockSname = stockSname;
	}


	public String getStockCirculFlag() {
		return stockCirculFlag;
	}


	public void setStockCirculFlag(String stockCirculFlag) {
		this.stockCirculFlag = stockCirculFlag;
	}


	public String getMaturityDate() {
		return maturityDate;
	}


	public void setMaturityDate(String maturityDate) {
		this.maturityDate = maturityDate;
	}


	public String getBeginStockNum() {
		return beginStockNum;
	}


	public void setBeginStockNum(String beginStockNum) {
		this.beginStockNum = beginStockNum;
	}


	public String getAppendStockNum() {
		return appendStockNum;
	}


	public void setAppendStockNum(String appendStockNum) {
		this.appendStockNum = appendStockNum;
	}


	public String getBondBalance() {
		return bondBalance;
	}


	public void setBondBalance(String bondBalance) {
		this.bondBalance = bondBalance;
	}


	public String getOtherRiskReleAmt() {
		return otherRiskReleAmt;
	}


	public void setOtherRiskReleAmt(String otherRiskReleAmt) {
		this.otherRiskReleAmt = otherRiskReleAmt;
	}


	public String getAppendRiskReleAmt() {
		return appendRiskReleAmt;
	}


	public void setAppendRiskReleAmt(String appendRiskReleAmt) {
		this.appendRiskReleAmt = appendRiskReleAmt;
	}


	public String getWarnLineLimit() {
		return warnLineLimit;
	}


	public void setWarnLineLimit(String warnLineLimit) {
		this.warnLineLimit = warnLineLimit;
	}


	public String getWarnRuleLimit() {
		return warnRuleLimit;
	}


	public void setWarnRuleLimit(String warnRuleLimit) {
		this.warnRuleLimit = warnRuleLimit;
	}


	public String getWarnLineUnlock() {
		return warnLineUnlock;
	}


	public void setWarnLineUnlock(String warnLineUnlock) {
		this.warnLineUnlock = warnLineUnlock;
	}


	public String getWarnRuleUnlock() {
		return warnRuleUnlock;
	}


	public void setWarnRuleUnlock(String warnRuleUnlock) {
		this.warnRuleUnlock = warnRuleUnlock;
	}


	public String getLiquidLine() {
		return liquidLine;
	}


	public void setLiquidLine(String liquidLine) {
		this.liquidLine = liquidLine;
	}


	public String getLiquidRule() {
		return liquidRule;
	}


	public void setLiquidRule(String liquidRule) {
		this.liquidRule = liquidRule;
	}


	public String getBeginPldgRate() {
		return beginPldgRate;
	}


	public void setBeginPldgRate(String beginPldgRate) {
		this.beginPldgRate = beginPldgRate;
	}


	public String getMaintainRate() {
		return maintainRate;
	}


	public void setMaintainRate(String maintainRate) {
		this.maintainRate = maintainRate;
	}


	public String getNetAssetsWarnline() {
		return netAssetsWarnline;
	}


	public void setNetAssetsWarnline(String netAssetsWarnline) {
		this.netAssetsWarnline = netAssetsWarnline;
	}


	public String getLoanSeqId() {
		return loanSeqId;
	}


	public void setLoanSeqId(String loanSeqId) {
		this.loanSeqId = loanSeqId;
	}


	public String getBusinessType() {
		return businessType;
	}


	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}


	public String getIouNum() {
		return iouNum;
	}


	public void setIouNum(String iouNum) {
		this.iouNum = iouNum;
	}

	/**
	 * @return the interestAmt
	 */
	public String getInterestAmt() {
		return interestAmt;
	}


	/**
	 * @param interestAmt the interestAmt to set
	 */
	public void setInterestAmt(String interestAmt) {
		this.interestAmt = interestAmt;
	}
	
	/*@Override
	public String toString(){
		return "market_info [loan_seq_id=" + loanSeqId + ", business_type=" + businessType + ", iou_num=" + iouNum + "]";
	}*/

}
