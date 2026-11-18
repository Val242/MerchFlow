package com.merchflow.customer;

import com.merchflow.common.exception.*;

import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CustomerService {

    private final CustomerRepository repository;

    public CustomerService(CustomerRepository repository) {
        this.repository = repository;
    }

    public CustomerDtos.Response create(
            CustomerDtos.Request request) {

        if (repository.existsByEmailIgnoreCase(request.email())) {
            throw new BadRequestException(
                "Email already registered"
            );
        }

        Customer customer = new Customer(
            request.fullName(),
            request.email(),
            request.phone(),
            request.address()
        );

        return map(repository.save(customer));
    }

    public List<CustomerDtos.Response> findAll() {
        return repository.findAll()
            .stream()
            .map(this::map)
            .toList();
    }

    public CustomerDtos.Response find(Long id) {
        Customer customer = repository.findById(id)
            .orElseThrow(() ->
                new ResourceNotFoundException("Customer not found")
            );

        return map(customer);
    }

    private CustomerDtos.Response map(Customer customer) {
        return new CustomerDtos.Response(
            customer.getId(),
            customer.getFullName(),
            customer.getEmail(),
            customer.getPhone(),
            customer.getAddress()
        );
    }
}