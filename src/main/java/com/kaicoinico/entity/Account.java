package com.kaicoinico.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the account database table.
 * 
 */
@Entity
@Table(name="account")
@NamedQuery(name="Account.findAll", query="SELECT a FROM Account a")
public class Account implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="idf_account", unique=true, nullable=false)
	private int idfAccount;

	@Column(length=50)
	private String email;

	@Column(name="ethereum_addr", length=38)
	private String ethereumAddr;

	@Column(length=10)
	private String gencode;

	@Column(name="kaicoin_addr", length=38)
	private String kaicoinAddr;

	@Column(length=20)
	private String mobile;

	@Column(length=20)
	private String name1;

	@Column(length=20)
	private String name2;

	@Column(length=64)
	private String password;

	@Column(name="reg_date", insertable=false, updatable=false)
	private Timestamp regDate;

	@Column(length=1)
	private String state;

	//bi-directional many-to-one association to Remittance
	@OneToMany(mappedBy="account")
	private List<Remittance> remittances;

	public Account() {
	}

	public int getIdfAccount() {
		return this.idfAccount;
	}

	public void setIdfAccount(int idfAccount) {
		this.idfAccount = idfAccount;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEthereumAddr() {
		return this.ethereumAddr;
	}

	public void setEthereumAddr(String ethereumAddr) {
		this.ethereumAddr = ethereumAddr;
	}

	public String getGencode() {
		return this.gencode;
	}

	public void setGencode(String gencode) {
		this.gencode = gencode;
	}

	public String getKaicoinAddr() {
		return this.kaicoinAddr;
	}

	public void setKaicoinAddr(String kaicoinAddr) {
		this.kaicoinAddr = kaicoinAddr;
	}

	public String getMobile() {
		return this.mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getName1() {
		return this.name1;
	}

	public void setName1(String name1) {
		this.name1 = name1;
	}

	public String getName2() {
		return this.name2;
	}

	public void setName2(String name2) {
		this.name2 = name2;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Timestamp getRegDate() {
		return this.regDate;
	}

	public void setRegDate(Timestamp regDate) {
		this.regDate = regDate;
	}

	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public List<Remittance> getRemittances() {
		return this.remittances;
	}

	public void setRemittances(List<Remittance> remittances) {
		this.remittances = remittances;
	}

	public Remittance addRemittance(Remittance remittance) {
		getRemittances().add(remittance);
		remittance.setAccount(this);

		return remittance;
	}

	public Remittance removeRemittance(Remittance remittance) {
		getRemittances().remove(remittance);
		remittance.setAccount(null);

		return remittance;
	}

}