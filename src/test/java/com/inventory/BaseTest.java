package com.inventory;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.io.IOException;
import java.time.LocalDate;

import javax.servlet.Filter;

import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithSecurityContextTestExecutionListener;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inventory.config.InventoryManagementApplication;
import com.inventory.domain.Stock;

@SpringBootTest
@ContextConfiguration(classes = {InventoryManagementApplication.class})
@TestExecutionListeners(listeners = {WithSecurityContextTestExecutionListener.class, DirtiesContextTestExecutionListener.class})
@Rollback
public abstract class BaseTest extends AbstractJUnit4SpringContextTests {

  protected static final String ADMIN_TOKEN =
      "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImtpZCI6Ik9EY3hPVEJDT1VWR09UVXhPREpETmpreU9VRkdRelZHUlVVNU0wRkZPRU15T0RFMk1qZ3hPQSJ9.eyJ1c2VybmFtZSI6IkFkbWluIFVzZXIiLCJ1c2VyaWQiOiJ1c3JfMSIsInJvbGUiOiJzeXMtYWRtaW4ifQ.tOvW4N2pAT0KF8JeTEdLJ-wHt1VVyGasBrmN-PFl_Gv_rURTuhd2BNVbIKArcSlw9diNeArMiD5xUzefzKGfo0OhYKS0PEWUVRh10TQtDLakh6-Mz_ut8ugb8D8H41ZeGWPV_060OL9tQ6Bq1d_3lV6_TPu4ty9VlNyc_48or35xmbLo_CvbZr4LwVIDB3li3OgGHOhUjRx1hEApi5NOlAX1Uz6BEZvT_E5jby3eU8r4og4v3W_2McFWfJlJeRdfj-dGZGd_hrDjSFXVPlfClbPVNcSPFDrN3SwvTdb5KVBHW-8dUc4I4AK5fnFQQe3I_ct4T8SEYwoAZl9Uc0sBeA\r\n" + 
      "";
  private static final String STOCKS_URI = "/stocks";
  protected MockMvc mockMvc;
  @Autowired
  protected ObjectMapper objectMapper;
  @Autowired
  protected WebApplicationContext context;
  @Autowired
  private Filter springSecurityFilterChain;

  public Stock stock;
  
  @Before
  public void beforeClass() throws Exception {
    mockMvc = MockMvcBuilders.webAppContextSetup(context)
        .addFilters(springSecurityFilterChain)
        .build();
    
      stock = new Stock();
	  stock.setName("first");
	  stock.setAmountPerDay(10);
	  stock.setQuantity(1000);
	  stock.setEntryDate(LocalDate.now());
  }

  
  protected HttpHeaders headersWithToken(final String jwtToken) {
    HttpHeaders adminHeaders = new HttpHeaders();
    adminHeaders.add("Content-Type", MediaType.APPLICATION_JSON.toString());
    adminHeaders.add("Authorization", jwtToken);
    return adminHeaders;
  }

  private ResultActions performGetOperation(final String uri,
                                            final HttpHeaders headers,
                                            final String... uriParams) throws Exception {
    return mockMvc.perform(
        get(uri, (Object[]) uriParams)
            .headers(headers)
    );
  }

  /**
   * For post operation
   * @param uri
   * @param dto
   * @param headers
   * @param uriParams
   * @return
   * @throws Exception
   */
  private <T> ResultActions performPostOperation(final String uri,
                                                 final T dto,
                                                 final HttpHeaders headers,
                                                 final String... uriParams) throws Exception {
    return mockMvc.perform(
        post(uri, (Object[]) uriParams)
            .content(objectMapper.writeValueAsString(dto))
            .headers(headers)
    );
  }

 

  protected <T> T extractDtoFromMockResult(final MvcResult mvcResult, final Class<T> clazz) throws IOException {
    String responseBody = mvcResult.getResponse().getContentAsString();
    return objectMapper.readValue(responseBody, clazz);
  }
  
  
  protected ResultActions getAllStocks(final String token) throws Exception {
	    return performGetOperation(STOCKS_URI, headersWithToken(token));
	  }
  
  protected ResultActions addStock(final String token, Stock stock) throws Exception {
	  return performPostOperation(STOCKS_URI, stock, headersWithToken(token));
  }
  
  protected ResultActions updateStock(final String token, Stock stock, String id) throws Exception{
	  
	  return performPostOperation(STOCKS_URI+"/"+1, stock, headersWithToken(token));
  }
  
}
