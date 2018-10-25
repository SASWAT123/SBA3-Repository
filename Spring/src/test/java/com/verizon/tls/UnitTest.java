package com.verizon.tls;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.verizon.tls.Model.Customer;
import com.verizon.tls.Model.Plans;
import com.verizon.tls.Service.CustomerService;
import com.verizon.tls.Service.PlansService;
import com.verizon.tls.TestUtil.TestUtil;
import com.verizon.tls.restApi.TaranginiApi;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = TaranginiApi.class)
public class UnitTest {
	
	private MockMvc mockMvc;
	
	@Autowired
	private WebApplicationContext webApplicationContext;
	
	@MockBean
	private CustomerService customerServiceMock;
	
	@MockBean
	private PlansService plansServiceMock;
	
	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	@After
	public void tearDown() {
		mockMvc = null;
	}
	
	@Test
	public void testGetAllPlans() throws Exception {
		assertThat(this.plansServiceMock).isNotNull();

		List<Plans> planList = new ArrayList<>();
		planList.add(new Plans());

		when(plansServiceMock.getAllPlans()).thenReturn(planList);

		mockMvc.perform(get("/Tarangini")).andExpect(status().isOk()).andDo(print());

	}
	
	@Test
	public void testAddCustomer() throws Exception {
		assertThat(this.customerServiceMock).isNotNull();

		Customer emp = new Customer();
		
		Customer empAdded = new Customer();

		System.out.println(emp);
		

		when(customerServiceMock.addCustomer(Mockito.any(Customer.class))).thenReturn(empAdded);

		mockMvc.perform(post("/Tarangini").contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(emp))).andDo(print()).andExpect(status().isOk())
				.andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8));

	}
	
	@Test
	public void testGetCustomerById() throws Exception {
		assertThat(this.customerServiceMock).isNotNull();
		int orderNumber = 13;
	
		Customer empAdded = new Customer();

		when(customerServiceMock.getCustomerByOrderNumber(orderNumber)).thenReturn(empAdded);

		mockMvc.perform(get("/Tarangini/13")).andExpect(status().isOk()).andDo(print());

	}
	
	@Test
	public void testGetAllPlanss() throws Exception {
		assertThat(this.plansServiceMock).isNotNull();

		int charge=1000;
		int speed=100;
		int usage=1000;
		
		List<Plans> plansList = new ArrayList<>();
		plansList.add(new Plans());

		when(plansServiceMock.findAllByChargePerMonth(charge)).thenReturn(plansList);
		when(plansServiceMock.findAllByMaxSpeed(speed)).thenReturn(plansList);
		when(plansServiceMock.findAllByMaxUsage(usage)).thenReturn(plansList);
		
		
		mockMvc.perform(get("/Tarangini/maxSpeed/100")).andExpect(status().isOk()).andDo(print());

	}
}
