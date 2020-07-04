package com.kaicoinico.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the remittance database table.
 * 
 */
@Entity
@Table(name="remittance")
@NamedQuery(name="Remittance.findAll", query="SELECT r FROM Remittance r")
public class Remittance implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="idf_remittance", unique=true, nullable=false)
	private int idfRemittance;

	private double amount;

	@Column(length=200)
	private String memo;

	@Column(name="reg_date", insertable=false, updatable=false)
	private Timestamp regDate;

	@Column(name="to_addr", length=38)
	private String toAddr;

	//bi-directional many-to-one association to Account
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="idf_account", nullable=false)
	private Account account;

	public Remittance() {
	}

	public int getIdfRemittance() {
		return this.idfRemittance;
	}

	public void setIdfRemittance(int idfRemittance) {
		this.idfRemittance = idfRemittance;
	}

	public double getAmount() {
		return this.amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getMemo() {
		return this.memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Timestamp getRegDate() {
		return this.regDate;
	}

	public void setRegDate(Timestamp regDate) {
		this.regDate = regDate;
	}

	public String getToAddr() {
		return this.toAddr;
	}

	public void setToAddr(String toAddr) {
		this.toAddr = toAddr;
	}

	public Account getAccount() {
		return this.account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

}