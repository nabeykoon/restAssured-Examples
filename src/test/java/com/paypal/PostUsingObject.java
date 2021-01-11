package com.paypal;

import com.paypal.base.BaseClass;
import com.paypal.payment.pojo.*;
import io.restassured.http.ContentType;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;

public class PostUsingObject extends BaseClass {

    static String paymentId;

    @Test
    public void createPayment() {

        Redirect_urls redirect_urls = new Redirect_urls ();
        redirect_urls.setCancel_url ("https://example.com/cancel");
        redirect_urls.setReturn_url ("https://example.com/return");

        Details details = new Details ();
        details.setHandling_fee ("1.00");
        details.setInsurance ("0.01");
        details.setShipping ("0.03");
        details.setShipping_discount ("-1.00");
        details.setSubtotal ("30.00");
        details.setTax ("0.07");

        //Create Amount
        Amount amount = new Amount ();
        amount.setCurrency ("USD");
        amount.setTotal ("30.11");
        amount.setDetails (details);

        //Create Shipping address
        Shipping_address shipping_address = new Shipping_address ();
        shipping_address.setCity ("San Jose");
        shipping_address.setCountry_code ("US");
        shipping_address.setLine1 ("4th Floor");
        shipping_address.setLine2 ("Unit #34");
        shipping_address.setPhone ("011862212345678");
        shipping_address.setPostal_code ("95131");
        shipping_address.setRecipient_name ("Brian Robinson");
        shipping_address.setState ("CA");

        //Create Items

        Items item1 = new Items ();
        item1.setCurrency ("USD");
        item1.setDescription ("Brown hat.");
        item1.setName ("hat");
        item1.setPrice ("3");
        item1.setQuantity ("5");
        item1.setSku ("1");
        item1.setTax ("0.01");

        Items item2 = new Items ();
        item2.setCurrency ("USD");
        item2.setDescription ("Black handbag.");
        item2.setName ("handbag");
        item2.setPrice ("15");
        item2.setQuantity ("1");
        item2.setSku ("product34");
        item2.setTax ("0.02");

        List<Items> items = new ArrayList<Items> ();
        items.add (item1);
        items.add (item2);

        //Create item_list

        Item_list item_list = new Item_list ();
        item_list.setItems (items);
        item_list.setShipping_address (shipping_address);

        //Create payment options
        Payment_options payment_options = new Payment_options ();
        payment_options.setAllowed_payment_method ("INSTANT_FUNDING_SOURCE");

        //Create transaction
        Transactions transaction1 = new Transactions ();
        transaction1.setAmount (amount);
        transaction1.setCustom ("EBAY_EMS_90048630024435");
        transaction1.setDescription ("The payment transaction description.");
        transaction1.setInvoice_number ("48787589673");
        transaction1.setItem_list (item_list);
        transaction1.setPayment_options (payment_options);
        transaction1.setSoft_descriptor ("ECHI5786786");

        List<Transactions> transactionsList= new ArrayList<Transactions> ();
        transactionsList.add (transaction1);

        //Create Payer
        Payer payer = new Payer ();
        payer.setPayment_method ("paypal");

        //Create PostObject

        PostObject postObject = new PostObject ();
        postObject.setIntent ("sale");
        postObject.setNote_to_payer ("Contact us for any questions on your order.");
        postObject.setPayer (payer);
        postObject.setRedirect_urls (redirect_urls);
        postObject.setTransactions (transactionsList);

        paymentId = given ()
                .contentType (ContentType.JSON)
                .auth ()
                .oauth2 (accessToken)
                .when ()
                .body (postObject)
                .post ("/payments/payment")
                .then ()
                .log ()
                .all ()
                .extract ()
                .path ("id");
    }

    @Test
    public void getPaymentDetails(){

        given ()
                .auth ()
                .oauth2 (accessToken)
                .when ()
                .get ("/payments/payment/" + paymentId)
                .then ()
                .log ()
                .all ();

    }

}
