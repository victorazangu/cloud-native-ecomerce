package io.shemi.ecommerce.service;

import io.shemi.ecommerce.dto.request.CreateCustomerRequest;
import io.shemi.ecommerce.dto.request.UpdateCustomerRequest;
import io.shemi.ecommerce.dto.response.CustomerResponse;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface CustomerService {
  public CustomerResponse createCustomer(CreateCustomerRequest customer);
  public List<CustomerResponse> findAllCustomers();
  public CustomerResponse findCustomerById(String id);
  public CustomerResponse findCustomerByEmail(String email);
  public CustomerResponse updateCustomer(String id,UpdateCustomerRequest updateCustomerRequest);
  public String deleteCustomerById(String id);
}
