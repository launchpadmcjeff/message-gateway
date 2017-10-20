package com.robowebi.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.Test;

public class CustomerTest {

	private static final String CUSTOMER_DAT = "target/customer.dat";
	Logger log = Logger.getLogger("CustomerTest");

	@Test
	public void testCustomerSerializationAndDeserialization() throws IOException, ClassNotFoundException {
		Customer customer = new Customer();
		customer.setFirstName("Fred");
		customer.setLastName("Flintstone");
		customer.setMiddleName("J");
		FileOutputStream fout = new FileOutputStream(CUSTOMER_DAT);
		ObjectOutputStream oos = new ObjectOutputStream(fout);
		oos.writeObject(customer);
		oos.close();
		log.log(Level.INFO, "event=serializedCustomer data=" + customer.toString());

		FileInputStream fin = new FileInputStream(CUSTOMER_DAT);
		ObjectInputStream ois = new ObjectInputStream(fin);
		Customer deserializedCustomer = (Customer) ois.readObject();
		ois.close();

		assertThat(deserializedCustomer).isEqualTo(customer);
	}
}
