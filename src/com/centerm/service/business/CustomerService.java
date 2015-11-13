package com.centerm.service.business;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.centerm.dao.CustomerDao;
import com.centerm.entity.Customer;

@Service("customerService")
public class CustomerService {
	
	@Resource(name = "customerDao")
	private CustomerDao customerDao;
	
	@Transactional(readOnly = true,rollbackFor = Exception.class)
	public Customer findByAccount(String account){
		return customerDao.findByAccount(","+account+",");
	}
	
	@Transactional(readOnly = true,rollbackFor = Exception.class)
	public Customer findByIdnum(String idnum){
		return customerDao.findByIdnum(idnum);
	}
	
	@Transactional(readOnly = false,rollbackFor = Exception.class)
	public void addCustomerByBusinessRecord(String data) {
		JSONArray dataArray = JSONArray.fromObject(data);
		if(dataArray.size() == 0){
			return;
		}
		
		JSONObject json = (JSONObject) dataArray.get(0);
		// 有客户信息,name姓名，ID_num证件号，account账号
		if(json.get("name") != null && json.get("ID_num") != null){
			String name = json.getString("name");
			String ID_num = json.getString("ID_num");
			String account = "";
			if(json.get("account") != null){
				// 防止空数据
				String tmp = json.getString("account").trim();
				if(!tmp.isEmpty()){
					account = tmp+",";
				}
			}
			Customer customer = customerDao.findByIdnum(ID_num);
			// 过滤重复的客户信息
			if(customer == null){
				customer = new Customer();
				customer.setName(name);
				customer.setIdnum(ID_num);
				customer.setAccount(","+account);
				customerDao.save(customer);
				
			}else if(!account.isEmpty()){
				String customer_account = customer.getAccount().trim();
				// 过滤重复的账号
				boolean isExist = isAccountExist(account,customer_account);
				if(!isExist){
					customer.setAccount(customer_account + account);
					customerDao.update(customer);
				}
			}
		}
	}
	
	private boolean isAccountExist(String account,String customer_account){
		if(customer_account.indexOf(account) >= 0){
			return true;
		}
		return false;
	}
}
 