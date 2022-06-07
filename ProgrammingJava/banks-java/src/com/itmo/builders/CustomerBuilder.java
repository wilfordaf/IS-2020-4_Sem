package com.itmo.builders;

import com.itmo.models.Customer;
import com.itmo.tools.BanksException;

public class CustomerBuilder {
    private Customer customer;

    public CustomerBuilder() {
        customer = new Customer();
    }

    public CustomerBuilder setName(String firstName, String lastName) {
        customer.setFirstName(firstName);
        customer.setLastName(lastName);
        return this;
    }

    public CustomerBuilder setAddress(String address) {
        customer.setAddress(address);
        return this;
    }

    public CustomerBuilder setPassport(String passport) {
        customer.setPassport(passport);
        return this;
    }

    public Customer build() throws BanksException {
        if (customer.hasName()) {
            return customer;
        }

        Refresh();
        throw new BanksException("Unable to create customer without name");
    }

    private void Refresh() {
        customer = null;
    }
}
